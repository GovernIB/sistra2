package es.caib.sistramit.core.service.component.flujo.pasos.guardar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.registrar.UtilsPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite descargar firma documento en el paso Guardar.
 *
 * @author Indra
 *
 */
@Component("accionGuDescargarFirma")
public final class AccionDescargarFirma implements AccionPaso {

	/** Dao Flujo. */
	@Autowired
	private FlujoPasoDao dao;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// Recogemos parametros
		final String idDocumento = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "idDocumento", true);
		final String instanciaStr = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "instancia", false);
		final int instancia = UtilsFlujo.instanciaStrToInt(instanciaStr);
		final String firmante = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "firmante", true);

		// Buscamos referencia fichero
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);

		// Recupera referencia documento a firmar
		final ReferenciaFichero ficheroFirmar = UtilsPasoRegistrar.getInstance()
				.obtenerReferenciaFicheroFirmar(pVariablesFlujo, idDocumento, instancia);
		if (ficheroFirmar == null) {
			throw new AccionPasoNoPermitidaException(
					"No existe documento registro con id: " + idDocumento + " - " + instanciaStr);
		}

		// Recupera firma asociada al fichero
		final DocumentoPasoPersistencia docPersistencia = dao.obtenerDocumentoPersistencia(
				pVariablesFlujo.getIdSesionTramitacion(), dd.getIdPaso(), idDocumento, instancia);
		final FirmaDocumentoPersistencia fdp = docPersistencia.obtenerFirmaFichero(ficheroFirmar.getId(), firmante);
		if (fdp == null) {
			throw new AccionPasoNoPermitidaException("No existe firma documento registro con id: " + idDocumento + " - "
					+ instanciaStr + " para firmante " + firmante);
		}

		// Recuperamos datos firma
		final DatosFicheroPersistencia fic = dao.recuperarFicheroPersistencia(fdp.getFirma());

		// Devolvemos fichero
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("nombreFichero", fic.getNombre());
		rp.addParametroRetorno("datosFichero", fic.getContenido());
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

}
