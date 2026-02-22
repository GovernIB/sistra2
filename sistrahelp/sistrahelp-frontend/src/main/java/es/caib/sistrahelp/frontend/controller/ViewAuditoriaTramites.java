package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypeIniciadoPor;
import es.caib.sistrahelp.core.api.service.EventoService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.EventoAuditoriaTramitacionLazyDataModel;
import es.caib.sistrahelp.frontend.model.NavegacionEventos;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * La clase ViewAuditoriaTramites.
 */
@ManagedBean
@ViewScoped
public class ViewAuditoriaTramites extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;


	@Inject
	private EventoService eventoService;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * lista datos.
	 */
	private EventoAuditoriaTramitacionLazyDataModel listaDatos;

	/**
	 * dato seleccionado.
	 */
	private EventoAuditoriaTramitacion datoSeleccionado;

	private Entidad entidad;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaTramitacion filtros;

	private List<TypeEvento> tiposEventos;

	private List<TypeIniciadoPor> tiposIniciadoPor = Arrays.asList(TypeIniciadoPor.values());

	private String filtroArea;

	private String portapapeles;

	private String errorCopiar;

	private String idSesionParam;

	private String nifParam;

	private String idTramiteECM;

	private String versionTramiteECM;

	private String fechaDesdeECM;

	private String fechaHastaECM;

	private String horaDesdeECM;

	private String layout;

	private String excepcionECM;

	private String eventoHA;

	private String fechaDesdeHA;

	private String fechaHastaHA;

	private Boolean esDialogHA;

	private Boolean esDialog;

	private Boolean esDialogParams;

	private String idSesionCorreoIncidencia;

	private boolean comboIniciado;

	// Filtros dialogo errores
	private boolean checkTipoErrorDE = true;
	private boolean checkTextoTrazaDE = false;
	private String erroresSeleccionadosDE;
	private String filtroTextoTrazaDE;
    private String filtroTablaErroresDE;
    private String filtroTablaFirmaDE;
    private String filtroTablaPagoDE;

    private String firmasSeleccionadasDE;
    private String pagosSeleccionadosDE;

    private TypeEvento eventoFirmaGuardado;
    private TypeEvento eventoPagoGuardado;
    private TypeEvento eventoUltimaBusqueda = null;

	/**
	 * Inicializa.
	 */
	public void init() {
		UtilJSF.verificarAcceso();

		paginacion = UtilJSF.getPaginacion("viewAuditoriaTramites");

		// Titulo pantalla

		esDialog = esDialog == null ? false : esDialog;
		esDialogParams = esDialogParams == null ? false : esDialogParams;
		esDialogHA = esDialogHA != null && esDialogHA;

		filtros = new FiltroAuditoriaTramitacion(convierteListaAreas(), false);

		// cargamos los eventos quitando el de purga
		tiposEventos = eventoService.getTiposEvento(entidad);
		tiposEventos.removeAll(Arrays.asList(TypeEvento.PROCESO_PURGA, TypeEvento.INV_EJE, TypeEvento.INV_REQ));

		filtros.setTiposEventos(new ArrayList<>(tiposEventos));


		if (idSesionParam != null && !idSesionParam.isEmpty()) {
			filtros.setIdSesionTramitacion(idSesionParam);
			filtros.setFechaDesde(null);
			filtros.setSortField("fecha");
			filtros.setSortOrder("ASCENDING");
		}

		if (nifParam != null && !nifParam.isEmpty()) {
			filtros.setNif(nifParam);
			filtros.setFechaDesde(null);
			filtros.setSortField("fecha");
			filtros.setSortOrder("ASCENDING");
		}

		if (idTramiteECM != null && !idTramiteECM.isEmpty() && versionTramiteECM != null
				&& !versionTramiteECM.isEmpty()) {
			filtros.setIdTramite(idTramiteECM);
			filtros.setVersionTramite(Integer.parseInt(versionTramiteECM));
			filtros.setEvento(TypeEvento.ERROR);

			if (horaDesdeECM != null && !horaDesdeECM.isEmpty()) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date fecha = dateFormat.parse(horaDesdeECM);
					filtros.setFechaDesde(fecha);
				} catch (java.text.ParseException e) {

				}
			} else {

				if (fechaDesdeECM != null && !fechaDesdeECM.isEmpty()) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date fecha = dateFormat.parse(fechaDesdeECM);
						filtros.setFechaDesde(fecha);
					} catch (java.text.ParseException e) {

					}
				}

				if (fechaHastaECM != null && !fechaHastaECM.isEmpty()) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date fecha = dateFormat.parse(fechaHastaECM);
						filtros.setFechaHasta(fecha);
					} catch (java.text.ParseException e) {

					}
				}
			}
		}
		if (esDialog || esDialogParams) {
			layout = "../layout/dialogViewLayout.xhtml";
			if (esDialog) {
				setLiteralTituloPantalla(idTramiteECM + " / " + versionTramiteECM + " - " + excepcionECM);
			} else if (idSesionParam != null) {
				setLiteralTituloPantalla(idSesionParam);
			} else if (nifParam != null) {
				setLiteralTituloPantalla(nifParam);
			}
			filtros.setExcepcion(excepcionECM);
		} else if (esDialogHA) {
            layout = "../layout/dialogViewLayout.xhtml";
            setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
            if (fechaDesdeHA != null && !fechaDesdeHA.isEmpty()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date fecha = dateFormat.parse(fechaDesdeHA);
                    filtros.setFechaDesde(fecha);
                } catch (java.text.ParseException e) {

                }
            }

            if (fechaHastaHA != null && !fechaHastaHA.isEmpty()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date fecha = dateFormat.parse(fechaHastaHA);
                    filtros.setFechaHasta(fecha);
                } catch (java.text.ParseException e) {

                }
            }

            if (eventoHA != null && !eventoHA.isEmpty()) {
                try {
                    TypeEvento evento = TypeEvento.valueOf(eventoHA);
                    filtros.setEvento(evento);
                } catch (IllegalArgumentException e) {

                }
            }
        } else {
			layout = "../layout/mainLayout.xhtml";
			setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		}
		if(idSesionCorreoIncidencia != null && !idSesionCorreoIncidencia.isEmpty()) {
			filtros.setIdSesionTramitacion(idSesionCorreoIncidencia);
			filtros.setSortField("fecha");
			filtros.setSortOrder("DESCENDING");
		}
	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		TypeEvento eventoActual = this.filtros.getEvento();

		if (this.eventoUltimaBusqueda != null && !this.eventoUltimaBusqueda.equals(eventoActual)) {
			resetFiltroErrores(true);
		} else {
			resetFiltroErrores(false);
		}
		this.eventoUltimaBusqueda = eventoActual;

		// Normaliza filtro
		normalizarFiltro();

		// Reinicia la fila del dataTable
		final DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form:dataTable");
		dataTable.setFirst(0);

		// Buscar
		this.buscar();
	}

	private void normalizarFiltro() {
		filtros.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtros.setNif(StringUtils.trim(filtros.getNif()));
		filtros.setNombre(StringUtils.trim(filtros.getNombre()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtros.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
		filtros.setCodSia(filtros.getCodSia());
	}

    private void resetFiltroErrores(boolean forzarBorrado) {
    	TypeEvento eventoActual = this.filtros.getEvento();
    	List<TypeEvento> eventosEspeciales = Arrays.asList(TypeEvento.ERROR, TypeEvento.FIRMA_FIN_OK, TypeEvento.FIRMA_FIN_KO, TypeEvento.PAGO_ELECTRONICO_VERIFICADO, TypeEvento.PAGO_ELECTRONICO_NO_VERIFICADO, TypeEvento.PAGO_CANCELADO);

    	if (forzarBorrado || !eventosEspeciales.contains(eventoActual)) {

    		// Limpiarmos ERROR si el evento actual no es Error
    		if (!TypeEvento.ERROR.equals(eventoActual)) {
				setCheckTipoErrorDE(false);
				setCheckTextoTrazaDE(false);
				setErroresSeleccionadosDE(null);
				setFiltroTextoTrazaDE(null);
				filtros.setTiposErrores(null);
				filtros.setTextoTraza(null);
				filtroTablaErroresDE = null;
			}

			// Limpiamos FIRMA si el evento actual no es Firma, o si es una Firma DIFERENTE a la guardada
			boolean esFirma = TypeEvento.FIRMA_FIN_OK.equals(eventoActual) || TypeEvento.FIRMA_FIN_KO.equals(eventoActual);
			if (!esFirma || (eventoFirmaGuardado != null && !eventoFirmaGuardado.equals(eventoActual))) {
				setFirmasSeleccionadasDE(null);
				filtros.setTiposFirma(null);
				filtroTablaFirmaDE = null;
				eventoFirmaGuardado = null;
			}

			// Limpiamos PAGO si el evento actual no es Pago, o si es un Pago DIFERENTE al guardado
			boolean esPago = TypeEvento.PAGO_ELECTRONICO_VERIFICADO.equals(eventoActual) || TypeEvento.PAGO_ELECTRONICO_NO_VERIFICADO.equals(eventoActual) || TypeEvento.PAGO_CANCELADO.equals(eventoActual);
			if (!esPago || (eventoPagoGuardado != null && !eventoPagoGuardado.equals(eventoActual))) {
				setPagosSeleccionadosDE(null);
				filtros.setTiposPago(null);
				filtroTablaPagoDE = null;
				eventoPagoGuardado = null;
			}
    	}
    }

	/** Genera texto a copiar **/
	public void generarTxt() {
		/*
		 * if (this.datoSeleccionado != null) { EventoAuditoriaTramitacion eat =
		 * this.datoSeleccionado; String txt = ""; txt += "Event: " +
		 * eat.getTipoEvento().toString() + " - " + UtilJSF.getLiteral("typeEvento." +
		 * eat.getTipoEvento()); SimpleDateFormat sdf = new
		 * SimpleDateFormat("dd/MM/yyyy HH:mm"); txt += "\nData Inici: " +
		 * sdf.format(eat.getFecha()); txt += "\nId Sessió: " +
		 * eat.getIdSesionTramitacion(); txt += "\nNIF: " + eat.getNif(); if
		 * (eat.getNombre() == null) { eat.setNombre(""); } if (eat.getApellido1() ==
		 * null) { eat.setApellido1(""); } if (eat.getApellido2() == null) {
		 * eat.setApellido2(""); } txt += "\nNom: " +
		 * eat.getNombre().concat(" ").concat(eat.getApellido1()).concat(" ").concat(eat
		 * .getApellido2()); txt += "\nTràmit: " + split(eat.getIdTramite()); txt +=
		 * "\nÁrea: " + eat.getArea(); txt += "\nVersió: " + eat.getVersionTramite();
		 * txt += "\nCod Proc Cat: " + eat.getIdProcedimientoCP(); txt += "\nCod SIA: "
		 * + eat.getIdProcedimientoSIA(); txt += "\nDescripció: " +
		 * eat.getDescripcion(); txt += "\nError: " + eat.getCodigoError();
		 *
		 * txt = txt.replaceAll("null", "");
		 *
		 * UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
		 * UtilJSF.getLiteral("info.copiado.ok"));
		 *
		 * PrimeFaces.current().
		 * executeScript("document.focus; navigator.clipboard.writeText(`" + txt +
		 * "`);"); } else { UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
		 * UtilJSF.getLiteral("warning.copiar")); }
		 */
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
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
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * Abre dialogo sesión
	 */
	public void pantallaSesion() {
		final Map<String, String> params = new HashMap<>();

		String idSesion = datoSeleccionado.getIdSesionTramitacion();

		params.put("idSesionParam", idSesion);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 703);
	}

	/**
	 * Abre dialogo nif
	 */
	public void pantallaNif() {
		final Map<String, String> params = new HashMap<>();

		String nif = datoSeleccionado.getNif();

		params.put("nifParam", nif);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 703);
	}

	/**
	 * Abre dialogo errores
	 */
	public void abrirDialogErrores() {
        Map<String, String> params = new HashMap<>();

        TypeEvento evento = this.filtros.getEvento();

        params.put("tipoEvento", evento != null ? evento.name() : "");

        if (TypeEvento.ERROR.equals(evento)) {
        	params.put("eventoPlataforma", Boolean.FALSE.toString());
        	params.put("checkTipoError", String.valueOf(checkTipoErrorDE));
        	params.put("checkTextoTraza", String.valueOf(checkTextoTrazaDE));
        	if (erroresSeleccionadosDE != null) {
        		params.put("erroresSeleccionadosInit", erroresSeleccionadosDE);
        	}
        	if (filtroTextoTrazaDE != null) {
        		params.put("filtroTextoTraza", filtroTextoTrazaDE);
        	}
        	if (filtroTablaErroresDE != null) {
        		params.put("filtroTablaErrores", filtroTablaErroresDE);
        	}
        }
        else if (TypeEvento.FIRMA_FIN_OK.equals(evento) || TypeEvento.FIRMA_FIN_KO.equals(evento)) {
        	if (evento.equals(eventoFirmaGuardado)) {
        		if (firmasSeleccionadasDE != null) {
        			params.put("erroresSeleccionadosInit",  firmasSeleccionadasDE);
        		}
        		if (filtroTablaFirmaDE != null) {
        			params.put("filtroTablaErrores", filtroTablaFirmaDE);
        		}
        	}
        }
        else if (TypeEvento.PAGO_ELECTRONICO_VERIFICADO.equals(evento) || TypeEvento.PAGO_ELECTRONICO_NO_VERIFICADO.equals(evento) || TypeEvento.PAGO_CANCELADO.equals(evento)) {
        	if (evento.equals(eventoPagoGuardado)) {
        		if (pagosSeleccionadosDE != null) {
        			params.put("erroresSeleccionadosInit", pagosSeleccionadosDE);
        		}
        		if (filtroTablaPagoDE != null) {
        			params.put("filtroTablaErrores", filtroTablaPagoDE);
        		}
        	}
        }

		UtilJSF.openDialog(DialogFiltroErrores.class, TypeModoAcceso.CONSULTA, params.isEmpty() ? null : params, true, 650, 662);
	}

    public void onDialogErroresReturn(final SelectEvent event) {
        if (event == null || event.getObject() == null) {
            return;
        }
        DialogResult result = (DialogResult) event.getObject();
        if (result.isCanceled()) {
            return;
        }
        Map<String, Object> valores = (Map<String, Object>) result.getResult();
        if (valores != null) {

        	String tipoEventoResult = (String) valores.get("tipoEvento");
        	TypeEvento evento = TypeEvento.valueOf(tipoEventoResult);

        	String textoEscrito = (String) valores.get("filtroTablaErrores");

        	if (TypeEvento.ERROR.equals(evento)) {

        		setCheckTipoErrorDE((boolean) valores.get("checkTipoError"));
        		setCheckTextoTrazaDE((boolean) valores.get("checkTextoTraza"));
        		setFiltroTablaErroresDE(textoEscrito);

        		List<String> tiposErrores = (List<String>) valores.get("tiposErrorSeleccionados");
        		if (tiposErrores == null || tiposErrores.isEmpty()) {
        			setErroresSeleccionadosDE("");
        			filtros.setTiposErrores(null);
        		} else {
        			setErroresSeleccionadosDE(String.join(";", tiposErrores));
        			if (checkTipoErrorDE) {
        				/*if (filtroTablaErroresDE != null && !filtroTablaErroresDE.isEmpty()) {
        					Pattern pattern = Pattern.compile(filtroTablaErroresDE, Pattern.CASE_INSENSITIVE);
        					tiposErrores = tiposErrores.stream()
        							.filter(te -> pattern.matcher(te).find())
        							.collect(Collectors.toList());
        				}*/
        				filtros.setTiposErrores(tiposErrores);
        			} else {
        				filtros.setTiposErrores(null);
        			}
        		}

        		String filtroTextoTraza = (String) valores.get("filtroTextoTraza");
        		if (filtroTextoTraza != null) {
        			setFiltroTextoTrazaDE(filtroTextoTraza);
        			if (filtroTextoTraza.isEmpty() || !checkTextoTrazaDE) {
        				filtros.setTextoTraza(null);
        			} else {
        				filtros.setTextoTraza(filtroTextoTraza);
        			}
        		}
        	}

        	else if (TypeEvento.FIRMA_FIN_OK.equals(evento) || TypeEvento.FIRMA_FIN_KO.equals(evento)) {
        		setFiltroTablaFirmaDE(textoEscrito);
        		eventoFirmaGuardado = evento;

        		List<String> firmas = (List<String>) valores.get("tiposErrorSeleccionados");
        		if (firmas != null && !firmas.isEmpty()) {
        			setFirmasSeleccionadasDE(String.join(";", firmas));
        			filtros.setTiposFirma(firmas);
        		} else {
        			setFirmasSeleccionadasDE(null);
        			filtros.setTiposFirma(null);
        		}
        	}

        	else if (TypeEvento.PAGO_ELECTRONICO_VERIFICADO.equals(evento) || TypeEvento.PAGO_ELECTRONICO_NO_VERIFICADO.equals(evento) || TypeEvento.PAGO_CANCELADO.equals(evento)) {
        		setFiltroTablaPagoDE(textoEscrito);
        		eventoPagoGuardado = evento;

        		List<String> pagos = (List<String>) valores.get("tiposErrorSeleccionados");
        		if (pagos != null && !pagos.isEmpty()) {
        			setPagosSeleccionadosDE(String.join(";", pagos));
        			filtros.setTiposPago(pagos);
        		} else {
        			setPagosSeleccionadosDE(null);
        			filtros.setTiposPago(null);
        		}
        	}


        }
		PrimeFaces.current().executeScript("document.getElementById('form:btnBuscar').click()");
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
	 * Buscar.
	 */
	private void buscar() {
		sanitarFiltros();
		// Filtra
		Long rowCount = (long) 0;
		try {
			rowCount = helpDeskService.countAuditoriaEvento(filtros);
		} catch (Exception e) {

		}

		listaDatos = new EventoAuditoriaTramitacionLazyDataModel(helpDeskService, rowCount, filtros);
		// Quitamos seleccion de dato
		datoSeleccionado = null;

	}

	private void sanitarFiltros() {
		filtros.setIdTramite(filtrarString(filtros.getIdTramite(), "[a-zA-Z0-9_-]"));
		filtros.setIdProcedimientoCP(filtrarString(filtros.getIdProcedimientoCP(), "[a-zA-Z0-9_-]"));
	}

	private String filtrarString(String var, String regex) {
		String varB = "";
		if (!var.trim().isEmpty() && var != null) {
			for (int i = 0; i < var.length(); i++) {
				if (Character.toString(var.charAt(i)).matches(regex)) {
					varB += var.charAt(i);
				}
			}
		}
		return varB;
	}

	/**
	 *
	 */
	public String split(String idComp) {
		return idComp.split("\\.")[2];
	}

	/**
	 * Consultar.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_EVENTO, datoSeleccionado);

		// Añadimos objeto navegación de eventos: listado página, index, pageSize
		NavegacionEventos navegacionEventos = new NavegacionEventos();

		navegacionEventos.setEventos(listaDatos.getWrappedData());
		navegacionEventos.setPageSize(listaDatos.getPageSize());
		navegacionEventos.setFirst(listaDatos.getFirst());
		navegacionEventos.setTotal(listaDatos.getRowCount());

		mochila.put(Constantes.CLAVE_MOCHILA_EVENTO_NAVEGACION, navegacionEventos);

		// Muestra dialogo
		UtilJSF.openDialog(DialogAuditoriaTramites.class, TypeModoAcceso.CONSULTA, null, true, 950, 750);
	}

	/**
	 * Rc doble click.
	 */
	public void rcDobleClick() {
		consultar();
	}

	public void cambioTipoEvento(){

		if( TypeEvento.INICIAR_TRAMITE.equals(this.filtros.getEvento()) && entidad.isModoFuncionarioHabilitado()){
			comboIniciado = true;

		} else {
			comboIniciado = false;
			filtros.setIniciadoPor(null);
		}

	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("auditoriaTramites");
	}

	/**
	 * Ayuda.
	 */
	public void ayudaDialog() {
		if (esDialogParams) {
			UtilJSF.openHelp("auditoriaTramitesIncidencias");
		} else {
			UtilJSF.openHelp("auditoriaTramitesDialog");
		}
	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Obtiene el valor de filaSeleccionada.
	 *
	 * @return el valor de filaSeleccionada
	 */
	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	/**
	 * Verificar fila seleccionada.
	 *
	 * @return true, if successful
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.showMessageDialog(TypeNivelGravedad.WARNING, "", UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Convierte lista areas.
	 *
	 * @return the lista de
	 */
	private List<String> convierteListaAreas() {
		List<String> resultado = null;

		final List<Area> lista = UtilJSF.getSessionBean().getListaAreasEntidad();

		entidad = UtilJSF.getSessionBean().getEntidad();

		if (lista != null && !lista.isEmpty()) {
			resultado = new ArrayList<>();
			for (final Area area : lista) {
				resultado.add(area.getIdentificador());
			}
		}

		return resultado;

	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public EventoAuditoriaTramitacionLazyDataModel getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final EventoAuditoriaTramitacionLazyDataModel listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public EventoAuditoriaTramitacion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final EventoAuditoriaTramitacion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public FiltroAuditoriaTramitacion getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros el nuevo valor de filtros
	 */
	public void setFiltros(final FiltroAuditoriaTramitacion filtros) {
		this.filtros = filtros;
	}

	public List<TypeEvento> getTiposEventos() {
		return tiposEventos;
	}

	public void setTiposEventos(final List<TypeEvento> tiposEventos) {
		this.tiposEventos = tiposEventos;
	}

	public List<TypeIniciadoPor> getTiposIniciadoPor() {
		return tiposIniciadoPor;
	}

	public void setTiposIniciadoPor(List<TypeIniciadoPor> tiposIniciadoPor) {
		this.tiposIniciadoPor = tiposIniciadoPor;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewAuditoriaTramites");
	}

	/**
	 * @return the filtroArea
	 */
	public final String getFiltroArea() {
		return filtroArea;
	}

	/**
	 * @param filtroArea the filtroArea to set
	 */
	public final void setFiltroArea(String filtroArea) {
		List<String> areasEnt = convierteListaAreas();
		if (filtroArea != null && !filtroArea.isEmpty() && areasEnt != null) {
			filtros.getListaAreas().clear();
			for (String ar : areasEnt) {
				Pattern pattern = Pattern.compile(filtroArea, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(ar.split("\\.")[1]);
				boolean matchFound = matcher.find();
				if (matchFound) {
					filtros.getListaAreas().add(ar);
				}
			}
		} else {
			filtros.setListaAreas(areasEnt);
		}
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the idTramiteECM
	 */
	public String getIdTramiteECM() {
		return idTramiteECM;
	}

	/**
	 * @param idTramiteECM the idTramiteECM to set
	 */
	public void setIdTramiteECM(String idTramiteECM) {
		this.idTramiteECM = idTramiteECM;
	}

	/**
	 * @return the versionTramiteECM
	 */
	public String getVersionTramiteECM() {
		return versionTramiteECM;
	}

	/**
	 * @param versionTramiteECM the versionTramiteECM to set
	 */
	public void setVersionTramiteECM(String versionTramiteECM) {
		this.versionTramiteECM = versionTramiteECM;
	}

	/**
	 * @return the fechaDesdeECM
	 */
	public String getFechaDesdeECM() {
		return fechaDesdeECM;
	}

	/**
	 * @param fechaDesdeECM the fechaDesdeECM to set
	 */
	public void setFechaDesdeECM(String fechaDesdeECM) {
		this.fechaDesdeECM = fechaDesdeECM;
	}

	/**
	 * @return the fechaHastaECM
	 */
	public String getFechaHastaECM() {
		return fechaHastaECM;
	}

	/**
	 * @param fechaHastaECM the fechaHastaECM to set
	 */
	public void setFechaHastaECM(String fechaHastaECM) {
		this.fechaHastaECM = fechaHastaECM;
	}

	/**
	 * @return the horaDesdeECM
	 */
	public String getHoraDesdeECM() {
		return horaDesdeECM;
	}

	/**
	 * @param horaDesdeECM the horaDesdeECM to set
	 */
	public void setHoraDesdeECM(String horaDesdeECM) {
		this.horaDesdeECM = horaDesdeECM;
	}

	/**
	 * @return the layout
	 */
	public final String getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public final void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * @return the esDialog
	 */
	public final Boolean getEsDialog() {
		return esDialog;
	}

	/**
	 * @param esDialog the esDialog to set
	 */
	public final void setEsDialog(Boolean esDialog) {
		this.esDialog = esDialog;
	}

	public Boolean getEsDialogParams() {
		return esDialogParams;
	}

	public void setEsDialogParams(Boolean esDialogParams) {
		this.esDialogParams = esDialogParams;
	}

	/**
	 * @return the excepcion
	 */
	public final String getExcepcionECM() {
		return excepcionECM;
	}

	/**
	 * @param excepcion the excepcion to set
	 */
	public final void setExcepcionECM(String excepcionECM) {
		this.excepcionECM = excepcionECM;
	}

	public String getEventoHA() {
		return eventoHA;
	}

	public void setEventoHA(String eventoHA) {
		this.eventoHA = eventoHA;
	}

	public String getFechaDesdeHA() {
		return fechaDesdeHA;
	}

	public void setFechaDesdeHA(String fechaDesdeHA) {
		this.fechaDesdeHA = fechaDesdeHA;
	}

	public String getFechaHastaHA() {
		return fechaHastaHA;
	}

	public void setFechaHastaHA(String fechaHastaHA) {
		this.fechaHastaHA = fechaHastaHA;
	}

	public Boolean getEsDialogHA() {
		return esDialogHA;
	}

	public void setEsDialogHA(Boolean esDialogHA) {
		this.esDialogHA = esDialogHA;
	}

	public String getIdSesionParam() {
		return idSesionParam;
	}

	public void setIdSesionParam(String idSesionParam) {
		this.idSesionParam = idSesionParam;
	}

	public String getNifParam() {
		return nifParam;
	}

	public void setNifParam(String nifParam) {
		this.nifParam = nifParam;
	}
	/**
	 * @return the idSesionCorreoIncidencia
	 */
	public String getIdSesionCorreoIncidencia() {
		return idSesionCorreoIncidencia;
	}

	/**
	 * @param idSesionCorreoIncidencia the idSesionCorreoIncidencia to set
	 */
	public void setIdSesionCorreoIncidencia(String idSesionCorreoIncidencia) {
		this.idSesionCorreoIncidencia = idSesionCorreoIncidencia;
	}

	public boolean isComboIniciado() {
		return comboIniciado;
	}

	public void setComboIniciado(boolean comboIniciado) {
		this.comboIniciado = comboIniciado;
	}

	public boolean isCheckTipoErrorDE() {
		return checkTipoErrorDE;
	}

	public void setCheckTipoErrorDE(boolean checkTipoErrorDE) {
		this.checkTipoErrorDE = checkTipoErrorDE;
	}

	public boolean isCheckTextoTrazaDE() {
		return checkTextoTrazaDE;
	}

	public void setCheckTextoTrazaDE(boolean checkTextoTrazaDE) {
		this.checkTextoTrazaDE = checkTextoTrazaDE;
	}

	public String getErroresSeleccionadosDE() {
		return erroresSeleccionadosDE;
	}

	public void setErroresSeleccionadosDE(String erroresSeleccionadosDE) {
		this.erroresSeleccionadosDE = erroresSeleccionadosDE;
	}

	public String getFiltroTextoTrazaDE() {
		return filtroTextoTrazaDE;
	}

	public void setFiltroTextoTrazaDE(String filtroTextoTrazaDE) {
		this.filtroTextoTrazaDE = filtroTextoTrazaDE;
	}

    public String getFiltroTablaErroresDE() {
        return filtroTablaErroresDE;
    }

    public void setFiltroTablaErroresDE(String filtroTablaErroresDE) {
        this.filtroTablaErroresDE = filtroTablaErroresDE;
    }

	public String getFirmasSeleccionadasDE() {
		return firmasSeleccionadasDE;
	}

	public void setFirmasSeleccionadasDE(String firmasSeleccionadasDE) {
		this.firmasSeleccionadasDE = firmasSeleccionadasDE;
	}

	public String getPagosSeleccionadosDE() {
		return pagosSeleccionadosDE;
	}

	public void setPagosSeleccionadosDE(String pagosSeleccionadosDE) {
		this.pagosSeleccionadosDE = pagosSeleccionadosDE;
	}

	public String getFiltroTablaFirmaDE() {
		return filtroTablaFirmaDE;
	}

	public void setFiltroTablaFirmaDE(String filtroTablaFirmaDE) {
		this.filtroTablaFirmaDE = filtroTablaFirmaDE;
	}

	public String getFiltroTablaPagoDE() {
		return filtroTablaPagoDE;
	}

	public void setFiltroTablaPagoDE(String filtroTablaPagoDE) {
		this.filtroTablaPagoDE = filtroTablaPagoDE;
	}

	public TypeEvento getEventoPagoGuardado() {
		return eventoPagoGuardado;
	}

	public void setEventoPagoGuardado(TypeEvento eventoPagoGuardado) {
		this.eventoPagoGuardado = eventoPagoGuardado;
	}

	public TypeEvento getEventoFirmaGuardado() {
		return eventoFirmaGuardado;
	}

	public void setEventoFirmaGuardado(TypeEvento eventoFirmaGuardado) {
		this.eventoFirmaGuardado = eventoFirmaGuardado;
	}

	public TypeEvento getEventoUltimaBusqueda() {
		return eventoUltimaBusqueda;
	}

	public void setEventoUltimaBusqueda(TypeEvento eventoUltimaBusqueda) {
		this.eventoUltimaBusqueda = eventoUltimaBusqueda;
	}
}
