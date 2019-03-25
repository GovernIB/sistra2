package es.caib.sistrahelp.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistrahelp.core.api.model.FicheroAuditoria;
import es.caib.sistrahelp.core.api.model.FicheroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogInformacionPersistencia extends DialogControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	private PersistenciaAuditoria dato;
	private List<FicheroPersistenciaAuditoria> listaFicheros;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {
			setDato((PersistenciaAuditoria) UtilJSF.getSessionBean().getMochilaDatos()
					.get(Constantes.CLAVE_MOCHILA_PERSISTENCIA));
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_PERSISTENCIA);

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
		InputStream is = null;

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

			resultado = new DefaultStreamedContent(is, mimeType, fichero.getNombre(), fichero.getContenido().length);
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

}
