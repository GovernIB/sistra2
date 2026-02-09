package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.comun.ListaPropiedades;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.NavegacionEventos;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;


@ManagedBean
@ViewScoped
public class DialogAuditoriaTramites extends DialogControllerBase {


	@Inject
	private HelpDeskService helpDeskService;

	private EventoAuditoriaTramitacion dato;

	private Entry<String, String> valorSeleccionado;

	private String portapapeles;

	private String errorCopiar;

	private NavegacionEventos navegacionEventos;


	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {
			dato = (EventoAuditoriaTramitacion) UtilJSF.getSessionBean().getMochilaDatos()
					.get(Constantes.CLAVE_MOCHILA_EVENTO);
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_EVENTO);

			if(TypeEvento.INICIAR_TRAMITE.equals(dato.getTipoEvento()) && dato.getFuncionarioHabilitado() != null) {
				if(dato.getPropiedadesEvento() == null){
					dato.setPropiedadesEvento(new ListaPropiedades());
				}
				dato.getPropiedadesEvento().addPropiedades(dato.getFuncionarioHabilitado().toPropiedades());
			}

			navegacionEventos = (NavegacionEventos) UtilJSF.getSessionBean().getMochilaDatos()
					.get(Constantes.CLAVE_MOCHILA_EVENTO_NAVEGACION);

			if(navegacionEventos != null) {
				int indexRow = navegacionEventos.getEventos().indexOf(dato);
				if (indexRow < 0) { // desde stg por algún motivo no coinciden las instancias de evento seleccionado y la misma en la lista, por eso filtramos por id para localizarla
					indexRow = navegacionEventos.getEventos().stream().filter(e -> e.getId().equals(dato.getId())).findFirst().map(navegacionEventos.getEventos()::indexOf).orElse(-1);
				}

				navegacionEventos.setRowIndex(indexRow);
			}




		}
	}

	public boolean getMostrarDetalle() {
		boolean resultado = false;
		if (TypeEvento.ERROR.equals(dato.getTipoEvento()) || dato.getPropiedadesEvento() != null) {
			resultado = true;
		}
		return resultado;
	}

	public void sistrages() {
		final Map<String, String> params = new HashMap<>();
		params.put("TRAMITE", dato.getIdTramite());
		params.put("VERSION", dato.getVersionTramite().toString());
		UtilJSF.openDialog(DialogDefinicionVersion.class, TypeModoAcceso.CONSULTA, params, true, 1350, 550);
	}

	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, (String) respuesta.getResult());
		}
	}

	/**
	 * Abre dialogo sesión
	 */
	public void pantallaSesion() {
		final Map<String, String> params = new HashMap<>();

		String idSesion = dato.getIdSesionTramitacion();

		params.put("idSesionParam", idSesion);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 703);
	}

	/**
	 * Abre dialogo nif
	 */
	public void pantallaNif() {
		final Map<String, String> params = new HashMap<>();

		String nif = dato.getNif();

		params.put("nifParam", nif);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 703);
	}

	/**
	 * Retroceder al anterior evento de la búsqueda realizada
	 */
	public void navigatePrevious() {
		// Logic to navigate to the previous item
		// Example: Update `dato` to the previous item in the list

		if(navegacionEventos.hasPrevious()) {
			if(navegacionEventos.getRowIndex() != 0) {
				dato = navegacionEventos.getPreviousEvento();
			}else{
				List<EventoAuditoriaTramitacion> pagAnt = helpDeskService.obtenerAuditoriaEvento(navegacionEventos.getFiltros(), new FiltroPaginacion(navegacionEventos.getFirst() - navegacionEventos.getPageSize(), navegacionEventos.getPageSize()));
				navegacionEventos.retrocedePagina(pagAnt);
				dato = navegacionEventos.getEventos().get(navegacionEventos.getRowIndex());
			}
		}
	}

	/**
	 * Avanzar al siguiente evento de la búsqueda realizada
	 */
	public void navigateNext() {

		if(navegacionEventos.hasNext()) {
			if( navegacionEventos.ultimoDePagina()) {
				List<EventoAuditoriaTramitacion> pagSig = helpDeskService.obtenerAuditoriaEvento(navegacionEventos.getFiltros(), new FiltroPaginacion(navegacionEventos.getFirst() + navegacionEventos.getPageSize(), navegacionEventos.getPageSize()));
				navegacionEventos.avanzarPagina(pagSig);
				dato = navegacionEventos.getEventos().get(0);
			}else {
				dato = navegacionEventos.getNextEvento();
			}
		}
	}

	public boolean isMostrarNavegacion() {
		return navegacionEventos != null && navegacionEventos.getEventos() != null && navegacionEventos.getEventos().size() > 1;
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

	public String splitTramite(String idComp) {
		return idComp.split("\\.")[2];
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
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public EventoAuditoriaTramitacion getDato() {
		return dato;
	}

	public void setDato(final EventoAuditoriaTramitacion dato) {
		this.dato = dato;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoAuditoriaTramites");
	}

	/**
	 * @return the valorSeleccionado
	 */
	public Entry<String, String> getValorSeleccionado() {
		return valorSeleccionado;
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

	public NavegacionEventos getNavegacionEventos() {
		return navegacionEventos;
	}
}
