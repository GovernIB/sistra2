package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Registrar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoRegistrar extends DatosInternosPasoReferencia {

	/**
	 * Parámetros de registro.
	 */
	private ParametrosRegistro parametrosRegistro;

	/**
	 * Indica si se ha intentado registro y esta pendiente de reintentar.
	 */
	private boolean reintentarRegistro;

	/**
	 * Resultado de registro.
	 */
	private ResultadoRegistro resultadoRegistro;

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoRegistrar(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.REGISTRAR);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

	/**
	 * Método de acceso a parametrosRegistro.
	 *
	 * @return parametrosRegistro
	 */
	public ParametrosRegistro getParametrosRegistro() {
		return parametrosRegistro;
	}

	/**
	 * Método para establecer parametrosRegistro.
	 *
	 * @param parametrosRegistro
	 *            parametrosRegistro a establecer
	 */
	public void setParametrosRegistro(ParametrosRegistro parametrosRegistro) {
		this.parametrosRegistro = parametrosRegistro;
	}

	/**
	 * Método de acceso a reintentarRegistro.
	 *
	 * @return reintentarRegistro
	 */
	public boolean isReintentarRegistro() {
		return reintentarRegistro;
	}

	/**
	 * Método para establecer reintentarRegistro.
	 *
	 * @param reintentarRegistro
	 *            reintentarRegistro a establecer
	 */
	public void setReintentarRegistro(boolean reintentarRegistro) {
		this.reintentarRegistro = reintentarRegistro;
	}

	/**
	 * Método de acceso a resultadoRegistro.
	 *
	 * @return resultadoRegistro
	 */
	public ResultadoRegistro getResultadoRegistro() {
		return resultadoRegistro;
	}

	/**
	 * Método para establecer resultadoRegistro.
	 *
	 * @param resultadoRegistro
	 *            resultadoRegistro a establecer
	 */
	public void setResultadoRegistro(ResultadoRegistro resultadoRegistro) {
		this.resultadoRegistro = resultadoRegistro;
	}

}
