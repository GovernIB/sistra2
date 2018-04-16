package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValor;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * JFuenteDatos
 */
@Entity
@Table(name = "STG_FUEDAT", uniqueConstraints = @UniqueConstraint(columnNames = "FUE_IDENT"))
public class JFuenteDatos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FUEDAT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FUEDAT_SEQ", sequenceName = "STG_FUEDAT_SEQ")
	@Column(name = "FUE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "FUE_AMBITO", nullable = false, length = 1)
	private String ambito;

	@Column(name = "FUE_IDENT", unique = true, nullable = false, length = 20)
	private String identificador;

	@Column(name = "FUE_DESCR", nullable = false)
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUE_CODENT", nullable = true)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUE_CODARE", nullable = true)
	private JArea area;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fuenteDatos", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orden")
	private Set<JCampoFuenteDatos> campos = new HashSet<>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fuenteDatos", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JFilasFuenteDatos> filas = new HashSet<>(0);

	public JFuenteDatos() {
		// Constructor publico
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the entidad
	 */
	public JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the area
	 */
	public JArea getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final JArea area) {
		this.area = area;
	}

	/**
	 * @return the campos
	 */
	public Set<JCampoFuenteDatos> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final Set<JCampoFuenteDatos> campos) {
		this.campos = campos;
	}

	/**
	 * @return the filas
	 */
	public Set<JFilasFuenteDatos> getFilas() {
		return filas;
	}

	/**
	 * @param filas
	 *            the filas to set
	 */
	public void setFilas(final Set<JFilasFuenteDatos> filas) {
		this.filas = filas;
	}

	public FuenteDatos toModel() {
		final FuenteDatos fuenteDato = new FuenteDatos();
		fuenteDato.setCodigo(this.getCodigo());
		fuenteDato.setAmbito(TypeAmbito.fromString(this.getAmbito()));
		fuenteDato.setIdentificador(this.getIdentificador());
		fuenteDato.setDescripcion(this.getDescripcion());
		if (this.getCampos() != null) {
			final List<FuenteDatosCampo> lcampos = new ArrayList<>();
			for (final JCampoFuenteDatos campo : this.getCampos()) {
				lcampos.add(campo.toModel());
			}
			fuenteDato.setCampos(lcampos);
		}
		return fuenteDato;
	}

	/**
	 * Genera el objeto FuenteDatosValores. Intenta no pasar por los toModel para
	 * hacerlo más eficiente.
	 *
	 * @return FuenteDatos con Valores.
	 */
	public FuenteDatosValores toModelV() {
		final FuenteDatosValores fuenteDato = new FuenteDatosValores();
		fuenteDato.setCodigo(this.getCodigo());

		// Recorremos las filas
		if (this.getFilas() != null) {
			final List<FuenteFila> lfilas = new ArrayList<>();
			for (final JFilasFuenteDatos jfila : this.getFilas()) {
				final FuenteFila fila = new FuenteFila();
				fila.setId(jfila.getCodigo());

				// Por cada fila, recorremos sus valores.
				if (jfila.getValoresFuenteDatos() != null) {
					final List<FuenteDatosValor> valores = new ArrayList<>();
					for (final JValorFuenteDatos jvalor : jfila.getValoresFuenteDatos()) {

						final FuenteDatosValor valor = new FuenteDatosValor();
						valor.setId(jvalor.getCodigo());
						valor.setIdCampo(jvalor.getCampoFuenteDatos().getIdCampo());
						valor.setOrdenCampo(jvalor.getCampoFuenteDatos().getOrden());
						valor.setValor(jvalor.getValor());
						valores.add(valor);

					}

					fila.setDatos(valores);
					fila.sortValores();
				}
				lfilas.add(fila);
			}
			fuenteDato.setFilas(lfilas);
		}
		return fuenteDato;
	}

	public void fromModel(final FuenteDatos fuenteDato) {
		if (fuenteDato != null) {
			this.setCodigo(fuenteDato.getCodigo());
			this.setAmbito(fuenteDato.getAmbito().toString());
			this.setIdentificador(fuenteDato.getIdentificador());
			this.setDescripcion(fuenteDato.getDescripcion());
			campos = new HashSet<>(0);
			if (fuenteDato.getCampos() != null) {
				for (final FuenteDatosCampo campo : fuenteDato.getCampos()) {
					final JCampoFuenteDatos jcampoFuenteDatos = new JCampoFuenteDatos();
					jcampoFuenteDatos.fromModel(campo);
					jcampoFuenteDatos.setFuenteDatos(this);
					campos.add(jcampoFuenteDatos);
				}
			}
		}
	}

	public void fromModel(final FuenteDatosValores fuenteDato) {
		if (fuenteDato != null) {
			this.setCodigo(fuenteDato.getCodigo());
			filas = new HashSet<>(0);
			if (fuenteDato.getFilas() != null) {
				for (final FuenteFila fila : fuenteDato.getFilas()) {
					final JFilasFuenteDatos jcampoFuenteDatos = new JFilasFuenteDatos();
					jcampoFuenteDatos.fromModel(fila);
					jcampoFuenteDatos.setFuenteDatos(this);
					filas.add(jcampoFuenteDatos);
				}
			}
		}
	}

	/**
	 * Hace merge de las traducciones comparando lo almacenado con el modelo.
	 *
	 * @param jFuenteDato
	 *            datos almacenados
	 * @param mFuenteDato
	 *            datos modelo
	 * @return datos mergeados
	 */
	public static JFuenteDatos mergeModel(final JFuenteDatos jFuenteDato, final FuenteDatos mFuenteDato) {
		JFuenteDatos jModel = new JFuenteDatos();
		if (jFuenteDato == null) {

			jModel.fromModel(mFuenteDato);

		} else {

			mergeModelCampos(jFuenteDato, mFuenteDato);

			jModel = jFuenteDato;
		}
		return jModel;
	}

	/**
	 * Hace merge de las traducciones comparando lo almacenado con el modelo.
	 *
	 * @param jFuenteDato
	 *            datos almacenados
	 * @param mFuenteDato
	 *            datos modelo
	 * @return datos mergeados
	 */
	public static JFuenteDatos mergeModel(final JFuenteDatos jFuenteDato, final FuenteDatosValores mFuenteDato) {
		JFuenteDatos jModel = new JFuenteDatos();
		if (jFuenteDato == null) {

			jModel.fromModel(mFuenteDato);

		} else {

			mergeModelFilas(jFuenteDato, mFuenteDato);
			jModel = jFuenteDato;
		}
		return jModel;
	}

	/**
	 * Mergea Campos
	 *
	 * @param jFuenteDato
	 * @param mFuenteDato
	 */
	private static void mergeModelCampos(final JFuenteDatos jFuenteDato, final FuenteDatos mFuenteDato) {

		// Borrar campos no pasados en modelo
		for (final JCampoFuenteDatos jCampo : jFuenteDato.getCampos()) {
			boolean nocontiene = true;
			for (final FuenteDatosCampo mFuenteDatoCampo : mFuenteDato.getCampos()) {
				if (mFuenteDatoCampo.getId() != null && mFuenteDatoCampo.getId().compareTo(jCampo.getCodigo()) == 0) {
					nocontiene = false;
					break;
				}
			}

			if (nocontiene) {
				jFuenteDato.getCampos().remove(jCampo);
			}
		}

		// Actualizamos campos pasados en modelo
		for (final FuenteDatosCampo campo : mFuenteDato.getCampos()) {

			// Obtenemos la fuente de dato (si no la tiene, hay que añadirla)
			JCampoFuenteDatos jcampoFuenteDatos = jFuenteDato.getJFuenteCampo(campo.getId());
			if (jcampoFuenteDatos == null) { // No existe el campo de fuente
				jcampoFuenteDatos = new JCampoFuenteDatos();
				if (campo.isClavePrimaria()) {
					jcampoFuenteDatos.setClavePrimaria("S");
				} else {
					jcampoFuenteDatos.setClavePrimaria("N");
				}
				jcampoFuenteDatos.setIdCampo(campo.getCodigo());
				jcampoFuenteDatos.setCodigo(campo.getId());
				jcampoFuenteDatos.setFuenteDatos(jFuenteDato);
				jcampoFuenteDatos.setOrden(campo.getOrden());
				jFuenteDato.getCampos().add(jcampoFuenteDatos);
			} else {

				if (campo.isClavePrimaria()) {
					jcampoFuenteDatos.setClavePrimaria("S");
				} else {
					jcampoFuenteDatos.setClavePrimaria("N");
				}
				jcampoFuenteDatos.setIdCampo(campo.getCodigo());
				jcampoFuenteDatos.setOrden(campo.getOrden());

			}
		}
	}

	/**
	 * Mergea Campos
	 *
	 * @param jFuenteDato
	 * @param mFuenteDato
	 */
	private static void mergeModelFilas(final JFuenteDatos jFuenteDato, final FuenteDatosValores mFuenteDato) {

		// Borrar fila no pasados en modelo
		for (final JFilasFuenteDatos jFila : jFuenteDato.getFilas()) {
			boolean nocontiene = true;
			for (final FuenteFila mFuenteDatoFila : mFuenteDato.getFilas()) {
				if (mFuenteDatoFila.getId() != null && mFuenteDatoFila.getId().compareTo(jFila.getCodigo()) == 0) {
					nocontiene = false;
					break;
				}
			}

			if (nocontiene) {
				jFuenteDato.getFilas().remove(jFila);
			}
		}

		// Actualizamos filas pasados en modelo

		for (final FuenteFila fila : mFuenteDato.getFilas()) {

			// Obtenemos la fuente de dato (si no la tiene, hay que añadirla)
			JFilasFuenteDatos jfilaFuenteDatos = jFuenteDato.getJFuenteFila(fila.getId());
			if (jfilaFuenteDatos == null) { // No existe la fila de fuente
				jfilaFuenteDatos = new JFilasFuenteDatos();
				jfilaFuenteDatos.setFuenteDatos(jFuenteDato);
				jfilaFuenteDatos.setValoresFuenteDatos(getValores(fila.getDatos()));
				jFuenteDato.getFilas().add(jfilaFuenteDatos);
			} else {
				jfilaFuenteDatos.setCodigo(fila.getId());
				jfilaFuenteDatos.setValoresFuenteDatos(getValores(fila.getDatos()));
			}
		}
	}

	private static Set<JValorFuenteDatos> getValores(final List<FuenteDatosValor> datos) {
		final Set<JValorFuenteDatos> valores = new HashSet<>();
		if (datos != null) {
			for (final FuenteDatosValor dato : datos) {
				final JValorFuenteDatos valor = new JValorFuenteDatos();
				valor.fromModel(dato);
				valores.add(valor);
			}
		}
		return valores;
	}

	/**
	 * Obtiene fuente de datos según id
	 *
	 * @param id
	 * @return
	 */
	public JCampoFuenteDatos getJFuenteCampo(final Long idCampoFuenteDatos) {
		JCampoFuenteDatos campo = null;
		if (this.campos != null && idCampoFuenteDatos != null) {
			for (final JCampoFuenteDatos campoFuenteDatos : this.campos) {
				if (campoFuenteDatos.getCodigo() != null
						&& campoFuenteDatos.getCodigo().compareTo(idCampoFuenteDatos) == 0) {
					campo = campoFuenteDatos;
					break;
				}
			}

		}

		return campo;
	}

	/**
	 * Obtiene fuente de datos según id
	 *
	 * @param id
	 * @return
	 */
	private JFilasFuenteDatos getJFuenteFila(final Long idFilaFuenteDatos) {
		JFilasFuenteDatos campo = null;
		if (this.campos != null && idFilaFuenteDatos != null) {
			for (final JFilasFuenteDatos filaFuenteDatos : this.filas) {
				if (filaFuenteDatos.getCodigo() != null
						&& filaFuenteDatos.getCodigo().compareTo(idFilaFuenteDatos) == 0) {
					campo = filaFuenteDatos;
					break;
				}
			}

		}

		return campo;
	}

	public JFilasFuenteDatos getFilaFuenteDatos(final int numFila) {
		if (getFilas() == null || numFila <= 0 || numFila > getFilas().size()) {
			return null;
		}

		int num = 1;
		JFilasFuenteDatos ffd = null;
		for (final java.util.Iterator it = getFilas().iterator(); it.hasNext();) {
			final JFilasFuenteDatos f = (JFilasFuenteDatos) it.next();
			if (num == numFila) {
				ffd = f;
				break;
			}
			num++;
		}

		return ffd;
	}

	public void addFila(final JFilasFuenteDatos fila) {
		boolean encontrado = false;
		for (final JFilasFuenteDatos jfila : this.getFilas()) {
			if (fila.getCodigo() != null && fila.getCodigo().compareTo(jfila.getCodigo()) == 0) {
				encontrado = true;
				for (final JValorFuenteDatos valor : jfila.getValoresFuenteDatos()) {
					final String valorNuevo = jfila.getValorFuenteDatos(valor.getCampoFuenteDatos().getIdCampo());
					valor.setValor(valorNuevo);
				}
			}
		}

		if (!encontrado) {
			this.getFilas().add(fila);
		}

	}

}
