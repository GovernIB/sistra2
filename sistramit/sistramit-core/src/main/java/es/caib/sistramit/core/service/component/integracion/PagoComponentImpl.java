package es.caib.sistramit.core.service.component.integracion;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.pdf.ObjectStamp;
import es.caib.sistra2.commons.pdf.TextoStamp;
import es.caib.sistra2.commons.pdf.UtilPDF;
import es.caib.sistra2.commons.pdfcaib.GeneradorPdf;
import es.caib.sistra2.commons.pdfcaib.model.Cabecera;
import es.caib.sistra2.commons.pdfcaib.model.CampoTexto;
import es.caib.sistra2.commons.pdfcaib.model.FormularioPdf;
import es.caib.sistra2.commons.pdfcaib.model.Linea;
import es.caib.sistra2.commons.plugins.pago.api.DatosPago;
import es.caib.sistra2.commons.plugins.pago.api.EstadoPago;
import es.caib.sistra2.commons.plugins.pago.api.IComponentePagoPlugin;
import es.caib.sistra2.commons.plugins.pago.api.PagoPluginException;
import es.caib.sistra2.commons.plugins.pago.api.RedireccionPago;
import es.caib.sistra2.commons.plugins.pago.api.TypeEstadoPago;
import es.caib.sistramit.core.api.exception.FormateadorException;
import es.caib.sistramit.core.api.exception.PagoException;
import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.PagoComponentRedireccion;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;

/**
 * Implementación componente pago.
 *
 * @author Indra
 *
 */
