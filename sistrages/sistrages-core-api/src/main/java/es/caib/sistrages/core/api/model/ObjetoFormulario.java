package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public abstract class ObjetoFormulario extends ModelApi {

	/** Id. */
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

}
