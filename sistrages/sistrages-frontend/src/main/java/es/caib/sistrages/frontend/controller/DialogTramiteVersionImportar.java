package es.caib.sistrages.frontend.controller;

import java.io.File;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionImportar extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Tramite version. */
	private Tramite data;

	/**
	 * Inicialización.
	 */
	public void init() {

		data = tramiteService.getTramite(Long.valueOf(id));

		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "En fase BETA!");

	}

	/**
	 * Prueba JSON
	 *
	 * @throws IOException
	 */
	public void pruebaJSON() throws IOException {

		final String json = FileUtils.readFileToString(new File("C://tramiteVersion.json.sistra2"));
		final TramiteVersion model = (TramiteVersion) UtilJSON.fromJSON(json, TramiteVersion.class);

		final String json2 = FileUtils.readFileToString(new File("C://tramiteVersion.json2.sistra2"));
		final TramitePasoDebeSaber tp2 = (TramitePasoDebeSaber) UtilJSON.fromJSON(json2, TramitePasoDebeSaber.class);

	}

	/**
	 * Prueba Serealize
	 *
	 * @throws IOException
	 */
	public void pruebaSerialize() throws IOException {

		final byte[] content = FileUtils.readFileToByteArray(new File("C://tramiteVersion.byte.sistra2"));
		final TramiteVersion model = (TramiteVersion) UtilCoreApi.deserialize(content);

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
	 * @return the data
	 */
	public Tramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tramite data) {
		this.data = data;
	}

}
