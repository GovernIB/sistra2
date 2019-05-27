package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto email.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoEmail extends ConfiguracionCampoTexto {

	/**
	 * Opciones campo email.
	 */
	private OpcionesCampoTextoEmail opciones = new OpcionesCampoTextoEmail();

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoEmail() {
		super();
		setContenido(TypeTexto.EMAIL);
	}

	/**
	 * Método de acceso a opciones.
	 * 
	 * @return opciones
	 */
	public OpcionesCampoTextoEmail getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 * 
	 * @param opciones
	 *            opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoTextoEmail opciones) {
		this.opciones = opciones;
	}

}
