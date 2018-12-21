package es.caib.sistramit.core.service.repository.dao;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistra2.commons.utils.Serializador;
import es.caib.sistramit.core.api.exception.TicketPagoException;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.service.repository.model.HTicketCDC;

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

		final String ticket = GeneradorId.generarId();

		final HTicketCDC hTck = new HTicketCDC();
		hTck.setTicket(ticket);
		hTck.setFechaInicio(new Date());
		hTck.setIdSesionTramitacion(pInfoTicketAcceso.getIdSesionTramitacion());

		try {
			hTck.setInfoAutenticacion(Serializador.serialize(pInfoTicketAcceso.getUsuarioAutenticadoInfo()));
		} catch (final IOException e) {
			throw new TicketPagoException("Error serializando informacion usuario");
		}

		entityManager.persist(hTck);

		return ticket;
	}

}
