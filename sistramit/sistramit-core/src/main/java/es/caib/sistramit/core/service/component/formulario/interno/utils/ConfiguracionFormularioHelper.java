package es.caib.sistramit.core.service.component.formulario.interno.utils;

import java.util.List;

import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistramit.core.api.model.formulario.AccionFormulario;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
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
	 * Calcula el estado dinamico de los campos.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @return Configuracion dinamica de campos
	 */
	List<ConfiguracionModificadaCampo> evaluarEstadoCamposPagina(DatosSesionFormularioInterno pDatosSesion);

	/**
	 * Evalua estado dinamico de un campo.
	 *
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @param campoDef
	 *            Definicion campo
	 * @return Estado campo (nulo si no tiene estado dinamico)
	 */
	ResEstadoCampo evaluarEstadoCampo(final DatosSesionFormularioInterno pDatosSesion, final RComponente campoDef);

	/**
	 * Evalua acciones a mostrar en pagina actual.
	 *
	 * @param datosSesion
	 *            Datos sesion
	 * @return lista acciones
	 */
	List<AccionFormulario> evaluarAccionesPaginaActual(DatosSesionFormularioInterno datosSesion);

	/**
	 * Obtiene plantilla PDF de visualizacion.
	 *
	 * @param pDatosSesion
	 *            Datos sesion
	 * @return plantilla PDF de visualizacion.
	 */
	RPlantillaFormulario obtenerPlantillaPdfVisualizacion(DatosSesionFormularioInterno pDatosSesion);

}
