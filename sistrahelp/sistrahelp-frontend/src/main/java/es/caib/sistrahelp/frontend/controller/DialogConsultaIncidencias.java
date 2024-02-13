package es.caib.sistrahelp.frontend.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistrahelp.core.api.model.Alerta;

import es.caib.sistrahelp.core.api.model.Soporte;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeEstadoIncidencia;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogConsultaIncidencias extends DialogControllerBase {

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * lista datos.
	 */
	private List<Alerta> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private Soporte data;

	private List<TypeEstadoIncidencia> tiposEstados;

	@Inject
	private HelpDeskService hService;

	private String portapapeles;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Modo acceso

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		data = (Soporte) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_SOPORTE);
		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_SOPORTE);

		tiposEstados = new ArrayList<>();
		for (final TypeEstadoIncidencia ev : TypeEstadoIncidencia.values()) {
			tiposEstados.add(ev);
		}

	}

	public void aceptar() {

		// Realizamos alta o update
//		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		// Retornamos resultado
		hService.updateFormularioSoporte(data);

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("dialogIncidencias");
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
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void abrirDlgSesion() {

		final Map<String, String> params = new HashMap<>();

		String idSesion = data.getSesionTramitacion();

		params.put("idSesionParam", idSesion);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 717);

	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void abrirDlgNif() {

		final Map<String, String> params = new HashMap<>();

		String nif = data.getNif();

		params.put("nifParam", nif);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 717);

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
				if (respuesta != null && respuesta.getResult() != null) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));
				}
			}

			// Refrescamos datos
		}

	}
//	/**
//	 * Descarga fichero
//	 *
//	 * @param fichero
//	 */
//	public void descargaFichero(final Fichero fichero) {
//		if (fichero != null && fichero.getCodigo() != null) {
//			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + this.data.getDatosFichero());
//		}
//	}

	public StreamedContent montarFichero() {
		DefaultStreamedContent file = new DefaultStreamedContent();
		file.setName(this.data.getNombreFichero());
		file.setStream(new ByteArrayInputStream(data.getDatosFichero()));
		return file;

	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Alerta> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List<Alerta> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public Soporte getData() {
		return data;
	}

	public void setData(Soporte data) {
		this.data = data;
	}

	public HelpDeskService gethService() {
		return hService;
	}

	public void sethService(HelpDeskService hService) {
		this.hService = hService;
	}

	public String getPortapapeles() {
		return portapapeles;
	}

	public void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public List<TypeEstadoIncidencia> getTiposEstados() {
		return tiposEstados;
	}

	public void setTiposEstados(List<TypeEstadoIncidencia> tiposEstados) {
		this.tiposEstados = tiposEstados;
	}
}
