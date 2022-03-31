package es.caib.sistramit.core.service.component.script;

import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistra2.commons.utils.XssFilter;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.service.component.script.plugins.ClzValorCampoCompuesto;
import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;

/**
 * Utilidades para scripts.
 *
 * @author Indra
 *
 */
public final class ScriptUtils {

	/**
	 * Constructor.
	 */
	private ScriptUtils() {
		super();
	}

	/**
	 * Calcula mensaje de error.
	 *
	 * @param pCodigosError
	 *                                    Códigos de error y sus mensajes asociados
	 * @param pCodigoMensajeError
	 *                                    Código mensaje error a calcular
	 * @param pParametrosMensajeError
	 *                                    Parámetros mensaje error
	 * @return Mensaje de error
	 */
	public static String calculaMensajeError(final Map<String, String> pCodigosError, final String pCodigoMensajeError,
			final List<String> pParametrosMensajeError) {
		String res = null;
		if (pCodigosError != null) {
			res = pCodigosError.get(pCodigoMensajeError);
			if (res != null && pParametrosMensajeError != null && pParametrosMensajeError.size() > 0) {
				for (int i = 0; i < pParametrosMensajeError.size(); i++) {
					res = StringUtils.replace(res, "{" + i + "}", pParametrosMensajeError.get(i));
				}
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix missatge error amb còdi " + pCodigoMensajeError);
		}
		return res;
	}

	/**
	 * Valida datos persona.
	 *
	 * @param nifNormalizado
	 *                           Nif normalizado
	 * @param pNombre
	 *                           Nombre
	 * @param pApellido1
	 *                           Apellido 1
	 * @param pApellido2
	 *                           Apellido 2
	 * @throws ScriptException
	 *                             Genera excepcion si no pasa validacion
	 */
	public static void validarDatosPersona(final String nifNormalizado, final String pNombre, final String pApellido1,
			final String pApellido2) throws ScriptException {
		if (!NifUtils.esNifPersonaFisica(nifNormalizado) && !NifUtils.esNifPersonaJuridica(nifNormalizado)) {
			throw new ScriptException("La dada proporcionada com nif persona no és un nif vàlid: " + nifNormalizado);
		}

		if (StringUtils.isEmpty(pNombre) || !XssFilter.filtroXss(pNombre)) {
			throw new ScriptException(
					"La dada proporcionada com nom persona està buit o conté caràcters no permesos");
		}

		if (!StringUtils.isBlank(pApellido1) && !XssFilter.filtroXss(pApellido1)) {
			throw new ScriptException(
					"La dada proporcionada com cognom 1 persona conté caràcters no permesos");
		}

		if (!StringUtils.isBlank(pApellido2) && !XssFilter.filtroXss(pApellido2)) {
			throw new ScriptException(
					"La dada proporcionada com cognom 2 persona conté caràcters no permesos");
		}
	}

	/**
	 * Crea valor lista indexados a partir de un valor multiple.
	 *
	 * @param campo
	 *                          Id campo
	 * @param valorMultiple
	 *                          Valor multiple
	 * @return Valor lista indexados
	 */
	public static ValorCampoListaIndexados crearValorListaIndexados(final String campo,
			final ClzValorCampoMultipleInt valorMultiple) {
		final ValorCampoListaIndexados vci = new ValorCampoListaIndexados();
		vci.setId(campo);
		if (valorMultiple != null) {
			for (int i = 0; i < valorMultiple.getNumeroValores(); i++) {
				final ClzValorCampoCompuestoInt vcc = valorMultiple.getValor(i);
				vci.addValorIndexado(vcc.getCodigo(), vcc.getDescripcion());
			}
		}
		return vci;
	}

	/**
	 * Genera valor compuesto a partir de un valor indexado.
	 *
	 * @param vi
	 *               Valor indexado
	 * @return valor compuesto
	 */
	public static ClzValorCampoCompuestoInt crearValorCompuesto(final ValorIndexado vi) {
		ClzValorCampoCompuestoInt res;
		if (vi != null) {
			res = new ClzValorCampoCompuesto(vi.getValor(), vi.getDescripcion());
		} else {
			res = new ClzValorCampoCompuesto("", "");
		}
		return res;
	}

	/**
	 * Calucula mensaje a mostrar.
	 *
	 * @param codigoMensaje
	 *                                        Codigo mensaje
	 * @param parametrosMensaje
	 *                                        Parametros mensaje
	 * @param textoMensajeParticularizado
	 *                                        Texto particularizado
	 * @param pCodigosMensaje
	 *                                        Codigos de mensaje
	 * @return mensaje
	 */
	public static String calcularMensaje(final String codigoMensaje, final List<String> parametrosMensaje,
			final String textoMensajeParticularizado, final Map<String, String> pCodigosMensaje) {
		String textoMensajeError;
		if (StringUtils.isNotBlank(codigoMensaje)) {
			textoMensajeError = ScriptUtils.calculaMensajeError(pCodigosMensaje, codigoMensaje, parametrosMensaje);
		} else if (StringUtils.isNotBlank(textoMensajeParticularizado)) {
			textoMensajeError = textoMensajeParticularizado;
		} else {
			textoMensajeError = "";
		}
		return textoMensajeError;
	}

}
