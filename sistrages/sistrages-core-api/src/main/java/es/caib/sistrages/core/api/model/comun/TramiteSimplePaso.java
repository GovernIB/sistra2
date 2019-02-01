package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistrages.core.api.model.types.TypePaso;

/**
 * Clase para crear paso simplificado
 *
 * @author Indra
 *
 */
public class TramiteSimplePaso {

	/** Codigo. **/
	private Long codigo;

	/** Tipo paso. **/
	private TypePaso tipo;

	/** Lista de formulario simples **/
	private List<TramiteSimpleFormulario> formularios;

	public TramiteSimplePaso() {
		setFormularios(new ArrayList<>());
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
	 * @return the tipo
	 */
	public TypePaso getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypePaso tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the formularios
	 */
	public List<TramiteSimpleFormulario> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios
	 *            the formularios to set
	 */
	public void setFormularios(final List<TramiteSimpleFormulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * Comprueba si es de tipo paso rellenar
	 *
	 * @return
	 */
	public boolean isTipoPasoRellenar() {
		return this.tipo != null && this.tipo == TypePaso.RELLENAR;
	}
}
