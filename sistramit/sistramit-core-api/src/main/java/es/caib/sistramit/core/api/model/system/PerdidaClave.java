package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;
import java.util.Date;

/**
 * La clase PerdidaClave.
 */
@SuppressWarnings("serial")
public final class PerdidaClave implements Serializable {

	/**
	 * Crea una nueva instancia de PerdidaClave.
	 */
	public PerdidaClave() {
		super();
	}

	/**
	 * clave de tramitacion
	 */
	private String idSesionTramitacion;

	/**
	 * fecha.
	 */
	private Date fecha;

	/**
	 * id tramite.
	 */
	private String idTramite;

	/**
	 * version tramite.
	 */
	private Integer versionTramite;

	/**
	 * id procedimiento CP.
	 */
	private String idProcedimientoCP;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	public void setIdProcedimientoCP(final String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

}
