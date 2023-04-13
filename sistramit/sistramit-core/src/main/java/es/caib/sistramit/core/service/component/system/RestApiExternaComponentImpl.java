package es.caib.sistramit.core.service.component.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorParametroObligatorioException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.repository.dao.RestApiDao;
import es.caib.sistramit.core.service.repository.dao.TicketCDCDao;

@Component("restApiExternaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RestApiExternaComponentImpl implements RestApiExternaComponent {

	@Autowired
	private RestApiDao flujoTramiteDao;

	@Autowired
	private TicketCDCDao ticketCDCDao;

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public List<TramitePersistencia> recuperarTramites(final FiltroTramitePersistencia pFiltro) {
		if (pFiltro.getNif() == null && pFiltro.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <NIF> o <idSesionTramitacion> és obligatori");
		}
		return flujoTramiteDao.recuperarTramitesPersistencia(pFiltro);
	}

	@Override
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		if (pFiltro.getFecha() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Fecha evento> és obligatori");
		}
		return flujoTramiteDao.recuperarEventos(pFiltro);
	}

	@Override
	public String obtenerTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {

		if (pInfoTicketAcceso.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Identificador de la sesión> és obligatori");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getMetodoAutenticacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Metodo Autenticacion> és obligatori");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNif() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nif usuario> és obligatori");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNombre() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nombre usuario> és obligatori");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getUsername() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Código usuario> és obligatori");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getQaa() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <QAA> és obligatori");
			}
		}

		final String ticket = ticketCDCDao.generarTicketAcceso(pInfoTicketAcceso);

		// - Construimos url
		final String urlCallback = configuracionComponent.obtenerPropiedadConfiguracion(
				TypePropiedadConfiguracion.SISTRAMIT_URL) + ConstantesSeguridad.PUNTOENTRADA_RETORNO_CARPETA + "?"
				+ ConstantesSeguridad.PARAM_TICKETAUTH + "=" + ticket;

		return urlCallback;
	}

	@Override
	public List<TramiteFinalizado> recuperarTramitesFinalizados(final FiltroTramiteFinalizado pFiltro) {
		if (pFiltro.getNif() == null && pFiltro.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <NIF> o <idSesionTramitacion> és obligatori");
		}
		return flujoTramiteDao.recuperarTramitesFinalizados(pFiltro);
	}

}
