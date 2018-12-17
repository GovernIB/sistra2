package es.caib.sistrahelp.core.api.model;

import es.caib.sistrahelp.core.api.model.types.TypeDocumentoPersistencia;

/**
 * La clase FicheroPersistenciaAuditoria.
 */
public final class FicheroPersistenciaAuditoria extends ModelApi {

	private static final long serialVersionUID = 1L;

	private String identificadorPaso;

	private String tipoPaso;

	private String nombre;

	private Long codigo;

	private String clave;

	private TypeDocumentoPersistencia tipo;

	/**
	 * Crea una nueva instancia de Fichero.
	 */
	public FicheroPersistenciaAuditoria() {
		super();
	}

	public String getIdentificadorPaso() {
		return identificadorPaso;
	}

	public void setIdentificadorPaso(final String identificadorPaso) {
		this.identificadorPaso = identificadorPaso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(final String clave) {
		this.clave = clave;
	}

	public TypeDocumentoPersistencia getTipo() {
		return tipo;
	}

	public void setTipo(final TypeDocumentoPersistencia tipo) {
		this.tipo = tipo;
	}

	public String getTipoPaso() {
		return tipoPaso;
	}

	public void setTipoPaso(String tipoPaso) {
		this.tipoPaso = tipoPaso;
	}

}
