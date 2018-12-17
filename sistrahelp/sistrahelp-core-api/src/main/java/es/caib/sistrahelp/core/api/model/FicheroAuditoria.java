package es.caib.sistrahelp.core.api.model;

/**
 * La clase FicheroAuditoria.
 */
@SuppressWarnings("serial")
public final class FicheroAuditoria extends ModelApi {

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
