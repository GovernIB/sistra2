package es.caib.sistramit.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionCacheComponent;
import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionComponent;

@Service
@Transactional
public class FlujoTramitacionServiceImpl implements FlujoTramitacionService {

	/** Caché con con los flujos de tramitacion. */
	@Autowired
	private FlujoTramitacionCacheComponent flujoTramitacionCache;

	@Override
	@NegocioInterceptor
	public String crearSesionTramitacion(final UsuarioAutenticadoInfo usuarioAutenticado) {
		// Generamos flujo de tramitacion y almacenamos en map
		final FlujoTramitacionComponent ft = (FlujoTramitacionComponent) ApplicationContextProvider
				.getApplicationContext().getBean("flujoTramitacionComponent");
		final String idSesionTramitacion = ft.crearSesionTramitacion(usuarioAutenticado);
		flujoTramitacionCache.put(idSesionTramitacion, ft);
		return idSesionTramitacion;
	}

	@Override
	@NegocioInterceptor
	public void iniciarTramite(final String idSesionTramitacion, final String idTramite, final int version,
			final String idioma, final String idTramiteCatalogo, final String urlInicio,
			final Map<String, String> parametrosInicio) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		ft.iniciarTramite(idTramite, version, idioma, idTramiteCatalogo, urlInicio, parametrosInicio);
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
	public void cancelarTramite(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		ft.cancelarTramite();
	}

	@Override
	@NegocioInterceptor
	public void purgar() {
		flujoTramitacionCache.purgar();
	}

	@Override
	@NegocioInterceptor
	public void envioFormularioSoporte(final String idSesionTramitacion, final String nif, final String nombre,
			final String telefono, final String email, final String problemaTipo, final String problemaDesc,
			final AnexoFichero anexo) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		ft.envioFormularioSoporte(nif, nombre, telefono, email, problemaTipo, problemaDesc, anexo);
	}

	// TODO BORRAR
	@Override
	@NegocioInterceptor
	public String simularRellenarFormulario(final String idSesionTramitacion, final String xml) {
		final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
		return ft.simularRellenarFormulario(xml);
	}

	// TODO BORRAR
	@Override
	public String testFirmaCreateSesion(final String idSesionTramitacion) {
		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			return ft.testFirmaCreateSesion();
		} catch (final Exception e) {
			e.printStackTrace(); // Tengo en cuenta que hay que quitar el e.printstack
			return null;
		}
	}

	// TODO BORRAR
	@Override
	public String testFirmaAddFichero(final String idSesionTramitacion, final String idSession) {
		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			return ft.testFirmaAddFichero(idSession);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO BORRAR
	@Override
	public String testFirmaActivar(final String idSesionTramitacion, final String idSession) {
		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			return ft.testFirmaActivar(idSession);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO BORRAR
	@Override
	public String testFirmaEstado(final String idSesionTramitacion, final String idSession) {
		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			return ft.testFirmaEstado(idSession);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO BORRAR
	@Override
	public byte[] testFirmaDoc(final String idSesionTramitacion, final String idSession, final String idDoc) {

		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			return ft.testFirmaDoc(idSession, idDoc);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void testFirmaCerrar(final String idSesionTramitacion, final String idSession) {
		try {
			final FlujoTramitacionComponent ft = obtenerFlujoTramitacion(idSesionTramitacion);
			ft.testFirmaCerrar(idSession);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------
	// - Métodos especiales invocados desde el interceptor. No pasan por
	// interceptor
	// de auditoria.
	// -------------------------------------------------------------------------------------------
	@Override
	public FlujoTramitacionInfo obtenerFlujoTramitacionInfo(final String idSesionTramitacion) {
		// ATENCION: NO DEBE PASAR POR INTERCEPTOR. SE USA DESDE EL PROPIO
		// INTERCEPTOR.
		FlujoTramitacionInfo dt = null;
		final FlujoTramitacionComponent ft = flujoTramitacionCache.get(idSesionTramitacion);
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
		final FlujoTramitacionComponent ft = flujoTramitacionCache.get(idSesionTramitacion);
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
	 *            id sesión tramitación
	 * @return flujo tramitación
	 */
	private FlujoTramitacionComponent obtenerFlujoTramitacion(final String idSesionTramitacion) {
		final FlujoTramitacionComponent ft = flujoTramitacionCache.get(idSesionTramitacion);
		if (ft == null) {
			throw new NoExisteFlujoTramitacionException(idSesionTramitacion);
		}
		return ft;
	}

}
