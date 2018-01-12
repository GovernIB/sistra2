package es.caib.sistra2.gte.frontend.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import es.caib.sistra2.gte.frontend.model.DialogResult;
import es.caib.sistra2.gte.frontend.model.types.TypeModoAcceso;
import es.caib.sistra2.gte.frontend.model.types.TypeNivelGravedad;
import es.caib.sistra2.gte.frontend.model.types.TypeParametroDialogo;

/**
 * Utilidades.
 * @author Indra
 *
 */
public class UtilJSF {

	/**
	 * Abre pantalla de dialogo
	 * @param dialog Nombre pantalla dialogo (dialogo.xhtml o id navegacion)
	 * @param modoAcceso Modo de acceso
	 * @param params parametros
	 * @param modal si se abre en forma modal
	 * @param width anchura
	 * @param heigth altura
	 */
	public static void openDialog(String dialog, TypeModoAcceso modoAcceso, Map<String, String> params, boolean modal, int width, int heigth) {
		// Opciones dialogo
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("modal", modal);
		options.put("width", width);
		options.put("height", heigth);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
        // Parametros
	    final Map<String, List<String>> paramsDialog = new HashMap<>();
	    paramsDialog.put(TypeParametroDialogo.MODO_ACCESO.toString(), Collections.singletonList(modoAcceso.toString()));
	    if (params != null) {
	    	for (String key : params.keySet()){
	    		paramsDialog.put(key, Collections.singletonList(params.get(key)));
	    	}
	    }
		// Abre dialogo
		RequestContext.getCurrentInstance().openDialog(dialog, options, paramsDialog);
	}	
	
	/**
	 * Cierra dialog
	 * @param result
	 */
	public static void closeDialog(DialogResult result) {
		RequestContext.getCurrentInstance().closeDialog(result);
	}
	
	/**
	 * Muestra mensaje como ventana de dialogo.
	 * @param nivel Nivel gravedad
	 * @param title Titulo 
	 * @param message Mensaje
	 */
	public static void showMessageDialog(TypeNivelGravedad nivel, String title, String message) {
		Severity severity = getSeverity(nivel);
		RequestContext.getCurrentInstance().showMessageInDialog(new
				FacesMessage(severity,
				title, message));
	}

	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl, messages,...).
	 * @param nivel Nivel gravedad
	 * @param message Mensaje
	 */
	public static void addMessageContext(TypeNivelGravedad nivel, String message) {
		Severity severity = getSeverity(nivel);
		FacesContext context = FacesContext.getCurrentInstance();        
		context.addMessage(null, new FacesMessage(severity, message, message) );		
	}
	
	/**
	 * Añade mensaje al contexto para que lo trate la aplicación (growl, messages,...).
	 * @param nivel Nivel gravedad
	 * @param message Mensaje
	 * @param detail Detalle
	 */
	public static void addMessageContext(TypeNivelGravedad nivel, String message, String detail) {
		Severity severity = getSeverity(nivel);
		FacesContext context = FacesContext.getCurrentInstance();        
		context.addMessage(null, new FacesMessage(severity, message, detail) );		
	}
	
	public static String getLiteral(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle text = context.getApplication().evaluateExpressionGet(context, "#{msg}", ResourceBundle.class);
	    String result = text.getString(key);
	    return result;
	}
	
	/**
	 * Redirige pagina JSF.
	 * 
	 * @param jsfPage path JSF page
	 */
	public static void redirectJsfPage(String jsfPage) {
		try {
			ServletContext servletContext = (ServletContext) FacesContext
				    .getCurrentInstance().getExternalContext().getContext();
			String contextPath = servletContext.getContextPath();	  		
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + jsfPage);
		} catch (IOException e) {
			// TODO Gestion Excepciones 
		}
	}
	
	private static Severity getSeverity(TypeNivelGravedad nivel) {
		Severity severity;
		switch (nivel) {
		case INFO:
			severity = FacesMessage.SEVERITY_INFO;
			break;
		case WARNING:
			severity = FacesMessage.SEVERITY_WARN;
			break;
		case ERROR:
			severity = FacesMessage.SEVERITY_ERROR;
			break;	
		case FATAL:
			severity = FacesMessage.SEVERITY_FATAL;
			break;
		default:
			severity = FacesMessage.SEVERITY_INFO;
			break;
		}
		return severity;
	}
	
}
