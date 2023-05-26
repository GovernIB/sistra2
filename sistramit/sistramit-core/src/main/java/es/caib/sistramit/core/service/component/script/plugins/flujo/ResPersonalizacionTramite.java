package es.caib.sistramit.core.service.component.script.plugins.flujo;

import java.util.Date;

import javax.script.ScriptException;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.service.model.script.flujo.ResPersonalizacionTramiteInt;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 *
 * Datos que se pueden personalizar al cargar un trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResPersonalizacionTramite implements ResPersonalizacionTramiteInt {

	/**
	 * Título trámite.
	 */
	private String tituloTramite;

	/**
	 * Plazo inicio.
	 */
	private Date plazoInicio;

	/**
	 * Plazo fin.
	 */
	private Date plazoFin;

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void setTituloTramite(final String pTituloTramite) throws ScriptException {

		if (!XssFilter.filtroXss(pTituloTramite)) {
			throw new ScriptException("El títol del tràmit té caràcters no permesos");
		}
		tituloTramite = pTituloTramite;
	}

	/**
	 * Método de acceso a titulo personalizado tramite.
	 *
	 * @return titulo tramite personalizado
	 */
	public String getTituloTramite() {
		return tituloTramite;
	}

	/**
	 * Método de acceso a plazoInicio.
	 *
	 * @return plazoInicio
	 */
	public Date getPlazoInicio() {
		return plazoInicio;
	}

	@Override
	public void setPlazoInicio(final String pPlazoInicio) throws ScriptException {

		// DESHABILITAMOS YA QUE NO SE QUIERE USAR ESTA FUNCIONALIDAD
		if (true) {
			throw new ErrorConfiguracionException("Funcionalitat deshabilitada");
		}

		if (!ValidacionesTipo.getInstance().esFecha(pPlazoInicio, Constantes.FORMATO_FECHAHORA_FRONTAL)) {
			throw new ScriptException("La data no té un format correcte: " + Constantes.FORMATO_FECHAHORA_FRONTAL);
		}
		plazoInicio = UtilsFlujo.deformateaFecha(pPlazoInicio, Constantes.FORMATO_FECHAHORA_FRONTAL);
	}

	/**
	 * Método de acceso a plazoFin.
	 *
	 * @return plazoFin
	 */
	public Date getPlazoFin() {
		return plazoFin;
	}

	@Override
	public void setPlazoFin(final String pPlazoFin) throws ScriptException {

		// DESHABILITAMOS YA QUE NO SE QUIERE USAR ESTA FUNCIONALIDAD
		if (true) {
			throw new ErrorConfiguracionException("Funcionalitat deshabilitada");
		}

		if (!ValidacionesTipo.getInstance().esFecha(pPlazoFin, Constantes.FORMATO_FECHAHORA_FRONTAL)) {
			throw new ScriptException("La data no té un format correcte: " + Constantes.FORMATO_FECHAHORA_FRONTAL);
		}
		plazoFin = UtilsFlujo.deformateaFecha(pPlazoFin, Constantes.FORMATO_FECHAHORA_FRONTAL);
	}

}
