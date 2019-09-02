package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.service.ScriptService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteScripts extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Script service. */
	@Inject
	private ScriptService scriptService;

	/** Id de la version. */
	private String id;

	/** Scripts para descargar. **/
	private List<ScriptInfo> data;

	/** Inicialización. */
	public void init() {
		setData(tramiteService.listScriptsInfo(Long.valueOf(id)));
	}

	/**
	 * Descargar un txt con todos los scripts.
	 *
	 * @throws FileNotFoundException
	 **/
	public StreamedContent getFichero() {

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final TramiteVersion tv = tramiteService.getTramiteVersion(Long.valueOf(id));
		final Tramite tram = tramiteService.getTramite(tv.getIdTramite());

		final PrintWriter writer = new PrintWriter(outputStream);
		writer.println(" ************************************ ");
		writer.println(" ****** RESUMEN DE SCRIPTS ");
		writer.println(" ****** TRAMITE:  " + tram.getIdentificador());
		writer.println(" ****** VERSION:  " + tv.getNumeroVersion());
		writer.println(" ****** RELEASE:  " + tv.getRelease());
		writer.println(" ****** HUELLA :  " + tv.getHuella());
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		final String strDate = dateFormat.format(new Date());
		writer.println(" ****** FECHA  :  " + strDate);
		writer.println(" ****** TOTAL DE SCRIPTS:  " + this.data.size());
		writer.println(" ************************************ ");
		writer.println("");
		writer.println("");
		writer.println("");
		for (final ScriptInfo scriptInfo : data) {
			writer.println(
					" ************************************************************************************************************ ");
			writer.println(scriptInfo.getResumenTXT());
			writer.println(
					" ************************************************************************************************************ ");
			final Script script = scriptService.getScript(scriptInfo.getCodigoScript());
			writer.println(script.getContenido());
			if (script.getMensajes() != null && !script.getMensajes().isEmpty()) {
				writer.println("");
				for (final LiteralScript literalScript : script.getMensajes()) {
					for (final Traduccion traduccion : literalScript.getLiteral().getTraducciones()) {
						writer.println("// " + literalScript.getIdentificador() + " (" + traduccion.getIdioma() + ") : "
								+ traduccion.getLiteral());
					}
				}
			}
			writer.println("");
		}
		writer.close();

		final InputStream myInputStream = new ByteArrayInputStream(outputStream.toByteArray());
		return new DefaultStreamedContent(myInputStream, "text/plain",
				"scripts_" + tram.getIdentificador() + "_" + tv.getNumeroVersion() + ".txt");

	}

	/**
	 * Consultar un script.
	 *
	 * @param scriptInfo
	 */
	public void consultarScript(final ScriptInfo scriptInfo) {

		final Map<String, String> map = new HashMap<>();
		if (scriptInfo.getComponente() == null) {
			map.put(TypeParametroVentana.TIPO_SCRIPT_FLUJO.toString(), UtilJSON.toJSON(scriptInfo.getTipoScript()));
		} else {
			map.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
					UtilJSON.toJSON(scriptInfo.getTipoScript()));
		}
		final Script script = this.scriptService.getScript(scriptInfo.getCodigoScript());
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		map.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.CONSULTA, map, true, 700);

	}

	/** Cancelar. */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteScriptDialog");
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public List<ScriptInfo> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final List<ScriptInfo> data) {
		this.data = data;
	}

}
