package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;

/**
 * La clase SeccionReutilizableServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SeccionReutilizableServiceBean implements SeccionReutilizableService {

	/**
	 * seccion service.
	 */
	@Autowired
	SeccionReutilizableService seccionService;

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<SeccionReutilizable> listSeccionReutilizable(Long idEntidad, String filtro, Boolean activo) {
		return seccionService.listSeccionReutilizable(idEntidad, filtro,activo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public SeccionReutilizable getSeccionReutilizable(Long id) {
		return seccionService.getSeccionReutilizable(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public SeccionReutilizable addSeccion(Long idEntidad, SeccionReutilizable seccion, final String username) {
		return seccionService.addSeccion(idEntidad, seccion, username);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeSeccion(Long id) {
		return seccionService.removeSeccion(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateSeccionReutilizable(SeccionReutilizable seccion,List<ScriptSeccionReutilizable> scripts) {
		seccionService.updateSeccionReutilizable(seccion, scripts);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void bloquearSeccion(Long idSeccion, String username) {
		seccionService.bloquearSeccion(idSeccion, username);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void desbloquearSeccion(Long idSeccion, String username, String detalle) {
		seccionService.desbloquearSeccion(idSeccion, username, detalle);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<HistorialSeccionReutilizable> listHistorialSeccionReutilizable(Long idSeccion, String filtro) {
		return seccionService.listHistorialSeccionReutilizable(idSeccion, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public HistorialSeccionReutilizable getHistorialSeccionReutilizable(Long idHistorialSeccion) {
		return seccionService.getHistorialSeccionReutilizable(idHistorialSeccion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void actualizarFechaSeccion(long parseLong, String userName, String string) {
		 seccionService.actualizarFechaSeccion(parseLong, userName, string);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean existeIdentificador(Long idEntidad, String identificador) {
		return seccionService.existeIdentificador(idEntidad, identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void borradoHistorial(Long idSeccion, String userName) {
		seccionService.borradoHistorial(idSeccion, userName);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public SeccionReutilizable getSeccionReutilizableByIdentificador(TypeAmbito ambito, String identificador,
			Long idEntidad, Long idArea) {
		return seccionService.getSeccionReutilizableByIdentificador(ambito, identificador, idEntidad, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ScriptSeccionReutilizable> getScriptsByIdSeccionReutilizable(Long idSeccionReutilizable) {
		return seccionService.getScriptsByIdSeccionReutilizable(idSeccionReutilizable);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public ScriptSeccionReutilizable getScriptById(Long idScript) {
		return seccionService.getScriptById(idScript);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void importarSeccion(FilaImportarSeccion filaSeccion, Long idEntidad) {
		seccionService.importarSeccion(filaSeccion, idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public DisenyoFormulario getFormularioInternoCompleto(Long seccionFormID) {
		return seccionService.getFormularioInternoCompleto(seccionFormID);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Dominio> getDominiosByIdentificadorSeccion(String identificadorSeccion) {
		return seccionService.getDominiosByIdentificadorSeccion(identificadorSeccion);
	}

}
