package es.caib.sistramit.core.service.component.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorParametroObligatorioException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.repository.dao.AuditoriaDao;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.repository.dao.TicketCDCDao;

@Component("restApiExternaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RestApiExternaComponentImpl implements RestApiExternaComponent {

	@Autowired
	private FlujoTramiteDao flujoTramiteDao;

	@Autowired
	private AuditoriaDao auditoriaDao;

	@Autowired
	private TicketCDCDao ticketCDCDao;

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public List<TramitePersistencia> recuperarTramites(final FiltroTramitePersistencia pFiltro) {
		if (pFiltro.getNif() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <NIF> es obligatorio");
		}

		return flujoTramiteDao.recuperarTramitesPersistencia(pFiltro);
	}

	@Override
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		if (pFiltro.getFecha() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Fecha evento> es obligatorio");
		}

		if (pFiltro.getListaEventos() == null || pFiltro.getListaEventos().isEmpty()) {
			throw new ErrorParametroObligatorioException("El parámetro <Tipo Evento> es obligatorio");
		}

		return auditoriaDao.recuperarEventos(pFiltro);
	}

	@Override
	public String obtenerTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {

		if (pInfoTicketAcceso.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Identificador de la sesión> es obligatorio");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getMetodoAutenticacion() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Metodo Autenticacion> es obligatorio");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNif() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Nif usuario> es obligatorio");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNombre() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Nombre usuario> es obligatorio");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getUsername() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <Código usuario> es obligatorio");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getQaa() == null) {
			throw new ErrorParametroObligatorioException("El parámetro <QAA> es obligatorio");
		}

		final String ticket = ticketCDCDao.generarTicketAcceso(pInfoTicketAcceso);

		// - Construimos url
		final String urlCallback = configuracionComponent.obtenerPropiedadConfiguracion(
				TypePropiedadConfiguracion.SISTRAMIT_URL) + ConstantesSeguridad.PUNTOENTRADA_RETORNO_CARPETA + "?"
				+ ConstantesSeguridad.TICKET_PARAM + "=" + ticket;

		return urlCallback;
	}

}
