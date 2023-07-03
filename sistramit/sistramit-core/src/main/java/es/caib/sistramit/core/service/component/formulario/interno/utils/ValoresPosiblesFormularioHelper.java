package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.List;

import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * @author Indra
 *
 */
public interface ValoresPosiblesFormularioHelper {

	/**
	 * Calculamos valores posibles campos selectores de la pagina actual.
	 *
	 * @param pDatosSesion
	 *                         Datos sesion formulario
	 * @param elemento
	 *                         Indica si existe elemento
	 * @return Lista valores posibles campos
	 */
	List<ValoresPosiblesCampo> calcularValoresPosiblesPaginaActual(final DatosSesionFormularioInterno pDatosSesion,
			final boolean elemento);

	/**
	 * Calcula valores posibles para un campo de tipo selector.
	 *
	 * @param pDatosSesion
	 *                         Datos sesión formulario
	 * @param pCampoDef
	 *                         Identificador campo
	 * @param elemento
	 *                         Indica si existe elemento
	 * @return Valores posibles
	 */
	ValoresPosiblesCampo calcularValoresPosiblesCampoSelector(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final boolean elemento);

	/**
	 * Calcula valores posibles para un campo de tipo selector.
	 *
	 * @param pDatosSesion
	 *                          Datos sesión formulario
	 * @param pCampoDef
	 *                          Identificador campo
	 * @param textoBusqueda
	 *                          Texto búsqueda
	 * @param elemento
	 *                          Indica si existe elemento
	 * @return Valores posibles
	 */
	List<ValorIndexado> calcularValoresPosiblesCampoSelectorDinamico(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef, final String textoBusqueda, final boolean elemento);

}
