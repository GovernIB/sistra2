package es.caib.sistrages.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrages.core.api.model.Sesion;

/**
 * JSesion
 */
@Entity
@Table(name = "STG_SESION")
public class JSesion implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SESI_USUA", unique = true, nullable = false, length = 100)
	private String usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SESI_FECHA", nullable = false)
	private Date fecha;

	@Column(name = "SESI_PERFIL", nullable = false, length = 50)
	private String perfil;

	@Column(name = "SESI_IDIOMA", nullable = false, length = 2)
	private String idioma;

	@Column(name = "SESI_ENTIDA", nullable = true, precision = 18, scale = 0)
	private Long entidad;

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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(final String perfil) {
		this.perfil = perfil;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(final Long entidad) {
		this.entidad = entidad;
	}

	public Sesion toModel() {
		final Sesion sesion = new Sesion();
		sesion.setUsuario(usuario);
		sesion.setFecha(fecha);
		sesion.setPerfil(perfil);
		sesion.setIdioma(idioma);
		sesion.setEntidad(entidad);
		return sesion;
	}

	public static JSesion fromModel(final Sesion model) {
		JSesion jModel = null;
		if (model != null) {
			jModel = new JSesion();
			jModel.setUsuario(model.getUsuario());
			jModel.setFecha(model.getFecha());
			jModel.setPerfil(model.getPerfil());
			jModel.setIdioma(model.getIdioma());
			jModel.setEntidad(model.getEntidad());
		}
		return jModel;
	}

}
