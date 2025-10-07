package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.*;

@ManagedBean
@ViewScoped
public class DialogFiltroErrores extends DialogControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	private boolean eventoPlataforma;

    private boolean checkTipoError;

    private boolean checkTextoTraza;

    private List<String> listaTiposError;

    private List<String> listaErroresSeleccionados;
	private List<String> listaErroresFiltrados;
    private String erroresSeleccionadosInit;

	private String filtroTablaErrores;
	private String filtroTextoTraza;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		/*final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {

		}*/

        listaTiposError = helpDeskService.obtenerListaErroresAuditoria(eventoPlataforma);

		if(erroresSeleccionadosInit != null) {
			listaErroresSeleccionados = Arrays.asList(erroresSeleccionadosInit.split(";"));
		} else {
			listaErroresSeleccionados = listaTiposError;
		}

        /*listaTiposError.add("Error A");
        listaTiposError.add("Error B");
        listaTiposError.add("Error C");*/

	}

	public void aceptar() {
		// Se lanza aviso en caso de que no se seleccione ninguna opción de filtro
		if (!checkTipoError && !checkTextoTraza) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noFiltro"));
			return;
		}

		// Se lanza aviso en caso de no seleccionar ningún tipo de error si se ha marcado el checkbox
		// de filtro por tipo de error
		if (checkTipoError) {
			if (listaErroresSeleccionados == null || listaErroresSeleccionados.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noSeleccion"));
				return;
			}

			boolean hayCoincidencia = listaErroresFiltrados != null && listaErroresFiltrados.stream().anyMatch(listaErroresSeleccionados::contains);
			if (filtroTablaErrores != null && ((listaErroresFiltrados == null || listaErroresFiltrados.isEmpty()) || !hayCoincidencia)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noSeleccion"));
				return;
			}
		}

		// Se lanza aviso en caso de no introducir texto en el filtro de texto si se ha marcado el checkbox
		// de filtro por texto en la traza
		if (checkTextoTraza && (filtroTextoTraza == null || filtroTextoTraza.isEmpty())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noTexto"));
			return;
		}

		DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);

		Map<String, Object> valores = new HashMap<>();

		valores.put("checkTipoError", checkTipoError);
		valores.put("checkTextoTraza", checkTextoTraza);
		valores.put("tiposErrorSeleccionados", listaErroresSeleccionados);
		valores.put("filtroTablaErrores", filtroTablaErrores);
		valores.put("filtroTextoTraza", filtroTextoTraza);

		result.setResult(valores);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewAuditoriaTramites.copiar"));
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoFiltroErrores");
	}

	public boolean isEventoPlataforma() {
		return eventoPlataforma;
	}

	public void setEventoPlataforma(boolean eventoPlataforma) {
		this.eventoPlataforma = eventoPlataforma;
	}

	public boolean isCheckTipoError() {
		return checkTipoError;
	}

	public void setCheckTipoError(boolean checkTipoError) {
		this.checkTipoError = checkTipoError;
	}

	public boolean isCheckTextoTraza() {
		return checkTextoTraza;
	}

	public void setCheckTextoTraza(boolean checkTextoTraza) {
		this.checkTextoTraza = checkTextoTraza;
	}

	public List<String> getListaTiposError() {
		return listaTiposError;
	}

	public void setListaTiposError(List<String> listaTiposError) {
		this.listaTiposError = listaTiposError;
	}

    public List<String> getListaErroresSeleccionados() {
        return listaErroresSeleccionados;
    }

    public void setListaErroresSeleccionados(List<String> listaErroresSeleccionados) {
        this.listaErroresSeleccionados = listaErroresSeleccionados;
    }

	public List<String> getListaErroresFiltrados() {
		return listaErroresFiltrados;
	}

	public void setListaErroresFiltrados(List<String> listaErroresFiltrados) {
		this.listaErroresFiltrados = listaErroresFiltrados;
	}

	public String getFiltroTablaErrores() {
		return filtroTablaErrores;
	}

	public void setFiltroTablaErrores(String filtroTablaErrores) {
		this.filtroTablaErrores = filtroTablaErrores;
	}

	public String getFiltroTextoTraza() {
		return filtroTextoTraza;
	}

	public void setFiltroTextoTraza(String filtroTextoTraza) {
		this.filtroTextoTraza = filtroTextoTraza;
	}

	public String getErroresSeleccionadosInit() {
		return erroresSeleccionadosInit;
	}

	public void setErroresSeleccionadosInit(String erroresSeleccionadosInit) {
		this.erroresSeleccionadosInit = erroresSeleccionadosInit;
	}
}
