package es.caib.sistramit.core.service.component.script.plugins;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistramit.core.service.model.script.ClzProcedimientoCPInt;

/**
 * Datos usuario.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ClzProcedimientoCP implements ClzProcedimientoCPInt {

	/**
	 * Datos usuario.
	 */
	private final DefinicionProcedimientoCP procedimiento;

	/**
	 * Constructor.
	 *
	 * @param ptramite
	 *            usuario
	 */
	public ClzProcedimientoCP(final DefinicionProcedimientoCP pProcedimiento) {
		super();
		procedimiento = pProcedimiento;
	}

	@Override
	public String getDescripcion() {
		return procedimiento.getDescripcion();
	}

	@Override
	public String getIdentificador() {
		return procedimiento.getIdentificador();
	}

	@Override
	public String getIdProcedimientoSIA() {
		return procedimiento.getIdProcedimientoSIA();
	}

	@Override
	public String getOrganoResponsableDir3() {
		return procedimiento.getOrganoResponsableDir3();
	}

}
