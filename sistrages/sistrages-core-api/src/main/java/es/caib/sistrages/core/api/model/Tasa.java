package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypePago;

/**
 *
 * Tasa.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Tasa extends ModelApi {

	/** Codigo. */
	private Long codigo;

	/** Identificador. */
	private String identificador;

	/** Descripcion. */
	private Literal descripcion;

	/** Obligatoriedad. */
	private TypeFormularioObligatoriedad obligatoriedad;

	/** Orden. **/
	private int orden;

	/** En caso de ser dependiente establece obligatoriedad */
	private Script scriptObligatoriedad;

	/** Tipo de plugin. **/
	private Plugin tipoPlugin;

	/** Permite establecer dinámicamente los datos del pago */
	private Script scriptPago;

	/** Tipo: T (Telemático) / P (Presencial). Dependerá del tipo de plugin. */
	private TypePago tipo;

	/** Indica que el pago es simulado. */
	private boolean simulado;

	/**
	 * Crea una nueva instancia de Dominio.
	 */
	public Tasa() {
		super();
	}

	/**
	 * Obtiene el valor de codigo.
	 *
	 * @return el valor de codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Establece el valor de codigo.
	 *
	 * @param codigo
	 *            el nuevo valor de codigo
	 */
	public void setCodigo(final Long id) {
		this.codigo = id;
	}

	/**
	 * Obtiene el valor de identificador.
	 *
	 * @return el valor de identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Establece el valor de identificador.
	 *
	 * @param identificador
	 *            el nuevo valor de identificador
	 */
	public void setIdentificador(final String codigo) {
		this.identificador = codigo;
	}

	/**
	 * Obtiene el valor de descripcion.
	 *
	 * @return el valor de descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor de descripcion.
	 *
	 * @param descripcion
	 *            el nuevo valor de descripcion
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el valor de obligatoriedad.
	 *
	 * @return el valor de obligatoriedad
	 */
	public TypeFormularioObligatoriedad getObligatoriedad() {
		return obligatoriedad;
	}

	/**
	 * Establece el valor de obligatoriedad.
	 *
	 * @param obligatoriedad
	 *            el nuevo valor de obligatoriedad
	 */
	public void setObligatoriedad(final TypeFormularioObligatoriedad obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * @return the scriptObligatoriedad
	 */
	public Script getScriptObligatoriedad() {
		return scriptObligatoriedad;
	}

	/**
	 * @param scriptObligatoriedad
	 *            the scriptObligatoriedad to set
	 */
	public void setScriptObligatoriedad(final Script scriptObligatoriedad) {
		this.scriptObligatoriedad = scriptObligatoriedad;
	}

	/**
	 * @return the tipoPlugin
	 */
	public Plugin getTipoPlugin() {
		return tipoPlugin;
	}

	/**
	 * @param tipoPlugin
	 *            the tipoPlugin to set
	 */
	public void setTipoPlugin(final Plugin tipoPlugin) {
		this.tipoPlugin = tipoPlugin;
	}

	/**
	 * @return the scriptPago
	 */
	public Script getScriptPago() {
		return scriptPago;
	}

	/**
	 * @param scriptPago
	 *            the scriptPago to set
	 */
	public void setScriptPago(final Script scriptPago) {
		this.scriptPago = scriptPago;
	}

	/**
	 * @return the simulado
	 */
	public boolean isSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 *            the simulado to set
	 */
	public void setSimulado(final boolean simulado) {
		this.simulado = simulado;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypePago tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the tipo
	 */
	public TypePago getTipo() {
		return tipo;
	}

}
