package es.caib.sistrages.core.api.model;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 *
 * Fuente Datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FuenteDatos extends ModelApi {

	/** Id. */
	private Long codigo;

	/** Ambito. **/
	private TypeAmbito ambito;

	/** Codigo. */
	private String identificador;

	/** Descripcion. */
	private String descripcion;

	/** Campos. **/
	private List<FuenteDatosCampo> campos;

	public String getIdString() {
		if (codigo == null) {
			return null;
		} else {
			return codigo.toString();
		}
	}

	public void setIdString(final String idString) {
		if (idString == null) {
			this.codigo = null;
		} else {
			this.codigo = Long.valueOf(idString);
		}
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ambito
	 */
	public TypeAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final TypeAmbito ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the campos
	 */
	public List<FuenteDatosCampo> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<FuenteDatosCampo> campos) {
		this.campos = campos;
	}

}
