package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ProcesoAlertaService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.model.types.TypeParametroVentana;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La clase ViewEventosPlataforma.
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionAlertas extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private AlertaService alertaService;

	@Inject
	private ProcesoAlertaService procesoAlertaService;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * lista datos.
	 */
	private List<Alerta> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private Alerta datoSeleccionado;

	/**
	 * filtros.
	 */
	private String filtros;

	/**
	 * Inicializa.
	 *
	 */
	public void init() {
		paginacion = UtilJSF.getPaginacion("viewConfiguracionAlertas");

		UtilJSF.verificarAcceso();

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		listaDatos = alertaService.listAlertaActivo(null, true);

	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		// Buscar
		this.buscar(null);
	}

	/**
	 * Buscar.
	 */
	private void buscar(String filtros) {
		// Filtra
		listaDatos = alertaService.listAlertaActivo(filtros, true);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		abrirDlg(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	private void abrirDlg(final TypeModoAcceso modoAccesoDlg) {

		// Verifica si no hay fila seleccionada
		if (modoAccesoDlg != TypeModoAcceso.ALTA && !verificarFilaSeleccionada()) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (modoAccesoDlg != TypeModoAcceso.ALTA) {
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		}

		UtilJSF.openDialog(DialogConfiguracionAlertas.class, modoAccesoDlg, params, true, 1030, 680);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {

		abrirDlg(TypeModoAcceso.EDICION);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		// Inactivamos
		this.datoSeleccionado.setEliminar(true);
		alertaService.updateAlerta(this.datoSeleccionado);
		// Refrescamos datos
		filtrar();

	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				/*
				 * if (re.getCodigo() != 1) { message = UtilJSF.getLiteral("info.alta.ok") +
				 * ". " + UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje(); }
				 * else { message = UtilJSF.getLiteral("info.alta.ok") + ". " +
				 * UtilJSF.getLiteral("info.cache.ok"); }
				 */
				// UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
				// UtilJSF.getLiteral("info.alta.ok"));
			} else {
				String message = null;
				if (respuesta.getMensaje() == null) {
					// UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					// UtilJSF.getLiteral("info.modificado.ok"));
				}
			}

			// Refrescamos datos
			buscar(null);
		}

	}

	public String parseSecs(Integer segundos) {
		String aux = "";
		if (segundos != null) {
			aux = formatSeconds(segundos);
		}
		return aux;
	}

	private static String formatSeconds(int timeInSeconds) {
		int hours = timeInSeconds / 3600;
		int secondsLeft = timeInSeconds - hours * 3600;
		int minutes = secondsLeft / 60;
		int seconds = secondsLeft - minutes * 60;

		String formattedTime = "";
		if (hours < 10)
			formattedTime += "0";
		formattedTime += hours + ":";

		if (minutes < 10)
			formattedTime += "0";
		formattedTime += minutes + ":";

		if (seconds < 10)
			formattedTime += "0";
		formattedTime += seconds;

		return formattedTime;
	}

	/**
	 * Rc doble click.
	 */
	public void rcDobleClick() {
		editar();
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("alertas");
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	public String convertirEventos(List<String> lista) {
		String resultado = "";
		if (lista != null) {
			String grupoAnterior = "1";
			for (int i=0; i<lista.size(); i++) {
				String[] partes = lista.get(i).split(":");
				boolean cambioGrupo = false;
				String grupo = partes[1];
				if (i > 0) {
					if (!grupoAnterior.equals(grupo)) {
						grupoAnterior = grupo;
						cambioGrupo = true;
					}
				}
				String and_or = "";
				String not = "";

				if (!partes[2].equals("null")) {
					if (partes[2].equals("AND")) {
						and_or = "AND";
					} else {
						and_or = "OR";
					}
				}

				if (partes[3].equals("false")) {
					not = "";
				} else {
					not = " NOT";
				}

				String evento = UtilJSF.getLiteral("typeEvento."+TypeEvento.fromString(partes[4]));
				String operador = partes[5];
				String concurrencia = partes[6];

				if (i == 0) {
					resultado += "(" + not + " " + evento + " " + operador + " " + concurrencia;
					if (lista.size() == 1) {
						resultado += " )";
					}
				} else {
					if (cambioGrupo) {
						resultado += " )" + " " + and_or + " " + "(" + not + " " + evento + " " + operador + " " + concurrencia;
					} else {
						resultado += " " + and_or + " " + not + " " + evento + " " + operador + " " + concurrencia;
					}
					if (i == lista.size() - 1) {
						resultado += " )";
					}
				}
			}
		}
		return resultado;

	}

	public void probarAlerta() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		if(!this.datoSeleccionado.isActivo()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.alertaInactiva"));
			return;
		}

		if (this.datoSeleccionado.getNombre().equals("RESUMEN_DIARIO") || this.datoSeleccionado.getNombre().equals("RESUM_DIARI")) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.probarResumenDiario"));
			return;
		}

		boolean evaluarAlerta = procesoAlertaService.procesarAlertas(this.datoSeleccionado);
		if(evaluarAlerta){
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.evaluarAlerta.true"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.evaluarAlerta.false"));
		}
	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public List<Alerta> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final List<Alerta> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Alerta getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Alerta datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public String getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros el nuevo valor de filtros
	 */
	public void setFiltros(final String filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewEventosPlataforma");
	}

	private String portapapeles;

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

	public Integer getTamanyoTabla() {
		if (this.listaDatos != null) {
			return this.listaDatos.size();
		} else {
			return null;
		}
	}

	/**
	 * Duplica la alerta seleccionada
	 */
	public void duplicarAlerta() {

		if(this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogConfiguracionAlertas.class, TypeModoAcceso.DUPLICAR, params, true, 1030, 680);

	}

	public void returnDialogoClonar() {
		// Refrescamos datos
		buscar(filtros);


	}

	public String labelTipo(String tipo){

		// return label for tipo of alerta
		switch (tipo) {
			case "E":
				return UtilJSF.getLiteral("dialogInformacionPagos.entidadId");
			case "A":
				return UtilJSF.getLiteral("viewAuditoriaTramites.headArea");
			case "T":
				return UtilJSF.getLiteral("viewPerdidaClave.headTramite");
			case "V":
				return UtilJSF.getLiteral("viewAuditoriaTramites.headVersion");
			default:
				return tipo;
		}
	}

	// label ambito
	public String labelAmbito(Alerta alerta){

		// return label for ambito of alerta
		switch (alerta.getTipo()) {
			case "E":
				return alerta.getListaAreas().get(0).substring(0, alerta.getListaAreas().get(0).indexOf(".")-1);
			case "A":
				return alerta.getListaAreas().get(0);
			case "T":
				return alerta.getListaAreas().get(0) + "." + alerta.getTramite();
			case "V":
				return alerta.getListaAreas().get(0) + "." + alerta.getTramite() + "." + alerta.getVersion();
			default:
				return "";
		}
	}

	public String labelEmails(Alerta alerta){
		return StringUtils.join( alerta.getEmail(), ";");
	}
}
