package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.IncidenciaValoracion;

/**
 * JValoracion.
 */
@Entity
@Table(name = "STG_VALORA")
public class RIncidenciaValoracion implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Código interno */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_VALORA_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_VALORA_SEQ", sequenceName = "STG_VALORA_SEQ")
	@Column(name = "VAT_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Entidad */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VAT_CODENT", nullable = false)
	private JEntidad entidad;

	/** Identificador */
	@Column(name = "VAT_IDENTI", unique = true, nullable = false, length = 10)
	private String identificador;

	/** Descripcion */
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = { CascadeType.ALL })
	@JoinColumn(name = "VAT_DESCRI")
	private JLiteral descripcion;

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public final void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the entidad
	 */
	public final JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public final void setEntidad(final JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the identificador
	 */
	public final String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public final void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public final JLiteral getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public final void setDescripcion(final JLiteral descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public IncidenciaValoracion toModel() {
		final IncidenciaValoracion valora = new IncidenciaValoracion();
		valora.setCodigo(this.codigo);
		valora.setIdentificador(this.identificador);
		if (this.descripcion != null) {
			valora.setDescripcion(this.descripcion.toModel());
		}
		return valora;
	}

	/**
	 * From model.
	 *
	 * @param valora
	 * @return
	 */
	public RIncidenciaValoracion fromModel(final IncidenciaValoracion valora) {
		if (valora != null) {
			this.setCodigo(valora.getCodigo());
			this.setIdentificador(valora.getIdentificador());
			this.setDescripcion(JLiteral.fromModel(valora.getDescripcion()));
		}
		return this;
	}

}
