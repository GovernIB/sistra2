package es.caib.sistramit.core.service.component.flujo.pasos.guardar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.registro.api.ResultadoJustificante;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeJustificante;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.component.flujo.ConstantesFlujo;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.RegistroComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosDocumentoJustificante;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Acción que permite descargar justificante en el paso Guardar.
 *
 * @author Indra
 *
 */
@Component("accionGuDescargarJustificante")
public final class AccionDescargarJustificante implements AccionPaso {

	/** Componente de registro. */
	@Autowired
	private RegistroComponent registroComponent;

	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracion;

	@Override
	public RespuestaEjecutarAccionPaso ejecutarAccionPaso(final DatosPaso pDatosPaso, final DatosPersistenciaPaso pDpp,
			final TypeAccionPaso pAccionPasoObtenerAnexo, final ParametrosAccionPaso pParametros,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo) {

		// TODO Pendiente gestion entrega presencial. De momento solo electronico

		// Obtenemos datos registro
		final DatosDocumentoJustificante ddj = (DatosDocumentoJustificante) pVariablesFlujo
				.getDocumento(ConstantesFlujo.ID_JUSTIFICANTE_REGISTRO, ConstantesNumero.N1);

		// Obtenemos justificante
		final ResultadoJustificante justif = registroComponent.obtenerJustificanteRegistro(
				pDefinicionTramite.getDefinicionVersion().getIdEntidad(), ddj.getNumeroRegistro(),
				pVariablesFlujo.isDebugEnabled());

		// Devolvemos fichero
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		if (justif.getContenido() != null) {
			rp.addParametroRetorno("nombreFichero", ddj.getNumeroRegistro() + ".pdf");
			rp.addParametroRetorno("datosFichero", justif.getContenido());
		} else {
			String urlJustificante = justif.getUrl();
			if (justif.getTipo() == TypeJustificante.CARPETA_CIUDADANA) {
				final RConfiguracionEntidad entidadInfo = configuracion
						.obtenerConfiguracionEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());
				final String urlCarpeta = UtilsSTG.obtenerLiteral(entidadInfo.getUrlCarpeta(),
						pVariablesFlujo.getIdioma(), true);
				urlJustificante = urlCarpeta + urlJustificante;
			}
			rp.addParametroRetorno("url", urlJustificante);
		}
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;

	}

}
