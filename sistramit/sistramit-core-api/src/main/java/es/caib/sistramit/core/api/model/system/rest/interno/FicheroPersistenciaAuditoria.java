package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

/**
 * La clase FicheroPersistenciaAuditoria (RestApiInternaService).
 */
@SuppressWarnings("serial")
public final class FicheroPersistenciaAuditoria implements Serializable {

	private String identificadorPaso;

	private String tipoPaso;

	private String nombre;

	private Long codigo;

	private String clave;

	private String tipo;

	public FicheroPersistenciaAuditoria(final String identificadorPaso, final String tipoPaso, final String nombre,
			final Long codigo, final String clave, final String tipo) {
		super();
		this.identificadorPaso = identificadorPaso;
		this.tipoPaso = tipoPaso;
		this.nombre = nombre;
		this.codigo = codigo;
		this.clave = clave;
		this.tipo = tipo;
	}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getTipoPaso() {
		return tipoPaso;
	}

	public void setTipoPaso(final String tipoPaso) {
		this.tipoPaso = tipoPaso;
	}

}
