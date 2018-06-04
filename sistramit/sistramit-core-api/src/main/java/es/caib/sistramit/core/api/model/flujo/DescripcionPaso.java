package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Establece descripción del paso para mostrarlo en el Debe Saber.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DescripcionPaso implements Serializable {

	/**
	 * Id del paso.
	 */
	private String id;

	/**
	 * Título del paso.
	 */
	private String titulo;

	/**
	 * Tipo de paso.
	 */
	private TypePaso tipo;

	/**
	 * Descripción del paso.
	 */
	private String info;

	/**
	 * Crea nueva instancia DescripcionPaso.
	 *
	 * @return DescripcionPaso
	 */
	public static DescripcionPaso createNewDescripcionPaso() {
		return new DescripcionPaso();
	}

	/**
	 * Método de acceso a id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Indica id paso.
	 *
	 * @param pId
	 *            id a establecer
	 */
	public void setId(final String pId) {
		id = pId;
	}

	/**
	 * Indica tipo de paso.
	 *
	 * @return tipo
	 */
	public TypePaso getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	public void setTipo(final TypePaso pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a descripcion.
	 *
	 * @return descripcion
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Método para establecer descripcion.
	 *
	 * @param pDescripcion
	 *            descripcion a establecer
	 */
	public void setInfo(final String pDescripcion) {
		info = pDescripcion;
	}

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

}
