package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeComponenteFormulario;

/**
 * Componente formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class ComponenteFormulario extends ModelApi {

	/** Id. */
	private Long id;

	/** Código. */
	private String codigo;

	/** Tipo componente. */
	private TypeComponenteFormulario tipo;

	/** Orden. */
	private int orden;

	/** Columnas. */
	private int columnas = 1;

	/** Literal texto componente. */
	private Traducciones texto;

	/** Literal ayuda componente. */
	private Traducciones ayuda;

	/** Indica si se muestra texto componente. */
	private boolean mostrarTexto = true;

	/** Indica alineación texto. */
	private TypeAlineacionTexto alineacionTexto = TypeAlineacionTexto.IZQUIERDA;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public TypeComponenteFormulario getTipo() {
		return tipo;
	}

	protected void setTipo(final TypeComponenteFormulario tipo) {
		this.tipo = tipo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(final int columnas) {
		this.columnas = columnas;
	}

	public Traducciones getTexto() {
		return texto;
	}

	public void setTexto(final Traducciones texto) {
		this.texto = texto;
	}

	public Traducciones getAyuda() {
		return ayuda;
	}

	public void setAyuda(final Traducciones ayuda) {
		this.ayuda = ayuda;
	}

	public boolean isMostrarTexto() {
		return mostrarTexto;
	}

	public void setMostrarTexto(final boolean mostrarTexto) {
		this.mostrarTexto = mostrarTexto;
	}

	public TypeAlineacionTexto getAlineacionTexto() {
		return alineacionTexto;
	}

	public void setAlineacionTexto(final TypeAlineacionTexto alineacionTexto) {
		this.alineacionTexto = alineacionTexto;
	}

}
