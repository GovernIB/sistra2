package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;

/**
 * Configuración de un campo del formulario de tipo texto codigo postal.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ConfiguracionCampoTextoCP extends ConfiguracionCampoTexto {

	/**
	 * Opciones particularizadas.
	 */
	private OpcionesCampoTextoCP opciones = new OpcionesCampoTextoCP();

	/**
	 * Constructor.
	 */
	public ConfiguracionCampoTextoCP() {
		super();
		setContenido(TypeTexto.CODIGO_POSTAL);
	}

	/**
	 * Método de acceso a opciones.
	 * 
	 * @return opciones
	 */
	public OpcionesCampoTextoCP getOpciones() {
		return opciones;
	}

	/**
	 * Método para establecer opciones.
	 * 
	 * @param opciones
	 *                     opciones a establecer
	 */
	public void setOpciones(final OpcionesCampoTextoCP opciones) {
		this.opciones = opciones;
	}

}
