package es.caib.sistramit.core.service.component.integracion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.repository.dao.DominioDao;

/**
 * Resolución de dominios.
 *
 * Se ejecuta en una transacción distinta para evitar que interfiera en el
 * flujo.
 *
 *
 * @author Indra
 *
 */
@Component("dominiosResolucionComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public final class DominiosResolucionComponentImpl implements DominiosResolucionComponent {

	/** Configuracion. */
	@Autowired
	private SistragesApiComponent sistragesApiComponent;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Configuracion. */
	@Autowired
	private DominioDao dominioDao;

	/**
	 * Resuelve dominio para Dominios de Fuente de Datos.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioFD(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		final RValoresDominio rvalores = sistragesApiComponent.resuelveDominioFuenteDatos(dominio, parametrosDominio);
		return convertToValoresDominio(rvalores);
	}

	/**
	 * Resuelve dominio para Dominios de Lista Fija
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioLF(final RDominio dominio) {
		final RValoresDominio valores = sistragesApiComponent.resuelveDominioListaFija(dominio);
		return convertToValoresDominio(valores);
	}

	/**
	 *
	 * @param rvalores
	 * @return
	 */
	private ValoresDominio convertToValoresDominio(final RValoresDominio rvalores) {
		ValoresDominio valores = null;
		if (rvalores != null) {
			valores = new ValoresDominio();
			valores.setCodigoError(rvalores.getCodigoError());
			valores.setDescripcionError(rvalores.getDescripcionError());
			valores.setError(rvalores.isError());
			valores.setDatos(rvalores.getDatos());
		}
		return valores;
	}

	/**
	 * Resuelve dominio para Dominios de SQL.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioSQL(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		return dominioDao.realizarConsultaBD(dominio.getUri(), dominio.getSql(), parametrosDominio);
	}

	/**
	 * Resuelve dominio para Dominio de Web Service.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @return
	 */
	@Override
	public ValoresDominio resuelveDominioWS(final RDominio dominio, final ParametrosDominio parametrosDominio) {

		final IDominioPlugin iplugin = (IDominioPlugin) configuracionComponent
				.obtenerPluginGlobal(TypePluginGlobal.DOMINIO_REMOTO);

		ValoresDominio valoresDominio;
		if (iplugin == null) {
			valoresDominio = new ValoresDominio();
			valoresDominio.setError(true);
			valoresDominio.setCodigoError("PLG");
			valoresDominio.setDescripcionError("El plugin es nulo");
		} else {
			try {
				final List<es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio> parametros = new ArrayList<>();
				parametrosDominio.getParametros();
				String user = null;
				String pass = null;
				if (dominio.getConfiguracionAutenticacion() != null) {
					user = dominio.getConfiguracionAutenticacion().getUsuario();
					pass = dominio.getConfiguracionAutenticacion().getPassword();
				}
				Long timeout = dominio.getTimeout();
				if (timeout == null) {
					timeout = 60L;
				}

				final es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio rvalores = iplugin
						.invocarDominio(dominio.getIdentificador(), dominio.getUri(), parametros, user, pass, timeout);
				valoresDominio = new ValoresDominio();
				valoresDominio.setCodigoError(rvalores.getCodigoError());
				valoresDominio.setDatos(rvalores.getDatos());
				valoresDominio.setCodigoRetorno(rvalores.getCodigoRetorno());
				valoresDominio.setDescripcionError(rvalores.getDescripcionError());
				valoresDominio.setError(rvalores.isError());
			} catch (final DominioPluginException e) {
				valoresDominio = new ValoresDominio();
				valoresDominio.setError(true);
				valoresDominio.setDescripcionError(ExceptionUtils.getMessage(e));
				valoresDominio.setCodigoError("REMOTE.CONNECT");
			}
		}
		return valoresDominio;
	}

}
