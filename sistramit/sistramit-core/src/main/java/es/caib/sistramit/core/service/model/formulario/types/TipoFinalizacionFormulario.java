package es.caib.sistramit.core.service.model.formulario.types;

/**
 * Tipo para indicar finalización formulario.
 *
 * @author Indra
 *
 */
public enum TipoFinalizacionFormulario {

	/** Finalizado. */
	FINALIZADO(0),
	/** Cancelado. */
	CANCELADO(1),
	/** GUARDADO A MITAD. */
	SIN_FINALIZAR(2);

	private int valor;

	private TipoFinalizacionFormulario(final int iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un entero en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TipoFinalizacionFormulario fromInt(final Integer valueInt) {
		TipoFinalizacionFormulario respuesta = null;
		if (valueInt != null) {
			for (final TipoFinalizacionFormulario b : TipoFinalizacionFormulario.values()) {
				if (valueInt.intValue() == b.getValor()) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	/**
	 * Convierte un boolean en enumerado.
	 *
	 * @param valueBool
	 *                      valor boolean
	 * @return TipoFinalizacionFormulario
	 */
	public static TipoFinalizacionFormulario fromBoolean(final boolean valueBool) {
		TipoFinalizacionFormulario respuesta = null;
		if (valueBool) {
			respuesta = TipoFinalizacionFormulario.CANCELADO;
		} else {
			respuesta = TipoFinalizacionFormulario.FINALIZADO;
		}
		return respuesta;
	}

	@Override
	public String toString() {
		return valor + "";
	}

	public int getValor() {
		return valor;
	}

}
