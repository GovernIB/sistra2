package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.EnvioRemotoService;

/**
 * La clase DominioServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EnvioRemotoServiceBean implements EnvioRemotoService {

	/**
	 * dominio service.
	 */
	@Autowired
	EnvioRemotoService envioRemotoService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.envioRemotoService#loadDominio(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public EnvioRemoto loadEnvio(final Long codEnvio) {
		return envioRemotoService.loadEnvio(codEnvio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.envioRemotoService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<EnvioRemoto> listEnvio(final TypeAmbito ambito, final Long id, final String filtro) {
		return envioRemotoService.listEnvio(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.envioRemotoService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Long addEnvio(final EnvioRemoto envio, final Long idEntidad, final Long idArea) {
		return envioRemotoService.addEnvio(envio, idEntidad, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.envioRemotoService#removeDominio(java.
	 * lang. Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeEnvio(final Long idEnvio) {
		return envioRemotoService.removeEnvio(idEnvio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.envioRemotoService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateEnvio(final EnvioRemoto envio) {
		envioRemotoService.updateEnvio(envio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void clonar(final String dominioID, final String nuevoIdentificador, final Long areaID, final Long fdID,
			final Long idEntidad) {
		envioRemotoService.clonar(dominioID, nuevoIdentificador, areaID, fdID, idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<EnvioRemoto> getEnviosByConfAut(Long idConfiguracion, Long idArea) {
		return envioRemotoService.getEnviosByConfAut(idConfiguracion, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<EnvioRemoto> getEnviosByIdentificador(List<String> identificadoresDominio, final Long idEntidad,
			final Long idArea) {
		return envioRemotoService.getEnviosByIdentificador(identificadoresDominio, idEntidad, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean existeEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return envioRemotoService.existeEnvioByIdentificador(ambito, identificador, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public EnvioRemoto getEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return envioRemotoService.getEnvioByIdentificador(ambito, identificador, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public EnvioRemoto loadEnvioByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoDominio) {
		return envioRemotoService.loadEnvioByIdentificador(ambito, identificador, codigoEntidad, codigoArea,
				codigoDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public EnvioRemoto loadEnvioByIdentificadorCompuesto(String identificador) {
		return envioRemotoService.loadEnvioByIdentificadorCompuesto(identificador);

	}

}
