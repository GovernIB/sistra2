package es.caib.sistramit.core.service.component.formulario.interno.utils;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.service.component.script.plugins.formulario.ResEstadoCampo;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * Clase helper que contiene la logica de obtener la configuracion de los campos
 * de formulario.
 *
 * @author Indra
 *
 */
public interface ConfiguracionFormularioHelper {

	/**
	 * Crea configuración del campo.
	 *
	 * @param pCampoDef
	 *            Definición del campo
	 * @return Configuración campo
	 */
	ConfiguracionCampo obtenerConfiguracionCampo(final RComponente pCampoDef);

	/**
	 * Evalua estado dinamico de un campo.
	 *
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @param campoDef
	 *            Definicion campo
	 * @return Estado campo (nulo si no tiene estado dinamico)
	 */
	ResEstadoCampo evaluarEstadoCampo(final DatosSesionFormularioInterno pDatosSesion,
			final RComponente campoDef);

	/**
	 * Obtiene plantilla PDF de visualizacion.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @return plantilla PDF de visualizacion.
	 */
	byte[] obtenerPlantillaPdfVisualizacion(DatosSesionFormularioInterno pDatosSesion);

}
