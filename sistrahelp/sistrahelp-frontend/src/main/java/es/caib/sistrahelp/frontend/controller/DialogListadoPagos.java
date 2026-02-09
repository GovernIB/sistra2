package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

import org.primefaces.extensions.event.ClipboardSuccessEvent;
import org.apache.commons.lang3.StringUtils;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class DialogListadoPagos extends DialogControllerBase {
	@Inject
	private ConfiguracionService systemService;

	/** url iframe */
	private String url;

	/** identificador pago */
	private String identificador;

	private String mensaje;

	private String errorCopiar;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		url = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PAYMENTIB_VIEW_URL)
				+ "/dialogPagos.xhtml?IDENTIFICADOR=" + identificador + "&MODO_ACCESO=CONSULTA&TOKEN=" + crearToken()/* + "&LANG=" + UtilJSF.getIdioma()*/;
	}

	private String crearToken() {
		return "CONSULTA-" + identificador + "-" + System.currentTimeMillis();
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void closeDialog() {
		final DialogResult result = new DialogResult();
		result.setResult(mensaje);
		result.setCanceled(false);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the mensaje
	 */
	public final String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public final void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}
