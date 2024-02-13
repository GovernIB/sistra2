package es.caib.sistrahelp.core.service.repository.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrahelp.core.api.model.Sesion;
import es.caib.sistrahelp.core.api.model.comun.Propiedad;
import es.caib.sistrahelp.core.api.util.UtilJSON;

/**
 * JSesion
 */
@Entity
@Table(name = "STH_SESION")
public class JSesion implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SESI_USUA", unique = true, nullable = false, length = 100)
	private String usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SESI_FECHA", nullable = false)
	private Date fecha;

	/** Lista serializada propiedades (codigo - valor) */
	@Column(name = "SESI_PROPS", nullable = true, length = 4000)
	private String propiedades;

	public JSesion() {
		super();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(final String usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the propiedades
	 */
	public String getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(String propiedades) {
		this.propiedades = propiedades;
	}

	@SuppressWarnings("unchecked")
	public Sesion toModel() {
		final Sesion sesion = new Sesion();
		sesion.setUsuario(usuario);
		sesion.setFecha(fecha);
		sesion.setPropiedades((List<Propiedad>) UtilJSON.fromListJSON(propiedades, Propiedad.class));
		return sesion;
	}

	public static JSesion fromModel(final Sesion model) {
		JSesion jModel = null;
		if (model != null) {
			jModel = new JSesion();
			jModel.setUsuario(model.getUsuario());
			jModel.setFecha(model.getFecha());
			jModel.setPropiedades(UtilJSON.toJSON(model.getPropiedades()));
		}
		return jModel;
	}

}
