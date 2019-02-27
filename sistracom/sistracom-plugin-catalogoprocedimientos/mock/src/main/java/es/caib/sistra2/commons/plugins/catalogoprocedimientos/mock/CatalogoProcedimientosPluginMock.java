package es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;

/**
 * Plugin mock catálogo procedimientos.
 *
 * @author Indra
 *
 */
public class CatalogoProcedimientosPluginMock extends AbstractPluginProperties
		implements ICatalogoProcedimientosPlugin {

	private List<DefinicionProcedimientoCP> procedimientos;
	private List<DefinicionTramiteCP> tramites;

	public CatalogoProcedimientosPluginMock() {
		init();
	}

	public CatalogoProcedimientosPluginMock(final String prefijoPropiedades, final Properties properties) {
		init();
	}

	private void init() {

		procedimientos = new ArrayList<>();
		tramites = new ArrayList<>();

		final DefinicionProcedimientoCP procedimiento = new DefinicionProcedimientoCP();
		procedimiento.setIdentificador("PROC1");
		procedimiento.setDescripcion("Procedimiento 1");
		procedimiento.setIdProcedimientoSIA("SIA1");
		procedimiento.setOrganoResponsableDir3("RespDIR3");

		procedimientos.add(procedimiento);

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador("TC1");
		dt.setDescripcion("Tramite 1");
		dt.setProcedimiento(procedimiento);
		dt.setVigente(true);
		dt.setOrganoDestinoDir3("DIR3-1");
		tramites.add(dt);

		final DefinicionTramiteCP dt2 = new DefinicionTramiteCP();
		dt2.setIdentificador("TC2");
		dt2.setDescripcion("Tramite 2");
		dt2.setProcedimiento(procedimiento);
		dt2.setVigente(false);
		dt2.setOrganoDestinoDir3("DIR3-2");
		tramites.add(dt2);
	}

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, final String idioma) {
		DefinicionTramiteCP res = null;
		for (final DefinicionTramiteCP t : tramites) {
			if (t.getIdentificador().equals(idTramiteCP)) {
				res = t;
				break;
			}
		}
		return res;
	}

	@Override
	public List<DefinicionProcedimientoCP> obtenerProcedimientos(final String idTramite, final String version,
			final String idioma) {
		return procedimientos;
	}

	@Override
	public List<DefinicionTramiteCP> obtenerTramites(final String idTramite, final Integer version, final String idioma)
			throws CatalogoPluginException {
		return tramites;
	}

}
