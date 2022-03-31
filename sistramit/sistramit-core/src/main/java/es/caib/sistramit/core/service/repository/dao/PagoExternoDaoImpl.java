package es.caib.sistramit.core.service.repository.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistra2.commons.utils.Serializador;
import es.caib.sistramit.core.api.exception.SerializacionException;
import es.caib.sistramit.core.api.exception.TicketPagoException;
import es.caib.sistramit.core.api.model.flujo.RetornoPago;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.service.repository.model.HPagoExterno;

/**
 * Proceso DAO.
 */
@Repository("pagoExternoDao")
public class PagoExternoDaoImpl implements PagoExternoDao {

	/** Entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String generarTicketPago(final RetornoPago retornoPago) {

		final String ticket = GeneradorId.generarId();

		final HPagoExterno h = new HPagoExterno();
		h.setTicket(ticket);
		h.setFechaInicio(new Date());
		h.setIdSesionTramitacion(retornoPago.getIdSesionTramitacion());
		h.setIdPaso(retornoPago.getIdPaso());
		h.setIdPago(retornoPago.getIdPago());
		try {
			h.setInfoAutenticacion(Serializador.serialize(retornoPago.getUsuario()));
		} catch (final IOException e) {
			throw new SerializacionException("Error serialitzant informació usuari", e);
		}

		entityManager.persist(h);

		return ticket;
	}

	@Override
	public RetornoPago consumirTicketPago(final String ticket) {
		return obtenerDatosTicketPago(ticket, true);
	}

	@Override
	public RetornoPago obtenerTicketPago(final String ticket) {
		return obtenerDatosTicketPago(ticket, false);
	}

	/**
	 * Obtiene dato ticket.
	 *
	 * @param ticket
	 *            ticket
	 * @param consumirTicket
	 *            si se consume ticket de 1 solo uso
	 * @return datos ticket
	 */
	private RetornoPago obtenerDatosTicketPago(final String ticket, final boolean consumirTicket) {
		HPagoExterno h = null;
		final String sql = "SELECT t FROM HPagoExterno t WHERE t.ticket = :ticket";
		final Query query = entityManager.createQuery(sql);
		query.setParameter("ticket", ticket);
		final List results = query.getResultList();
		if (!results.isEmpty()) {
			h = (HPagoExterno) results.get(0);
		}
		if (h == null) {
			throw new TicketPagoException("No existeix pagament extern amb el ticket " + ticket);
		}

		// Si se consume ticket, no debe estar usado
		if (consumirTicket && h.isUsadoRetorno()) {
			throw new TicketPagoException("El ticket " + ticket + " ja ha estat utilitzat");
		}

		// Si no se consume ticket, debe estar usado
		if (!consumirTicket && !h.isUsadoRetorno()) {
			throw new TicketPagoException("El ticket " + ticket + " no ha estat utilitzat");
		}

		final RetornoPago res = new RetornoPago();
		res.setIdSesionTramitacion(h.getIdSesionTramitacion());
		res.setIdPaso(h.getIdPaso());
		res.setIdPago(h.getIdPago());
		try {
			res.setUsuario((UsuarioAutenticadoInfo) Serializador.deserialize(h.getInfoAutenticacion()));
		} catch (ClassNotFoundException | IOException e) {
			throw new SerializacionException("Error al deserialitzar informació usuari per ticket " + ticket, e);
		}

		// Si se consume ticket, marcamos ticket como usado
		if (consumirTicket) {
			h.setUsadoRetorno(true);
			h.setFechaFin(new Date());
			entityManager.merge(h);
		}

		return res;
	}

	@Override
	public int purgarPagosExternosFinalizados(final Date fechaLimite) {

		final String sql = "delete from STT_PAGEXT where PAE_FECFIN is not null and PAE_FECFIN < :fechaLimite ";

		final Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("fechaLimite", fechaLimite);
		return sqlQuery.executeUpdate();

	}

	@Override
	public int purgarPagosExternosNoFinalizados(final Date fechaLimite) {

		final String sql = "delete from STT_PAGEXT where PAE_FECFIN is null and PAE_FECINI < :fechaLimite ";

		final Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("fechaLimite", fechaLimite);
		return sqlQuery.executeUpdate();

	}

}
