package es.caib.sistramit.core.service.repository.dao;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistra2.commons.utils.Serializador;
import es.caib.sistramit.core.api.exception.TicketAccesoException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoAccesoFH;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.types.TypeTicketAcceso;
import es.caib.sistramit.core.service.repository.model.HTicketCDC;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Proceso DAO.
 */
@Repository("ticketCDCDao")
public class TicketCDCDaoImpl implements TicketCDCDao {

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String generarTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {

		String idioma;
		switch (pInfoTicketAcceso.getTipoTicketAcceso()) {
			case CARPETA:
				final String sql = "SELECT t.idioma from HTramite t where t.sesionTramitacion.idSesionTramitacion = :idSesionTramitacion";
				final Query query = entityManager.createQuery(sql);
				query.setParameter("idSesionTramitacion", pInfoTicketAcceso.getIdSesionTramitacion());
				final List<?> results = query.getResultList();
				if (results.isEmpty()) {
					throw new TicketAccesoException("No s'ha trobat la sessió de tramitació");
				}
				idioma = (String) results.get(0);
				break;
			case FUNCIONARIO_HABILITADO:
				idioma = pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getIdioma();
				break;
			default:
				throw new TicketAccesoException("No s'ha trobat la sessió de tramitació");
		}
		// Para acceso carpeta obtiene idioma de la sesion tramitacion
		if (pInfoTicketAcceso.getTipoTicketAcceso() == TypeTicketAcceso.CARPETA) {

		}

		// Genera ticket añadiendo opción para cambiar idioma
		final String ticket = GeneradorId.generarId() + ConstantesSeguridad.PARAM_TICKET_LANG + idioma;

		// Guarda ticket
		final HTicketCDC hTck = new HTicketCDC();
		hTck.setTipo(pInfoTicketAcceso.getTipoTicketAcceso().toString());
		hTck.setTicket(ticket);
		hTck.setFechaInicio(new Date());
		hTck.setIdSesionTramitacion(pInfoTicketAcceso.getIdSesionTramitacion());
		hTck.setUrlCallbackError(pInfoTicketAcceso.getUrlCallbackError());
		try {
			hTck.setInfoAutenticacion(JSONUtil.toJSON(pInfoTicketAcceso.getUsuarioAutenticadoInfo()));
			hTck.setInfoFH(JSONUtil.toJSON(pInfoTicketAcceso.getInfoAccesoFH()));
		} catch (final JSONUtilException e) {
			throw new TicketAccesoException("Error serialitzant informació usuari");
		}
		entityManager.persist(hTck);

		return ticket;
	}

	@Override
	public InfoTicketAcceso obtieneTicketAcceso(final String ticket) {
		// Recuperamos ticket
		final HTicketCDC h = recuperarTicket(ticket);
		// Devolvemos info ticket
		final InfoTicketAcceso infoTicket = new InfoTicketAcceso();
		infoTicket.setTipoTicketAcceso(TypeTicketAcceso.fromString(h.getTipo()));
		infoTicket.setIdSesionTramitacion(h.getIdSesionTramitacion());
		infoTicket.setUrlCallbackError(h.getUrlCallbackError());
		try {
			infoTicket.setUsuarioAutenticadoInfo(
					(UsuarioAutenticadoInfo) JSONUtil.fromJSON(h.getInfoAutenticacion(), UsuarioAutenticadoInfo.class));
			infoTicket.setInfoAccesoFH(
					(InfoAccesoFH) JSONUtil.fromJSON(h.getInfoFH(), InfoAccesoFH.class));
		} catch (final JSONUtilException e) {
			throw new TicketAccesoException("Error serialitzando informació autenticació");
		}
		infoTicket.setUsado(h.isUsadoRetorno());
		infoTicket.setFecha(h.getFechaInicio());
		return infoTicket;
	}

	@Override
	public void consumirTicketAcceso(final String ticket) {
		// Recuperamos ticket
		final HTicketCDC h = recuperarTicket(ticket);
		// Consumimos ticket
		h.setUsadoRetorno(true);
		h.setFechaFin(new Date());
		entityManager.merge(h);
	}

	/**
	 * Recupera info ticket.
	 *
	 * @param ticket
	 *                   ticket
	 * @return Info ticket
	 */
	private HTicketCDC recuperarTicket(final String ticket) {
		final String sql = "SELECT t FROM HTicketCDC t WHERE t.ticket = :ticket";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("ticket", ticket);
		final List results = query.getResultList();
		HTicketCDC h = null;
		if (!results.isEmpty()) {
			h = (HTicketCDC) results.get(0);
		}
		if (h == null) {
			throw new TicketAccesoException("No existe ticket " + ticket);
		}
		return h;
	}

}
