package es.caib.sistramit.core.api.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.AvisoPlataforma;
import es.caib.sistramit.core.api.model.flujo.Entidad;
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
	 * Idioma.
	 */
	private String idioma;

	/**
	 * Título trámite.
	 */
	private String titulo;

	/**
	 * Niveles autenticación.
	 */
	private List<TypeAutenticacion> niveles = new ArrayList<>();

	/**
	 * Avisos plataforma.
	 */
	private List<AvisoPlataforma> avisos = new ArrayList<>();

	/**
	 * Indica si se debe bloquear el login (aviso bloqueante).
	 */
	private boolean bloquear;

	/**
	 * Entidad.
	 */
	private Entidad entidad;

	/**
	 * QAA.
	 */
	private String qaa;

	/**
	 * Debug.
	 */
	private boolean debug;

	/**
	 * Login auto automático.
	 */
	private boolean loginAnonimoAuto;

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

	/**
	 * Método de acceso a entidad.
	 *
	 * @return entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * Método para establecer entidad.
	 *
	 * @param entidad
	 *            entidad a establecer
	 */
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * Método de acceso a qAA.
	 *
	 * @return qAA
	 */
	public String getQaa() {
		return qaa;
	}

	/**
	 * Método para establecer qAA.
	 *
	 * @param qAA
	 *            qAA a establecer
	 */
	public void setQaa(String qAA) {
		this.qaa = qAA;
	}

	/**
	 * Método de acceso a idioma.
	 *
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 *
	 * @param idioma
	 *            idioma a establecer
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Convierte niveles autenticacion a String separado por ";".
	 *
	 * @return niveles autenticacion a String separado por ";"
	 */
	public String nivelesAutenticacionToString() {
		String res = "";
		if (getNiveles() != null) {
			boolean primer = true;
			for (final TypeAutenticacion a : getNiveles()) {
				if (!primer) {
					res += ";";
				}
				res += a.toString();
				primer = false;
			}
		}
		return res;
	}

	/**
	 * Método de acceso a debug.
	 *
	 * @return debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Método para establecer debug.
	 *
	 * @param debug
	 *            debug a establecer
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Método de acceso a loginAnonimoAuto.
	 *
	 * @return loginAnonimoAuto
	 */
	public boolean isLoginAnonimoAuto() {
		return loginAnonimoAuto;
	}

	/**
	 * Método para establecer loginAnonimoAuto.
	 *
	 * @param loginAnonimoAuto
	 *            loginAnonimoAuto a establecer
	 */
	public void setLoginAnonimoAuto(boolean loginAnonimoAuto) {
		this.loginAnonimoAuto = loginAnonimoAuto;
	}

	/**
	 * Método de acceso a bloquear.
	 * 
	 * @return bloquear
	 */
	public boolean isBloquear() {
		return bloquear;
	}

	/**
	 * Método para establecer bloquear.
	 * 
	 * @param bloquear
	 *            bloquear a establecer
	 */
	public void setBloquear(boolean bloquear) {
		this.bloquear = bloquear;
	}

}
