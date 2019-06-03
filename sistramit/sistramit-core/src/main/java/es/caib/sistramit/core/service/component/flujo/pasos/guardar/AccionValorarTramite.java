package es.caib.sistramit.core.service.component.flujo.pasos.guardar;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetallePasoGuardar;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.api.model.system.types.TypeParametroEvento;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoGuardar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que realiza la encuesta de valoracion del tramite realizado. Para
 * almacenar la valoracion genera evento en auditoria.
 *
 * @author Indra
 *
 */
@Component("accionGuValorarTramite")
public final class AccionValorarTramite implements AccionPaso {

	/** Auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPaso, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Obtenemos datos internos paso guardar
		final DatosInternosPasoGuardar dipa = (DatosInternosPasoGuardar) pDatosPaso.internalData();

		// Obtenemos detalle paso
		final DetallePasoGuardar detallePaso = ((DetallePasoGuardar) dipa.getDetallePaso());

		// Controlamos que no este valorado
		if (detallePaso.getValoracion() == null) {
			throw new ErrorConfiguracionException("No está configurada la valoración en el trámite");
		}
		if (detallePaso.getValoracion().getValorado() == TypeSiNo.NO) {

			// Recuperamos parametros
			final String valoracion = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "valoracion", true);
			final String problemas = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "problemas", false);
			final String observaciones = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "observaciones",
					false);

			// Generamos auditoria manualmente
			final ListaPropiedades listaPropiedades = new ListaPropiedades();

			// NO ASOCIAMOS A SESION PARA QUE SEA ANONIMO Y TOCA ASIGNAR DATOS TRAMITE
			// MANUALMENTE
			// evento.setIdSesionTramitacion(pVariablesFlujo.getIdSesionTramitacion());
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_TRAMITE_ELECTRONICO.toString(),
					pVariablesFlujo.getIdTramite() + "-" + pVariablesFlujo.getVersionTramite());
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_SIA.toString(),
					pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getIdProcedimientoSIA());
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_PROCEDIMIENTO.toString(),
					pVariablesFlujo.getDatosTramiteCP().getProcedimiento().getIdentificador());
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_TRAMITE.toString(),
					pVariablesFlujo.getDatosTramiteCP().getIdentificador());
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_ES_SERVICIO.toString(),
					Boolean.toString(pVariablesFlujo.getDatosTramiteCP().getProcedimiento().isServicio()));

			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_PUNTUACION.toString(), valoracion);
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_PROBLEMAS.toString(), problemas);
			listaPropiedades.addPropiedad(TypeParametroEvento.VALORACION_OBSERVACIONES.toString(), observaciones);
			final EventoAuditoria evento = new EventoAuditoria();
			evento.setFecha(new Date());
			evento.setTipoEvento(TypeEvento.VALORACION_TRAMITE);

			evento.setDescripcion("Valoración trámite");
			evento.setPropiedadesEvento(listaPropiedades);
			auditoriaComponent.auditarEventoAplicacion(evento);

			// Actualizamos detalle para indicar que esta valorado
			((DetallePasoGuardar) dipa.getDetallePaso()).getValoracion().setValorado(TypeSiNo.SI);

		}

		// Devolvemos respuesta vacia
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;

	}
}
