package es.caib.sistramit.core.service.component.formulario.interno.utils;

import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.ResultadoValidacion;

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
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @return Mensaje de error (nulo si pasa validacion)
	 */
	ResultadoValidacion validarConfiguracionCampos(final DatosSesionFormularioInterno pDatosSesion);

	/**
	 * Valida página al guardar.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @return En caso de encontrar error de validación devuelve mensaje error (null
	 *         si no hay error de validación).
	 */
	ResultadoValidacion validarScriptValidacionPagina(final DatosSesionFormularioInterno pDatosSesion);

	/**
	 * Realiza validacion de los campos de la pagina segun el script de validacion
	 * del campo.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @return En caso de error devuelve error de validación.
	 */
	ResultadoValidacion validarScriptValidacionCampos(final DatosSesionFormularioInterno pDatosSesion);

}
