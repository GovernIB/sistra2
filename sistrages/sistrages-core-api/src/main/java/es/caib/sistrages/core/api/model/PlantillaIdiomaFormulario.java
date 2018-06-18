package es.caib.sistrages.core.api.model;

@SuppressWarnings("serial")
public class PlantillaIdiomaFormulario extends ModelApi {

	private Long codigo;

	private Fichero fichero;

	private String idioma;

	/**
	 * Crea una nueva instancia de PlantillaIdiomaFormulario.
	 */
	public PlantillaIdiomaFormulario() {
		super();
	}

	public PlantillaIdiomaFormulario(final String pIdioma) {
		super();
		setIdioma(pIdioma);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long id) {
		this.codigo = id;
	}

	public Fichero getFichero() {
		return fichero;
	}

	public void setFichero(final Fichero fichero) {
		this.fichero = fichero;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

}
