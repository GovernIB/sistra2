package es.caib.sistramit.core.service.component.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.flujo.DatosSesionPago;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeSoporteEstado;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.system.rest.interno.DetallePagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.ErroresPorTramiteCM;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoCM;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FicheroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FormularioSoporte;
import es.caib.sistramit.core.api.model.system.rest.interno.OUTPerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.PagoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.PerdidaClave;
import es.caib.sistramit.core.api.model.system.rest.interno.PersistenciaAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.VerificacionPago;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.pagar.ControladorPasoPagarHelper;
import es.caib.sistramit.core.service.component.integracion.PagoComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.integracion.PagoComponentVerificacion;
import es.caib.sistramit.core.service.model.system.PerdidaClaveFichero;
import es.caib.sistramit.core.service.repository.dao.RestApiDao;
import es.caib.sistramit.core.service.util.UtilsFormulario;

@Component("restApiInternaComponen")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RestApiInternaComponentImpl implements RestApiInternaComponent {

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Autowired
	private RestApiDao restApiDao;

	@Autowired
	private PagoComponent pagoComponent;

	@Override
	public OUTPerdidaClave recuperarClaveTramitacionArea(final FiltroPerdidaClave pFiltroBusqueda) {
		PerdidaClave perdidaClave = null;
		final OUTPerdidaClave resPedidaClave = new OUTPerdidaClave();

		final String numMax = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.PERDIDA_CLAVE_NUMMAXTRAMITES);

		final Long ntramites = restApiDao.countTramitesPerdidaClave(pFiltroBusqueda);

		if (Long.valueOf(numMax) < ntramites) {
			resPedidaClave.setResultado(-1);
		} else {

			final List<PerdidaClaveFichero> listaTramites = restApiDao.obtenerTramitesPerdidaClave(pFiltroBusqueda);

			resPedidaClave.setListaClaves(new ArrayList<>());

			for (final PerdidaClaveFichero perdidaClaveFichero : listaTramites) {
				final DatosFicheroPersistencia fichero = restApiDao
						.recuperarFicheroPersistencia(perdidaClaveFichero.getFichero());

				final XmlFormulario xmlFormulario = UtilsFormulario.xmlToValores(fichero.getContenido());

				if (xmlFormulario != null && !xmlFormulario.getValores().isEmpty()) {

					for (final ValorCampo valorCampo : xmlFormulario.getValores()) {

						if (valorCampo instanceof ValorCampoSimple) {
							if (pFiltroBusqueda.getDatoFormulario()
									.equals(((ValorCampoSimple) valorCampo).getValor())) {
								perdidaClave = convertPerdidaClave(perdidaClaveFichero);
								if (!resPedidaClave.getListaClaves().contains(perdidaClave)) {
									resPedidaClave.getListaClaves().add(perdidaClave);
								}
							}
						} else if (valorCampo instanceof ValorCampoIndexado) {
							if (pFiltroBusqueda.getDatoFormulario()
									.equals(((ValorCampoIndexado) valorCampo).getValor().getDescripcion())) {
								perdidaClave = convertPerdidaClave(perdidaClaveFichero);
								if (!resPedidaClave.getListaClaves().contains(perdidaClave)) {
									resPedidaClave.getListaClaves().add(perdidaClave);
								}
							}
						} else if (valorCampo instanceof ValorCampoListaIndexados) {
							for (final ValorIndexado valor : ((ValorCampoListaIndexados) valorCampo).getValor()) {
								if (pFiltroBusqueda.getDatoFormulario().equals(valor.getDescripcion())) {
									perdidaClave = convertPerdidaClave(perdidaClaveFichero);
									if (!resPedidaClave.getListaClaves().contains(perdidaClave)) {
										resPedidaClave.getListaClaves().add(perdidaClave);
									}
								}
							}
						}

					}
				}
			}

			if (!resPedidaClave.getListaClaves().isEmpty()) {
				resPedidaClave.setResultado(1);
			}

		}

		return resPedidaClave;
	}

	@Override
	public FicheroAuditoria recuperarFichero(final Long pIdFichero, final String pClave) {
		FicheroAuditoria ficheroAuditoria = null;
		final ReferenciaFichero rf = new ReferenciaFichero(pIdFichero, pClave);
		final DatosFicheroPersistencia fichero = restApiDao.recuperarFicheroPersistenciaNoBorrado(rf);

		if (fichero != null) {
			ficheroAuditoria = new FicheroAuditoria();
			ficheroAuditoria.setNombre(fichero.getNombre());
			ficheroAuditoria.setContenido(fichero.getContenido());
		}

		return ficheroAuditoria;
	}

	@Override
	public List<PagoAuditoria> recuperarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		final List<PagoAuditoria> listaPagos = restApiDao.obtenerPagos(pFiltroBusqueda, pFiltroPaginacion);

		for (final PagoAuditoria pagoAuditoria : listaPagos) {
			final DatosFicheroPersistencia fichero = restApiDao.recuperarFicheroPersistencia(
					new ReferenciaFichero(pagoAuditoria.getFichero(), pagoAuditoria.getFicheroClave()));
			if (fichero.getContenido() != null) {
				final DatosSesionPago sesionPago = ControladorPasoPagarHelper.getInstance()
						.fromXML(fichero.getContenido());

				if (sesionPago != null) {
					pagoAuditoria.setIdentificador(sesionPago.getIdentificadorPago());
					pagoAuditoria.setPresentacion(sesionPago.getPresentacion().toString());
					pagoAuditoria.setPasarelaId(sesionPago.getPasarelaId());
					pagoAuditoria.setImporte(sesionPago.getImporte());
					pagoAuditoria.setTasaId(sesionPago.getTasaId());
					pagoAuditoria.setLocalizador(sesionPago.getLocalizador());
					pagoAuditoria.setFechaPago(sesionPago.getFechaPago());
				}

			}
		}

		return listaPagos;
	}

	@Override
	public Long contarPagosArea(final FiltroPagoAuditoria pFiltroBusqueda) {
		return restApiDao.countPagos(pFiltroBusqueda);
	}

	@Override
	public DetallePagoAuditoria recuperarDetallePago(final Long pIdPago) {

		final DetallePagoAuditoria detallePago = new DetallePagoAuditoria();

		final DocumentoPasoPersistencia doc = restApiDao.obtenerDocumento(pIdPago);

		if (doc != null) {
			final DatosFicheroPersistencia fichero = restApiDao.recuperarFicheroPersistencia(doc.getFichero());

			if (fichero != null && fichero.getContenido() != null) {
				detallePago.setDatos(ControladorPasoPagarHelper.getInstance().fromXML(fichero.getContenido()));
			}

			if (doc.getEstado().equals(TypeEstadoDocumento.RELLENADO_INCORRECTAMENTE)
					&& doc.getPagoEstadoIncorrecto() == TypeEstadoPagoIncorrecto.PAGO_INICIADO
					&& detallePago.getDatos() != null
					&& TypePresentacion.ELECTRONICA.equals(detallePago.getDatos().getPresentacion())) {
				final PagoComponentVerificacion verificacion = pagoComponent
						.verificarPagoElectronico(detallePago.getDatos(), true);

				detallePago.setVerificacion(convertVerificacionPago(verificacion));
			}
		}

		return detallePago;
	}

	@Override
	public Long contarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		return restApiDao.countTramitesPersistencia(pFiltroBusqueda);
	}

	@Override
	public List<PersistenciaAuditoria> recuperarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return restApiDao.obtenerTramitesPersistencia(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public List<FicheroPersistenciaAuditoria> recuperarPersistenciaFicheros(final Long pIdTramite) {
		return restApiDao.recuperarPersistenciaFicheros(pIdTramite);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.retrieveByAreas(pFiltroBusqueda);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return restApiDao.retrieveByAreas(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.countByAreas(pFiltroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarEventosCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.recuperarEventosCM(pFiltroBusqueda);
	}

	@Override
	public List<ErroresPorTramiteCM> recuperarErroresPorTramiteCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiDao.recuperarErroresPorTramiteCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPorTramiteCM(FiltroEventoAuditoria filtroBusqueda) {
		return restApiDao.contarErroresPorTramiteCM(filtroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPorTramiteCMExpansion(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiDao.recuperarErroresPorTramiteCMExpansion(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPorTramiteExpansionCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.contarErroresPorTramiteExpansionCM(pFiltroBusqueda);
	}

	@Override
	public List<EventoCM> recuperarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiDao.recuperarErroresPlataformaCM(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarErroresPlataformaCM(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.contarErroresPlataformaCM(pFiltroBusqueda);
	}

	@Override
	public List<FormularioSoporte> recuperarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda,
			FiltroPaginacion filtroPaginacion) {
		return restApiDao.recuperarFormularioSoporte(pFiltroBusqueda, filtroPaginacion);
	}

	@Override
	public Long contarFormularioSoporte(FiltroEventoAuditoria pFiltroBusqueda) {
		return restApiDao.contarFormularioSoporte(pFiltroBusqueda);
	}

	@Override
	public void updateEstadoIncidencia(Long idSoporte, TypeSoporteEstado estado, String comentarios) {
		restApiDao.updateEstadoIncidencia(idSoporte, estado, comentarios);
		;
	}
	// ------------------ FUNCIONES INTERNAS ------------------

	/**
	 * Convert perdida clave.
	 *
	 * @param pPerdidaClaveFichero perdida clave fichero
	 * @return the perdida clave
	 */
	private PerdidaClave convertPerdidaClave(final PerdidaClaveFichero pPerdidaClaveFichero) {
		PerdidaClave res = null;

		if (pPerdidaClaveFichero != null) {
			res = new PerdidaClave();
			res.setIdSesionTramitacion(pPerdidaClaveFichero.getIdSesionTramitacion());
			res.setFecha(pPerdidaClaveFichero.getFecha());
			res.setIdTramite(pPerdidaClaveFichero.getIdTramite());
			res.setVersionTramite(pPerdidaClaveFichero.getVersionTramite());
			res.setIdProcedimientoCP(pPerdidaClaveFichero.getIdProcedimientoCP());
		}
		return res;
	}

	private VerificacionPago convertVerificacionPago(final PagoComponentVerificacion pPCV) {
		VerificacionPago res = null;
		if (pPCV != null) {
			res = new VerificacionPago();
			res.setVerificado(pPCV.isVerificado());
			res.setPagado(pPCV.isPagado());
			res.setCodigoError(pPCV.getCodigoError());
			res.setMensajeError(pPCV.getMensajeError());
			res.setFechaPago(pPCV.getFechaPago());
			res.setLocalizador(pPCV.getLocalizador());
			res.setJustificantePDF(pPCV.getJustificantePDF());
		}

		return res;

	}

}
