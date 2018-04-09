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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_ROLARE_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_ROLARE_SEQ", sequenceName = "STG_ROLARE_SEQ")
	@Column(name = "RLA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RLA_CODARE", nullable = false)
	private JArea area;

	@Column(name = "RLA_TIPO", nullable = false, length = 1)
	private String tipo;

	@Column(name = "RLA_VALOR", nullable = false, length = 100)
	private String valor;

	@Column(name = "RLA_DESCR", nullable = false)
	private String descripcion;

	@Column(name = "RLA_PERALT", nullable = false, precision = 1, scale = 0)
	private boolean permisoAltaBajaTramites;

	@Column(name = "RLA_PERMOD", nullable = false, precision = 1, scale = 0)
	private boolean permisoModificacionTramites;

	@Column(name = "RLA_PERCON", nullable = false, precision = 1, scale = 0)
	private boolean permisoConsultaTramites;

	@Column(name = "RLA_PERHLP", nullable = false, precision = 1, scale = 0)
	private boolean permisoAccesoHelpdesk;

	public JRolArea() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JArea getArea() {
		return this.area;
	}

	public void setArea(final JArea area) {
		this.area = area;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
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

	public boolean isPermisoAltaBajaTramites() {
		return this.permisoAltaBajaTramites;
	}

	public void setPermisoAltaBajaTramites(final boolean permisoAltaBajaTramites) {
		this.permisoAltaBajaTramites = permisoAltaBajaTramites;
	}

	public boolean isPermisoModificacionTramites() {
		return this.permisoModificacionTramites;
	}

	public void setPermisoModificacionTramites(final boolean permisoModificacionTramites) {
		this.permisoModificacionTramites = permisoModificacionTramites;
	}

	public boolean isPermisoConsultaTramites() {
		return this.permisoConsultaTramites;
	}

	public void setPermisoConsultaTramites(final boolean permisoConsultaTramites) {
		this.permisoConsultaTramites = permisoConsultaTramites;
	}

	public boolean isPermisoAccesoHelpdesk() {
		return this.permisoAccesoHelpdesk;
	}

	public void setPermisoAccesoHelpdesk(final boolean permisoAccesoHelpdesk) {
		this.permisoAccesoHelpdesk = permisoAccesoHelpdesk;
	}

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

		return rol;
	}

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
		}
		return jModel;
	}

}
