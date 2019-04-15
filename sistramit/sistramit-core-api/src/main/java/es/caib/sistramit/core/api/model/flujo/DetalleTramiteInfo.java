package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Detalle tramite info.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DetalleTramiteInfo implements ModelApi {

	/** Id trámite. */
	private String idSesion;

	/** Id trámite. */
	private String id;

	/** Versión trámite. */
	private int version;

	/** Idioma. */
	private String idioma;

	/** Título trámite. */
	private String titulo;

	/** Tipo flujo tramitación. */
	private TypeFlujoTramitacion tipoFlujo;

	/** Tipo autenticacion. */
	private TypeAutenticacion autenticacion;

	/** Metodo Autenticacion. */
	private TypeMetodoAutenticacion metodoAutenticacion;

	/** Indica si es nuevo o se ha cargado de persistencia. */
	private TypeSiNo nuevo;

	/** Indica si es persistente. */
	private TypeSiNo persistente;

	/** Dias persistencia. Si 0 persistencia infinita. */
	private int diasPersistencia;

	/** Paso actual. */
	private String idPasoActual;

	/** Tipo paso actual. */
	private TypePaso tipoPasoActual;

	/** Tipo presentacion (puede variar a lo largo del flujo). */
	private TypePresentacion presentacion = TypePresentacion.ELECTRONICA;

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param idSesionTramitacion
	 *            idSesionTramitacion a establecer
	 */
	public void setIdSesion(final String idSesionTramitacion) {
		this.idSesion = idSesionTramitacion;
	}

	/**
	 * Método de acceso a idTramite.
	 *
	 * @return idTramite
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método para establecer idTramite.
	 *
	 * @param idTramite
	 *            idTramite a establecer
	 */
	public void setId(final String idTramite) {
		this.id = idTramite;
	}

	/**
	 * Método de acceso a version.
	 *
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Método para establecer version.
	 *
	 * @param version
	 *            version a establecer
	 */
	public void setVersion(final int version) {
		this.version = version;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *            idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param titulo
	 *            titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Método de acceso a tipoFlujo.
	 *
	 * @return tipoFlujo
	 */
	public TypeFlujoTramitacion getTipoFlujo() {
		return tipoFlujo;
	}

	/**
	 * Método para establecer tipoFlujo.
	 *
	 * @param tipoFlujo
	 *            tipoFlujo a establecer
	 */
	public void setTipoFlujo(final TypeFlujoTramitacion tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *            autenticacion a establecer
	 */
	public void setAutenticacion(final TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion
	 *            metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeMetodoAutenticacion metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a nuevo.
	 *
	 * @return nuevo
	 */
	public TypeSiNo getNuevo() {
		return nuevo;
	}

	/**
	 * Método para establecer nuevo.
	 *
	 * @param nuevo
	 *            nuevo a establecer
	 */
	public void setNuevo(final TypeSiNo nuevo) {
		this.nuevo = nuevo;
	}

	/**
	 * Método de acceso a persistente.
	 *
	 * @return persistente
	 */
	public TypeSiNo getPersistente() {
		return persistente;
	}

	/**
	 * Método para establecer persistente.
	 *
	 * @param persistente
	 *            persistente a establecer
	 */
	public void setPersistente(final TypeSiNo persistente) {
		this.persistente = persistente;
	}

	/**
	 * Método de acceso a diasPersistencia.
	 *
	 * @return diasPersistencia
	 */
	public int getDiasPersistencia() {
		return diasPersistencia;
	}

	/**
	 * Método para establecer diasPersistencia.
	 *
	 * @param diasPersistencia
	 *            diasPersistencia a establecer
	 */
	public void setDiasPersistencia(final int diasPersistencia) {
		this.diasPersistencia = diasPersistencia;
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
	public void setIdPasoActual(final String idPasoActual) {
		this.idPasoActual = idPasoActual;
	}

	/**
	 * Método de acceso a tipoPasoActual.
	 *
	 * @return tipoPasoActual
	 */
	public TypePaso getTipoPasoActual() {
		return tipoPasoActual;
	}

	/**
	 * Método para establecer tipoPasoActual.
	 *
	 * @param tipoPasoActual
	 *            tipoPasoActual a establecer
	 */
	public void setTipoPasoActual(final TypePaso tipoPasoActual) {
		this.tipoPasoActual = tipoPasoActual;
	}

	/**
	 * Método de acceso a presentacion.
	 *
	 * @return presentacion
	 */
	public TypePresentacion getPresentacion() {
		return presentacion;
	}

	/**
	 * Método para establecer presentacion.
	 *
	 * @param presentacion
	 *            presentacion a establecer
	 */
	public void setPresentacion(final TypePresentacion presentacion) {
		this.presentacion = presentacion;
	}

}