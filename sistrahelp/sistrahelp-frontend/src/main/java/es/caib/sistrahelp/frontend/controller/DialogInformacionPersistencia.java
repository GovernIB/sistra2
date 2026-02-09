package es.caib.sistrahelp.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogInformacionPersistencia extends DialogControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	private PersistenciaAuditoria dato;
	private List<FicheroPersistenciaAuditoria> listaFicheros;
	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {
			setDato((PersistenciaAuditoria) UtilJSF.getSessionBean().getMochilaDatos()
					.get(Constantes.CLAVE_MOCHILA_PERSISTENCIA));
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PERSISTENCIA);
			Date now = new Date();
			if (getDato() != null) {
				setListaFicheros(helpDeskService.obtenerAuditoriaPersistenciaFichero(getDato().getId()));
			}
		}

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

	public StreamedContent descargarFichero(final FicheroPersistenciaAuditoria pFichero) {
		StreamedContent resultado = null;
		String mimeType = null;
		InputStream is;

		if (pFichero != null) {
			final FicheroAuditoria fichero = helpDeskService.obtenerAuditoriaFichero(pFichero.getCodigo(),
					pFichero.getClave());

			is = new ByteArrayInputStream(fichero.getContenido());
			try {
				mimeType = URLConnection.guessContentTypeFromStream(is);
			} catch (final IOException e) {
			}

			if (StringUtils.isBlank(mimeType)) {
				mimeType = "application/octet-stream";
			}

			resultado = DefaultStreamedContent.builder().contentType(mimeType).name(fichero.getNombre()).stream(() -> is).build();

		} else {
            is = null;
        }
        return resultado;
	}

	public PersistenciaAuditoria getDato() {
		return dato;
	}

	public void setDato(final PersistenciaAuditoria dato) {
		this.dato = dato;
	}

	public List<FicheroPersistenciaAuditoria> getListaFicheros() {
		return listaFicheros;
	}

	public void setListaFicheros(final List<FicheroPersistenciaAuditoria> listaFicheros) {
		this.listaFicheros = listaFicheros;
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoInformacionPersistencia");
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
