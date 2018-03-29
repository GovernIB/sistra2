package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;

// TODO: Auto-generated Javadoc
/**
 * La clase DominioServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DominioServiceBean implements DominioService {

	/**
	 * dominio service.
	 */
	@Autowired
	DominioService dominioService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public Dominio loadDominio(final Long idDominio) {
		return dominioService.loadDominio(idDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioService.listDominio(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		dominioService.addDominio(entidad, idEntidad, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeDominio(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public boolean removeDominio(final Long idDominio) {
		return dominioService.removeDominio(idDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateDominio(final Dominio dominio) {
		dominioService.updateDominio(dominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public FuenteDatos loadFuenteDato(final Long idFuenteDato) {
		return dominioService.loadFuenteDato(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void addFuenteDato(final FuenteDatos fuenteDato, final Long idArea) {
		dominioService.addFuenteDato(fuenteDato, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#updateFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateFuenteDato(final FuenteDatos fuenteDato) {
		dominioService.updateFuenteDato(fuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void removeFuenteDato(final Long idFuenteDato) {
		dominioService.removeFuenteDato(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioService.listFuenteDato(ambito, id, filtro);
	}

}
