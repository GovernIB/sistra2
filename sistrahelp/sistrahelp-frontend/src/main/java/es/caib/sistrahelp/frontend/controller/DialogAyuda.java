package es.caib.sistrahelp.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

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

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 **/
	public void init() {
		urlIframe = "AyudaServlet?ts=" + System.currentTimeMillis();
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUrlIframe() {
		return urlIframe + "&id=" + id + "&lang=" + UtilJSF.getIdioma().toString();
	}

	public void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Genera texto a copiar **/
	public void generarTxt() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));

		PrimeFaces.current().executeScript("document.focus; navigator.clipboard.writeText(`" + portapapeles + "`);");
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

}
