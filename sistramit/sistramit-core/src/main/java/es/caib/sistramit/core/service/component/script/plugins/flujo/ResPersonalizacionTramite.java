package es.caib.sistramit.core.service.component.script.plugins.flujo;

import javax.script.ScriptException;

import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.service.model.script.ResPersonalizacionTramiteInt;

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
	 * Plazo inicio (Formato: dd/MM/yyyy HH:mm:ss).
	 */
	private String plazoInicio;

	/**
	 * Plazo fin (Formato: dd/MM/yyyy HH:mm:ss).
	 */
	private String plazoFin;

	@Override
	public String getPluginId() {
		return ID;
	}

	@Override
	public void setTituloTramite(final String pTituloTramite) throws ScriptException {
		if (!XssFilter.filtroXss(pTituloTramite)) {
			throw new ScriptException("El titulo del tramite tiene caracteres no permitidos");
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
	public String getPlazoInicio() {
		return plazoInicio;
	}

	@Override
	public void setPlazoInicio(final String pPlazoInicio) throws ScriptException {
		if (!ValidacionesTipo.getInstance().esFecha(pPlazoInicio, FORMATO_FECHA)) {
			throw new ScriptException("La fecha no tiene un formato correcto: " + FORMATO_FECHA);
		}
		plazoInicio = pPlazoInicio;
	}

	/**
	 * Método de acceso a plazoFin.
	 *
	 * @return plazoFin
	 */
	public String getPlazoFin() {
		return plazoFin;
	}

	@Override
	public void setPlazoFin(final String pPlazoFin) throws ScriptException {
		if (!ValidacionesTipo.getInstance().esFecha(pPlazoFin, FORMATO_FECHA)) {
			throw new ScriptException("La fecha no tiene un formato correcto: " + FORMATO_FECHA);
		}
		plazoFin = pPlazoFin;
	}

}
