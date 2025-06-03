package es.caib.sistramit.core.api.model.system.rest.externo;

import es.caib.sistramit.core.api.model.flujo.PersonaDesglosado;

import java.io.Serializable;

/**
 * Información trámite que se iniciará con acceso FH.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class InfoTramiteFH implements Serializable {

	/** Trámite. **/
	private String tramite;

	/** Versión. */
	private int version;

	/** Idioma. */
	private String idioma;

	/** Id trámite catálogo. */
	private String idTramiteCatalogo;

	/** Indica si es servicio o procedimiento. */
	private boolean servicioCatalogo;

	/** Parámetros inicio trámite. */
	private String parametros;

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getIdTramiteCatalogo() {
		return idTramiteCatalogo;
	}

	public void setIdTramiteCatalogo(String idTramiteCatalogo) {
		this.idTramiteCatalogo = idTramiteCatalogo;
	}

	public boolean isServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(boolean servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
}
