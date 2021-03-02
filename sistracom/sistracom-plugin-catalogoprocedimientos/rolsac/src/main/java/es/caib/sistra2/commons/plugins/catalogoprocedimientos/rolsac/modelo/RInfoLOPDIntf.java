package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac.modelo;

/**
 * Interface info LOPD.
 * 
 * @author Indra
 *
 */
public interface RInfoLOPDIntf {

	/**
	 * Método de acceso a lopdCabecera.
	 *
	 * @return lopdCabecera
	 */
	String getLopdCabecera();

	/**
	 * Método de acceso a lopdResponsable.
	 *
	 * @return lopdResponsable
	 */
	String getLopdResponsable();

	/**
	 * Método de acceso a lopdFinalidad.
	 *
	 * @return lopdFinalidad
	 */
	String getLopdFinalidad();

	/**
	 * Método de acceso a lopdDestinatario.
	 *
	 * @return lopdDestinatario
	 */
	String getLopdDestinatario();

	/**
	 * Método de acceso a lopdDerechos.
	 *
	 * @return lopdDerechos
	 */
	String getLopdDerechos();

	/**
	 * Método de acceso a lopdLegitimacion.
	 *
	 * @return lopdLegitimacion
	 */
	RLegitimacion getLopdLegitimacion();

	/**
	 * Método de acceso a link_lopdInfoAdicional.
	 *
	 * @return link_lopdInfoAdicional
	 */
	RLink getLink_lopdInfoAdicional();
}
