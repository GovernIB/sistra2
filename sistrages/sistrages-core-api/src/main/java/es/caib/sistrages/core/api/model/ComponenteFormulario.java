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
public abstract class ComponenteFormulario extends ObjetoFormulario {

	/** Código. */
	private String idComponente;

	/** Tipo componente. */
	private TypeComponenteFormulario tipo;

	/** Orden. */
	private int orden;

	/** Columnas. */
	private int numColumnas = 1;

	/** Literal texto componente. */
	private Literal texto;

	/** Literal ayuda componente. */
	private Literal ayuda;

	/** Indica si se muestra texto componente. */
	private boolean mostrarTexto = true;

	/** Indica alineación texto. */
	private TypeAlineacionTexto alineacionTexto = TypeAlineacionTexto.IZQUIERDA;

	public String getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(final String codigo) {
		this.idComponente = codigo;
	}

	public TypeComponenteFormulario getTipo() {
		return tipo;
	}

	public void setTipo(final TypeComponenteFormulario tipo) {
		this.tipo = tipo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(final int orden) {
		this.orden = orden;
	}

	public int getNumColumnas() {
		return numColumnas;
	}

	public void setNumColumnas(final int columnas) {
		this.numColumnas = columnas;
	}

	public Literal getTexto() {
		return texto;
	}

	public void setTexto(final Literal texto) {
		this.texto = texto;
	}

	public Literal getAyuda() {
		return ayuda;
	}

	public void setAyuda(final Literal ayuda) {
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
