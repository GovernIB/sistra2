package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 *
 * Datos de la sesión de tramitación que se mantienen en memoria (id, estado
 * pasos, definición trámite, etc.).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosSesionTramitacion implements Serializable {

	/**
	 * Definicion del trámite en el GTT.
	 */
	private final DefinicionTramiteSTG definicionTramite;

	/**
	 * Fecha recuperacion definicion del trámite en el GTT.
	 */
	private Date fechaRecuperacionDefinicionTramite;

	/**
	 * Datos de la instancia del trámite.
	 */
	private final DatosTramite datosTramite = new DatosTramite();

	/**
	 * Constructor.
	 *
	 * @param pDefinicionTramite
	 *            Parámetro definicion tramite
	 * @param pFechaRecuperacionDefinicion
	 *            Fecha recuperacion definicion del trámite en el GTT.
	 */
	public DatosSesionTramitacion(final DefinicionTramiteSTG pDefinicionTramite, final Date pFechaRecuperacionDefinicion) {
		super();
		definicionTramite = pDefinicionTramite;
		fechaRecuperacionDefinicionTramite = pFechaRecuperacionDefinicion;
	}

	/**
	 * Método de acceso a definicionTramite.
	 *
	 * @return definicionTramite
	 */
	public DefinicionTramiteSTG getDefinicionTramite() {
		return definicionTramite;
	}

	/**
	 * Método de acceso a datosTramite.
	 *
	 * @return datosTramite
	 */
	public DatosTramite getDatosTramite() {
		return datosTramite;
	}

	/**
	 * Método de acceso a fechaRecuperacionDefinicionTramite.
	 *
	 * @return fechaRecuperacionDefinicionTramite
	 */
	public Date getFechaRecuperacionDefinicionTramite() {
		return fechaRecuperacionDefinicionTramite;
	}

	/**
	 * Método para establecer fechaRecuperacionDefinicionTramite.
	 *
	 * @param pFechaRecuperacionDefinicionTramite
	 *            fechaRecuperacionDefinicionTramite a establecer
	 */
	public void setFechaRecuperacionDefinicionTramite(final Date pFechaRecuperacionDefinicionTramite) {
		fechaRecuperacionDefinicionTramite = pFechaRecuperacionDefinicionTramite;
	}

}
