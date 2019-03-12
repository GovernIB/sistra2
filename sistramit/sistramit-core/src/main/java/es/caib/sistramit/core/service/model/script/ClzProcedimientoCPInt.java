package es.caib.sistramit.core.service.model.script;

/**
 * Tramite CP.
 *
 *
 * @author Indra
 *
 */
public interface ClzProcedimientoCPInt extends PluginClass {

	/**
	 * Método de acceso a la descripción del procedimiento.
	 *
	 * @return Descripción
	 */
	public String getDescripcion();

	/**
	 * Método de acceso al identificador.
	 *
	 * @return Identificador
	 */
	public String getIdentificador();

	/**
	 * Método de acceso al id SIA
	 *
	 * @return Id SIA
	 */
	public String getIdProcedimientoSIA();

	/**
	 * Método de acceso al organo del responsable
	 *
	 * @return Dir3 responsable
	 */
	public String getOrganoResponsableDir3();

}
