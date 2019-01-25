package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.List;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;

/**
 * Interfaz del controlador de formularios internos.
 *
 * Contiene la lógica del gestor de formularios interno.
 *
 * @author Indra
 *
 */
public interface ControladorGestorFormulariosInterno {

	/**
	 * Inicia sesión formulario.
	 *
	 * @param dis
	 *            Datos inicio sesión
	 * @return Ticket acceso formulario
	 */
	String iniciarSesion(DatosInicioSesionFormulario dis);

	/**
	 * Carga sesión formulario.
	 *
	 * @param ticket
	 *            Ticket
	 * @return Datos sesión
	 */
	DatosSesionFormularioInterno cargarSesion(String ticket);

	/**
	 * Carga página formulario.
	 *
	 * @param pDatosSesion
	 *            Datos sesión
	 * @return página
	 */
	PaginaFormulario cargarPagina(final DatosSesionFormularioInterno pDatosSesion);

	/**
	 * Guardar página.
	 *
	 * @param pDatosSesion
	 *            Datos sesion formulario
	 * @param valoresPagina
	 *            valores pagina
	 * @param accionPersonalizada
	 *            accion personalizada
	 * @return Resultado
	 */
	ResultadoGuardarPagina guardarPagina(DatosSesionFormularioInterno pDatosSesion, List<ValorCampo> valoresPagina,
			String accionPersonalizada);

	/**
	 * Obtiene datos finalización formulario.
	 *
	 * @param tck
	 *            Ticket acceso a datos finalización sesión formulario.
	 * @return datos finalización formulario
	 */
	DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(String tck);

	// TODO PENDIENTE FUNCIONES

}
