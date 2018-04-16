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

import es.caib.sistrages.core.api.model.FuenteDatosValor;
import es.caib.sistrages.core.api.model.FuenteFila;

/**
 * JFilasFuenteDatos
 */
@Entity
@Table(name = "STG_FILFUE")
public class JFilasFuenteDatos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FILFUE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FILFUE_SEQ", sequenceName = "STG_FILFUE_SEQ")
	@Column(name = "FIF_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIF_CODFUE", nullable = false)
	private JFuenteDatos fuenteDatos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "filaFuenteDatos", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<JValorFuenteDatos> valoresFuenteDatos = new HashSet<JValorFuenteDatos>(0);

	public JFilasFuenteDatos() {
		// Constructor vacio.
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JFuenteDatos getFuenteDatos() {
		return this.fuenteDatos;
	}

	public void setFuenteDatos(final JFuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public Set<JValorFuenteDatos> getValoresFuenteDatos() {
		return this.valoresFuenteDatos;
	}

	public void setValoresFuenteDatos(final Set<JValorFuenteDatos> valoresFuenteDatos) {
		this.valoresFuenteDatos = valoresFuenteDatos;
	}

	public FuenteFila toModel() {
		final FuenteFila fila = new FuenteFila();
		fila.setId(this.getCodigo());
		if (this.valoresFuenteDatos != null) {
			final List<FuenteDatosValor> valores = new ArrayList<>();
			for (final JValorFuenteDatos valor : valoresFuenteDatos) {
				valores.add(valor.toModel());
			}
			fila.setDatos(valores);
		}
		return fila;
	}

	public void fromModel(final FuenteFila fila) {
		if (fila != null) {
			this.setCodigo(fila.getId());
			if (fila.getDatos() != null) {
				final Set<JValorFuenteDatos> valores = new HashSet<>();
				for (final FuenteDatosValor valor : fila.getDatos()) {
					final JValorFuenteDatos jvalor = new JValorFuenteDatos();
					jvalor.fromModel(valor);
					jvalor.setFilaFuenteDatos(this);
					valores.add(jvalor);
				}
				this.setValoresFuenteDatos(valores);
			}
		}
	}

	/**
	 * Mergea dos filas.
	 *
	 * @param fila
	 */
	public void merge(final FuenteFila fila) {
		for (final JValorFuenteDatos jdato : this.getValoresFuenteDatos()) {
			final FuenteDatosValor dato = fila.getDato(jdato.getCodigo());
			jdato.setValor(dato.getValor());
		}
	}

	public void addValor(final JValorFuenteDatos valor) {
		valor.setFilaFuenteDatos(this);
		this.getValoresFuenteDatos().add(valor);
	}

	/**
	 * Borrar valor según campo.
	 *
	 * @param hfuenteDatoCampo
	 */
	public JValorFuenteDatos removeValor(final Long idCampo) {
		JValorFuenteDatos valorBorrar = null;
		for (final JValorFuenteDatos valor : this.getValoresFuenteDatos()) {
			if (valor.getCampoFuenteDatos() != null
					&& valor.getCampoFuenteDatos().getCodigo().compareTo(idCampo) == 0) {
				valorBorrar = valor;
				break;
			}
		}
		if (valorBorrar != null) {
			this.getValoresFuenteDatos().remove(valorBorrar);
		}

		return valorBorrar;
	}

	public String getValorFuenteDatos(final String identificadorCampo) {
		String valor = null;
		if (this.getValoresFuenteDatos() != null) {
			JValorFuenteDatos vfdCampo = null;
			for (final java.util.Iterator it = getValoresFuenteDatos().iterator(); it.hasNext();) {
				final JValorFuenteDatos vfd = (JValorFuenteDatos) it.next();
				if (vfd.getCampoFuenteDatos().getIdCampo().equals(identificadorCampo)) {
					vfdCampo = vfd;
					break;
				}
			}
			if (vfdCampo != null) {
				valor = vfdCampo.getValor();
			}
		}
		return valor;
	}

}
