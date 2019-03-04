package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de tramite simple.
 *
 * @author slromero
 *
 */
public class TramiteSimple {

	/** Idiomas soportados. **/
	private String codigo;

	/** Idiomas soportados. **/
	private String idiomasSoportados;

	/** Lista de pasos simples **/
	private List<TramiteSimplePaso> pasos;

	public TramiteSimple() {
		setPasos(new ArrayList<>());
	}

	/**
	 * @return the pasos
	 */
	public List<TramiteSimplePaso> getPasos() {
		return pasos;
	}

	/**
	 * @param pasos
	 *            the pasos to set
	 */
	public void setPasos(final List<TramiteSimplePaso> pasos) {
		this.pasos = pasos;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the idiomasSoportados
	 */
	public String getIdiomasSoportados() {
		return idiomasSoportados;
	}

	/**
	 * @param idiomasSoportados
	 *            the idiomasSoportados to set
	 */
	public void setIdiomasSoportados(final String idiomasSoportados) {
		this.idiomasSoportados = idiomasSoportados;
	}
}
