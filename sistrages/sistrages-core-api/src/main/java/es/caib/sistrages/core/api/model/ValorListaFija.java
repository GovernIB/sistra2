package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public class ValorListaFija extends ModelApi {

	private Long codigo;

	private Literal descripcion;

	private String valor;

	private boolean porDefecto;

	private int orden;

	public ValorListaFija() {
		super();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long id) {
		this.codigo = id;
	}

	public Literal getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(final String valor) {
		this.valor = valor;
	}

	public boolean isPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}
}
