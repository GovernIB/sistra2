package es.caib.sistrages.core.service.repository.model;

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

import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.types.TypeRoleUser;

/**
 * JRolArea
 */
@Entity
@Table(name = "STG_ROLARE")
public class JRolArea implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ROLARE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ROLARE_SEQ", sequenceName = "STG_ROLARE_SEQ")
	@Column(name = "RLA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Area. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RLA_CODARE", nullable = false)
	private JArea area;

	/** Tipo: R (Role) / U (Usuario) **/
	@Column(name = "RLA_TIPO", nullable = false, length = 1)
	private String tipo;

	/** Valor. **/
	@Column(name = "RLA_VALOR", nullable = false, length = 100)
	private String valor;

	/** Descripcion. **/
	@Column(name = "RLA_DESCR", nullable = false)
	private String descripcion;

	/** Permiso alta-baja tramites */
	@Column(name = "RLA_PERALT", nullable = false, precision = 1, scale = 0)
	private boolean permisoAltaBajaTramites;

	/** Permiso modificar tramites */
	@Column(name = "RLA_PERMOD", nullable = false, precision = 1, scale = 0)
	private boolean permisoModificacionTramites;

	/** Permiso consultar tramites */
	@Column(name = "RLA_PERCON", nullable = false, precision = 1, scale = 0)
	private boolean permisoConsultaTramites;

	/** Permiso acceso helpdesk */
	@Column(name = "RLA_PERHLP", nullable = false, precision = 1, scale = 0)
	private boolean permisoAccesoHelpdesk;

	/** Permiso para promocionar trámites entre entorno. */
	@Column(name = "RLA_PERPRO", nullable = false, precision = 1, scale = 0)
	private boolean permisoPromocionar;

	/** Constructor */
	public JRolArea() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(final String valor) {
		this.valor = valor;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the permisoAltaBajaTramites
	 */
	public boolean isPermisoAltaBajaTramites() {
		return permisoAltaBajaTramites;
	}

	/**
	 * @param permisoAltaBajaTramites
	 *            the permisoAltaBajaTramites to set
	 */
	public void setPermisoAltaBajaTramites(final boolean permisoAltaBajaTramites) {
		this.permisoAltaBajaTramites = permisoAltaBajaTramites;
	}

	/**
	 * @return the permisoModificacionTramites
	 */
	public boolean isPermisoModificacionTramites() {
		return permisoModificacionTramites;
	}

	/**
	 * @param permisoModificacionTramites
	 *            the permisoModificacionTramites to set
	 */
	public void setPermisoModificacionTramites(final boolean permisoModificacionTramites) {
		this.permisoModificacionTramites = permisoModificacionTramites;
	}

	/**
	 * @return the permisoConsultaTramites
	 */
	public boolean isPermisoConsultaTramites() {
		return permisoConsultaTramites;
	}

	/**
	 * @param permisoConsultaTramites
	 *            the permisoConsultaTramites to set
	 */
	public void setPermisoConsultaTramites(final boolean permisoConsultaTramites) {
		this.permisoConsultaTramites = permisoConsultaTramites;
	}

	/**
	 * @return the permisoAccesoHelpdesk
	 */
	public boolean isPermisoAccesoHelpdesk() {
		return permisoAccesoHelpdesk;
	}

	/**
	 * @param permisoAccesoHelpdesk
	 *            the permisoAccesoHelpdesk to set
	 */
	public void setPermisoAccesoHelpdesk(final boolean permisoAccesoHelpdesk) {
		this.permisoAccesoHelpdesk = permisoAccesoHelpdesk;
	}

	/**
	 * @return the permisoPromocionar
	 */
	public boolean isPermisoPromocionar() {
		return permisoPromocionar;
	}

	/**
	 * @param permisoPromocionar
	 *            the permisoPromocionar to set
	 */
	public void setPermisoPromocionar(final boolean permisoPromocionar) {
		this.permisoPromocionar = permisoPromocionar;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public Rol toModel() {
		final Rol rol = new Rol();
		rol.setId(codigo);
		rol.setTipo(TypeRoleUser.fromString(tipo));
		rol.setCodigo(valor);
		rol.setDescripcion(descripcion);
		rol.setAlta(permisoAltaBajaTramites);
		rol.setModificacion(permisoModificacionTramites);
		rol.setConsulta(permisoConsultaTramites);
		rol.setHelpdesk(permisoAccesoHelpdesk);
		rol.setPromocionar(permisoPromocionar);
		return rol;
	}

	/**
	 * fromModel.
	 *
	 * @param model
	 * @return
	 */
	public static JRolArea fromModel(final Rol model) {
		JRolArea jModel = null;
		if (model != null) {
			jModel = new JRolArea();
			jModel.setCodigo(model.getId());
			jModel.setTipo(model.getTipo().toString());
			jModel.setValor(model.getCodigo());
			jModel.setDescripcion(model.getDescripcion());
			jModel.setPermisoAltaBajaTramites(model.isAlta());
			jModel.setPermisoModificacionTramites(model.isModificacion());
			jModel.setPermisoConsultaTramites(model.isConsulta());
			jModel.setPermisoAccesoHelpdesk(model.isHelpdesk());
			jModel.setPermisoPromocionar(model.isPromocionar());
		}
		return jModel;
	}

}
