package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;

/**
 * JConfiguracionGlobal
 */
@Entity
@Table(name = "STG_CNFGLO")
public class JConfiguracionGlobal implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_CNFGLO_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_CNFGLO_SEQ", sequenceName = "STG_CNFGLO_SEQ")
	@Column(name = "CFG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Column(name = "CFG_PROP", nullable = false, length = 100)
	private String propiedad;

	@Column(name = "CFG_VALOR", length = 500)
	private String valor;

	@Column(name = "CFG_DESCR", nullable = false)
	private String descripcion;

	/** Indica si es no modificable */
	@Column(name = "CFG_NOMOD", nullable = false, precision = 1, scale = 0)
	private boolean noModificable;

	public JConfiguracionGlobal() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(final String propiedad) {
		this.propiedad = propiedad;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the noModificable
	 */
	public final boolean isNoModificable() {
		return noModificable;
	}

	/**
	 * @param noModificable the noModificable to set
	 */
	public final void setNoModificable(final boolean noModificable) {
		this.noModificable = noModificable;
	}

	public ConfiguracionGlobal toModel() {
		final ConfiguracionGlobal model = new ConfiguracionGlobal();
		model.setCodigo(codigo);
		model.setPropiedad(propiedad);
		model.setValor(valor);
		model.setDescripcion(descripcion);
		model.setNoModificable(noModificable);
		return model;
	}

	public static JConfiguracionGlobal fromModel(final ConfiguracionGlobal model) {
		final JConfiguracionGlobal jConfiguracionglobal = new JConfiguracionGlobal();
		jConfiguracionglobal.setCodigo(model.getCodigo());
		jConfiguracionglobal.setDescripcion(model.getDescripcion());
		jConfiguracionglobal.setPropiedad(model.getPropiedad());
		jConfiguracionglobal.setValor(model.getValor());
		jConfiguracionglobal.setNoModificable(model.isNoModificable());
		return jConfiguracionglobal;
	}

}
