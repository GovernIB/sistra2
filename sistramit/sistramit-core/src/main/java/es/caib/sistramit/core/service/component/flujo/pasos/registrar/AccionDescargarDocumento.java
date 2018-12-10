package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.AccionPasoNoPermitidaException;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoAnexo;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoFormulario;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoPago;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite descargar documento en el paso Registrar.
 *
 * @author Indra
 *
 */
@Component("accionRtDescargarDocumento")
public final class AccionDescargarDocumento implements AccionPaso {

	/** Atributo dao de AccionObtenerDatosAnexo. */
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

		// Buscamos referencia fichero
		final DatosDocumento dd = pVariablesFlujo.getDocumento(idDocumento, instancia);

		ReferenciaFichero ref;
		switch (dd.getTipo()) {
		case FORMULARIO:
			ref = ((DatosDocumentoFormulario) dd).getPdf();
			break;
		case ANEXO:
			ref = ((DatosDocumentoAnexo) dd).getFichero();
			break;
		case PAGO:
			ref = ((DatosDocumentoPago) dd).getJustificantePago();
			break;
		default:
			throw new AccionPasoNoPermitidaException(
					"No existe documento registro con id: " + idDocumento + " - " + instanciaStr);
		}

		// Recuperamos datos fichero
		final DatosFicheroPersistencia fic = dao
				.recuperarFicheroPersistencia(new ReferenciaFichero(ref.getId(), ref.getClave()));

		// Devolvemos fichero
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("nombreFichero", fic.getNombre());
		rp.addParametroRetorno("datosFichero", fic.getContenido());
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

}
