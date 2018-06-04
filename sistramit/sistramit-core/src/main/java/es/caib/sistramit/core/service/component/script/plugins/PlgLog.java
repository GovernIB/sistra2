package es.caib.sistramit.core.service.component.script.plugins;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.service.ApplicationContextProvider;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.model.script.PlgLogInt;
import es.caib.sistramit.core.service.model.script.types.TypeScript;
import es.caib.sistramit.core.service.model.system.EventoAplicacion;
import es.caib.sistramit.core.service.model.system.types.TypeEvento;

/**
 * Plugin para debug de scripts. Genera evento en auditoria.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlgLog implements PlgLogInt {

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(PlgLog.class);

	/**
	 * Id sesión tramitación.
	 */
	private final String idSesionTramitacion;
	/**
	 * Tipo de script.
	 */
	private final TypeScript tipoScript;
	/**
	 * Id elemento. En caso de que el script se aplique sobre un elemento
	 * identificable se puede indicar el id (p.e. formulario, anexo, etc.)
	 */
	private final String idElemento;

	/**
	 * Indica si se realizara el debug.
	 */
	private final boolean debug;

	/**
	 * Componente auditoria.
	 */
	private final AuditoriaComponent auditoriaComponent;

	/**
	 * Constructor.
	 *
	 * @param pIdSesionTramitacion
	 *            Id sesión tramitación
	 * @param pTipoScript
	 *            Tipo script
	 * @param pIdElemento
	 *            Id elemento (si aplica)
	 * @param pDebug
	 *            Indica si se realizara el debug
	 */
	public PlgLog(final String pIdSesionTramitacion, final TypeScript pTipoScript, final String pIdElemento,
			final boolean pDebug) {
		this.idSesionTramitacion = pIdSesionTramitacion;
		this.tipoScript = pTipoScript;
		this.idElemento = pIdElemento;
		this.auditoriaComponent = (AuditoriaComponent) ApplicationContextProvider.getApplicationContext()
				.getBean("generacionAuditoriaComponent");
		this.debug = pDebug;
	}

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void debug(final String mensaje) {
		if (this.debug) {
			// Generamos debug en log
			final StringBuilder sb = new StringBuilder(mensaje.length() + ConstantesNumero.N200);
			sb.append("[").append(idSesionTramitacion).append(" - ").append(tipoScript.name());
			if (StringUtils.isNotEmpty(idElemento)) {
				sb.append(" - ").append(idElemento);
			}
			sb.append("] ").append(mensaje);
			final String msgLog = sb.toString();
			log.debug(msgLog);

			// Generamos debug en auditoria
			final EventoAplicacion evento = new EventoAplicacion();
			evento.setFecha(new Date());
			evento.setTipoEvento(TypeEvento.DEBUG_SCRIPT);
			evento.setIdSesionTramitacion(idSesionTramitacion);
			evento.setDescripcion(mensaje);
			final Map<String, String> propiedadesEvento = new HashMap<String, String>();
			propiedadesEvento.put("idSesionTramitacion", this.idSesionTramitacion);
			if (StringUtils.isNotBlank(this.idElemento)) {
				propiedadesEvento.put("idElemento", this.idElemento);
			}
			propiedadesEvento.put("script", this.tipoScript.name());
			evento.setPropiedadesEvento(propiedadesEvento);
			auditoriaComponent.auditarEventoAplicacion(evento);
		}
	}

	@Override
	public void retardo(final int pTimeout) {
		debug("Simulando retardo de " + pTimeout + " segundos");
		final long inicio = System.currentTimeMillis();
		while (true) {
			if ((System.currentTimeMillis() - inicio) > (pTimeout * ConstantesNumero.N1000)) {
				break;
			}
		}
		debug("Fin simulando retardo de " + pTimeout + " segundos");
	}

}
