package es.caib.sistra2.stg.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import es.caib.sistra2.stg.core.api.model.TestData;
import es.caib.sistra2.stg.core.api.service.TestService;
import es.caib.sistra2.stg.frontend.model.DialogResult;
import es.caib.sistra2.stg.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.stg.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.stg.frontend.model.types.TypeParametroDialogo;
import es.caib.sistra2.stg.frontend.util.UtilJSF;

/**
 * Mantenimiento de test.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTest extends ViewControllerBase {

	/**
	 * Enlace servicio.
	 */
	@Inject
	private TestService testService;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<TestData> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private TestData datoSeleccionado;

	/**
	 * Inicializacion.
	 */
	// @PostConstruct -> no se ven los parametros que se pasan por la request
	// public void init(ComponentSystemEvent event) { // para <f:event
	// type="preRenderView" . Habria que controlar manualmente con una vble boolean
	// si la vista ya se ha inicializado
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		listaDatos = testService.list(filtro);
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Filtra
		listaDatos = testService.list(filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Aplicado filtro");
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		// Muestra dialogo
		UtilJSF.openDialog(DialogTest.class, TypeModoAcceso.ALTA, null, true, 340, 140);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<String, String>();
		params.put(TypeParametroDialogo.ID.toString(), this.datoSeleccionado.getCodigo());
		UtilJSF.openDialog(DialogTest.class, TypeModoAcceso.EDICION, params, true, 340, 140);

	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		testService.remove(this.datoSeleccionado.getCodigo());
		// Refrescamos datos
		filtrar();
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Se ha eliminado fila");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No se ha seleccionado fila");
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				this.filtrar();
				// Mensaje
				message = UtilJSF.getLiteral("test.altaOk");
				break;
			case EDICION:
				// Actualizamos fila actual
				final String id = (String) respuesta.getResult();
				final TestData dataUpdated = testService.load(id);
				this.datoSeleccionado.setDescripcion(dataUpdated.getDescripcion());
				// Mensaje
				// TODO Ver acceso literales desde codigo
				message = "Update realizado";
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Prueba para abrir dialogo ubicado en otro package.
	 */
	public void abrirDialogoExterno() {
		// Muestra dialogo externo (en diferente directorio)
		// VIEW_NAME debe estar definido como regla de navegacion)
		UtilJSF.openDialog(DialogTestExterno.class, TypeModoAcceso.CONSULTA, null, true, 640, 340);
	}

	/**
	 * Retorno dialogo externo.
	 *
	 * @param event
	 *            resultado
	 */
	public void returnDialogoExterno(final SelectEvent event) {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Retorno dialogo externo");
	}

	/**
	 * Test para mostrar un mensaje.
	 */
	public void mostrarMensaje() {
		// Test muestra mensaje
		String id = "";
		if (datoSeleccionado != null) {
			id = datoSeleccionado.getCodigo();
		}
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Fila seleccionada: " + id);
	}

	/**
	 * Muestra un message dialog.
	 */
	public void mostrarMensajeDialog() {
		UtilJSF.showMessageDialog(TypeNivelGravedad.WARNING, "Titulo mensaje", "Mensaje");
	}

	/**
	 *
	 * @return
	 */
	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	public List<TestData> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(final List<TestData> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public TestData getDatoSeleccionado() {
		return datoSeleccionado;
	}

	public void setDatoSeleccionado(final TestData datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Prueba a crear una transaccion dentro de otra, haciendo que la principal
	 * falle y la secundaria termine.
	 */
	public void nuevaTransaccion() {
		testService.testTransactionNew();
		filtrar();
	}

	public void expired() {
		final HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		if (sesion != null) {
			sesion.setMaxInactiveInterval(1);
		}
	}

}
