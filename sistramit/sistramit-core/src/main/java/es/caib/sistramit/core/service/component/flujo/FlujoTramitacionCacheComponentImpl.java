package es.caib.sistramit.core.service.component.flujo;

import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.FlujoTramitacionCache;
import org.fundaciobit.pluginsib.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("flujoTramitacionCacheComponent")
public class FlujoTramitacionCacheComponentImpl implements FlujoTramitacionCacheComponent {

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Map con las sesiones de tramitación activas. */
	private final Map<String, FlujoTramitacionCache> flujoTramitacionMap = new HashMap<>();

	@Override
	public void put(final String idSesionTramitacion, final FlujoTramitacionComponent flujoTramitacionComponent) {
		synchronized (flujoTramitacionMap) {
			final FlujoTramitacionCache fc = new FlujoTramitacionCache();
			fc.setFcUltimoAcceso(new Date());
			fc.setFlujoTramitacion(flujoTramitacionComponent);
			flujoTramitacionMap.put(idSesionTramitacion, fc);
		}
	}

	@Override
	public FlujoTramitacionComponent get(final String idSesionTramitacion) {
		FlujoTramitacionComponent res = null;
		synchronized (flujoTramitacionMap) {
			final FlujoTramitacionCache fc = flujoTramitacionMap.get(idSesionTramitacion);
			if (fc != null && !isTimeout(fc.getFcUltimoAcceso())) {
				res = fc.getFlujoTramitacion();
				fc.setFcUltimoAcceso(new Date());
			}
		}
		return res;
	}

	@Override
	public void purgar() {
		synchronized (flujoTramitacionMap) {
			for (final String idSesionTramitacion : flujoTramitacionMap.keySet()) {
				final FlujoTramitacionCache fc = flujoTramitacionMap.get(idSesionTramitacion);
				if (fc != null && isTimeout(fc.getFcUltimoAcceso())) {
					log.debug("Purga flujo " + idSesionTramitacion);
					flujoTramitacionMap.remove(idSesionTramitacion);
				}
			}
		}

	}

	private boolean isTimeout(final Date fecha) {
		final Date ahora = new Date();
		final int timeout = Integer.parseInt(
				configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.TIMEOUT_CACHE_FLUJO));
		final long secs = (ahora.getTime() - fecha.getTime()) / ConstantesNumero.N1000;
		return (secs > timeout);
	}

	// TODO Borrar

	private final String entidad = "DIR3-1";

	@Override
	public String testFirmaCreateSesion() throws Exception {
		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
		infoSesionFirma.setEntidad("12345678C");
		infoSesionFirma.setIdioma("ca");
		infoSesionFirma.setNombreUsuario("jmico");
		return plgLogin.generarSesionFirma(infoSesionFirma);
	}

	@Override
	public String testFirmaAddFichero(final String idSession) throws Exception {

		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		// Paso 2. Leer y subir los 2 ficheros.
		/** Añadimos doc. **/
		InputStream is = null;
		try {
			is = new DataInputStream(new FileInputStream("C://hola.pdf"));
		} catch (final Exception e) {
			is = new DataInputStream(new FileInputStream("//hola.pdf"));
		}
		final ByteArrayOutputStream fos = new ByteArrayOutputStream();
		FileUtils.copy(is, fos);
		final FicheroAFirmar fichero = new FicheroAFirmar();
		fichero.setFichero(fos.toByteArray());
		fichero.setMimetypeFichero("application/pdf");
		fichero.setIdioma("ca");
		fichero.setSignNumber(1);
		fichero.setNombreFichero("hola.pdf");
		fichero.setRazon("Fichero prueba1");
		fichero.setSignID("666");
		fichero.setSesion(idSession);
		plgLogin.ficheroAFirmar(fichero);

		InputStream is2 = null;
		try {
			is2 = new DataInputStream(new FileInputStream("C://hola2.pdf"));
		} catch (final Exception e) {
			is2 = new DataInputStream(new FileInputStream("//hola2.pdf"));
		}
		final ByteArrayOutputStream fos2 = new ByteArrayOutputStream();
		FileUtils.copy(is2, fos2);
		final FicheroAFirmar fichero2 = new FicheroAFirmar();
		fichero2.setFichero(fos2.toByteArray());
		fichero2.setMimetypeFichero("application/pdf");
		fichero2.setIdioma("ca");
		fichero2.setSignNumber(1);
		fichero2.setNombreFichero("hola2.pdf");
		fichero2.setRazon("Fichero prueba1");
		fichero2.setSignID("777");
		fichero2.setSesion(idSession);
		plgLogin.ficheroAFirmar(fichero2);

		return null;
	}

	@Override
	public String testFirmaActivar(final String idSession) throws FirmaPluginException {

		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);

		return plgLogin.iniciarSesionFirma(idSession, "http://www.google.es", null);
	}

	@Override
	public String testFirmaEstado(final String idSession) throws FirmaPluginException {

		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		final TypeEstadoFirmado estado = plgLogin.obtenerEstadoSesionFirma(idSession);
		switch (estado) {
		case CANCELADO:
			return "CANCELADO";
		case EN_PROGRESO:
			return "En progreso";
		case FINALIZADO_CON_ERROR:
			return "Finalizado con error";
		case FINALIZADO_OK:
			return "Finalizado ok";
		case INICIALIZADO:
			return "Inicializado";
		default:
			return "Descononcido:" + estado;
		}
	}

	@Override
	public byte[] testFirmaDoc(final String idSession, final String idDoc) throws FirmaPluginException {

		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		final FicheroFirmado fic = plgLogin.obtenerFirmaFichero(idSession, idDoc);
		if (fic == null) {
			return null;
		} else {
			return fic.getFirmaFichero();
		}
	}

	@Override
	public void testFirmaCerrar(final String idSession) throws FirmaPluginException {

		final IFirmaPlugin plgLogin = (IFirmaPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FIRMA, entidad);
		plgLogin.cerrarSesionFirma(idSession);
	}
}
