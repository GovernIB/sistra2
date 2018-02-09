package es.caib.sistra2.stg.frontend.model;

public class OpcionArbol {

	private String name;

	private String url;

	public OpcionArbol(final String name) {
		super();
		this.name = name;
	}

	public OpcionArbol(final String nombre, final String url) {
		super();
		this.name = nombre;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(final String nombre) {
		this.name = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return name;
	}

}