@Component("pagoComponent")
public final class PagoComponentImpl implements PagoComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public int consultaTasa(final String idEntidad, final String idPasarela, final String idTasa,
			final boolean debugEnabled) {
		try {
			if (debugEnabled) {
				log.debug("Consulta tasa " + idTasa + " en pasarela " + idPasarela);
			}
			final IComponentePagoPlugin plugin = obtenerPlugin(idEntidad);
			return plugin.consultaTasa(idPasarela, idTasa);
		} catch (final PagoPluginException e) {
			throw new PagoException("Excepcion consultando tasa " + idTasa + ": " + e.getMessage(), e);
		}
	}

	@Override
	public PagoComponentRedireccion iniciarPagoElectronico(final DatosSesionPago sesionPago, final String urlCallback,
			final boolean debugEnabled) {

		if (debugEnabled) {
			log.debug("Inicio sesion pago - simulado: " + sesionPago.isSimulado());
		}

		if (sesionPago.getPresentacion() != TypePresentacion.ELECTRONICA) {
			throw new PagoException(
					"El pago no tiene presentacion electronica (tipo presentacion: " + sesionPago.getPresentacion());
		}

		PagoComponentRedireccion res = null;
		if (sesionPago.isSimulado()) {
			res = iniciarPagoElectronicoSimulado(sesionPago, urlCallback);
		} else {
			res = iniciarPagoElectronicoPasarela(sesionPago, urlCallback);
		}

		if (debugEnabled) {
			log.debug("Inicio sesion pago -  identificador: " + res.getIdentificador() + " - url: " + res.getUrl());
		}

		return res;
	}

	@Override
	public PagoComponentVerificacion verificarPagoElectronico(final DatosSesionPago sesionPago,
			final boolean debugEnabled) {

		if (sesionPago.getPresentacion() != TypePresentacion.ELECTRONICA) {
			throw new PagoException(
					"El pago no tiene presentacion electronica (tipo presentacion: " + sesionPago.getPresentacion());
		}

		PagoComponentVerificacion res = null;
		if (sesionPago.isSimulado()) {
			res = verificarPagoElectronicoSimulado(sesionPago);
		} else {
			res = verificarPagoElectronicoPasarela(sesionPago);
		}

		return res;
	}

	@Override
	public byte[] obtenerCartaPagoPresencial(final DatosSesionPago sesionPago, final boolean debugEnabled) {
		try {
			if (debugEnabled) {
				log.debug("Obtener carta pago presencial en pasarela " + sesionPago.getPasarelaId());
			}

			if (sesionPago.getPresentacion() != TypePresentacion.PRESENCIAL) {
				throw new PagoException(
						"El pago no tiene presentacion presencial (tipo presentacion: " + sesionPago.getPresentacion());
			}

			byte[] cartaPago = null;
			if (sesionPago.isSimulado()) {
				cartaPago = obtenerCartaPagoPresencialSimulado(sesionPago);
			} else {
				cartaPago = obtenerCartaPagoPresencialPasarela(sesionPago);
			}

			return cartaPago;
		} catch (final PagoPluginException e) {
			throw new PagoException("Excepcion obteniendo carta de pago presencial: " + e.getMessage(), e);
		}
	}

	// ----------------------------------------------------------------
	// METODOS AUXILIARES
	// ----------------------------------------------------------------

	/**
	 * Obtiene plugin de pago.
	 *
	 * @param entidadId
	 *                      id entidad
	 * @return plugin pago
	 */
	private IComponentePagoPlugin obtenerPlugin(final String entidadId) {
		return (IComponentePagoPlugin) configuracionComponent.obtenerPluginEntidad(TypePluginEntidad.PAGOS, entidadId);
	}

	/**
	 * Inicio pago electrónico real.
	 *
	 * @param sesionPago
	 *                        datos sesión pago
	 * @param urlCallback
	 *                        urlCallback
	 * @return redirección pago
	 */
	private PagoComponentRedireccion iniciarPagoElectronicoPasarela(final DatosSesionPago sesionPago,
			final String urlCallback) {
		try {
			final DatosPago datosPago = crearDatosPago(sesionPago);
			final IComponentePagoPlugin plugin = obtenerPlugin(sesionPago.getEntidadId());
			final RedireccionPago resPlg = plugin.iniciarPagoElectronico(datosPago, urlCallback);
			final PagoComponentRedireccion res = new PagoComponentRedireccion();
			res.setIdentificador(resPlg.getIdentificador());
			res.setUrl(resPlg.getUrlPago());
			return res;
		} catch (final PagoPluginException e) {
			throw new PagoException("Excepcion al iniciar pago electronico: " + e.getMessage(), e);
		}
	}

	/**
	 * Inicio pago electrónico de forma simulada.
	 *
	 * @param sesionPago
	 *                        datos sesión pago
	 * @param urlCallback
	 * @return redirección pago
	 */
	private PagoComponentRedireccion iniciarPagoElectronicoSimulado(final DatosSesionPago sesionPago,
			final String urlCallback) {
		final PagoComponentRedireccion res = new PagoComponentRedireccion();
		res.setIdentificador(System.currentTimeMillis() + "");
		res.setUrl(urlCallback);
		return res;
	}

	/**
	 * Crear datos pago.
	 *
	 * @param sesionPago
	 *                       sesión pago
	 * @return datos pago
	 */
	private DatosPago crearDatosPago(final DatosSesionPago sesionPago) {
		final DatosPago datosPago = new DatosPago();
		datosPago.setEntidadId(sesionPago.getEntidadId());
		datosPago.setPasarelaId(sesionPago.getPasarelaId());
		datosPago.setOrganismoId(sesionPago.getOrganismoId());
		datosPago.setIdioma(sesionPago.getIdioma());
		datosPago.setModelo(sesionPago.getModelo());
		datosPago.setConcepto(sesionPago.getConcepto());
		datosPago.setTasaId(sesionPago.getTasaId());
		datosPago.setDetallePago(sesionPago.getDetallePago());
		datosPago.setImporte(sesionPago.getImporte());
		datosPago.setSujetoPasivoNif(sesionPago.getSujetoPasivo().getNif());
		datosPago.setSujetoPasivoNombre(sesionPago.getSujetoPasivo().getNombre());
		return datosPago;
	}

	/**
	 * Verifica pago electronico contra la pasarela.
	 *
	 * @param sesionPago
	 *                       sesion pago
	 * @return verificacion
	 */
	private PagoComponentVerificacion verificarPagoElectronicoPasarela(final DatosSesionPago sesionPago) {
		try {
			final IComponentePagoPlugin plugin = obtenerPlugin(sesionPago.getEntidadId());

			// Verifica pago
			final EstadoPago resPlg = plugin.verificarPagoElectronico(sesionPago.getIdentificadorPago());

			// Obtiene justificante si esta pagado
			byte[] justif = null;
			if (resPlg.getEstado() == TypeEstadoPago.PAGADO) {
				justif = plugin.obtenerJustificantePagoElectronico(sesionPago.getIdentificadorPago());
			}

			// Devuelve verificacion y justificante
			final PagoComponentVerificacion res = new PagoComponentVerificacion();
			// TODO Ver si algun caso podemos establecer como no verificado
			// (capturar excepcion??)
			res.setVerificado(true);
			res.setPagado(resPlg.getEstado() == TypeEstadoPago.PAGADO);
			res.setFechaPago(resPlg.getFechaPago());
			res.setLocalizador(resPlg.getLocalizador());
			res.setJustificantePDF(justif);
			res.setCodigoError(resPlg.getCodigoErrorPasarela());
			res.setMensajeError(resPlg.getMensajeErrorPasarela());
			return res;
		} catch (final PagoPluginException e) {
			throw new PagoException("Excepcion al iniciar pago electronico: " + e.getMessage(), e);
		}
	}

	/**
	 * Verifica pago electronico de forma simulada (indica como pagado).
	 *
	 * @param sesionPago
	 *                       sesion pago
	 * @return verificacion
	 */
	private PagoComponentVerificacion verificarPagoElectronicoSimulado(final DatosSesionPago sesionPago) {

		final boolean verificado = true;
		final boolean realizado = true;

		final PagoComponentVerificacion res = new PagoComponentVerificacion();
		res.setVerificado(verificado);
		res.setPagado(realizado);

		if (verificado && realizado) {
			res.setLocalizador(System.currentTimeMillis() + "");
			res.setJustificantePDF(generaPdfMock(sesionPago));
		}

		if (verificado && !realizado) {
			res.setCodigoError("ERR");
			res.setMensajeError("Error simulado");
		}

		return res;
	}

	/**
	 * Genera PDF mock.
	 *
	 * @param sesionPago
	 *
	 * @return pdf
	 */
	private byte[] generaPdfMock(final DatosSesionPago sesionPago) {
		// Genera PDF con datos pago
		try {
			final FormularioPdf formularioPdf = new FormularioPdf();
			final Cabecera cabecera = new Cabecera();
			cabecera.setTitulo("PAGAMENT SIMULAT");
			cabecera.setSubtitulo("  ");
			formularioPdf.setCabecera(cabecera);
			final List<Linea> lineas = new ArrayList<Linea>();
			formularioPdf.setLineas(lineas);

			generaPdfMockLinea(lineas, "MODEL", sesionPago.getModelo());
			generaPdfMockLinea(lineas, "TAXA", sesionPago.getTasaId());
			generaPdfMockLinea(lineas, "CONCEPTE", sesionPago.getConcepto());
			generaPdfMockLinea(lineas, "SUBJECTE",
					sesionPago.getSujetoPasivo().getNif() + " - " + sesionPago.getSujetoPasivo().getNombre());
			generaPdfMockLinea(lineas, "IMPORT (CENTS)", Integer.toString(sesionPago.getImporte()));

			// Generar PDF
			final GeneradorPdf generadorPdf = new GeneradorPdf();
			byte[] pdf = generadorPdf.generarPdf(formularioPdf);

			// Stamp "Sense validesa"
			final ByteArrayOutputStream bos = new ByteArrayOutputStream(pdf.length + 200);
			final ByteArrayInputStream bis = new ByteArrayInputStream(pdf);
			final ObjectStamp[] textos = new ObjectStamp[1];
			textos[0] = new TextoStamp();
			((TextoStamp) textos[0]).setTexto("Sense validesa");
			((TextoStamp) textos[0]).setFontSize(85);
			((TextoStamp) textos[0]).setFontColor(Color.LIGHT_GRAY);
			textos[0].setPage(0);
			textos[0].setX(100);
			textos[0].setY(300);
			textos[0].setRotation(45f);
			textos[0].setOverContent(false);
			UtilPDF.stamp(bos, bis, textos);
			pdf = bos.toByteArray();
			bis.close();
			bos.close();

			return pdf;
		} catch (final Exception e) {
			throw new FormateadorException("Error convirtiendo el documento a bytes", e);
		}

	}

	/**
	 * Añade linea a pdf mock.
	 *
	 * @param lineas
	 *                      Lineas
	 * @param campoDesc
	 *                      campo desc
	 * @param campoVal
	 *                      campo valor
	 */
	protected void generaPdfMockLinea(final List<Linea> lineas, final String campoDesc, final String campoVal) {
		final Linea linea = new Linea();
		lineas.add(linea);
		linea.getObjetosLinea().add(new CampoTexto(6, false, campoDesc, campoVal));
	}

	/**
	 * Obtiene carta pago presencial simulado.
	 *
	 * @param sesionPago
	 *                       sesion pago
	 * @return carta pago
	 */
	private byte[] obtenerCartaPagoPresencialSimulado(final DatosSesionPago sesionPago) {
		return generaPdfMock(sesionPago);
	}

	/**
	 * Obtiene carta pago presencial.
	 *
	 * @param sesionPago
	 *                       sesion pago
	 * @return carta pago
	 */
	private byte[] obtenerCartaPagoPresencialPasarela(final DatosSesionPago sesionPago) throws PagoPluginException {
		final IComponentePagoPlugin plugin = obtenerPlugin(sesionPago.getEntidadId());
		final DatosPago datosPago = crearDatosPago(sesionPago);
		return plugin.obtenerCartaPagoPresencial(datosPago);
	}

}
