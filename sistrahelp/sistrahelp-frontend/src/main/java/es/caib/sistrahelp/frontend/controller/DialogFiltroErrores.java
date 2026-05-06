package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

@ManagedBean
@ViewScoped
public class DialogFiltroErrores extends DialogControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	private String tipoEvento; //ERROR, FIRMA_FIN_OK, FIRMA_FIN_KO, PAGO_ELECTRONICO_VERIFICADO, PAGO_ELECTRONICO_NO_VERIFICADO, PAGO_CANCELADO

	private List<String> opcionesFijas; //Propiedades para FIRMA/PAGO

	private boolean eventoPlataforma;

    private boolean checkTipoError;
    private boolean checkTipoFirma = true;
    private boolean checkTipoPago = true;

    private boolean checkTextoTraza;

    private List<String> listaTiposError;

    private List<String> listaSeleccionados;
	private List<String> listaErroresFiltrados;
    private String erroresSeleccionadosInit;

	private String filtroTablaErrores;
	private String filtroTextoTraza;

	private String portapapeles;

	private String errorCopiar;

	private FiltroAuditoriaTramitacion filtro = new FiltroAuditoriaTramitacion();

	/**
	 * Inicialización.
	 */
	public void init() {
		/*final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {

		}*/

		if (tipoEvento == null) return;

		try {
			Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

			this.filtro.setNif(requestParams.get("fNif"));
		    this.filtro.setIdSesionTramitacion(requestParams.get("fSesion"));
		    this.filtro.setNombre(requestParams.get("fNombre"));
		    this.filtro.setIdTramite(requestParams.get("fIdTra"));
		    this.filtro.setCodSia(requestParams.get("fSia"));
		    this.filtro.setIdProcedimientoCP(requestParams.get("fProc"));

		    String areaParam = requestParams.get("fArea");
		    if (areaParam != null && !areaParam.isEmpty()) {
		        this.filtro.setListaAreas(Arrays.asList(areaParam.split(",")));
		    }

		    if (requestParams.get("fVer") != null)
		        this.filtro.setVersionTramite(Integer.parseInt(requestParams.get("fVer")));
		    if (requestParams.get("fDesde") != null)
		        this.filtro.setFechaDesde(new Date(Long.parseLong(requestParams.get("fDesde"))));
		    if (requestParams.get("fHasta") != null)
		        this.filtro.setFechaHasta(new Date(Long.parseLong(requestParams.get("fHasta"))));


	    } catch (Exception e) {
	        // Si falla, el filtro se queda a null y la consulta no filtrará por los campos
	    }

	    if ("ERROR".equals(tipoEvento)) {
	    	this.checkTipoError = true;
	        this.checkTipoFirma = false;
	        this.checkTipoPago = false;

	        listaTiposError = helpDeskService.obtenerListaErroresAuditoria(eventoPlataforma, filtro);

	        if(erroresSeleccionadosInit != null) {
	        	listaSeleccionados = Arrays.asList(erroresSeleccionadosInit.split(";"));
	        } else {
	        	listaSeleccionados = listaTiposError;
	        }

	    } else if (isModoFirma()) {
	    	this.checkTipoError = false;
	        this.checkTipoFirma = true;
	        this.checkTipoPago = false;

	        listaTiposError = helpDeskService.obtenerListaMetodosFirma(tipoEvento, filtro);

	        if(erroresSeleccionadosInit != null && !erroresSeleccionadosInit.isEmpty()) {
	        	listaSeleccionados = Arrays.asList(erroresSeleccionadosInit.split(";"));
	        } else {
	        	listaSeleccionados = new ArrayList<>(listaTiposError != null ? listaTiposError : new ArrayList<>());
	        }

	    } else if (isModoPago()) {
	    	this.checkTipoError = false;
	        this.checkTipoFirma = false;
	        this.checkTipoPago = true;

	        listaTiposError = helpDeskService.obtenerListaMetodosPago(tipoEvento, filtro);

	        if(erroresSeleccionadosInit != null && !erroresSeleccionadosInit.isEmpty()) {
	        	listaSeleccionados = Arrays.asList(erroresSeleccionadosInit.split(";"));
	        } else {
	        	listaSeleccionados = new ArrayList<>(listaTiposError != null ? listaTiposError : new ArrayList<>());
	        }
	    }
	}

	public void aceptar() {

		if (this.filtroTablaErrores != null && !this.filtroTablaErrores.trim().isEmpty()
				&& this.listaSeleccionados != null) {

			String textoFiltro = this.filtroTablaErrores.toLowerCase();
			List<String> seleccionadosVisibles = new ArrayList<>();


			for (String codigo : this.listaSeleccionados) {
				String etiquetaVisible = obtenerEtiqueta(codigo).toLowerCase();

				if (etiquetaVisible.contains(textoFiltro)) {
					seleccionadosVisibles.add(codigo);
				}
			}

			this.listaSeleccionados = seleccionadosVisibles;
		}

		// Se lanza aviso en caso de que no se seleccione ninguna opción de filtro
		if (!"ERROR".equals(this.tipoEvento)) {
			if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noSeleccion"));
				return;
			}
		}

		else {
			if (!checkTipoError && !checkTextoTraza) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noFiltro"));
				return;
			}

			// Se lanza aviso en caso de no seleccionar ningún tipo de error si se ha marcado el checkbox
			// de filtro por tipo de error
			if (checkTipoError) {
				if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noSeleccion"));
					return;
				}

				/*boolean hayCoincidencia = listaErroresFiltrados != null && listaErroresFiltrados.stream().anyMatch(listaSeleccionados::contains);
				if (filtroTablaErrores != null && ((listaErroresFiltrados == null || listaErroresFiltrados.isEmpty()) || !hayCoincidencia)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noSeleccion"));
					return;
				}*/
			}

			// Se lanza aviso en caso de no introducir texto en el filtro de texto si se ha marcado el checkbox
			// de filtro por texto en la traza
			if (checkTextoTraza && (filtroTextoTraza == null || filtroTextoTraza.isEmpty())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFiltroErrores.error.noTexto"));
				return;
			}
		}



		DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);

		Map<String, Object> valores = new HashMap<>();

		valores.put("tipoEvento", tipoEvento);
		valores.put("tiposErrorSeleccionados", listaSeleccionados);
		valores.put("filtroTablaErrores", filtroTablaErrores);

		if ("ERROR".equals(tipoEvento)) {
			valores.put("checkTipoError", checkTipoError);
			valores.put("checkTextoTraza", checkTextoTraza);
			valores.put("filtroTextoTraza", filtroTextoTraza);
		} else {
			valores.put("checkTipoError", false);
			valores.put("checkTextoTraza", false);
		}

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

	public boolean isModoFirma() {
		return "FIRMA_FIN_OK".equals(this.tipoEvento)
				|| "FIRMA_FIN_KO".equals(this.tipoEvento);
	}

	public boolean isModoPago() {
		return "PAGO_ELECTRONICO_VERIFICADO".equals(this.tipoEvento)
					|| "PAGO_ELECTRONICO_NO_VERIFICADO".equals(this.tipoEvento)
					|| "PAGO_CANCELADO".equals(this.tipoEvento);
	}

    /**
     * Traduce el código a texto legible.
     */
	public String obtenerEtiqueta(String codigo) {
		if (codigo == null) return "";

		// Si estamos en el modo de PAGO, traducimos las siglas
		if (isModoPago()) {
			String key = "";
			switch (codigo) {
				case "TJ":  key = "entidadPago.TJ.titulo"; break;
				case "BZ":  key = "entidadPago.BZ.titulo"; break;
				case "EXT": key = "entidadPago.EXT.titulo"; break;
				case "BM":  key = "entidadPago.BM.titulo"; break;
				case "LC":  key = "entidadPago.LC.titulo"; break;
				case "BB":  key = "entidadPago.BB.titulo"; break;
				case "MKP": key = "entidadPago.MKP.titulo"; break;
				case "MKX": key = "entidadPago.MKX.titulo"; break;
				default: return codigo;
			}
			try {
				return UtilJSF.getLiteral(key);
			} catch (Exception e) {
				return codigo;
			}
		}

		if (isModoFirma()) {
			String valor = codigo.toUpperCase();

			if (valor.contains("AUTOFIRM")) {
				return "Autofirm@";
			}
			else if (valor.contains("CL@VE")) {
				return "Cl@veFirm@";
			}
			else if (valor.contains("AGIL") || valor.contains("ÀGIL") || valor.contains("ÁGIL")) {
				return "Firma Àgil";
			}

			if (codigo.contains(" - ")) {
				String[] partes = codigo.split(" - ");
				if (partes.length > 1) {
					return partes[partes.length - 1].trim();
				}
			}
		}

		return codigo;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr(AjaxBehaviorEvent event) {

		if (StringUtils.isEmpty(portapapeles)) {
			copiadoErr(event);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
     * Devuelve la clave del properties para el título según el evento.
     */
    public String getTituloPantalla() {
        if (isModoFirma()) {
            return "dialogFiltroErrores.titulo.firma";
        } else if (isModoPago()) {
            return "dialogFiltroErrores.titulo.pago";
        }
        // Por defecto (ERROR u otros)
        return "dialogFiltroErrores.titulo";
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
	public void copiadoErr(AjaxBehaviorEvent event) {
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

	public String getTipoEvento() {
		return tipoEvento;
	}

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

	public List<String> getOpcionesFijas() {
		return opcionesFijas;
	}

	public void setOpcionesFijas(List<String> opcionesFijas) {
		this.opcionesFijas = opcionesFijas;
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

    public List<String> getListaSeleccionados() {
        return listaSeleccionados;
    }

    public void setListaSeleccionados(List<String> listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
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

	public boolean isCheckTipoFirma() {
		return checkTipoFirma;
	}

	public void setCheckTipoFirma(boolean checkTipoFirma) {
		this.checkTipoFirma = checkTipoFirma;
	}

	public boolean isCheckTipoPago() {
		return checkTipoPago;
	}

	public void setCheckTipoPago(boolean checkTipoPago) {
		this.checkTipoPago = checkTipoPago;
	}

	public FiltroAuditoriaTramitacion getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroAuditoriaTramitacion filtro) {
		this.filtro = filtro;
	}
}
