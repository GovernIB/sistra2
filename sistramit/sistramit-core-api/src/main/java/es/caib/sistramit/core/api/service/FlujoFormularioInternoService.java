package es.caib.sistramit.core.api.service;

import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;

/**
 *
 * Interfaz del gestor de formularios interno.
 *
 * @author Indra
 *
 */
public interface FlujoFormularioInternoService {

	/**
	 * Carga la sesión iniciada en el gestor de formularios.
	 *
	 * @param ticket
	 *                   Ticket de acceso a la sesión del formulario
	 * @return id sesion formulario
	 */
	String cargarSesion(String ticket);

	/**
	 * Inicializa sesión formulario (separada de la carga de sesión para poder
	 * registrar errores en auditoría).
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 */
	void inicializarSesion(String idSesionFormulario);

	/**
	 * Realiza la carga de la página actual.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @return Página formulario (html, configuracion y datos)
	 */
	PaginaFormulario cargarPaginaActual(String idSesionFormulario);

	/**
	 * Pasa a página anterior.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @param valores
	 *                               valores actuales pagina
	 */
	PaginaFormulario cargarPaginaAnterior(String idSesionFormulario, List<ValorCampo> valores);

	/**
	 * Evalua el cambio de una página de un formulario y calcula el valor los campos
	 * según los scripts del formulario.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @param idCampo
	 *                               Id campo que se esta modificando
	 * @param valoresPagina
	 *                               Datos actuales de la página en el cliente
	 * @return Datos de la página resultantes que deben refrescarse en el cliente
	 */
	ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(String idSesionFormulario, String idCampo,
			List<ValorCampo> valoresPagina);

	/**
	 * Guarda los datos de la página.
	 *
	 * @param idSesionFormulario
	 *                                id sesion formulario
	 * @param valoresPagina
	 *                                Datos de la página
	 * @param accionPersonalizada
	 *                                Accion personalizada (nulo en caso de que no
	 *                                existan acciones personalizadas)
	 * @return Resultado de guardar la página: indica si se ha guardado bien, si no
	 *         ha pasado la validación y se ha generado un mensaje, etc. En caso de
	 *         llegar al fin del formulario se indicará el ticket de acceso al XML y
	 *         PDF generados.
	 */
	ResultadoGuardarPagina guardarPagina(final String idSesionFormulario, List<ValorCampo> valoresPagina,
			final String accionPersonalizada);

	/**
	 * Guarda los datos de la página y sale del formulario sin finalizar.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @param valoresPagina
	 *                               Datos de la página
	 * @return Resultado de guardar la página: Se indicará el ticket de acceso al
	 *         XML.
	 */
	ResultadoGuardarPagina guardarSalirPagina(final String idSesionFormulario, List<ValorCampo> valoresPagina);

	/**
	 * Cancela el rellenado del formulario.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 */
	void cancelarFormulario(String idSesionFormulario);

	/**
	 * Deserializa valores devueltos por el formulario.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @param valores
	 *                               Valores serializados (id campo, valor
	 *                               serializado)
	 * @return Lista de valores campo
	 */
	List<ValorCampo> deserializarValoresCampos(String idSesionFormulario, Map<String, String> valores);

	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por
	// interceptor de auditoria.
	// -------------------------------------------------------------------------------------------

	/**
	 * Devuelve informacion de la sesion de formulario. No tiene que pasar por
	 * interceptor.
	 *
	 * @param idSesionFormulario
	 *                               id sesion formulario
	 * @return Info sesion formulario
	 */
	SesionFormularioInfo obtenerInformacionFormulario(final String idSesionFormulario);

}
