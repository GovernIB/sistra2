package es.caib.sistramit.core.service.repository.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistra2.commons.utils.JSONUtilException;
import es.caib.sistra2.commons.utils.Serializador;
import es.caib.sistramit.core.api.exception.SerializacionException;
import es.caib.sistramit.core.api.exception.TicketFormularioException;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.ParametrosAperturaFormulario;
import es.caib.sistramit.core.service.model.formulario.types.TipoFinalizacionFormulario;
import es.caib.sistramit.core.service.repository.model.HFormulario;

/**
 * Implementación DAO Formulario.
 *
 * @author Indra
 */
@Repository("formularioDao")
public final class FormularioDaoImpl implements FormularioDao {

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String crearSesionGestorFormularios(final DatosInicioSesionFormulario dis) {
		// Generamos el ticket
		final String ticket = GeneradorId.generarId();
		// Establecemos datos
		final HFormulario hFormulario = new HFormulario();
		hFormulario.setTicket(ticket);
		hFormulario.setEntidad(dis.getEntidad());
		hFormulario.setFechaInicio(new Date());
		hFormulario.setIdSesionTramitacion(dis.getIdSesionTramitacion());
		hFormulario.setIdPaso(dis.getIdPaso());
		hFormulario.setIdTramite(dis.getIdTramite());
		hFormulario.setVersionTramite(dis.getVersionTramite());
		hFormulario.setReleaseTramite(dis.getReleaseTramite());
		hFormulario.setIdioma(dis.getIdioma());
		hFormulario.setIdFormulario(dis.getIdFormulario());
		hFormulario.setInterno(dis.isInterno());
		hFormulario.setDatosActuales(dis.getXmlDatosActuales());
		hFormulario.setTituloProcedimiento(dis.getTituloProcedimiento());
		hFormulario.setCodigoSiaProcedimiento(dis.getCodigoSiaProcedimiento());
		hFormulario.setDir3ResponsableProcedimiento(dis.getDir3ResponsableProcedimiento());
		hFormulario.setIdGestorFormulariosExterno(dis.getIdGestorFormulariosExterno());

		try {
			hFormulario.setInfoAutenticacion(Serializador.serializeJSON(dis.getInfoAutenticacion()));
			hFormulario.setParametrosFormulario(Serializador.serializeJSON(dis.getParametros()));
		} catch (final JSONUtilException e) {
			throw new SerializacionException("Error serializando datos inicio formulario", e);
		}
		// Almacenamos datos inicio formulario
		entityManager.persist(hFormulario);
		// Retornamos ticket
		return ticket;
	}

	@Override
	public DatosInicioSesionFormulario obtenerDatosInicioSesionGestorFormularios(final String ticket,
			final boolean interno) {

		// Busca datos inicio
		final HFormulario h = obtenerSesionFormulario(ticket, interno);

		// Establecemos datos
		final DatosInicioSesionFormulario dis = new DatosInicioSesionFormulario();
		dis.setTicket(h.getTicket());
		dis.setIdioma(h.getIdioma());
		dis.setIdSesionTramitacion(h.getIdSesionTramitacion());
		dis.setIdPaso(h.getIdPaso());
		dis.setIdTramite(h.getIdTramite());
		dis.setVersionTramite(h.getVersionTramite());
		dis.setReleaseTramite(h.getReleaseTramite());
		dis.setIdFormulario(h.getIdFormulario());
		dis.setInterno(h.isInterno());
		dis.setXmlDatosActuales(h.getDatosActuales());
		dis.setTituloProcedimiento(h.getTituloProcedimiento());
		dis.setCodigoSiaProcedimiento(h.getCodigoSiaProcedimiento());
		dis.setDir3ResponsableProcedimiento(h.getDir3ResponsableProcedimiento());
		dis.setEntidad(h.getEntidad());
		dis.setIdGestorFormulariosExterno(h.getIdGestorFormulariosExterno());
		try {
			dis.setInfoAutenticacion((UsuarioAutenticadoInfo) Serializador.deserializeJSON(h.getInfoAutenticacion(),
					UsuarioAutenticadoInfo.class));
			dis.setParametros((ParametrosAperturaFormulario) Serializador.deserializeJSON(h.getParametrosFormulario(),
					ParametrosAperturaFormulario.class));
		} catch (final JSONUtilException e) {
			throw new SerializacionException("Error serializando datos inicio formulario", e);
		}

		return dis;
	}

	@Override
	public void finalizarSesionGestorFormularios(final String ticket,
			final DatosFinalizacionFormulario datosFinSesion) {
		final HFormulario hFormulario = obtenerSesionFormulario(ticket);
		if (hFormulario.getFechaFin() != null) {
			throw new TicketFormularioException("La sesión de formulario ya se ha finalizado anteriormente: " + ticket);
		}
		hFormulario.setPdf(datosFinSesion.getPdf());
		hFormulario.setXml(datosFinSesion.getXml());
		hFormulario.setFechaFin(new Date());
		hFormulario.setEstadoFinalizacion(datosFinSesion.getEstadoFinalizacion().getValor());
		hFormulario.setTicketFormularioExterno(datosFinSesion.getTicketExterno());
		entityManager.merge(hFormulario);
	}

	@Override
	public DatosFinalizacionFormulario obtenerDatosFinSesionGestorFormularios(final String ticket,
			final boolean interno) {
		final HFormulario hFormulario = obtenerSesionFormulario(ticket, interno);
		if (hFormulario.isUsadoRetorno()) {
			throw new TicketFormularioException("Ya se ha usado el ticket para retornar el formulario " + ticket);
		}
		if (hFormulario.getFechaFin() == null) {
			throw new TicketFormularioException("La sesión de formulario no se ha finalizado " + ticket);
		}
		final DatosFinalizacionFormulario df = new DatosFinalizacionFormulario();
		df.setFechaFinalizacion(hFormulario.getFechaFin());
		df.setEstadoFinalizacion(TipoFinalizacionFormulario.fromInt(hFormulario.getEstadoFinalizacion()));
		df.setXml(hFormulario.getXml());
		df.setPdf(hFormulario.getPdf());
		return df;
	}

	/**
	 * Busca sesión formulario a partir ticket.
	 *
	 * @param ticket
	 *                   ticket
	 * @return sesión formulario
	 */
	private HFormulario obtenerSesionFormulario(final String ticket) {
		return obtenerSesionFormulario(ticket, true);
	}

	/**
	 * Busca sesión formulario a partir ticket.
	 *
	 * @param ticket
	 *                   ticket
	 * @return sesión formulario
	 */
	private HFormulario obtenerSesionFormulario(final String ticket, final boolean interno) {
		HFormulario h = null;
		String sql = null;
		if (interno) {
			sql = "SELECT t FROM HFormulario t WHERE t.ticket = :ticket";
		} else {
			sql = "SELECT t FROM HFormulario t WHERE t.ticketFormularioExterno = :ticket";
		}
		final Query query = entityManager.createQuery(sql);
		query.setParameter("ticket", ticket);
		final List results = query.getResultList();
		if (!results.isEmpty()) {
			h = (HFormulario) results.get(0);
		}
		if (h == null) {
			throw new TicketFormularioException("No existe formulario con el ticket " + ticket);
		}
		return h;
	}

}
