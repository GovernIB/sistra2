package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.types.TypeAccionFormularioPersonalizado;

/**
 * Acción formulario personalizada.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AccionFormularioPersonalizada implements AccionFormulario {

	/** Accion formulario. */
	private final TypeAccionFormularioPersonalizado tipo;

	/** Titulo. */
	private final String titulo;

	/** Valor. */
	private final String valor;

	/** Estilo. */
	private final String estilo;

	/**
	 * Validar formulario. Indica si debe validar los datos del formulario al pulsar
	 * el boton de acción, ya que existen acciones que no tendrán en cuenta los
	 * datos del formulario.
	 */
	private TypeSiNo validar = TypeSiNo.SI;

	/**
	 * Constructor.
	 *
	 * @param tipo
	 *            tipo
	 * @param titulo
	 *            titulo
	 * @param valor
	 *            valor
	 * @param estilo
	 *            estilo
	 */
	public AccionFormularioPersonalizada(TypeAccionFormularioPersonalizado tipo, String titulo, String valor,
			String estilo, boolean validar) {
		super();
		this.tipo = tipo;
		this.titulo = titulo;
		this.valor = valor;
		this.estilo = estilo;
		this.validar = TypeSiNo.fromBoolean(validar);
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeAccionFormularioPersonalizado getTipo() {
		return tipo;
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
	 * Método de acceso a valor.
	 *
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Método de acceso a estilo.
	 *
	 * @return estilo
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * Método de acceso a validar.
	 *
	 * @return validar
	 */
	public TypeSiNo getValidar() {
		return validar;
	}

}
