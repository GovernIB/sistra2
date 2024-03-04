package es.caib.sistrages.core.service.repository.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistrages.core.api.model.Sesion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.util.UtilJSON;

/**
 * JSesion
 */
@Entity
@Table(name = "STG_SESION")
public class JSesion implements IModelApi {

	private static final long serialVersionUID = 1L;

	public static String PROPIEDAD_DEFECTO = "[{\"codigo\":\"paginacion\",\"valor\":\"10\",\"orden\":null}]";

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

	/** Lista serializada propiedades (codigo - valor) */
	@Column(name = "SESI_PROPS", nullable = false, length = 4000)
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
		sesion.setPerfil(perfil);
		sesion.setIdioma(idioma);
		sesion.setEntidad(entidad);
		sesion.setPropiedades((List<Propiedad>) UtilJSON.fromListJSON(propiedades, Propiedad.class));
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
			jModel.setPropiedades(UtilJSON.toJSON(model.getPropiedades()));
		}
		return jModel;
	}

}
