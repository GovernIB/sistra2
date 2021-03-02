package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * LOPD Legitimacion.
 *
 * @author Indra
 *
 */
public class RLegitimacion {

	/** Identificador **/
	private String identificador;

	/** Descripcion **/
	private String nombre;

	/**
	 * Método de acceso a identificador.
	 * 
	 * @return identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Método para establecer identificador.
	 * 
	 * @param identificador
	 *                          identificador a establecer
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}

	/**
	 * Método de acceso a nombre.
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método para establecer nombre.
	 * 
	 * @param nombre
	 *                   nombre a establecer
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

}
