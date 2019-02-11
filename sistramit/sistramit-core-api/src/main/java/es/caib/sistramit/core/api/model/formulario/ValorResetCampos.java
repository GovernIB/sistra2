package es.caib.sistramit.core.api.model.formulario;

/**
 * Valor especial usado en el script de modificacion de formularios y
 * autorrellenable para indicar. En el script de modificacion de formularios
 * indica que se reseteen todos los datos introducidos en el formulario. En el
 * script autorrellenable indica que se resetee valor del campo para el que se
 * establece el script.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValorResetCampos extends ValorCampo {

	/**
	 * Constructor.
	 */
	public ValorResetCampos() {
		super();
		this.setTipo(null);
	}

	@Override
	public boolean esVacio() {
		return true;
	}

	@Override
	public String print() {
		final String res = "RESET CAMPOS";
		return res;
	}

	/**
	 * Crea instancia ValorResetCampos.
	 *
	 * @return ValorResetCampos
	 */
	public static ValorResetCampos createNewValorResetCampos() {
		return new ValorResetCampos();
	}

	@Override
	public boolean esValorIgual(final ValorCampo pValorCampo) {
		return false;
	}

	@Override
	public ValorCampo duplicar() {
		return null;
	}

	@Override
	public void reemplazaValor(ValorCampo valorCampo) {
	}
}
