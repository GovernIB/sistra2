package es.caib.sistramit.core.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.flujo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.formulario.api.DatosRetornoFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.FormularioPluginException;
import es.caib.sistra2.commons.plugins.formulario.api.IFormularioPlugin;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionAutenticacion;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RGestorFormularioExterno;
import es.caib.sistramit.core.api.exception.TicketAccesoException;
import es.caib.sistramit.core.api.exception.TicketFormularioException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.SesionInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoRepresentante;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.integracion.AutenticacionComponent;
import es.caib.sistramit.core.service.component.integracion.CatalogoProcedimientosComponent;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.flujo.DatosPersistenciaTramite;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.types.TipoFinalizacionFormulario;
import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionUsuario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FlujoTramiteDao;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;
import es.caib.sistramit.core.service.repository.dao.PagoExternoDao;
import es.caib.sistramit.core.service.repository.dao.TicketCDCDao;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	/** Timeout tickets autenticación por defecto. */
	private static final int TIMEOUT_TICKET_DEFAULT = 30;

	/** Acceso configuración. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Acceso Componente Autenticacion. */
	@Autowired
	private AutenticacionComponent autenticacionComponent;

	/** Acceso Catalogo Procedimientos. */
	@Autowired
	private CatalogoProcedimientosComponent catalogoProcedimientosComponent;

	/** DAO Flujo tramite. */
	@Autowired
	private FlujoTramiteDao flujoTramiteDao;

	/** DAO Pago externo. */
	@Autowired
	private PagoExternoDao pagoExternoDao;

	/** Dao Formulario Externo. */
	@Autowired
	private FormularioDao formularioDao;

	/** DAO Ticket CDC. */
	@Autowired
	private TicketCDCDao ticketCDCDao;

	/** Acceso a persistencia. */
	@Autowired
	private FlujoTramiteDao dao;

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	@NegocioInterceptor
	public InfoLoginTramite obtenerInfoLoginTramite(final String codigoTramite, final int versionTramite,
			final String idTramiteCatalogo, final boolean servicioCatalogo, final String idioma) {
		return generarInfoLoginTramite(codigoTramite, versionTramite, idTramiteCatalogo, servicioCatalogo, idioma);
	}

	@Override
	@NegocioInterceptor
	public InfoLoginTramite obtenerInfoLoginTramite(final String idSesionTramitacion) {
		final DatosPersistenciaTramite dpt = flujoTramiteDao.obtenerTramitePersistencia(idSesionTramitacion);
		final InfoLoginTramite infoLogin = generarInfoLoginTramite(dpt.getIdTramite(), dpt.getVersionTramite(),
				dpt.getIdTramiteCP(), dpt.isServicioCP(), dpt.getIdioma());
		return infoLogin;
	}

	@Override
	@NegocioInterceptor
	public String iniciarSesionAutenticacion(final String idEntidad, final String lang,
			final List<TypeMetodoAutenticacion> authList, final TypeQAA qaa, final String urlCallback,
			final String urlCallbackError, final boolean debug) {
		final String urlAutenticacion = autenticacionComponent.iniciarSesionAutenticacion(idEntidad, lang, authList,
				qaa, urlCallback, urlCallbackError, debug);
		return urlAutenticacion;
	}

	@Override
	@NegocioInterceptor
	public String iniciarLogoutSesion(final String idEntidad, final String lang, final String urlCallback,
			final boolean debug) {
		final String urlAutenticacion = autenticacionComponent.iniciarSesionLogout(idEntidad, lang, urlCallback, debug);
		return urlAutenticacion;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketAutenticacionClave(final SesionInfo sesionInfo, final String ticket) {

		final DatosAutenticacionUsuario usuario = autenticacionComponent.validarTicketAutenticacion(ticket);

		final UsuarioAutenticadoInfo u = new UsuarioAutenticadoInfo();
		if (usuario.getMetodoAutenticacion() == TypeMetodoAutenticacion.ANONIMO) {
			u.setUsername(ConstantesSeguridad.ANONIMO_USER);
		} else {
			u.setUsername(usuario.getNif());
		}
		u.setNif(usuario.getNif());
		u.setNombre(usuario.getNombre());
		u.setApellido1(usuario.getApellido1());
		u.setApellido2(usuario.getApellido2());
		u.setEmail(usuario.getEmail());
		u.setAutenticacion(usuario.getAutenticacion());
		u.setMetodoAutenticacion(usuario.getMetodoAutenticacion());
		u.setQaa(usuario.getQaa());
		u.setSesionInfo(sesionInfo);

		if (usuario.getRepresentante() != null) {
			final UsuarioAutenticadoRepresentante representante = new UsuarioAutenticadoRepresentante();
			representante.setNif(usuario.getRepresentante().getNif());
			representante.setNombre(usuario.getRepresentante().getNombre());
			representante.setApellido1(usuario.getRepresentante().getApellido1());
			representante.setApellido2(usuario.getRepresentante().getApellido2());
			representante.setEmail(usuario.getRepresentante().getEmail());
			u.setRepresentante(representante);
		}

		return u;

	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarUsuarioAnonimo(final SesionInfo sesionInfo) {
		final UsuarioAutenticadoInfo u = new UsuarioAutenticadoInfo();
		u.setUsername(ConstantesSeguridad.ANONIMO_USER);
		u.setAutenticacion(TypeAutenticacion.ANONIMO);
		u.setSesionInfo(sesionInfo);
		return u;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketFormularioExterno(final SesionInfo sesionInfo, final String ticket) {

		// El ticket debe estar compuesto por: idSesionFormulario:ticketGFE
		final String params[] = ticket.split(":");
		if (params.length != 2) {
			throw new TicketFormularioException(
					"Format ticket formulari extern no és correcte (idSesionFormulario:ticketGFE): " + ticket);
		}

		// Id sesion formulario
		final String idSesionFormulario = params[0];

		// - Obtenemos datos sesion formulario
		final DatosInicioSesionFormulario dif = formularioDao
				.obtenerDatosInicioSesionGestorFormularios(idSesionFormulario, true);
		final String idGestorFormulariosExterno = dif.getIdGestorFormulariosExterno();

		// Obtenemos definicion trámite
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(dif.getIdTramite(),
				dif.getVersionTramite(), dif.getIdioma());

		// Obtenemos conf entidad
		final RConfiguracionEntidad confEntidad = configuracionComponent
				.obtenerConfiguracionEntidad(defTramite.getDefinicionVersion().getIdEntidad());

		// Obtenemos datos conexión GFE
		final RGestorFormularioExterno confGfe = UtilsSTG.obtenerConfiguracionGFE(confEntidad,
				idGestorFormulariosExterno);
		String urlGestorFormulario = confGfe.getUrl();
		String usrGestorFormulario = null;
		String pwdGestorFormulario = null;

		// Reemplazamos vbles area
		if (confGfe.getIdentificadorArea() != null) {
			urlGestorFormulario = UtilsFlujo.replaceVariablesArea(urlGestorFormulario, confEntidad,
					confGfe.getIdentificadorArea());
		}

		if (confGfe.getIdentificadorConfAutenticacion() != null) {
			final RConfiguracionAutenticacion confAut = configuracionComponent.obtenerConfiguracionAutenticacion(
					confGfe.getIdentificadorConfAutenticacion(), confGfe.getIdentificadorEntidad());
			usrGestorFormulario = confAut.getUsuario();
			pwdGestorFormulario = confAut.getPassword();
		}

		// Obtenemos plugin formularios
		final IFormularioPlugin plgFormularios = (IFormularioPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.FORMULARIOS_EXTERNOS, dif.getEntidad());

		// Invocamos plugin para obtener resultado
		DatosRetornoFormulario drf = null;
		try {
			drf = plgFormularios.obtenerResultadoFormulario(idGestorFormulariosExterno, urlGestorFormulario,
					usrGestorFormulario, pwdGestorFormulario, ticket);
		} catch (final FormularioPluginException e) {
			throw new TicketFormularioException("Error al obtenir resultat formulari: " + e.getMessage(), e);
		}

		// Almacenamos finalizacion formulario
		final DatosFinalizacionFormulario datosFinSesion = new DatosFinalizacionFormulario();
		datosFinSesion.setFechaFinalizacion(new Date());
		datosFinSesion.setEstadoFinalizacion(TipoFinalizacionFormulario.fromBoolean(drf.isCancelado()));
		datosFinSesion.setPdf(drf.getPdf());
		datosFinSesion.setXml(drf.getXml());
		datosFinSesion.setTicketExterno(ticket);
		formularioDao.finalizarSesionGestorFormularios(drf.getIdSesionFormulario(), datosFinSesion);

		// Retornamos autenticacion
		return dif.getInfoAutenticacion();
	}

	@Override
	@NegocioInterceptor
	public RetornoFormularioExterno obtenerTicketFormularioExterno(final String ticket) {
		final DatosInicioSesionFormulario dif = formularioDao.obtenerDatosInicioSesionGestorFormularios(ticket, false);
		final RetornoFormularioExterno res = new RetornoFormularioExterno();
		res.setTicket(dif.getIdSesionFormulario());
		res.setIdSesionTramitacion(dif.getIdSesionTramitacion());
		res.setIdFormulario(dif.getIdFormulario());
		res.setIdPaso(dif.getIdPaso());
		res.setUsuario(dif.getInfoAutenticacion());
		return res;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketPasarelaPagos(final SesionInfo sesionInfo, final String ticket) {
		final RetornoPago datosTicket = pagoExternoDao.consumirTicketPago(ticket);
		return datosTicket.getUsuario();
	}

	@Override
	@NegocioInterceptor
	public RetornoPago obtenerTicketPago(final String ticket) {
		return pagoExternoDao.obtenerTicketPago(ticket);
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketCarpetaCiudadana(final SesionInfo sesionInfo, final String ticket) {
		// Valida ticket
		final InfoTicketAcceso infoTicket = validarTicketAcceso(ticket);
		// Devolvemos usuario autenticado
		final UsuarioAutenticadoInfo usu = infoTicket.getUsuarioAutenticadoInfo();
		usu.setSesionInfo(sesionInfo);
		return usu;
	}

	@Override
	@NegocioInterceptor
	public UsuarioAutenticadoInfo validarTicketFH(final SesionInfo sesionInfo, final String ticket) {
		// Valida ticket
		final InfoTicketAcceso infoTicket = validarTicketAcceso(ticket);
		// FH
		UsuarioAutenticadoInfo fh = infoTicket.getUsuarioAutenticadoInfo();
		// Interesado
		PersonaDesglosado interesado = infoTicket.getInfoAccesoFH().getInteresado();
		// Representante
		PersonaDesglosado representante = infoTicket.getInfoAccesoFH().getRepresentante();
		// Devolvemos como usuario autenticado el interesado
		UsuarioAutenticadoInfo usu = new UsuarioAutenticadoInfo();
		usu.setSesionInfo(sesionInfo);
		usu.setNif(interesado.getNif());
		usu.setUsername(interesado.getNif());
		usu.setNombre(interesado.getNombre());
		usu.setApellido1(interesado.getApellido1());
		usu.setApellido2(interesado.getApellido2());
		usu.setAutenticacion(fh.getAutenticacion());
		usu.setMetodoAutenticacion(fh.getMetodoAutenticacion());
		usu.setQaa(fh.getQaa());
		if (representante != null) {
			UsuarioAutenticadoRepresentante rep = new UsuarioAutenticadoRepresentante();
			rep.setNif(representante.getNif());
			rep.setNombre(representante.getNombre());
			rep.setApellido1(representante.getApellido1());
			rep.setApellido2(representante.getApellido2());
			usu.setRepresentante(rep);
		}
		// FH
		usu.setFuncionarioHabilitado(new FuncionarioHabilitado(fh.getUsername(), fh.getNif(), fh.getNombre(), fh.getApellido1(), fh.getApellido2(), infoTicket.getInfoAccesoFH().getDir3FH(), infoTicket.getInfoAccesoFH().getIdActuacionFH()));
		return usu;
	}

	@Override
	public void verificarLimiteTramitacionTramite(String codigoTramite, int versionTramite, String idioma) {
		final DefinicionTramiteSTG defTramSTG = configuracionComponent.recuperarDefinicionTramite(codigoTramite,
				versionTramite, idioma);
		UtilsFlujo.controlLimitacionTramitacion(defTramSTG, dao);
	}

	@Override
	@NegocioInterceptor
	public InfoTicketAcceso obtenerTicketAcceso(final String ticket) {
		return ticketCDCDao.obtieneTicketAcceso(ticket);
	}

	@Override
	@NegocioInterceptor
	public List<TramiteIniciado> obtenerTramitacionesIniciadas(final String nif, final String tramite,
															   final int version, final String idTramiteCatalogo,
															   final boolean servicioCatalogo, String nifFH) {
		return flujoTramiteDao.obtenerTramitacionesIniciadas(nif, tramite, version, idTramiteCatalogo,
				servicioCatalogo, nifFH);
	}

	// ------------------------------------------------------------------------
	// FUNCIONES PRIVADAS
	// ------------------------------------------------------------------------
	private InfoLoginTramite generarInfoLoginTramite(final String codigoTramite, final int versionTramite,
			final String idTramiteCatalogo, final boolean servicioCatalogo, final String idioma) {
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(codigoTramite,
				versionTramite, idioma);
		final RConfiguracionEntidad entidad = configuracionComponent
				.obtenerConfiguracionEntidad(defTramite.getDefinicionVersion().getIdEntidad());
		final RAvisosEntidad avisosEntidad = configuracionComponent
				.obtenerAvisosEntidad(defTramite.getDefinicionVersion().getIdEntidad());
		final List<AvisoPlataforma> avisos = UtilsSTG.obtenerAvisosTramite(defTramite, avisosEntidad, idioma, false);
		boolean avisosBloqueantes = false;
		for (final AvisoPlataforma a : avisos) {
			if (a.isBloquearAcceso()) {
				avisosBloqueantes = true;
				break;
			}
		}

		final List<TypeAutenticacion> niveles = new ArrayList<>();
		if (defTramite.getDefinicionVersion().getPropiedades().isAutenticado()) {
			niveles.add(TypeAutenticacion.AUTENTICADO);
		}
		if (defTramite.getDefinicionVersion().getPropiedades().isNoAutenticado()) {
			niveles.add(TypeAutenticacion.ANONIMO);
		}

		// Obtenemos descripción trámite
		// En caso de que hayan avisos bloqueantes, no accedemos para no enmascarar error acceso a CP
		String descripcionTramite = "";
		if (!avisosBloqueantes) {
			final DefinicionTramiteCP defTramiteCP = catalogoProcedimientosComponent
					.obtenerDefinicionTramite(entidad.getIdentificador(), idTramiteCatalogo, servicioCatalogo, idioma);
			descripcionTramite = defTramiteCP.getDescripcion();
		}

		final InfoLoginTramite res = new InfoLoginTramite();
		res.setIdTramite(codigoTramite);
		res.setVersion(versionTramite);
		res.setIdioma(idioma);
		res.setTitulo(descripcionTramite);
		res.setNiveles(niveles);
		final Integer nivelSeguridadAutenticado = defTramite.getDefinicionVersion().getPropiedades().getNivelSeguridadAutenticado();
		res.setMetodosAutenticado(UtilsSTG.obtenerMetodosAutenticacionNivelSeguridadAutenticado(nivelSeguridadAutenticado));
		res.setQaa(UtilsSTG.obtenerQAANivelSeguridadAutenticado(nivelSeguridadAutenticado));
		res.setEntidad(UtilsFlujo.detalleTramiteEntidad(entidad, idioma, configuracionComponent));
		res.setAvisos(avisos);
		res.setBloquear(avisosBloqueantes);
		res.setDebug(UtilsSTG.isDebugEnabled(defTramite));
		return res;
	}

	/**
	 * Valida ticket acceso
	 * @param ticket Ticket
	 * @return Info ticket acceso
	 */
	private InfoTicketAcceso validarTicketAcceso(String ticket) {
		// Recuperamos info ticket
		final InfoTicketAcceso infoTicket = ticketCDCDao.obtieneTicketAcceso(ticket);
		// Verificamos que no ha sido usado y que no se ha cumplido timeout
		if (infoTicket.isUsado()) {
			throw new TicketAccesoException("Ticket ja ha estat utilitzat: " + ticket);
		}
		int secsTimeout = TIMEOUT_TICKET_DEFAULT;
		final String secsTimeoutStr = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.TIMEOUT_TICKET);
		if (StringUtils.isBlank(secsTimeoutStr)) {
			log.warn("No está configurada la propiedad " + TypePropiedadConfiguracion.TIMEOUT_TICKET.toString());
		}
		try {
			secsTimeout = Integer.parseInt(secsTimeoutStr);
			if (secsTimeout <= 0) {
				log.warn("La propiedad " + TypePropiedadConfiguracion.TIMEOUT_TICKET.toString()
						+ " no tiene un valor válido: " + secsTimeoutStr);
				secsTimeout = TIMEOUT_TICKET_DEFAULT;
			}
		} catch (final NumberFormatException e) {
			log.warn("La propietat " + TypePropiedadConfiguracion.TIMEOUT_TICKET.toString() + " no té un valor vàlid: "
					+ secsTimeoutStr);
			secsTimeout = TIMEOUT_TICKET_DEFAULT;
		}
		final Date dateNow = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(dateNow);
		cal.add(Calendar.SECOND, secsTimeout);
		final Date dateMax = cal.getTime();
		if (dateNow.after(dateMax)) {
			throw new TicketAccesoException("Ticket ha expirat: " + ticket);
		}
		// Marcamos ticket como usado
		ticketCDCDao.consumirTicketAcceso(ticket);
		return infoTicket;
	}


}
