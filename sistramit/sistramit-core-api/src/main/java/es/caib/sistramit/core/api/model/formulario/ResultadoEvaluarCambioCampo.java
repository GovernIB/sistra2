package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Resultado de evaluar el cambio de un campo con dependencias en los scripts.
 * Se devuelve la lista de campos modificados, bien por sus valores, su estado
 * (enabled, disabled o readonly) o sus valores posibles (campo selector). Se
 * ejecuta también el script de validación de un campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoEvaluarCambioCampo implements Serializable {

	/**
	 * Estado validación.
	 */
	private ValidacionEstado validacion;

	/**
	 * Lista de campos para los que se ha modificado su valor.
	 */
	private List<ValorCampo> valores = new ArrayList<>();

	/**
	 * Lista de campos para los que se ha modificado su estado.
	 */
	private List<ConfiguracionModificadaCampo> configuracion = new ArrayList<>();

	/**
	 * Lista de campos de tipo selector para los que se modifican sus valores
	 * posibles. Sólo para tipo lista desplegable y múltiple.Para tipo ventana no
	 * hace falta para actualizar la página.
	 */
	private List<ValoresPosiblesCampo> valoresPosibles = new ArrayList<>();

	/**
	 * Método de acceso a valores.
	 *
	 * @return valores
	 */
	public List<ValorCampo> getValores() {
		return valores;
	}

	/**
	 * Método para establecer valores.
	 *
	 * @param pValores
	 *            valores a establecer
	 */
	public void setValores(final List<ValorCampo> pValores) {
		valores = pValores;
	}

	/**
	 * Método de acceso a estados.
	 *
	 * @return estados
	 */
	public List<ConfiguracionModificadaCampo> getConfiguracion() {
		return configuracion;
	}

	/**
	 * Método para establecer estados.
	 *
	 * @param pConfiguracion
	 *            el nuevo valor para configuracion
	 */
	public void setConfiguracion(final List<ConfiguracionModificadaCampo> pConfiguracion) {
		configuracion = pConfiguracion;
	}

	/**
	 * Método de acceso a valoresPosibles.
	 *
	 * @return valoresPosibles
	 */
	public List<ValoresPosiblesCampo> getValoresPosibles() {
		return valoresPosibles;
	}

	/**
	 * Método para establecer valoresPosibles.
	 *
	 * @param pValoresPosibles
	 *            valoresPosibles a establecer
	 */
	public void setValoresPosibles(final List<ValoresPosiblesCampo> pValoresPosibles) {
		valoresPosibles = pValoresPosibles;
	}

	/**
	 * Método de acceso a estado.
	 * 
	 * @return estado
	 */
	public ValidacionEstado getValidacion() {
		return validacion;
	}

	/**
	 * Método para establecer estado.
	 * 
	 * @param estado
	 *            estado a establecer
	 */
	public void setValidacion(ValidacionEstado estado) {
		this.validacion = estado;
	}

}
