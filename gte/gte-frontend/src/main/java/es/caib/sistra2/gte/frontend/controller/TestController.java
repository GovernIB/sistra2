package es.caib.sistra2.gte.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.api.service.TestService;
import es.caib.sistra2.gte.frontend.model.DialogResult;
import es.caib.sistra2.gte.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.gte.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.gte.frontend.model.types.TypeParametroDialogo;
import es.caib.sistra2.gte.frontend.util.UtilJSF;



// Spring tags
//@Named
//@Scope("view")

// Faces tags
@ManagedBean
@ViewScoped
public class TestController {

	/**
	 * Enlace servicio.
	 */
	// @Autowired  --> Spring tag
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
	//@PostConstruct -> no se ven los parametros que se pasan por la request
	//  public void init(ComponentSystemEvent event) {  // para <f:event type="preRenderView" . Habria que controlar manualmente con una vble boolean si la vista ya se ha inicializado
	public void init() {
		listaDatos = testService.list(filtro);
	}
	
	
	public void filtrar() {
		// Filtra
		listaDatos = testService.list(filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Aplicado filtro");		
	}
	
	
	public void nuevo() {
		// Muestra dialogo
		UtilJSF.openDialog(TestDialogController.VIEW_NAME, TypeModoAcceso.ALTA, null, true, 640, 180);
	}
	
	public void editar() {
		
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) return;
		
		// Muestra dialogo
		Map<String, String> params = new HashMap<String, String>();
		params.put(TypeParametroDialogo.ID.toString(), this.datoSeleccionado.getCodigo());
		UtilJSF.openDialog(TestDialogController.VIEW_NAME, TypeModoAcceso.EDICION, params, true, 640, 180);
		        
	}
	
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) return;
		// Eliminamos
		testService.remove(this.datoSeleccionado.getCodigo());		
		// Refrescamos datos
		filtrar();		
		// Mostramos mensaje		      
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Se ha eliminado fila");		
	}

	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, "No se ha seleccionado fila");	
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}
	
	
	// Retorno de dialogo
	public void returnDialogo(SelectEvent event) {
		
		DialogResult respuesta = (DialogResult) event.getObject();
		
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
				String id = (String) respuesta.getResult();
				TestData dataUpdated = testService.load(id);
				this.datoSeleccionado.setDescripcion(dataUpdated.getDescripcion());
				// Mensaje
				// TODO Ver acceso literales desde codigo
				message = "Update realizada (falta coger desde literales)";
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

	public void abrirDialogoExterno() {
		// Muestra dialogo externo (en diferente directorio)
		// VIEW_NAME debe estar definido como regla de navegacion)
		UtilJSF.openDialog(TestDialogExternoController.VIEW_NAME, TypeModoAcceso.CONSULTA, null, true, 640, 180);
	}
	
	
	// Retorno de dialogs
	public void returnDialogoExterno(SelectEvent event) {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Retorno dialogo externo");		
	}	
	
	
	public void mostrarMensaje() {
		// Test muestra mensaje
		String id = "";
		if (datoSeleccionado != null) {
			id = datoSeleccionado.getCodigo();
		}
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Fila seleccionada: " + id);		
	}
	
	public void mostrarMensajeDialog() {
		UtilJSF.showMessageDialog(TypeNivelGravedad.WARNING, "Titulo mensaje", "Mensaje");
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<TestData> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List<TestData> listaDatos) {
		this.listaDatos = listaDatos;
	}	


	public TestData getDatoSeleccionado() {
		return datoSeleccionado;
	}


	public void setDatoSeleccionado(TestData datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}


}


