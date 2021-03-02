package es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ArchivoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CampoLOPD;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionLOPD;
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
		final DefinicionLOPD lopd = new DefinicionLOPD();
		lopd.setTextoCabecera("Texto para la cabecera");
		final List<CampoLOPD> camposLopd = new ArrayList<>();
		final CampoLOPD campoTexto = new CampoLOPD();
		campoTexto.setTitulo("Campo texto");
		campoTexto.setDescripcion("Contenido del campo texto");
		camposLopd.add(campoTexto);
		final CampoLOPD campoArchivo = new CampoLOPD();
		campoArchivo.setTitulo("Campo archivo");
		campoArchivo.setDescripcion("Contenido del campo archivo");
		campoArchivo.setReferenciaArchivo("archivo1234");
		camposLopd.add(campoArchivo);
		final CampoLOPD campoEnlace = new CampoLOPD();
		campoEnlace.setTitulo("Campo enlace");
		campoEnlace.setDescripcion("Contenido del campo enlace");
		campoEnlace.setEnlace("http://www.google.es");
		camposLopd.add(campoEnlace);
		lopd.setCampos(camposLopd);
		procedimiento.setLopd(lopd);
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
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idTramiteCP, final boolean servicio,
			final String idioma) {
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
	public List<DefinicionTramiteCP> obtenerTramites(final String idTramite, final Integer version, final String idioma)
			throws CatalogoPluginException {
		return tramites;
	}

	@Override
	public ArchivoCP descargarArchivo(final String referenciaArchivo) throws CatalogoPluginException {
		final ArchivoCP a = new ArchivoCP();
		a.setFilename("lopd-mock.txt");
		a.setContent("lopd mock".getBytes(StandardCharsets.UTF_8));
		return a;
	}

}
