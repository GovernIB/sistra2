package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.List;

import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * Clase helper que contiene la logica de calculo de los datos campos de
 * formulario.
 *
 * @author Indra
 *
 */
public interface CalculoDatosFormularioHelper {

	/**
	 * Calcula datos de la pagina tras el cambio de un campo. Evalua los scripts de
	 * los campos y actualiza los valores y estado del formulario. Como resultado
	 * indica los cambios efectuados.
	 *
	 * @param datosSesion
	 *                          Datos sesión formulario
	 * @param idCampo
	 *                          Identificador campo
	 * @param valoresPagina
	 *                          Valores pagina actuales con el campo modificado y el
	 *                          resto
	 * @return Resultado del calculo de datos
	 */
	ResultadoEvaluarCambioCampo calcularDatosPaginaCambioCampo(final DatosSesionFormularioInterno datosSesion,
			final String idCampo, final List<ValorCampo> valoresPagina);

	/**
	 * Recalcula datos pagina actual (campos autorrellenables).
	 * 
	 * @param datosSesion
	 *                        Datos sesión
	 */
	void recalcularDatosPagina(DatosSesionFormularioInterno datosSesion);

}
