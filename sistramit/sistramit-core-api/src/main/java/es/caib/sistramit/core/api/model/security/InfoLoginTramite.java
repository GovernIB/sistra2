package es.caib.sistramit.core.api.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;

/**
 * Informacion del tramite para el login.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class InfoLoginTramite implements Serializable {

	/**
	 * Título trámite.
	 */
	private String titulo;

	/**
	 * Url entidad.
	 */
	private String urlEntidad;

	/**
	 * Niveles autenticación.
	 */
	private List<TypeAutenticacion> niveles = new ArrayList<>();

	/**
	 * Avisos plataforma.
	 */
	private List<AvisoPlataforma> avisos = new ArrayList<>();

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param pTitulo
	 *            titulo a establecer
	 */
	public void setTitulo(final String pTitulo) {
		titulo = pTitulo;
	}

	/**
	 * Método de acceso a niveles.
	 *
	 * @return niveles
	 */
	public List<TypeAutenticacion> getNiveles() {
		return niveles;
	}

	/**
	 * Método de acceso a avisos.
	 *
	 * @return avisos
	 */
	public List<AvisoPlataforma> getAvisos() {
		return avisos;
	}

	/**
	 * Método para establecer avisos.
	 *
	 * @param pAvisos
	 *            avisos a establecer
	 */
	public void setAvisos(final List<AvisoPlataforma> pAvisos) {
		avisos = pAvisos;
	}

	/**
	 * Método para establecer niveles.
	 *
	 * @param pNiveles
	 *            niveles a establecer
	 */
	public void setNiveles(final List<TypeAutenticacion> pNiveles) {
		niveles = pNiveles;
	}

	public String getUrlEntidad() {
		return urlEntidad;
	}

	public void setUrlEntidad(final String urlEntidad) {
		this.urlEntidad = urlEntidad;
	}

}
