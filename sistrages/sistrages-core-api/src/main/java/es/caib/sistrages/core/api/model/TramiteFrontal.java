package es.caib.sistrages.core.api.model;

import java.util.List;

/**
 * The Class TramiteVersion.
 */

public class TramiteFrontal extends ModelApi {

	private Tramite tramite;
	private List<TramiteVersion> listaVersiones;
	private Integer ultimaVersion;

	public TramiteFrontal(final Tramite tramite, final List<TramiteVersion> listaVersiones) {
		super();
		this.tramite = tramite;
		this.listaVersiones = listaVersiones;
		this.ultimaVersion = 0;
	}

	public TramiteFrontal(final Tramite tramite, final List<TramiteVersion> listaVersiones, Integer ultimaVersion) {
		super();
		this.tramite = tramite;
		this.listaVersiones = listaVersiones;
		this.ultimaVersion = ultimaVersion;
	}

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(final Tramite tramite) {
		this.tramite = tramite;
	}

	public List<TramiteVersion> getListaVersiones() {
		return listaVersiones;
	}

	public void setListaVersiones(final List<TramiteVersion> listaVersiones) {
		this.listaVersiones = listaVersiones;
	}

	public Integer getUltimaVersion() {
		return ultimaVersion;
	}

	public void setUltimaVersion(Integer ultimaVersion) {
		this.ultimaVersion = ultimaVersion;
	}
}
