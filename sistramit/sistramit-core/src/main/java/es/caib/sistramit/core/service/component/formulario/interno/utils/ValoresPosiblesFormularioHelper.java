package es.caib.sistramit.core.service.component.formulario.interno.utils;

import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * @author Indra
 *
 */
public interface ValoresPosiblesFormularioHelper {

	/**
	 * Calcula valores posibles para un campo de tipo selector.
	 *
	 * @param pDatosSesion
	 *            Datos sesión formulario
	 * @param pCampoDef
	 *            Identificador campo
	 * @return Valores posibles
	 */
	ValoresPosiblesCampo calcularValoresPosiblesCampoSelector(final DatosSesionFormularioInterno pDatosSesion,
			final RComponenteSelector pCampoDef);

}
