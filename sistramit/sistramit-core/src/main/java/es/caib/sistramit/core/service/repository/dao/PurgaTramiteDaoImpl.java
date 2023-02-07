package es.caib.sistramit.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.repository.model.HTramite;

/**
 * Implementacion Purga DAO.
 */
@Repository("purgaTramiteDao")
public class PurgaTramiteDaoImpl implements PurgaTramiteDao {

	/** Parametro sql limiteregs. */
	private static final String PARAM_LIMITE_REGS = "limiteRegs";

	/** Parametro sql fechaLimiteCaducados. */
	private static final String PARAM_FECHA_LIMITE_CADUCADOS = "fechaLimiteCaducados";

	/** Parametro sql fechaLimiteSinFinalizar. */
	private static final String PARAM_FECHA_LIMITE_SIN_FINALIZAR = "fechaLimiteSinFinalizar";

	/** Parametro sql fechaLimiteFinalizados. */
	private static final String PARAM_FECHA_LIMITE_FINALIZADOS = "fechaLimiteFinalizados";

	/** Parametro sql fechaLimitePendientePurgaPago. */
	private static final String PARAM_FECHA_LIMITE_PENDIENTE_PURGA_PAGO = "fechaLimitePendientePurgaPago";

	/** Parametro sql fechaLimitePendientePurgaPago. */
	private static final String PARAM_FECHA_LIMITE_SIN_CADUCIDAD = "fechaLimiteSinCaducidad";

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(PurgaTramiteDaoImpl.class);

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ListaPropiedades marcarPurgarTramites(final Date pFinalizadosHasta, final Date pSinCaducidadHasta,
			final Date pSinFinalizarHasta, final Date pCaducadosHasta, final Date pPendientePurgaPagoRealizadoHasta) {

		Query sqlQuery;
		int numRegsUpdate;

		String tramitesMarcados = "";
		String tramitesMarcadosPendientePagoRealizado = "";
		String tramitesPendientes = "";
		String tramitesPendientePagoRealizado = "";

		// Marcamos para purgar los tramites pendientes de purga por pago tras
		// pasar los dias de espera
		if (pPendientePurgaPagoRealizadoHasta != null) {
			final String sqlUpdatePurgarPendientePago = "update STT_TRAPER set TRP_PURCHK = 1 where "
					+ "TRP_PURPAG = 1 and TRP_PURCHK = 0  and TRP_FECACC < :fechaLimitePendientePurgaPago";

			sqlQuery = entityManager.createNativeQuery(sqlUpdatePurgarPendientePago);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_PENDIENTE_PURGA_PAGO, pPendientePurgaPagoRealizadoHasta);
			numRegsUpdate = sqlQuery.executeUpdate();
			tramitesMarcadosPendientePagoRealizado = Integer.toString(numRegsUpdate);
		}

		// Genera filtro tramites pendientes purga
		final String sqlFiltroWherePendientesPurga = generarFiltroTramitesPendientesPurga(pFinalizadosHasta,
				pSinCaducidadHasta, pSinFinalizarHasta, pCaducadosHasta);

		// Update marcar tramites para purgar tramites si se ha establecido
		// alguno de los filtros
		if (pFinalizadosHasta != null || pSinCaducidadHasta != null || pSinFinalizarHasta != null
				|| pCaducadosHasta != null) {

			// Marcamos tramites que quedan pendiente purga por pago realizado
			// (del filtro de los que entran para purgar se quitan los
			// finalizados)
			final String sqlSelectTramitesPagoRealizado = "select distinct PTR_CODTRP FROM STT_TRAPER, STT_PASTRP, STT_DOCPTR "
					+ "WHERE TRP_CODIGO = PTR_CODTRP AND DTP_CODPTR = PTR_CODIGO AND DTP_DOCTIP = '"
					+ TypeDocumento.PAGO.toString() + "' AND DTP_ESTADO = '"
					+ TypeEstadoDocumento.RELLENADO_CORRECTAMENTE.toString() + "' AND TRP_ESTADO <> '"
					+ TypeEstadoTramite.FINALIZADO.toString() + "'";
			final String sqlUpdatePdtePurgaPago = "update STT_TRAPER set TRP_PURPAG = 1 where "
					+ sqlFiltroWherePendientesPurga + " and TRP_CODIGO in ( " + sqlSelectTramitesPagoRealizado + ")";
			sqlQuery = entityManager.createNativeQuery(sqlUpdatePdtePurgaPago);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_FINALIZADOS, pFinalizadosHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_CADUCIDAD, pSinCaducidadHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_FINALIZAR, pSinFinalizarHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_CADUCADOS, pCaducadosHasta);
			numRegsUpdate = sqlQuery.executeUpdate();
			tramitesPendientePagoRealizado = Integer.toString(numRegsUpdate);

