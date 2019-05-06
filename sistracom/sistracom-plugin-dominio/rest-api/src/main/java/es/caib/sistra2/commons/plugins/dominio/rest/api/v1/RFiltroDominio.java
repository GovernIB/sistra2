package es.caib.sistra2.commons.plugins.dominio.rest.api.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro de dominio
 * 
 * @author Indra
 *
 */
public class RFiltroDominio {

	/** Id dominio. **/
	private String idDominio;

	/**
	 * Filas de valores.
	 */
	private List<RParametroDominio> filtro = new ArrayList<>();

	/** Consctructor. **/
	public RFiltroDominio() {
		filtro = new ArrayList<>();
	}

	/** Consctructor. **/
	public RFiltroDominio(final List<RParametroDominio> pfiltro) {
		filtro = pfiltro;
	}

	/**
	 * @return the filtro
	 */
	public List<RParametroDominio> getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
	 */
	public final void setFiltro(final List<RParametroDominio> filtro) {
		this.filtro = filtro;
	}

	/**
	 * Add Parametro.
	 *
	 * @param param
	 */
	public void addParam(final RParametroDominio param) {
		this.filtro.add(param);
	}

	/**
	 * @return the idDominio
	 */
	public String getIdDominio() {
		return idDominio;
	}

	/**
	 * @param idDominio
	 *            the idDominio to set
	 */
	public void setIdDominio(final String idDominio) {
		this.idDominio = idDominio;
	}
}
