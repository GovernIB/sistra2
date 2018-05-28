package es.caib.sistrages.frontend.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionExportar extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Tramite version. */
	private TramiteVersion tramiteVersion;

	/** Tramite. **/
	private Tramite tramite;

	/** Area. **/
	private Area area;

	/** Pasos. **/
	List<TramitePaso> pasos;

	/** Dominios. */
	List<Dominio> dominios;

	/**
	 * Inicialización.
	 */
	public void init() {

		tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(id));
		pasos = tramiteService.getTramitePasos(Long.valueOf(id));
		dominios = tramiteService.getTramiteDominios(Long.valueOf(id));
		tramiteVersion.setListaPasos(pasos);
		tramiteVersion.setListaDominios(dominios);
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());
		area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "En fase BETA!");

	}

	/**
	 * Prueba JSON
	 *
	 * @throws IOException
	 */
	public void pruebaJSON() throws IOException {
		final String json = UtilJSON.toJSON(tramiteVersion);

		// final ModelApi model = UtilJSON.fromJSON(json, TramiteVersion.class)
		final TramiteVersion model = (TramiteVersion) UtilJSON.fromJSON(json, TramiteVersion.class);

		final TramitePasoDebeSaber tp = (TramitePasoDebeSaber) tramiteVersion.getListaPasos().get(0);
		final String json2 = UtilJSON.toJSON(tp);
		final TramitePasoDebeSaber tp2 = (TramitePasoDebeSaber) UtilJSON.fromJSON(json2, TramitePasoDebeSaber.class);

		FileUtils.writeStringToFile(new File("C://tramiteVersion.json.sistra2"), json, "UTF-8");
		FileUtils.writeStringToFile(new File("C://tramiteVersion.json2.sistra2"), json2, "UTF-8");
	}

	/**
	 * Prueba Serealize
	 *
	 * @throws IOException
	 */
	public void pruebaSerialize() throws IOException {
		final byte[] content = UtilCoreApi.serialize(tramiteVersion);

		final TramiteVersion model = (TramiteVersion) UtilCoreApi.deserialize(content);
		FileUtils.writeByteArrayToFile(new File("C://tramiteVersion.byte.sistra2"), content);
	}

	private void resumen(final String ruta) throws IOException {
		final File ficheroResumen = new File(ruta + "//carga.txt");
		final List<String> lineas = new ArrayList<>();
		lineas.add("#DOCUMENTO DE CARGA DE SISTRA2");
		lineas.add("entorno=");
		lineas.add("version=1.0");
		lineas.add("fecha=" + Calendar.getInstance().getTime().toString());
		lineas.add("usuario=" + UtilJSF.getSessionBean().getUserName());

		FileUtils.writeLines(ficheroResumen, "UTF-8", lineas, "\n", true);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion data) {
		this.tramiteVersion = data;
	}

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the pasos
	 */
	public List<TramitePaso> getPasos() {
		return pasos;
	}

	/**
	 * @param pasos
	 *            the pasos to set
	 */
	public void setPasos(final List<TramitePaso> pasos) {
		this.pasos = pasos;
	}

	/**
	 * @return the dominios
	 */
	public List<Dominio> getDominios() {
		return dominios;
	}

	/**
	 * @param dominios
	 *            the dominios to set
	 */
	public void setDominios(final List<Dominio> dominios) {
		this.dominios = dominios;
	}

}
