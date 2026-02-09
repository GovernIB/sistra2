package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Disenyo formulario.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogAyuda extends DialogControllerBase {

	/** Id formulario **/
	private String id;

	/** Url iframe. **/
	private String urlIframe;

	private boolean esIframe = false;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 **/
	public void init() {
		urlIframe = "AyudaServlet?ts=" + System.currentTimeMillis() + "&id=" + id + "&lang="
				+ UtilJSF.getIdioma().toString();
	}

	/**
	 * Botón de cancelar la ayuda.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the urlIframe
	 */
	public final String getUrlIframe() {
		return urlIframe;
	}

	/**
	 * @param urlIframe the urlIframe to set
	 */
	public final void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public boolean isEsIframe() {
		return esIframe;
	}

	public void setEsIframe(boolean esIframe) {
		this.esIframe = esIframe;
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
}
