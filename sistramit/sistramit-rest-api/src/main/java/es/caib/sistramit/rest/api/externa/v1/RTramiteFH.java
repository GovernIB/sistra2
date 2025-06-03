package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModelProperty;

public class RTramiteFH {

	/** Trámite. **/
	@ApiModelProperty(value = "Trámite", required = true)
	private String tramite;

	/** Versión. */
	@ApiModelProperty(value = "Versión", required = true)
	private int version;

	/** Idioma. */
	@ApiModelProperty(value = "Idioma", required = true)
	private String idioma;

	/** Id trámite catálogo. */
	@ApiModelProperty(value = "Id trámite en catálogo", required = true)
	private String idTramiteCatalogo;

	/** Indica si es servicio o procedimiento. */
	@ApiModelProperty(value = "Indica si es servicio", required = true)
	private boolean servicioCatalogo;

	/** Parámetros inicio trámite. */
	@ApiModelProperty(value = "Parámetros inicio", required = false)
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
