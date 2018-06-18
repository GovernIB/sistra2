package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public class PlantillaFormulario extends ModelApi {

	private Long codigo;

	private Long idFormateadorFormulario;

	private String descripcion;

	private boolean porDefecto;

	/**
	 * Crea una nueva instancia de PlantillaFormulario.
	 */
	public PlantillaFormulario() {
		super();
	}

	public PlantillaFormulario(final Long codigo) {
		super();
		setCodigo(codigo);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long id) {
		this.codigo = id;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + (porDefecto ? 1231 : 1237);
		return result;
	}

}
