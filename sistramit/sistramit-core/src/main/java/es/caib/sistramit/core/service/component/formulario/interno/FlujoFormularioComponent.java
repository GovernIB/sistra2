package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.model.system.FlujoTramitacionCacheIntf;

/**
 * Componente con estado con la sesión de formulario.
 *
 * @author Indra
 *
 */
public interface FlujoFormularioComponent extends FlujoTramitacionCacheIntf {

	/**
	 * Carga la sesión iniciada en el gestor de formularios.
	 *
	 * @param ticket
	 *            Ticket de acceso a la sesión del formulario
	 * @return id sesion formulario
	 */
	String cargarSesion(String ticket);

	/**
	 * Realiza la carga de la página actual.
	 *
	 * @return Página formulario (html, configuracion y datos)
	 */
	PaginaFormulario cargarPaginaActual();

	/**
	 * Pasa a página anterior.
	 *
	 */
	PaginaFormulario cargarPaginaAnterior();

	/**
	 * Evalua el cambio de una página de un formulario y calcula el valor los campos
	 * según los scripts del formulario.
	 *
	 * @param idCampo
	 *            Id campo que se esta modificando
	 * @param valoresPagina
	 *            Datos actuales de la página en el cliente
	 * @return Datos de la página resultantes que deben refrescarse en el cliente
	 */
	ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(String idCampo, List<ValorCampo> valoresPagina);

	/**
	 * Guarda los datos de la página.
	 *
	 * @param valoresPagina
	 *            Datos de la página
	 * @param accionPersonalizada
	 *            Accion personalizada (nulo en caso de que no existan acciones
	 *            personalizadas)
	 * @return Resultado de guardar la página: indica si se ha guardado bien, si no
	 *         ha pasado la validación y se ha generado un mensaje, etc. En caso de
	 *         llegar al fin del formulario se indicará el ticket de acceso al XML y
	 *         PDF generados.
	 */
	ResultadoGuardarPagina guardarPagina(List<ValorCampo> valoresPagina, final String accionPersonalizada);

	/**
	 * Cancela el rellenado del formulario.
	 *
	 */
	void cancelarFormulario();

	/**
	 * Deserializa valores devueltos por el formulario.
	 *
	 * @param valores
	 *            Valores serializados (id campo, valor serializado)
	 * @return Lista de valores campo
	 */
	List<ValorCampo> deserializarValoresCampos(Map<String, String> valores);

	/**
	 * Devuelve informacion de la sesion de formulario. No tiene que pasar por
	 * interceptor.
	 *
	 * @return Info sesion formulario
	 */
	SesionFormularioInfo obtenerInformacionFormulario();

}