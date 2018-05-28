package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.EntidadService;

/**
 * La clase EntidadServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntidadServiceBean implements EntidadService {

	/**
	 * entidad service.
	 */
	@Autowired
	EntidadService entidadService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidad(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Entidad loadEntidad(final Long idEntidad) {
		return entidadService.loadEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadEntidadByArea(java.lang
	 * .Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Entidad loadEntidadByArea(final Long idArea) {
		return entidadService.loadEntidadByArea(idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#listEntidad(es.caib.
	 * sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public List<Entidad> listEntidad(final TypeIdioma idioma, final String filtro) {
		return entidadService.listEntidad(idioma, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#addEntidad(es.caib.
	 * sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void addEntidad(final Entidad entidad) {
		entidadService.addEntidad(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeEntidad(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public boolean removeEntidad(final Long idEntidad) {
		return entidadService.removeEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadSuperAdministrador(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN })
	public void updateEntidadSuperAdministrador(final Entidad entidad) {
		entidadService.updateEntidadSuperAdministrador(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateEntidadAdministradorEntidad(es.caib.sistrages.core.api.model.Entidad)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void updateEntidadAdministradorEntidad(final Entidad entidad) {
		entidadService.updateEntidadAdministradorEntidad(entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeLogoGestorEntidad(
	 * java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void removeLogoGestorEntidad(final Long idEntidad) {
		entidadService.removeLogoGestorEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeLogoAsistenteEntidad(
	 * java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void removeLogoAsistenteEntidad(final Long idEntidad) {
		entidadService.removeLogoAsistenteEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#removeCssEntidad(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void removeCssEntidad(final Long idEntidad) {
		entidadService.removeCssEntidad(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadLogoGestorEntidad(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void uploadLogoGestorEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		entidadService.uploadLogoGestorEntidad(idEntidad, fichero, content);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadLogoAsistenteEntidad(
	 * java.lang.Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void uploadLogoAsistenteEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		entidadService.uploadLogoAsistenteEntidad(idEntidad, fichero, content);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#uploadCssEntidad(java.lang.
	 * Long, es.caib.sistrages.core.api.model.Fichero, byte[])
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void uploadCssEntidad(final Long idEntidad, final Fichero fichero, final byte[] content) {
		entidadService.uploadCssEntidad(idEntidad, fichero, content);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * listOpcionesFormularioSoporte(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad) {
		return entidadService.listOpcionesFormularioSoporte(idEntidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#loadOpcionFormularioSoporte
	 * (java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public FormularioSoporte loadOpcionFormularioSoporte(final Long id) {
		return entidadService.loadOpcionFormularioSoporte(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.EntidadService#addOpcionFormularioSoporte(
	 * java.lang.Long, es.caib.sistrages.core.api.model.FormularioSoporte)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void addOpcionFormularioSoporte(final Long idEntidad, final FormularioSoporte fst) {
		entidadService.addOpcionFormularioSoporte(idEntidad, fst);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * updateOpcionFormularioSoporte(es.caib.sistrages.core.api.model.
	 * FormularioSoporte)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public void updateOpcionFormularioSoporte(final FormularioSoporte fst) {
		entidadService.updateOpcionFormularioSoporte(fst);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.EntidadService#
	 * removeOpcionFormularioSoporte(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT })
	public boolean removeOpcionFormularioSoporte(final Long id) {
		return entidadService.removeOpcionFormularioSoporte(id);
	}

}
