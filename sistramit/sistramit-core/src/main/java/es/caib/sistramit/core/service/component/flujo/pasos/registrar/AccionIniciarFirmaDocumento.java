package es.caib.sistramit.core.service.component.flujo.pasos.registrar;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.firmacliente.api.FicheroAFirmar;
import es.caib.sistra2.commons.plugins.firmacliente.api.FirmaPluginException;
import es.caib.sistra2.commons.plugins.firmacliente.api.IFirmaPlugin;
import es.caib.sistra2.commons.plugins.firmacliente.api.InfoSesionFirma;
import es.caib.sistramit.core.api.exception.SesionFirmaClienteException;
import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.flujo.pasos.AccionPaso;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosFicheroPersistencia;
import es.caib.sistramit.core.service.model.flujo.DatosInternosPasoRegistrar;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaPaso;
import es.caib.sistramit.core.service.model.flujo.RedireccionFirma;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaEjecutarAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
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

		// Generamos sesion de firma en componente firma
		final IFirmaPlugin plgFirma = (IFirmaPlugin) configuracionComponent.obtenerPluginEntidad(
				TypePluginEntidad.FIRMA, pDefinicionTramite.getDefinicionVersion().getIdEntidad());

		// En funcion del documento obtiene la referencia del fichero a firmar
		final ReferenciaFichero ref = UtilsPasoRegistrar.getInstance().obtenerReferenciaFicheroFirmar(pVariablesFlujo,
				idDocumento, instancia);

		// Recuperamos datos fichero
		final DatosFicheroPersistencia fic = dao
				.recuperarFicheroPersistencia(new ReferenciaFichero(ref.getId(), ref.getClave()));

		// Crea sesion de firma
		final InfoSesionFirma infoSesionFirma = new InfoSesionFirma();
		infoSesionFirma.setEntidad(pDefinicionTramite.getDefinicionVersion().getIdEntidad());
		infoSesionFirma.setNif(firmante.getNif());
		infoSesionFirma.setNombreUsuario(firmante.getNombre());
		infoSesionFirma.setIdioma(pVariablesFlujo.getIdioma());
		String sf;
		try {
			sf = plgFirma.generarSesionFirma(infoSesionFirma);
		} catch (final FirmaPluginException e) {
			throw new SesionFirmaClienteException("Excepción al generar sesión firma: " + e.getMessage(), e);
		}

		// Añade fichero
		final FicheroAFirmar fichero = new FicheroAFirmar();
		fichero.setFichero(fic.getContenido());
		if (FilenameUtils.getExtension(fic.getNombre()).toLowerCase().endsWith(".pdf")) {
			fichero.setMimetypeFichero("application/pdf");
		} else {
			fichero.setMimetypeFichero("application/octet-stream");
		}
		fichero.setIdioma(pVariablesFlujo.getIdioma());
		fichero.setSignNumber(1);
		fichero.setNombreFichero(fic.getNombre());
		fichero.setRazon(fic.getNombre());
		fichero.setSignID(idDocumento + "-" + instancia);
		fichero.setSesion(sf);
		try {
			plgFirma.anyadirFicheroAFirmar(fichero);
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("idDocumento", idDocumento);
			lp.addPropiedad("instancia", instancia + "");
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepción al enviar fichero a firmar", lp);
		}

		// Iniciar sesion firma
		String urlRedireccion = null;
		try {
			urlRedireccion = plgFirma.iniciarSesionFirma(sf,
					configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
							+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_FIRMA_EXTERNO + "?idPaso="
							+ pDatosPaso.getIdPaso() + "&idDocumento=" + idDocumento + "&instancia=" + instancia
							+ "&firmante=" + firmante.getNif(),
					null);
		} catch (final FirmaPluginException e) {
			final ListaPropiedades lp = new ListaPropiedades();
			lp.addPropiedad("idDocumento", idDocumento);
			lp.addPropiedad("instancia", instancia + "");
			lp.addPropiedad("idSesionFirma", sf);
			throw new SesionFirmaClienteException("Excepción al iniciar sesión firma", lp);
		}

		final RedireccionFirma res = new RedireccionFirma();
		res.setIdSesion(sf);
		res.setUrl(urlRedireccion);
		return res;
	}

}
