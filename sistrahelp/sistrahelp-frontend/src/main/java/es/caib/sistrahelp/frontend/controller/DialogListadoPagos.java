package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.util.UtilJSF;

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

}
