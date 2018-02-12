package es.caib.sistrages.core.api.model;

import java.io.Serializable;

/**
 * Datos de test.
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TestData implements Serializable{

	private String codigo;
	
	private String descripcion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
