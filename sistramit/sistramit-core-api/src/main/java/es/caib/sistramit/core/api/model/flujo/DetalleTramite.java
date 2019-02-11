package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Detalle tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetalleTramite implements ModelApi {

	/** Entorno. */
	private TypeEntorno entorno;

	/** Debug habilitado. */
	private TypeSiNo debug = TypeSiNo.NO;

	/** Fecha recuperacion definicion STG (dd/MM/yyyy hh:mm). */
	private String fechaDefinicion;

	// TODO CAMBIAR JS PARA QUE USE DetalleTramiteInfo
	/** Paso actual. */
	private String idPasoActual;

	/** Tramite info. */
	private DetalleTramiteInfo tramite;

	/** Usuario autenticado. */
	private DatosUsuario usuario;

	/** Info entidad. */
	private Entidad entidad;

	/**
	 * Imprime detalle tramite.
	 *
	 * @return Detalle tramite
	 */
	public String print() {
		final StringBuffer strb = new StringBuffer(ConstantesNumero.N1000 * ConstantesNumero.N8);
		strb.append("\nDETALLE TRAMITE\n");
		strb.append("===============\n");
		strb.append("Entorno:" + getEntorno() + "\n");
		if (getUsuario() != null) {
			strb.append("Usuario:" + getUsuario().print() + "\n");
		}
		strb.append("Idioma:" + tramite.getIdioma() + "\n");
		strb.append("Titulo:" + tramite.getTitulo() + "\n");
		strb.append("Tipo flujo:" + tramite.getTipoFlujo() + "\n");
		strb.append("Id paso actual:\n" + getIdPasoActual() + "\n");
		strb.append("===============\n");
		strb.append("FIN DETALLE TRAMITE\n");
		return strb.toString();
	}

	/**
	 * Método de acceso a entorno.
	 *
	 * @return entorno
	 */
	public TypeEntorno getEntorno() {
		return entorno;
	}

	/**
	 * Método para establecer entorno.
	 *
	 * @param entorno
	 *            entorno a establecer
	 */
	public void setEntorno(TypeEntorno entorno) {
		this.entorno = entorno;
	}

	/**
	 * Método de acceso a debug.
	 *
	 * @return debug
	 */
	public TypeSiNo getDebug() {
		return debug;
	}

	/**
	 * Método para establecer debug.
	 *
	 * @param debug
	 *            debug a establecer
	 */
	public void setDebug(TypeSiNo debug) {
		this.debug = debug;
	}

	/**
	 * Método de acceso a fechaDefinicion.
	 *
	 * @return fechaDefinicion
	 */
	public String getFechaDefinicion() {
		return fechaDefinicion;
	}

	/**
	 * Método para establecer fechaDefinicion.
	 *
	 * @param fechaDefinicion
	 *            fechaDefinicion a establecer
	 */
	public void setFechaDefinicion(String fechaDefinicion) {
		this.fechaDefinicion = fechaDefinicion;
	}

	/**
	 * Método de acceso a idPasoActual.
	 *
	 * @return idPasoActual
	 */
	public String getIdPasoActual() {
		return idPasoActual;
	}

	/**
	 * Método para establecer idPasoActual.
	 *
	 * @param idPasoActual
	 *            idPasoActual a establecer
	 */
	public void setIdPasoActual(String idPasoActual) {
		this.idPasoActual = idPasoActual;
	}

	/**
	 * Método de acceso a tramite.
	 *
	 * @return tramite
	 */
	public DetalleTramiteInfo getTramite() {
		return tramite;
	}

	/**
	 * Método para establecer tramite.
	 *
	 * @param tramite
	 *            tramite a establecer
	 */
	public void setTramite(DetalleTramiteInfo tramite) {
		this.tramite = tramite;
	}

	/**
	 * Método de acceso a usuario.
	 *
	 * @return usuario
	 */
	public DatosUsuario getUsuario() {
		return usuario;
	}

	/**
	 * Método para establecer usuario.
	 *
	 * @param usuario
	 *            usuario a establecer
	 */
	public void setUsuario(DatosUsuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *            entidad a establecer
	 */
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

}
