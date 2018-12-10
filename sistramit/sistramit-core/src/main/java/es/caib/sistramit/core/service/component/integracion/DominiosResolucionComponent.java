package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;

/**
 * Acceso a dominios.
 *
 * @author Indra
 *
 */
public interface DominiosResolucionComponent {

	/**
	 * Resuelve dominio para Dominios de Fuente de Datos.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @return
	 */
	ValoresDominio resuelveDominioFD(final RDominio dominio, final ParametrosDominio parametrosDominio);

	/**
	 * Resuelve dominio para Dominios de Lista Fija
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	ValoresDominio resuelveDominioLF(final RDominio dominio);

	/**
	 * Resuelve dominio para Dominios de SQL.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	ValoresDominio resuelveDominioSQL(final RDominio dominio, final ParametrosDominio parametrosDominio);

	/**
	 * Resuelve dominio para Dominio de Web Service.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @return
	 */
	ValoresDominio resuelveDominioWS(final RDominio dominio, final ParametrosDominio parametrosDominio);

}
