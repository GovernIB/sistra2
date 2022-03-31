package es.caib.sistramit.core.service.component.formulario.externo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.formulario.api.DatosInicioFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.FormularioPluginException;
import es.caib.sistra2.commons.plugins.formulario.api.IFormularioPlugin;
import es.caib.sistra2.commons.plugins.formulario.api.ParametroFormulario;
import es.caib.sistra2.commons.plugins.formulario.api.UsuarioInfo;
import es.caib.sistramit.core.api.exception.InicioFormularioExternoException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.ParametroAperturaFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Interfaz del controlador de formularios externo.
 *
 * Contiene la lógica del gestor de formularios externo..
 *
 * @author Indra
 */
@Component("controladorGestorFormulariosExterno")
public final class ControladorGestorFormulariosExternoImpl implements ControladorGestorFormulariosExterno {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Dao para acceso a bbdd. */
	@Autowired
	private FormularioDao dao;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public String iniciarSesion(final DatosInicioSesionFormulario difi) {

		// Almacena sesion en BBDD generando ticket
		final String ticket = dao.crearSesionGestorFormularios(difi);

		// Obtenemos datos conexión GFE a partir versión trámite
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(difi.getIdTramite(),
				difi.getVersionTramite(), difi.getIdioma());
		final String urlGestorFormulario = UtilsSTG.obtenerUrlGestorFormulariosExterno(defTramite,
				difi.getIdGestorFormulariosExterno());
		final String usrGestorFormulario = UtilsSTG.obtenerUsrGestorFormulariosExterno(defTramite,
				difi.getIdGestorFormulariosExterno());
		final String pwdGestorFormulario = UtilsSTG.obtenerPwdGestorFormulariosExterno(defTramite,
				difi.getIdGestorFormulariosExterno());

		// Info usuario
		UsuarioInfo usuarioInfo = null;
		if (difi.getInfoAutenticacion().getAutenticacion() == TypeAutenticacion.AUTENTICADO) {
			usuarioInfo = new UsuarioInfo();
			usuarioInfo.setNif(difi.getInfoAutenticacion().getNif());
			usuarioInfo.setNombre(difi.getInfoAutenticacion().getNombre());
			usuarioInfo.setApellido1(difi.getInfoAutenticacion().getApellido1());
			usuarioInfo.setApellido2(difi.getInfoAutenticacion().getApellido2());
		}

		// Parametros apertura
		final List<ParametroFormulario> paramsApertura = new ArrayList<>();
		if (difi.getParametros() != null && difi.getParametros().getParametros() != null) {
			for (final ParametroAperturaFormulario p : difi.getParametros().getParametros()) {
				final ParametroFormulario pa = new ParametroFormulario();
				pa.setCodigo(p.getCodigo());
				pa.setValor(p.getValor());
				paramsApertura.add(pa);
			}
		}

		// Url callback
		final String urlCallback = configuracionComponent
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO;

		// Datos inicio
		final DatosInicioFormulario datosInicio = new DatosInicioFormulario();
		datosInicio.setIdSesionFormulario(ticket);
		datosInicio.setEntidad(difi.getEntidad());
		datosInicio.setIdioma(difi.getIdioma());
		datosInicio.setIdFormularioExterno(difi.getIdFormularioExterno());
		datosInicio.setParametrosApertura(paramsApertura);
		datosInicio.setUsuario(usuarioInfo);
		datosInicio.setXmlDatosActuales(difi.getXmlDatosActuales());
		datosInicio.setUrlCallback(urlCallback);

		// Invocamos a GFE
		final IFormularioPlugin plgFormulario = obtenerPluginFormulario(difi.getEntidad());
		String urlRedireccion = null;
		try { //urlGestorFormulario = "http://localhost:8081/projectebaseexemple/api/externa/services";
			urlRedireccion = plgFormulario.invocarFormulario(difi.getIdGestorFormulariosExterno(), urlGestorFormulario,
					usrGestorFormulario, pwdGestorFormulario, datosInicio);
		} catch (final FormularioPluginException e) {
			throw new InicioFormularioExternoException("Error iniciant formulari en Gestor Formulari "
					+ difi.getIdGestorFormulariosExterno() + ": " + e.getMessage(), e);
		}

		// Retornamos url redireccion formulario
		return urlRedireccion;
	}

	@Override
	public DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(final String ticket) {
		// Recupera datos finalizacion
		final DatosFinalizacionFormulario dff = dao.obtenerDatosFinSesionGestorFormularios(ticket, true);
		return dff;
	}

	/**
	 * Obtiene plugin de formulario.
	 *
	 * @param entidadId
	 *                      id entidad
	 * @return plugin pago
	 */
	private IFormularioPlugin obtenerPluginFormulario(final String entidadId) {
		return (IFormularioPlugin) configuracionComponent.obtenerPluginEntidad(TypePluginEntidad.FORMULARIOS_EXTERNOS,
				entidadId);
	}

}
