package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
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
	private List<FuenteDatosCampo> campos = new ArrayList<>();

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

	/**
	 * Metodo para obtener el Campo de fuente de datos segun una id.
	 *
	 * @param id
	 * @return
	 */
	public FuenteDatosCampo getCampoById(final Long id) {
		FuenteDatosCampo campo = null;
		for (final FuenteDatosCampo campoFD : this.campos) {
			if (campoFD.getId() != null && campoFD.getId().compareTo(id) == 0) {
				campo = campoFD;
				break;
			}
		}

		return campo;
	}

	/**
	 * Añade un campo.
	 *
	 * @param fuenteDatosCampo
	 */
	public void addCampo(final FuenteDatosCampo fuenteDatosCampo) {
		this.campos.add(fuenteDatosCampo);
	}

	/**
	 * Borra un campo.
	 *
	 * @param fuenteDatosCampo
	 */
	public void removeCampo(final FuenteDatosCampo fuenteDatosCampo) {
		if (this.campos.contains(fuenteDatosCampo)) {
			this.campos.remove(fuenteDatosCampo);
		}
	}

}
