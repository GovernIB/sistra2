package es.caib.sistrages.core.api.model.migracion;

import java.util.List;

public class PantalSistra {

	private Long panCodi;

	private String panNombre;
	private Long panOrden;
	private String panExpres;
	private Long panUltima;
	private Long panInicia;
	private Long panCodfor;
	private String panDetall;

	private List<ComponSistra> componentes;

	public PantalSistra() {
		super();
	}

	public Long getPanCodi() {
		return panCodi;
	}

	public void setPanCodi(final Long panCodi) {
		this.panCodi = panCodi;
	}

	public String getPanNombre() {
		return panNombre;
	}

	public void setPanNombre(final String panNombre) {
		this.panNombre = panNombre;
	}

	public Long getPanOrden() {
		return panOrden;
	}

	public void setPanOrden(final Long panOrden) {
		this.panOrden = panOrden;
	}

	public String getPanExpres() {
		return panExpres;
	}

	public void setPanExpres(final String panExpres) {
		this.panExpres = panExpres;
	}

	public Long getPanUltima() {
		return panUltima;
	}

	public void setPanUltima(final Long panUltima) {
		this.panUltima = panUltima;
	}

	public Long getPanInicia() {
		return panInicia;
	}

	public void setPanInicia(final Long panInicia) {
		this.panInicia = panInicia;
	}

	public Long getPanCodfor() {
		return panCodfor;
	}

	public void setPanCodfor(final Long panCodfor) {
		this.panCodfor = panCodfor;
	}

	public String getPanDetall() {
		return panDetall;
	}

	public void setPanDetall(final String panDetall) {
		this.panDetall = panDetall;
	}

	public List<ComponSistra> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ComponSistra> componentes) {
		this.componentes = componentes;
	}

}
