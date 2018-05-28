package es.caib.sistramit.core.api.exception;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistramit.core.api.model.comun.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al ejecutar un script. Se trata de un error de ejecución
 * (error compilación, etc.), no de un error lógico (generado con el plugin de
 * error).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EngineScriptException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		// TypeNivelExcepcion FATAL
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor EngineScriptException.
	 *
	 * @param scriptName
	 *            Nombre script
	 * @param idSesion
	 *            Id sesión tramitación
	 * @param idElemento
	 *            Id elemento (si aplica)
	 * @param linea
	 *            Línea
	 * @param pMessageExcep
	 *            Mensaje error
	 */
	public EngineScriptException(final String scriptName, final String idSesion, final String idElemento,
			final int linea, final String pMessageExcep) {
		super(pMessageExcep);
		setPropiedades(scriptName, idSesion, idElemento, linea);
	}

	/**
	 * Constructor EngineScriptException.
	 *
	 * @param pscriptName
	 *            Nombre script
	 * @param idSesion
	 *            Id sesión tramitación
	 * @param idElemento
	 *            Id elemento (si aplica)
	 * @param linea
	 *            Línea
	 * @param pMessageExcep
	 *            Mensaje error
	 * @param ex
	 *            Exception
	 */
	public EngineScriptException(final String pscriptName, final String idSesion, final String idElemento,
			final int linea, final String pMessageExcep, final Exception ex) {
		super(pMessageExcep, ex);
		setPropiedades(pscriptName, idSesion, idElemento, linea);
	}

	/**
	 * Establece propiedades excepcion.
	 *
	 * @param pscriptName
	 *            Propiedad Nombre script
	 * @param pidSesion
	 *            Propiedad Id sesión tramitación
	 * @param pidElemento
	 *            Propiedad Id elemento (si aplica)
	 * @param plinea
	 *            Propiedad Línea
	 */
	private void setPropiedades(final String pscriptName, final String pidSesion, final String pidElemento,
			final int plinea) {
		final ListaPropiedades detalles = new ListaPropiedades();
		detalles.addPropiedad("idSesion", pidSesion);
		detalles.addPropiedad("script", pscriptName);
		if (StringUtils.isNotEmpty(pidElemento)) {
			detalles.addPropiedad("elemento", pidElemento);
		}
		if (plinea != ConstantesNumero.N_1) {
			detalles.addPropiedad("linea", Integer.toString(plinea));
		}
		this.setDetallesExcepcion(detalles);
	}

}
