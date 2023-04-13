package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto fecha.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoFecha extends ConfiguracionCampoTexto {

	/**
	 * Opciones particularizadas.
	 */
	private OpcionesCampoTextoFecha opciones = new OpcionesCampoTextoFecha();

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoFecha() {
		super();
		setContenido(TypeTexto.FECHA);
	}

	/**
	 * Método de acceso a opciones.
	 * 
	 * @return opciones
	 */
	public OpcionesCampoTextoFecha getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 * 
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoTextoFecha opciones) {
		this.opciones = opciones;
	}

}
