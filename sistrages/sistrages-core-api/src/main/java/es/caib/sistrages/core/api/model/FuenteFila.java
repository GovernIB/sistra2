package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Fila de una fuente de datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteFila extends ModelApi {

	/** Id. **/
	private Long id;

	/** Fuente datos valores. **/
	FuenteDatosValores fuenteDatosValores;

	/** Lista de fuentes de datos. **/
	private List<FuenteDatosValor> datos;

	/** Constructor basico. **/
	public FuenteFila() {
		datos = new ArrayList<>();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the idString
	 */
	public String getIdString() {
		if (id == null) {
			return null;
		} else {
			return id.toString();
		}
	}

	/**
	 * @param idString
	 *            the id to set
	 */
	public void setIdString(final String idString) {
		if (id == null) {
			this.id = null;
		} else {
			this.id = Long.valueOf(idString);
		}
	}

	/**
	 * @return the datos
	 */
	public List<FuenteDatosValor> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(final List<FuenteDatosValor> datos) {
		this.datos = datos;
	}

	/**
	 * Agrega una fuente de datos.
	 *
	 * @param dato
	 */
	public void addFuenteDato(final FuenteDatosValor dato) {
		this.datos.add(dato);
	}

	/**
	 * Borra una fuente de datos.
	 *
	 * @param dato
	 */
	public void removeFuenteDato(final FuenteDatosValor dato) {
		this.datos.remove(dato);
	}

	/**
	 * @return the fuenteDatosValores
	 */
	public FuenteDatosValores getFuenteDatosValores() {
		return fuenteDatosValores;
	}

	/**
	 * @param fuenteDatosValores
	 *            the fuenteDatosValores to set
	 */
	public void setFuenteDatosValores(final FuenteDatosValores fuenteDatosValores) {
		this.fuenteDatosValores = fuenteDatosValores;
	}

	/**
	 * Ordena los valores
	 */
	public void sortValores() {
		// Ordenamos los valores según el orden del campo
		Collections.sort(this.datos, new Comparator<FuenteDatosValor>() {

			@Override
			public int compare(final FuenteDatosValor valor1, final FuenteDatosValor valor2) {
				if (valor1 == null || valor1.getOrdenCampo() == null) {
					return -1;
				}

				if (valor2 == null || valor1.getOrdenCampo() == null) {
					return 1;
				}

				return valor1.getOrdenCampo().compareTo(valor2.getOrdenCampo());
			}

		});
	}

	/**
	 * Devuelve un FuenteDatosValor segun una id.
	 * 
	 * @param codigo
	 * @return
	 */
	public FuenteDatosValor getDato(final Long codigo) {
		FuenteDatosValor valor = null;
		if (this.getDatos() != null) {
			for (final FuenteDatosValor val : this.getDatos()) {
				if (codigo.compareTo(val.getId()) == 0) {
					valor = val;
					break;
				}
			}
		}
		return valor;
	}

}
