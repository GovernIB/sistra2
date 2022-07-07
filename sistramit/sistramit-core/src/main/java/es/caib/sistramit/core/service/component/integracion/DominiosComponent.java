package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;

/**
 * Acceso a dominios.
 *
 * @author Indra
 *
 */
public interface DominiosComponent {

	/**
	 * Recupera dominio (teniendo en cuenta opciones de caché).
	 *
	 * @param idDominio
	 * @param parametrosDominio
	 * @param defTramite
	 * @return
	 */
	ValoresDominio recuperarDominio(String idDominio, ParametrosDominio parametrosDominio,
			DefinicionTramiteSTG defTramite);

	/**
	 * Invalida dominio en la caché.
	 *
	 * @param idDominio
	 *                      idDominio
	 */
	void invalidarDominio(String idDominio);

	/**
	 * Borra caché dominios.
	 *
	 * @param idDominio
	 *                      idDominio
	 */
	void invalidarDominios();

}
