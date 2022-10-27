package es.caib.sistrages.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.HistorialSeccionReutilizable;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.types.TypeAccionHistorial;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.SeccionReutilizableDao;

/**
 * La clase DominioServiceImpl.
 */
@Service
@Transactional
public class SeccionReutilizableServiceImpl implements SeccionReutilizableService {

	/**
	 * log.
	 */
	private final Logger log = LoggerFactory.getLogger(SeccionReutilizableServiceImpl.class);

	@Autowired
	SeccionReutilizableDao seccionReutilizableDao;

	@Autowired
	FormularioInternoDao formularioInternoDao;

	@Override
	@NegocioInterceptor
	public List<SeccionReutilizable> listSeccionReutilizable(Long idEntidad, String filtro, Boolean activo) {
		return seccionReutilizableDao.listSeccionReutilizable(idEntidad, filtro, activo);
	}

	@Override
	@NegocioInterceptor
	public SeccionReutilizable getSeccionReutilizable(Long id) {
		return seccionReutilizableDao.getSeccionReutilizable(id);
	}

	@Override
	@NegocioInterceptor
	public SeccionReutilizable addSeccion(Long idEntidad, SeccionReutilizable seccion, final String username) {
		log.debug("addSeccion");
		Long idFormulario = formularioInternoDao.addFormulario( seccion);
		return seccionReutilizableDao.addSeccion(idEntidad, seccion, idFormulario, username);
	}

	@Override
	@NegocioInterceptor
	public boolean removeSeccion(Long id) {
		return seccionReutilizableDao.removeSeccion(id);
	}

	@Override
	@NegocioInterceptor
	public void updateSeccionReutilizable(SeccionReutilizable seccion, List<ScriptSeccionReutilizable> scripts) {
		seccionReutilizableDao.updateSeccionReutilizable(seccion, scripts);
	}

	@Override
	@NegocioInterceptor
	public void bloquearSeccion(Long idSeccion, String username) {
		seccionReutilizableDao.bloquearSeccion(idSeccion, username);
	}

	@Override
	@NegocioInterceptor
	public void desbloquearSeccion(Long idSeccion, String username, String detalle) {
		seccionReutilizableDao.desbloquearSeccion(idSeccion, username, detalle);
	}

	@Override
	@NegocioInterceptor
	public List<HistorialSeccionReutilizable> listHistorialSeccionReutilizable(Long idSeccion, String filtro) {
		return seccionReutilizableDao.listHistorialSeccionReutilizable(idSeccion, filtro);
	}

	@Override
	@NegocioInterceptor
	public HistorialSeccionReutilizable getHistorialSeccionReutilizable(Long idHistorialSeccion) {
		return seccionReutilizableDao.getHistorialSeccionReutilizable(idHistorialSeccion);
	}

	@Override
	@NegocioInterceptor
	public void actualizarFechaSeccion(long id, String usuario, String literal) {
		seccionReutilizableDao.actualizarFechaSeccion(id, usuario, literal, TypeAccionHistorial.MODIFICAR_TRAMITE);
	}

	@Override
	@NegocioInterceptor
	public boolean existeIdentificador(Long idEntidad, String identificador) {
		return  seccionReutilizableDao.existeIdentificador( idEntidad,  identificador);
	}

	@Override
	@NegocioInterceptor
	public void borradoHistorial(final Long idSeccion, final String username) {
		seccionReutilizableDao.borradoHistorial(idSeccion, username);
	}

	@Override
	@NegocioInterceptor
	public SeccionReutilizable getSeccionReutilizableByIdentificador(TypeAmbito ambito, String identificador,
			Long idEntidad, Long idArea) {
		return seccionReutilizableDao.getSeccionReutilizableByIdentificador(ambito, identificador, idEntidad,  idArea);
	}

	@Override
	@NegocioInterceptor
	public List<ScriptSeccionReutilizable> getScriptsByIdSeccionReutilizable(Long idSeccionReutilizable) {
		return seccionReutilizableDao.getScriptsByIdSeccionReutilizable(idSeccionReutilizable);
	}

	@Override
	@NegocioInterceptor
	public ScriptSeccionReutilizable getScriptById(Long idScript) {
		return seccionReutilizableDao.getScriptById(idScript);
	}

	@Override
	@NegocioInterceptor
	public void importarSeccion(FilaImportarSeccion filaSeccion, Long idEntidad) {
		final Long idFormularioAsociado = formularioInternoDao.importarFormularioSeccion(filaSeccion);
		seccionReutilizableDao.importar(filaSeccion, idEntidad, idFormularioAsociado );
	}

	@Override
	@NegocioInterceptor
	public DisenyoFormulario getFormularioInternoCompleto(Long seccionFormID) {
		DisenyoFormulario disenyo = formularioInternoDao.getFormularioCompletoById(seccionFormID, false);
		//El método obtiene el disenyo formulario y marca todos los componentes como tipo seccion reutilizable por si acaso
		if (disenyo != null && disenyo.getPaginas() != null) {
				for(PaginaFormulario pagina : disenyo.getPaginas()) {
					if (pagina.getLineas() != null) {
						for(LineaComponentesFormulario linea : pagina.getLineas()) {
							if (linea.getComponentes() != null) {
								for( ComponenteFormulario componente : linea.getComponentes()) {
									componente.setTipoSeccionReutilizable(true);
									componente.setIdFormSeccion(seccionFormID);
								}
							}
						}
					}
				}
		}
		return disenyo;
	}

	@Override
	@NegocioInterceptor
	public List<Dominio> getDominiosByIdentificadorSeccion(String identificadorSeccion) {
		return seccionReutilizableDao.getDominiosByIdentificadorSeccion(identificadorSeccion);
	}

}
