package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto IBAN.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionCampoTextoIban extends ConfiguracionCampoTexto {

	/**
	 * Opciones particularizadas.
	 */
	private OpcionesCampoTextoIBAN opciones = new OpcionesCampoTextoIBAN();

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoIban() {
		super();
		setContenido(TypeTexto.IBAN);
	}

	/**
	 * Método de acceso a opciones.
	 * 
	 * @return opciones
	 */
	public OpcionesCampoTextoIBAN getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 * 
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoTextoIBAN opciones) {
		this.opciones = opciones;
	}

}
