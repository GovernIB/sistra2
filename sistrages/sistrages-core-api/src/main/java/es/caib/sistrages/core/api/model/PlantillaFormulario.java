package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public class PlantillaFormulario extends ModelApi {

	private Long id;

	private FormateadorFormulario formateadorFormulario;

	private String descripcion;

	private boolean porDefecto;

	/**
	 * Crea una nueva instancia de PlantillaFormulario.
	 */
	public PlantillaFormulario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public FormateadorFormulario getFormateadorFormulario() {
		return formateadorFormulario;
	}

	public void setFormateadorFormulario(final FormateadorFormulario formateadorFormulario) {
		this.formateadorFormulario = formateadorFormulario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(final boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	public int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((formateadorFormulario == null) ? 0 : formateadorFormulario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (porDefecto ? 1231 : 1237);
		return result;
	}

}
