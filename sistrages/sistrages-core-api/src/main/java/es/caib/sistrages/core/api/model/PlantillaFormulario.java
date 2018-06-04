package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public class PlantillaFormulario extends ModelApi {

	private Long id;

	private Long idFormateadorFormulario;

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

	public Long getIdFormateadorFormulario() {
		return idFormateadorFormulario;
	}

	public void setIdFormateadorFormulario(final Long formateadorFormulario) {
		this.idFormateadorFormulario = formateadorFormulario;
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
		result = prime * result + ((idFormateadorFormulario == null) ? 0 : idFormateadorFormulario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (porDefecto ? 1231 : 1237);
		return result;
	}

}
