package es.caib.sistramit.core.service.component.formulario.interno.utils;

import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * Clase helper que contiene la logica de las validaciones de los campos al
 * guardar una pagina.
 *
 * @author Indra
 *
 */
public interface ValidacionesFormularioHelper {

	/**
	 * Realiza verificacion de la pagina actual del formulario.
	 *
	 * @param datosSesion
	 *                        Datos sesion formulario
	 * @param elemento
	 *                        Indica si existe elemento
	 */
	void validarConfiguracionCampos(final DatosSesionFormularioInterno datosSesion, final boolean elemento);

	/**
	 * Valida página al guardar.
	 *
	 * @param datosSesion
	 *                        Datos sesion
	 * @param elemento
	 *                        Indica si existe elemento
	 * @return En caso de encontrar error de validación devuelve mensaje (null si no
	 *         hay error de validación).
	 */
	MensajeValidacion validarScriptValidacionPagina(final DatosSesionFormularioInterno datosSesion,
			final boolean elemento);

	/**
	 * Realiza validacion de los campos de la pagina segun el script de validacion
	 * del campo.
	 *
	 * @param datosSesion
	 *                        Datos sesion
	 * @param elemento
	 *                        Indica si existe elemento
	 * @return En caso de error devuelve error de validación (se detiene en el
	 *         primer error de validación encontrado).
	 */
	MensajeValidacion validarScriptValidacionCampos(final DatosSesionFormularioInterno datosSesion,
			final boolean elemento);

}
