package es.caib.sistramit.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.exception.NoExisteFlujoTramitacionException;
import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.component.system.FlujoTramitacionCacheComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Service
@Transactional
public class FlujoTramitacionServiceImpl implements FlujoTramitacionService {

	/** Caché con con los flujos de tramitacion. */
	@Autowired
	private FlujoTramitacionCacheComponent flujoTramitacionCache;

	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracion;

	@Override
	@NegocioInterceptor
	public String iniciarTramite(final UsuarioAutenticadoInfo usuarioAutenticado, final String idTramite,
			final int version, final String idioma, final String idTramiteCatalogo, final boolean servicioCatalogo,
			final String urlInicio, final Map<String, String> parametrosInicio) {
		// Generamos flujo de tramitacion
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		// Iniciamos tramite
		final String idSesionTramitacion = ft.iniciarTramite(usuarioAutenticado, idTramite, version, idioma,
				idTramiteCatalogo, servicioCatalogo, urlInicio, parametrosInicio);
		// Almacenamos en map
		flujoTramitacionCache.put(idSesionTramitacion, ft);
		// Retornamos id tramitacion
		return idSesionTramitacion;
	}

	@Override
	@NegocioInterceptor
	public DetalleTramite obtenerDetalleTramite(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.obtenerDetalleTramite();
	}

	@Override
	@NegocioInterceptor
	public DetallePasos obtenerDetallePasos(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.obtenerDetallePasos();
	}

	@Override
	@NegocioInterceptor
	public void cargarTramite(final String idSesionTramitacion, final UsuarioAutenticadoInfo usuarioAutenticado) {
		// Generamos flujo de tramitacion, almacenamos en map y cargamos trámite
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		flujoTramitacionCache.put(idSesionTramitacion, ft);
		ft.cargarTramite(idSesionTramitacion, usuarioAutenticado);
	}

	@Override
	@NegocioInterceptor
	public void recargarTramite(final String idSesionTramitacion, final UsuarioAutenticadoInfo userInfo) {
		// Generamos flujo de tramitacion, almacenamos en map y cargamos trámite
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		flujoTramitacionCache.put(idSesionTramitacion, ft);
		ft.recargarTramite(idSesionTramitacion, userInfo);
	}

	@Override
	@NegocioInterceptor
	public ResultadoIrAPaso irAPaso(final String idSesionTramitacion, final String idPaso) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.irAPaso(idPaso);
	}

	@Override
	@NegocioInterceptor
	public ResultadoIrAPaso irAPasoActual(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.irAPasoActual();
	}

	@Override
	@NegocioInterceptor
	public ResultadoAccionPaso accionPaso(final String idSesionTramitacion, final String idPaso,
			final TypeAccionPaso accionPaso, final ParametrosAccionPaso parametros) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.accionPaso(idPaso, accionPaso, parametros);
	}

	@Override
	@NegocioInterceptor
	public String cancelarTramite(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.cancelarTramite();
	}

	@Override
	@NegocioInterceptor
	public void envioFormularioSoporte(final String idSesionTramitacion, final String nif, final String nombre,
			final String telefono, final String email, final String problemaTipo, final String problemaDesc,
			final String horarioContacto, final AnexoFichero anexo) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		ft.envioFormularioSoporte(nif, nombre, telefono, email, problemaTipo, problemaDesc, horarioContacto, anexo);
	}

	@Override
	public byte[] obtenerClavePdf(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		final byte[] pdf = ft.obtenerClavePdf();
		return pdf;
	}

	@Override
	public String logoutTramite(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		final String url = ft.logoutTramite();
		flujoTramitacionCache.remove(idSesionTramitacion);
		return url;
	}

	@Override
	public String obtenerUrlEntidad(final String idTramite, final int version, final String idioma) {
		final DefinicionTramiteSTG defTram = configuracion.recuperarDefinicionTramite(idTramite, version, idioma);
		final String idEntidad = defTram.getDefinicionVersion().getIdEntidad();
		final RConfiguracionEntidad entidad = configuracion.obtenerConfiguracionEntidad(idEntidad);
		return UtilsSTG.obtenerLiteral(entidad.getUrlCarpeta(), idioma, true);
	}

	@Override
	public AnexoFichero descargarArchivoCP(final String idSesionTramitacion, final String referenciaArchivo) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		final AnexoFichero res = ft.descargarArchivoCP(referenciaArchivo);
		return res;
	}

	// -------------------------------------------------------------------------------------------
	// Métodos especiales invocados desde el interceptor. No pasan por interceptor
	// de auditoria.
	// -------------------------------------------------------------------------------------------
	@Override
	public FlujoTramitacionInfo obtenerFlujoTramitacionInfo(final String idSesionTramitacion) {
		// ATENCION: NO DEBE PASAR POR INTERCEPTOR. SE USA DESDE EL PROPIO
		// INTERCEPTOR.
		FlujoTramitacionInfo dt = null;
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) flujoTramitacionCache.get(idSesionTramitacion);
		if (ft != null) {
			try {
				dt = ft.obtenerFlujoTramitacionInfo();
			} catch (final Exception ex) {
				// No hacemos nada
				dt = null;
			}
		}
		return dt;
	}

	@Override
	public void invalidarFlujoTramitacion(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) flujoTramitacionCache.get(idSesionTramitacion);
		if (ft != null) {
			try {
				ft.invalidarFlujoTramicacion();
			} catch (final Exception ex) {
				// No hacemos nada
			}
		}
	}

	// --------------------------------------------------------------
	// ---- FUNCIONES INTERNAS
	// --------------------------------------------------------------

	/**
	 * Obtiene flujo de tramitación.
	 *
	 * @param idSesionTramitacion
	 *                                id sesión tramitación
	 * @return flujo tramitación
	 */
	private FlujoTramitacionComponent obtenerFlujoTramitacion(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) flujoTramitacionCache.get(idSesionTramitacion);
		if (ft == null) {
			throw new NoExisteFlujoTramitacionException(idSesionTramitacion);
		}
		return ft;
	}

}
