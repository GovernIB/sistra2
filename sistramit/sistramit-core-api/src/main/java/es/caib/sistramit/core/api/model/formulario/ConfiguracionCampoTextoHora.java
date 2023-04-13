package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto hora.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoHora extends ConfiguracionCampoTexto {

	/**
	 * Opciones particularizadas.
	 */
	private OpcionesCampoTextoHora opciones = new OpcionesCampoTextoHora();

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoHora() {
		super();
		setContenido(TypeTexto.HORA);
	}

	/**
	 * Método de acceso a opciones.
	 * 
	 * @return opciones
	 */
	public OpcionesCampoTextoHora getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 * 
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoTextoHora opciones) {
		this.opciones = opciones;
	}

}