			// Marcamos para purgar (excepto si tienen pagos realizados)
			final String sqlUpdate = "update STT_TRAPER set TRP_PURCHK = 1 where " + sqlFiltroWherePendientesPurga;
			sqlQuery = entityManager.createNativeQuery(sqlUpdate);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_FINALIZADOS, pFinalizadosHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_CADUCIDAD, pSinCaducidadHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_FINALIZAR, pSinFinalizarHasta);
			sqlQuery.setParameter(PARAM_FECHA_LIMITE_CADUCADOS, pCaducadosHasta);

			numRegsUpdate = sqlQuery.executeUpdate();
			tramitesMarcados = Integer.toString(numRegsUpdate);
		}

		// Calculamos cuantos tramites quedan por purgar
		final String sqlPendientes = "select count(*) as count from STT_TRAPER where " + sqlFiltroWherePendientesPurga;
		sqlQuery = entityManager.createNativeQuery(sqlPendientes);
		sqlQuery.setParameter(PARAM_FECHA_LIMITE_FINALIZADOS, pFinalizadosHasta);
		sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_CADUCIDAD, pSinCaducidadHasta);
		sqlQuery.setParameter(PARAM_FECHA_LIMITE_SIN_FINALIZAR, pSinFinalizarHasta);
		sqlQuery.setParameter(PARAM_FECHA_LIMITE_CADUCADOS, pCaducadosHasta);
		tramitesPendientes = sqlQuery.getSingleResult().toString();

		// Devolvemos tramites marcados para purgar y tramites pendientes
		final ListaPropiedades res = new ListaPropiedades();
		res.addPropiedad("MARCADOS", tramitesMarcados);
		res.addPropiedad("PENDIENTES", tramitesPendientes);
		res.addPropiedad("PENDIENTEPAGOREALIZADO", tramitesPendientePagoRealizado);
		res.addPropiedad("MARCADOSPENDIENTEPAGOREALIZADO", tramitesMarcadosPendientePagoRealizado);
		return res;
	}

	@Override
	public int realizarPurgaTramitesMarcadosParaPurgar() {
		// Marcamos ficheros a borrar
		log.info("Proceso purga: purgando los tramites marcados para purgar (marcarFicherosBorrarPurgaTramites)");
		marcarFicherosBorrarPurgaTramites();
		// Borramos firmas
		log.info("Proceso purga: purgando los tramites marcados para purgar (borrarFirmasPurgaTramites)");
		borrarFirmasPurgaTramites();
		// Borramos documentos
		log.info("Proceso purga: purgando los tramites marcados para purgar (borrarDocumentosPurgaTramites)");
		borrarDocumentosPurgaTramites();
		// Borramos pasos
		log.info("Proceso purga: purgando los tramites marcados para purgar (borrarPasosPurgaTramites)");
		borrarPasosPurgaTramites();
		// Marcamos tramites como purgados
		log.info("Proceso purga: purgando los tramites marcados para purgar (marcarTramitesPurgados)");
		return marcarTramitesPurgados();
	}

	@Override
	public int purgarSesionesFormularioFinalizadas(final Date fechaLimite) {

		final String sql = "delete from STT_FORMUL where SFR_FECFIN is not null and SFR_FECFIN < :fechaLimite ";

		final Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("fechaLimite", fechaLimite);
		return sqlQuery.executeUpdate();
	}

	@Override
	public int purgarSesionesFormularioNoFinalizadas(final Date fechaLimite) {

		final String sql = "delete from STT_FORMUL where SFR_FECFIN is null and SFR_FECINI < :fechaLimite ";

		final Query sqlQuery = entityManager.createNativeQuery(sql);
		sqlQuery.setParameter("fechaLimite", fechaLimite);
		return sqlQuery.executeUpdate();
	}

	@Override
	public int purgarFicherosHuerfanos() {
		final String sql = "delete from STT_FICPTR where FIC_BORRAR=1";
		final Query sqlQuery = entityManager.createNativeQuery(sql);
		return sqlQuery.executeUpdate();
	}

	@Override
	public List<DatosPersistenciaTramite> getTramitesPersistentesPendientesCaducar(final int pNumDias) {

		final String consulta = "from HTramite t where t.persistente = " + true + " and t.purgado=" + false
				+ " and t.fechaCaducidad is not null and t.fechaCaducidad between :fechaInicio and :fechaActual";

		final Date ahora = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(ahora);
		calendar.add(Calendar.DAY_OF_YEAR, pNumDias * ConstantesNumero.N_1);
		final Date fechaInicio = calendar.getTime();

		final Query query = entityManager.createQuery(consulta);
		query.setParameter("fechaInicio", fechaInicio);
		query.setParameter("fechaActual", ahora);

		final List<HTramite> tramites = query.getResultList();

		final List<DatosPersistenciaTramite> res = new ArrayList<>();
		for (final HTramite tramite : tramites) {
			res.add(HTramite.toModel(tramite));
		}
		return res;
	}

	@Override
	public int eliminarTramitesPurgados(final Date pFechaLimitePurga) {

		// Borramos log interno
		// NO HACE FALTA, SERA BORRADO POR PURGA DE ERRORES SIN SESION
		// TRAMITACION

		// Borramos tramite
		borrarCabeceraTramiteTramitesPurgados(pFechaLimitePurga);

		// Borramos los eventos auditoria antes de borrar las sesiones para que no de
		// error
		borrarEventoAuditoriaTramitesPurgados(pFechaLimitePurga);

		// Borramos sesiones
		return borrarSesionesTramitesPurgados(pFechaLimitePurga);

	}

	// ----------------- FUNCIONES AUXILIARES -----------------------------

	/**
	 * Construye filtro a aplicar para tramites pendientes de purga: finalizados, no
	 * persistentes sin finalizar y caducados (aplicando los días especificados para
	 * cada uno).
	 *
	 * @param pFinalizadosHasta
	 *                               Fecha tras su fecha de finalizacion tras el
	 *                               cual un tramite finalizado sera purgado.
	 * @param pSinFinalizarHasta
	 *                               Fecha tras su fecha de ultimo acceso tras el
	 *                               cual seran purgados los tramites no
	 *                               persistentes no finalizados
	 * @param pCaducadosHasta
	 *                               Fecha tras su fecha de caducidad tras el cual
	 *                               seran purgados los tramites persistentes
	 *                               caducados.
	 * @return Filtro sql a aplicar
	 */
	private String generarFiltroTramitesPendientesPurga(final Date pFinalizadosHasta, final Date pSinCaducidadHasta,
			final Date pSinFinalizarHasta, final Date pCaducadosHasta) {
		String filtroFinalizados = null;
		String filtroPSinFechaCaducidad = null;
		String filtroNPSinFinalizar = null;
		String filtroCaducados = null;

		// Filtro tramites finalizados
		if (pFinalizadosHasta != null) {
			filtroFinalizados = " ( TRP_ESTADO = '" + TypeEstadoTramite.FINALIZADO
					+ "' and (TRP_FECFIN < :fechaLimiteFinalizados or TRP_FECFIN is null) ) ";
		}
		// Filtro persistente sin finalizar y sin fecha caducidad
		if (pSinCaducidadHasta != null) {
			filtroPSinFechaCaducidad = " ( TRP_ESTADO <> '" + TypeEstadoTramite.FINALIZADO
					+ "' and TRP_PERSIS=1 and TRP_FECCAD is null and TRP_FECACC < :fechaLimiteSinCaducidad )";
		}
		// Filtro no persistente sin finalizar
		if (pSinFinalizarHasta != null) {
			filtroNPSinFinalizar = " ( TRP_ESTADO <> '" + TypeEstadoTramite.FINALIZADO
					+ "' and TRP_PERSIS=0 and TRP_FECACC < :fechaLimiteSinFinalizar )";
		}
		// Filtro caducados
		if (pCaducadosHasta != null) {
			filtroCaducados = " ( TRP_ESTADO <> '" + TypeEstadoTramite.FINALIZADO
					+ "' and TRP_PERSIS=1 and TRP_FECCAD < :fechaLimiteCaducados ) ";
		}

		// Construimos filtro where
		final StringBuffer sbFiltroWhere = new StringBuffer(ConstantesNumero.N500);
		// - No este purgado, ni marcado purgar, ni pendiente purga por pago
		sbFiltroWhere.append(" TRP_PURCHK=0 and TRP_PURGA=0 AND TRP_PURPAG=0 ");
		if (pFinalizadosHasta != null || pSinCaducidadHasta != null || pSinFinalizarHasta != null
				|| pCaducadosHasta != null) {
			sbFiltroWhere.append("and ( ");
			final String[] filtros = new String[] { filtroFinalizados, filtroPSinFechaCaducidad, filtroNPSinFinalizar,
					filtroCaducados };
			boolean primer = false;
			for (final String filtro : filtros) {
				if (filtro != null) {
					if (!primer) {
						primer = true;
					} else {
						sbFiltroWhere.append(" OR ");
					}
					sbFiltroWhere.append(filtro);
				}
			}
			sbFiltroWhere.append(") ");
		}

		final String sqlFiltroWherePendientesPurga = sbFiltroWhere.toString();
		return sqlFiltroWherePendientesPurga;
	}

	/**
	 * Borra eventos de auditorias de sesiones de tramites purgados.
	 *
	 * @param pFechaLimitePurga
	 *                              Fecha limite purga
	 * @return Número de auditorias borradas
	 */
	private int borrarEventoAuditoriaTramitesPurgados(final Date pFechaLimitePurga) {

		// Eliminamos sesiones por HQL
		final String deleteTramite = "delete from HEventoAuditoria e where e.sesionTramitacion in (select s from HSesionTramitacion s where s.fecha < :fechaLimite and s not in (select t.sesionTramitacion from HTramite t) )";
		final Query query = entityManager.createQuery(deleteTramite);
		query.setParameter("fechaLimite", pFechaLimitePurga);
		return query.executeUpdate();
	}

	/**
	 * Borra sesiones tramites purgados.
	 *
	 * @param pFechaLimitePurga
	 *                              Fecha limite purga
	 * @return Número de sesiones borradas
	 */
	private int borrarSesionesTramitesPurgados(final Date pFechaLimitePurga) {

		// Eliminamos sesiones por HQL
		final String deleteTramite = "delete from HSesionTramitacion s where s.fecha < :fechaLimite and s not in (select t.sesionTramitacion from HTramite t)";
		final Query query = entityManager.createQuery(deleteTramite);
		query.setParameter("fechaLimite", pFechaLimitePurga);
		return query.executeUpdate();
	}

	/**
	 * Borra cabecera tramite de tramites purgados.
	 *
	 * @param pFechaLimitePurga
	 *                              Fecha limite purga
	 */
	private void borrarCabeceraTramiteTramitesPurgados(final Date pFechaLimitePurga) {

		final String deleteTramite = "delete from HTramite where purgado = " + true
				+ " and fechaPurgado < :fechaPurgado ";
		final Query query = entityManager.createQuery(deleteTramite);
		query.setParameter("fechaPurgado", pFechaLimitePurga);
		query.executeUpdate();
	}

	/**
	 * Marca tramites como purgados los pendientes de purgar.
	 *
	 * @return Número de trámites marcados como purgados.
	 */
	private int marcarTramitesPurgados() {
		final String updateTramite = "update HTramite set purgado = " + true + ", fechaPurgado = :fechaPurgado "
				+ " where purgado = " + false + " and purgar = " + true;
		final Query query = entityManager.createQuery(updateTramite);
		query.setParameter("fechaPurgado", new Date());
		return query.executeUpdate();

	}

	/***
	 * Borra firmas de tramites marcados para purgar.
	 */
	private void borrarFirmasPurgaTramites() {
		final String filtroTramites = "select fir from HFirma fir where fir.documentoPersistente.paso.tramitePersistencia.purgar = "
				+ true + " and fir.documentoPersistente.paso.tramitePersistencia.purgado = " + false;
		final String deletePasos = "delete from HFirma firB where firB in (" + filtroTramites + ")";
		entityManager.createQuery(deletePasos).executeUpdate();
	}

	/**
	 * Borra documentos de tramites marcados para purgar.
	 */
	private void borrarDocumentosPurgaTramites() {
		final String filtroTramites = "select doc from HDocumento doc where doc.paso.tramitePersistencia.purgar = "
				+ true + " and doc.paso.tramitePersistencia.purgado = " + false;
		final String deleteDocumentos = "delete from HDocumento docB where docB in (" + filtroTramites + ")";
		entityManager.createQuery(deleteDocumentos).executeUpdate();
	}

	/**
	 * Borra pasos de tramites marcados para purgar.
	 */
	private void borrarPasosPurgaTramites() {
		final String filtroTramites = "select pas from HPaso pas where pas.tramitePersistencia.purgar = " + true
				+ " and pas.tramitePersistencia.purgado = " + false;
		final String deletePasos = "delete from HPaso pasB where pasB in (" + filtroTramites + ")";
		entityManager.createQuery(deletePasos).executeUpdate();
	}

	/**
	 * Marca ficheros para borrar de tramites marcados para purgar.
	 */
	private void marcarFicherosBorrarPurgaTramites() {
		final String hql = "update HFichero fic set fic.borrar = true where " + " fic.borrar = false and "
				+ " fic.codigoTramite in ( " + "   select tra.codigo from HTramite tra where tra.purgar = " + true
				+ " and tra.purgado = " + false + ")";
		entityManager.createQuery(hql).executeUpdate();
	}

}
