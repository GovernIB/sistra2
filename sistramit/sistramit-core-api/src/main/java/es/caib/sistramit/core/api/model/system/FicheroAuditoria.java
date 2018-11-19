package es.caib.sistramit.core.api.model.system;

import java.io.Serializable;

/**
 * La clase FicheroAuditoria.
 */
@SuppressWarnings("serial")
public final class FicheroAuditoria implements Serializable {

	/** Atributo nombre. */
	private String nombre;

	/** Atributo contenido. */
	private byte[] contenido;

	/**
	 * Crea una nueva instancia de Fichero.
	 */
	public FicheroAuditoria() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(final byte[] contenido) {
		this.contenido = contenido;
	}

}
