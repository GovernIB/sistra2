package es.caib.sistrahelp.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.comun.Propiedad;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogUmbralesAviso extends DialogControllerBase {

	private String rolUsuarioString;
	private String umbralNormalAtencionPropertiesString;
	private String umbralAtencionRevisarPropertiesString;
	private String umbralNormalAtencionUsuarioString;
	private String umbralAtencionRevisarUsuarioString;
	private String umbralNormalAtencionString;
	private String umbralAtencionRevisarString;
	private Integer umbralNormalAtencion;
	private Integer umbralAtencionRevisar;
	private Integer umbralNormalAtencionProperties;
	private Integer umbralAtencionRevisarProperties;

	private boolean resetearUmbrales;
	private String portapapeles;
	private String errorCopiar;
	/** Propiedades */
	private List<Propiedad> propiedades = new ArrayList<>();


	/**
	 * Inicialización.
	 */
	public void init() {
		if (umbralNormalAtencionString != null || umbralAtencionRevisarString != null) {
			umbralNormalAtencion = Integer.parseInt(umbralNormalAtencionString);
			umbralAtencionRevisar = Integer.parseInt(umbralAtencionRevisarString);
		} else {
			umbralNormalAtencion = umbralNormalAtencionProperties = Integer.parseInt(umbralNormalAtencionPropertiesString);
			umbralAtencionRevisar = umbralAtencionRevisarProperties = Integer.parseInt(umbralAtencionRevisarPropertiesString);
		}
		resetearUmbrales = false;
	}

	public void actualizarUmbralNA() {
		setUmbralNormalAtencion(umbralNormalAtencion);
	}

	public void actualizarUmbralAR() {
		setUmbralAtencionRevisar(umbralAtencionRevisar);
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

	public void aceptar() {
		if (umbralNormalAtencion == null || umbralAtencionRevisar == null || umbralNormalAtencion >= umbralAtencionRevisar) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.umbrales.nullCruce"));
			return;
		}
		if (resetearUmbrales) {
			UtilJSF.getSessionBean().eliminarUmbrales();
		} else {
			UtilJSF.getSessionBean().setUmbralNAUsuario(umbralNormalAtencion.toString());
			UtilJSF.getSessionBean().setUmbralARUsuario(umbralAtencionRevisar.toString());
		}
		resetearUmbrales = false;
		cerrar();
	}

	public void valoresDefecto() {
		umbralNormalAtencion = Integer.parseInt(umbralNormalAtencionPropertiesString);
		umbralAtencionRevisar = Integer.parseInt(umbralAtencionRevisarPropertiesString);
		resetearUmbrales = true;
	}

	public void ayuda() {
		UtilJSF.openHelp("dialogoEnviarMail");
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
	 * @return the umbralNormalAtencion
	 */
	public Integer getUmbralNormalAtencion() {
		return umbralNormalAtencion;
	}

	/**
	 * @param umbralNormalAtencion the umbralNormalAtencion to set
	 */
	public void setUmbralNormalAtencion(Integer umbralNormalAtencion) {
		this.umbralNormalAtencion = umbralNormalAtencion;
	}

	/**
	 * @return the umbralAtencionRevisar
	 */
	public Integer getUmbralAtencionRevisar() {
		return umbralAtencionRevisar;
	}

	/**
	 * @param umbralAtencionRevisar the umbralAtencionRevisar to set
	 */
	public void setUmbralAtencionRevisar(Integer umbralAtencionRevisar) {
		this.umbralAtencionRevisar = umbralAtencionRevisar;
	}

	/**
	 * @return the resetearUmbrales
	 */
	public boolean isResetearUmbrales() {
		return resetearUmbrales;
	}

	/**
	 * @param resetearUmbrales the resetearUmbrales to set
	 */
	public void setResetearUmbrales(boolean resetearUmbrales) {
		this.resetearUmbrales = resetearUmbrales;
	}

	/**
	 * @return the umbralNormalAtencionUsuarioString
	 */
	public String getUmbralNormalAtencionUsuarioString() {
		return umbralNormalAtencionUsuarioString;
	}

	/**
	 * @param umbralNormalAtencionUsuarioString the umbralNormalAtencionUsuarioString to set
	 */
	public void setUmbralNormalAtencionUsuarioString(String umbralNormalAtencionUsuarioString) {
		this.umbralNormalAtencionUsuarioString = umbralNormalAtencionUsuarioString;
	}

	/**
	 * @return the umbralAtencionRevisarUsuarioString
	 */
	public String getUmbralAtencionRevisarUsuarioString() {
		return umbralAtencionRevisarUsuarioString;
	}

	/**
	 * @param umbralAtencionRevisarUsuarioString the umbralAtencionRevisarUsuarioString to set
	 */
	public void setUmbralAtencionRevisarUsuarioString(String umbralAtencionRevisarUsuarioString) {
		this.umbralAtencionRevisarUsuarioString = umbralAtencionRevisarUsuarioString;
	}

	/**
	 * @return the umbralNormalAtencionString
	 */
	public String getUmbralNormalAtencionString() {
		return umbralNormalAtencionString;
	}

	/**
	 * @param umbralNormalAtencionString the umbralNormalAtencionString to set
	 */
	public void setUmbralNormalAtencionString(String umbralNormalAtencionString) {
		this.umbralNormalAtencionString = umbralNormalAtencionString;
	}

	/**
	 * @return the umbralAtencionRevisarString
	 */
	public String getUmbralAtencionRevisarString() {
		return umbralAtencionRevisarString;
	}

	/**
	 * @param umbralAtencionRevisarString the umbralAtencionRevisarString to set
	 */
	public void setUmbralAtencionRevisarString(String umbralAtencionRevisarString) {
		this.umbralAtencionRevisarString = umbralAtencionRevisarString;
	}


	/**
	 * @return the umbralNormalAtencionPropertiesString
	 */
	public String getUmbralNormalAtencionPropertiesString() {
		return umbralNormalAtencionPropertiesString;
	}

	/**
	 * @param umbralNormalAtencionPropertiesString the umbralNormalAtencionPropertiesString to set
	 */
	public void setUmbralNormalAtencionPropertiesString(String umbralNormalAtencionPropertiesString) {
		this.umbralNormalAtencionPropertiesString = umbralNormalAtencionPropertiesString;
	}

	/**
	 * @return the umbralAtencionRevisarPropertiesString
	 */
	public String getUmbralAtencionRevisarPropertiesString() {
		return umbralAtencionRevisarPropertiesString;
	}

	/**
	 * @param umbralAtencionRevisarPropertiesString the umbralAtencionRevisarPropertiesString to set
	 */
	public void setUmbralAtencionRevisarPropertiesString(String umbralAtencionRevisarPropertiesString) {
		this.umbralAtencionRevisarPropertiesString = umbralAtencionRevisarPropertiesString;
	}

	/**
	 * @return the umbralNormalAtencionProperties
	 */
	public Integer getUmbralNormalAtencionProperties() {
		return umbralNormalAtencionProperties;
	}

	/**
	 * @param umbralNormalAtencionProperties the umbralNormalAtencionProperties to set
	 */
	public void setUmbralNormalAtencionProperties(Integer umbralNormalAtencionProperties) {
		this.umbralNormalAtencionProperties = umbralNormalAtencionProperties;
	}

	/**
	 * @return the umbralAtencionRevisarProperties
	 */
	public Integer getUmbralAtencionRevisarProperties() {
		return umbralAtencionRevisarProperties;
	}

	/**
	 * @param umbralAtencionRevisarProperties the umbralAtencionRevisarProperties to set
	 */
	public void setUmbralAtencionRevisarProperties(Integer umbralAtencionRevisarProperties) {
		this.umbralAtencionRevisarProperties = umbralAtencionRevisarProperties;
	}

	/**
	 * @return the rolUsuarioString
	 */
	public String getRolUsuarioString() {
		return rolUsuarioString;
	}

	/**
	 * @param rolUsuarioString the rolUsuarioString to set
	 */
	public void setRolUsuarioString(String rolUsuarioString) {
		this.rolUsuarioString = rolUsuarioString;
	}

}
