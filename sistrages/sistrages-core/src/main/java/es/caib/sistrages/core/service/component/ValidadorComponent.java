package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;

/**
 * Componentes ValidadorComponent.
 *
 * @author indra
 *
 */
public interface ValidadorComponent {

	/**
	 * Valida que no tenga errores una version de un tramite.
	 *
	 * @param pId
	 *            id. version tramite
	 * @param pIdioma
	 *            idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	List<ErrorValidacion> comprobarVersionTramite(Long pId, String pIdioma);

	/**
	 * Valida que no tenga errores una version de un tramite.
	 *
	 * @param pTramiteVersion
	 *            tramite version
	 * @param pIdioma
	 *            idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	List<ErrorValidacion> comprobarVersionTramite(TramiteVersion pTramiteVersion, String pIdioma);

	/**
	 * Valida que no tenga errores un script de un tramite.
	 *
	 * @param pScript
	 *            script a validar
	 * @param pListaDominios
	 *            lista dominios de la version
	 * @param pIdiomasTramiteVersion
	 *            idiomas definidos en la version
	 * @param pIdioma
	 *            idioma para mostrar los errores
	 * @return lista de errores de validacion
	 */
	List<ErrorValidacion> comprobarScript(Script pScript, List<Dominio> pListaDominios,
			List<String> pIdiomasTramiteVersion, String pIdioma);

	/**
	 * Valida que un dominio no se utiliza.
	 * @param idDominio
	 * @param idTramiteVersion
	 * @return
	 */
	List<ErrorValidacion> checkDominioNoUtilizado(Long idDominio, Long idTramiteVersion, final String pIdioma);

}