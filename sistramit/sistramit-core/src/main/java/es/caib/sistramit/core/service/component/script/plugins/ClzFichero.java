package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistramit.core.service.model.integracion.FicheroDominio;
import es.caib.sistramit.core.service.model.script.ClzFicheroInt;

/**
 * Clase de acceso a un fichero desde los plugins.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzFichero implements ClzFicheroInt {

	/**
	 * Fichero.
	 */
	private FicheroDominio fichero;

	/**
	 * Constructor.
	 */
	public ClzFichero() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param pfichero
	 *            fichero
	 *
	 */
	public ClzFichero(final FicheroDominio pfichero) {
		fichero = pfichero;
	}

	/**
	 * Constructor.
	 *
	 * @param pNombre
	 *            Nombre fichero
	 * @param pContenidoB64
	 *            Contenido b64
	 */
	public ClzFichero(final String pNombre, final String pContenidoB64) {
		super();
		fichero = new FicheroDominio();
		fichero.setNombre(pNombre);
		fichero.setContenidoB64(pContenidoB64);
	}


	@Override
	public String getNombre() {
		String nombre = null;
		if (fichero != null) {
			nombre = fichero.getNombre();
		}
		return nombre;
	}


	@Override
	public void setNombre(final String pNombre) {
		if (fichero == null) {
			fichero = new FicheroDominio();
		}
		fichero.setNombre(pNombre);

	}


	@Override
	public String getContenidoB64() {
		String contenidoB64 = null;
		if (fichero != null) {
			contenidoB64 = fichero.getContenidoB64();
		}
		return contenidoB64;
	}


	@Override
	public void setContenidoB64(final String pContenidoB64) {
		if (fichero == null) {
			fichero = new FicheroDominio();
		}
		fichero.setContenidoB64(pContenidoB64);
	}

	@Override
	public void setDescripcion(final String pDescripcion) {
		if (fichero == null) {
			fichero = new FicheroDominio();
		}
		fichero.setDescripcion(pDescripcion);
	}

	@Override
	public String getDescripcion() {
		String descripcion = null;
		if (fichero != null) {
			descripcion = fichero.getDescripcion();
		}
		return descripcion;
	}

}
