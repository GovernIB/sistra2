package es.caib.sistramit.core.service.model.system;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;

/**
 * La clase PerdidaClaveFichero.
 */
@SuppressWarnings("serial")
public final class PerdidaClaveFichero implements Serializable {

	/**
	 * Crea una nueva instancia de PerdidaClaveFichero.
	 */
	public PerdidaClaveFichero() {
		super();
	}

	public PerdidaClaveFichero(final String claveTramitacion, final Date fecha, final String idTramite,
			final Integer versionTramite, final String idProcedimientoCP, final Long fichero,
			final String ficheroClave) {
		super();
		this.claveTramitacion = claveTramitacion;
		this.fecha = fecha;
		this.idTramite = idTramite;
		this.versionTramite = versionTramite;
		this.idProcedimientoCP = idProcedimientoCP;
		this.fichero = new ReferenciaFichero(fichero, ficheroClave);
	}

	/**
	 * clave de tramitacion
	 */
	private String claveTramitacion;

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

	private ReferenciaFichero fichero;

	/**
	 * id procedimiento CP.
	 */
	private String idProcedimientoCP;

	public String getClaveTramitacion() {
		return claveTramitacion;
	}

	public void setClaveTramitacion(final String claveTramitacion) {
		this.claveTramitacion = claveTramitacion;
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

	public ReferenciaFichero getFichero() {
		return fichero;
	}

	public void setFichero(final ReferenciaFichero fichero) {
		this.fichero = fichero;
	}

}
