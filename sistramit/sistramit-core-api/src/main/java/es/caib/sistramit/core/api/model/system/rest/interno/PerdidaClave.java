package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;
import java.util.Date;

/**
 * La clase PerdidaClave. (RestApiInternaService)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((idProcedimientoCP == null) ? 0 : idProcedimientoCP.hashCode());
		result = prime * result + ((idSesionTramitacion == null) ? 0 : idSesionTramitacion.hashCode());
		result = prime * result + ((idTramite == null) ? 0 : idTramite.hashCode());
		result = prime * result + ((versionTramite == null) ? 0 : versionTramite.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PerdidaClave))
			return false;
		final PerdidaClave other = (PerdidaClave) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idProcedimientoCP == null) {
			if (other.idProcedimientoCP != null)
				return false;
		} else if (!idProcedimientoCP.equals(other.idProcedimientoCP))
			return false;
		if (idSesionTramitacion == null) {
			if (other.idSesionTramitacion != null)
				return false;
		} else if (!idSesionTramitacion.equals(other.idSesionTramitacion))
			return false;
		if (idTramite == null) {
			if (other.idTramite != null)
				return false;
		} else if (!idTramite.equals(other.idTramite))
			return false;
		if (versionTramite == null) {
			if (other.versionTramite != null)
				return false;
		} else if (!versionTramite.equals(other.versionTramite))
			return false;
		return true;
	}

}
