package es.caib.sistra2.ate.core.api.model;

import java.io.Serializable;

public class EjemploDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;

	public EjemploDto(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EjemploDto() {
		super();
	}


}
