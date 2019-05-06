package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.integracion.FirmaComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.RedireccionFirma;
import es.caib.sistramit.core.service.repository.dao.FlujoPasoDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;

/**
 * Acción que permite iniciar proceso firma documento en el paso Registrar.
 *
 * @author Indra
 *
 */
@Component("accionRtIniciarFirmaDocumento")
public final class AccionIniciarFirmaDocumento implements AccionPaso {

	/** Configuración. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Firma. */
	@Autowired
	private FirmaComponent firmaComponent;

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
		final String nifFirmante = (String) UtilsFlujo.recuperaParametroAccionPaso(pParametros, "firmante", true);

		// Validaciones
		UtilsPasoRegistrar.getInstance().validacionesFirmaDocumento(pDatosPaso, pVariablesFlujo, idDocumento, instancia,
				nifFirmante);

		// Buscamos datos firmante
		final Persona firmante = UtilsPasoRegistrar.getInstance().obtieneDatosFirmante(pVariablesFlujo, idDocumento,
				instancia, nifFirmante);

		// Envia fichero a firmar
		final RedireccionFirma res = enviarFicheroFirmar(pDatosPaso, pDefinicionTramite, pVariablesFlujo, idDocumento,
				instancia, firmante);

		// Almacena sesion firma en datos internos paso
		final DatosInternosPasoRegistrar dipa = (DatosInternosPasoRegistrar) pDatosPaso.internalData();
		dipa.guardarSesionFirma(idDocumento, instancia, res.getIdSesion());

		// Devolvemos url componente firma
		final RespuestaAccionPaso rp = new RespuestaAccionPaso();
		rp.addParametroRetorno("url", res.getUrl());
		final RespuestaEjecutarAccionPaso rep = new RespuestaEjecutarAccionPaso();
		rep.setRespuestaAccionPaso(rp);
		return rep;
	}

	/**
	 * Envia fichero a firmar
	 *
	 * @param pDatosPaso
	 *            Datos paso
	 * @param pDefinicionTramite
	 *            Definición trámite
	 * @param pVariablesFlujo
	 *            Variables flujo
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @param firmante
	 *            firmante
	 * @return id sesión firma y url redirección
	 */
	private RedireccionFirma enviarFicheroFirmar(final DatosPaso pDatosPaso,
			final DefinicionTramiteSTG pDefinicionTramite, final VariablesFlujo pVariablesFlujo,
			final String idDocumento, final int instancia, final Persona firmante) {

		// Recupera documento a firmar
		final ReferenciaFichero ref = UtilsPasoRegistrar.getInstance().obtenerReferenciaFicheroFirmar(pVariablesFlujo,
				idDocumento, instancia);
		final DatosFicheroPersistencia fic = dao
				.recuperarFicheroPersistencia(new ReferenciaFichero(ref.getId(), ref.getClave()));
		final byte[] fileContent = fic.getContenido();
		final String fileName = fic.getNombre();

		// Calcula url Callback
		final String urlCallBack = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_FIRMA_EXTERNO + "?idPaso=" + pDatosPaso.getIdPaso()
				+ "&idDocumento=" + idDocumento + "&instancia=" + instancia + "&firmante=" + firmante.getNif();

		// Invoca a componente para redirección firma
		final RedireccionFirma redireccionFirma = firmaComponent.redireccionFirmaExterna(
				pDefinicionTramite.getDefinicionVersion().getIdEntidad(), firmante, idDocumento + "-" + instancia,
				fileContent, fileName, urlCallBack, pVariablesFlujo.getIdioma());
		return redireccionFirma;
	}
}
