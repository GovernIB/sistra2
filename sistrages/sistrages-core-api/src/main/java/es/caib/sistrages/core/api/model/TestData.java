package es.caib.sistrages.core.api.model;

/**
 * Datos de test.
 * 
 * @author Indra
 *
 */

@SuppressWarnings("serial")
public class TestData extends ModelApi {

	private String codigo;

	private String descripcion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}
