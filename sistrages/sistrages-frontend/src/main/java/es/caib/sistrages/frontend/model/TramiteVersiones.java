package es.caib.sistrages.frontend.model;

import java.util.List;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;

public class TramiteVersiones {

	private Tramite tramite;
	private List<TramiteVersion> listaVersiones;

	public TramiteVersiones(final Tramite tramite, final List<TramiteVersion> listaVersiones) {
		super();
		this.tramite = tramite;
		this.listaVersiones = listaVersiones;
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

}
