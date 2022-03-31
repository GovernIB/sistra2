package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;

/**
 * La clase FormularioExternoServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FormularioExternoServiceBean implements FormularioExternoService {

	/**
	 * aviso entidad service.
	 */
	@Autowired
	FormularioExternoService formularioExternoService;

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * getFormularioExterno(java. lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public GestorExternoFormularios getFormularioExterno(final Long id) {
		return formularioExternoService.getFormularioExterno(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * addFormularioExterno(java. lang.Long,
	 * es.caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addFormularioExterno(final Long idEntidad, final GestorExternoFormularios FormularioExterno) {
		formularioExternoService.addFormularioExterno(idEntidad, FormularioExterno);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * removeFormularioExterno( java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeFormularioExterno(final Long id) {
		return formularioExternoService.removeFormularioExterno(id);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * tieneTramitesAsociados( java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean tieneTramitesAsociados(Long idGFE) {
		return formularioExternoService.tieneTramitesAsociados(idGFE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * updateFormularioExterno(es. caib.sistrages.core.api.model.FormularioExterno)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFormularioExterno(final GestorExternoFormularios FormularioExterno) {
		formularioExternoService.updateFormularioExterno(FormularioExterno);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.FormularioExternoService#
	 * listFormularioExterno(java. lang.Long,
	 * es.caib.sistrages.core.api.model.types.TypeIdioma, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<GestorExternoFormularios> listFormularioExterno(final Long idEntidad, final TypeIdioma idioma,
			final String filtro) {
		return formularioExternoService.listFormularioExterno(idEntidad, idioma, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean existeFormulario(final String identificador, final Long codigo, final Long idArea) {
		return formularioExternoService.existeFormulario(identificador, codigo, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<GestorExternoFormularios> getGestorExternoByAutenticacion(Long id, Long idArea) {
		return formularioExternoService.getGestorExternoByAutenticacion(id, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean existeGFEByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad,
			Long codigoArea, Long codigoGFE) {
		return formularioExternoService.existeGFEByIdentificador(ambito, identificador, codigoEntidad, codigoArea,
				codigoGFE);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public GestorExternoFormularios getFormularioExternoByIdentificador(TypeAmbito ambito, String identificador,
			Long codigoEntidad, Long codigoArea, Long codigoGFE) {
		return formularioExternoService.getFormularioExternoByIdentificador(ambito, identificador, codigoEntidad,
				codigoArea, codigoGFE);
	}

}
