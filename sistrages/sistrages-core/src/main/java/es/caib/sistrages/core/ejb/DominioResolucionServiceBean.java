package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioResolucionService;

/**
 * La clase DominioServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DominioResolucionServiceBean implements DominioResolucionService {

	/**
	 * dominio service.
	 */
	@Autowired
	DominioResolucionService dominioResolucionService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ValoresDominio realizarConsultaFuenteDatos(final String idDominio,
			final List<ValorParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaFuenteDatos(idDominio, parametros);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ValoresDominio realizarConsultaBD(final String datasource, final String sql,
			final List<ValorParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaBD(datasource, sql, parametros);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ValoresDominio realizarConsultaRemota(final TypeAmbito ambito, final Long idEntidad, final String idDominio,
			final String url, final List<ParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaRemota(ambito, idEntidad, idDominio, url, parametros);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ValoresDominio realizarConsultaListaFija(final TypeAmbito ambito, final Long idEntidad,
			final String identificador, final String url, final List<Propiedad> parametros) {
		return dominioResolucionService.realizarConsultaListaFija(ambito, idEntidad, identificador, url, parametros);
	}

}
