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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fuenteDatos", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JCampoFuenteDatos> campos = new HashSet<>(0);

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
					campos.add(jcampoFuenteDatos);
				}
			}
		}
	}

}
