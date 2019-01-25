package es.caib.sistramit.core.service.component.formulario.interno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.utils.CalculoDatosFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ConfiguracionFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValidacionesFormularioHelper;
import es.caib.sistramit.core.service.component.formulario.interno.utils.ValoresPosiblesFormularioHelper;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.formulario.DatosFinalizacionFormulario;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.interno.DatosFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DatosSesionFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.interno.DependenciaCampo;
import es.caib.sistramit.core.service.model.formulario.interno.InicializacionPagina;
import es.caib.sistramit.core.service.model.formulario.interno.PaginaFormularioData;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.repository.dao.FormularioDao;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Controlador de formularios internos.
 *
 * Contiene la lógica del gestor de formularios interno.
 *
 * @author Indra
 */
@Component("controladorGestorFormulariosInterno")
public final class ControladorGestorFormulariosInternoImpl implements ControladorGestorFormulariosInterno {

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Dao para acceso a bbdd. */
	@Autowired
	private FormularioDao dao;

	/** Helper para calculo de la configuracion de un campo. */
	@Autowired
	private ConfiguracionFormularioHelper configuracionFormularioHelper;

	/** Helper para calculo de valores posibles de un campo. */
	@Autowired
	private ValoresPosiblesFormularioHelper valoresPosiblesFormularioHelper;

	/** Helper para validaciones. */
	@Autowired
	private ValidacionesFormularioHelper validacionesFormularioHelper;

	/** Helper para calculo de datos. */
	@Autowired
	private CalculoDatosFormularioHelper calculoDatosFormularioHelper;

	// TODO BORRAR
	private final boolean SIMULAR = true;

	@Override
	public String iniciarSesion(final DatosInicioSesionFormulario difi) {
		// Almacena sesion en BBDD generando ticket
		final String ticket = dao.crearSesionGestorFormularios(difi);
		return ticket;

	}

	@Override
	public DatosSesionFormularioInterno cargarSesion(final String ticket) {
		// Obtenemos datos inicio de sesión
		final DatosInicioSesionFormulario dis = dao.obtenerDatosInicioSesionGestorFormularios(ticket);

		// Obtenemos definición del tramite
		final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(dis.getIdTramite(),
				dis.getVersionTramite(), dis.getIdioma());

		// Verificamos si ha variado la release
		if (defTramite.getDefinicionVersion().getRelease() != dis.getReleaseTramite()) {
			throw new ErrorConfiguracionException("Ha variado la release del trámite: " + dis.getReleaseTramite()
					+ " vs " + defTramite.getDefinicionVersion().getRelease());
		}

		// Generamos datos sesion en memoria
		final DatosSesionFormularioInterno datosSesion = new DatosSesionFormularioInterno();
		datosSesion.setIdFormulario(dis.getIdFormulario());
		datosSesion.setTicket(ticket);
		datosSesion.setDefinicionTramite(defTramite);
		datosSesion.setDatosInicioSesion(dis);
		datosSesion.setDebugEnabled(UtilsSTG.isDebugEnabled(defTramite));

		// Inicializamos datos formulario y los almacenamos en sesion
		final RFormularioTramite defFormulario = UtilsSTG.devuelveDefinicionFormulario(dis.getIdPaso(),
				dis.getIdFormulario(), defTramite);
		final DatosFormularioInterno datForm = inicializarConfiguracionFormulario(defFormulario,
				dis.getXmlDatosActuales());
		datosSesion.setDatosFormulario(datForm);

		// Devolvemos datos sesión inicializados
		return datosSesion;
	}

	@Override
	public DatosFinalizacionFormulario obtenerDatosFinalizacionFormulario(final String ticket) {

		// TODO BORRAR
		if (SIMULAR) {
			try {
				final DatosFinalizacionFormulario dff = new DatosFinalizacionFormulario();
				dff.setFechaFinalizacion(new Date());
				dff.setCancelado(false);
				dff.setXml(SimulacionFormularioMock.getDatosSimulados().getBytes("UTF-8"));
				dff.setPdf(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("formulario.pdf")));

				return dff;
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		// Recupera datos finalizacion
		final DatosFinalizacionFormulario dff = dao.obtenerDatosFinSesionGestorFormularios(ticket);
		return dff;
	}

	// TODO Pendiente resto funciones

	@Override
	public ResultadoGuardarPagina guardarPagina(final DatosSesionFormularioInterno datosSesion,
			final List<ValorCampo> valoresPagina, final String accionPersonalizada) {

		return null;
	}

	@Override
	public PaginaFormulario cargarPagina(final DatosSesionFormularioInterno datosSesion) {
		// TODO Auto-generated method stub
		return null;
	}

	// ---------------------------------------------------------------------------------------
	// Métodos utilidad
	// ---------------------------------------------------------------------------------------

	/**
	 * Inicializa configuración formulario.
	 *
	 * @param defForm
	 *            Definición formulario
	 * @param xmlDatosIniciales
	 *            xml datos iniciales
	 * @return Genera datos formulario interno
	 */
	private DatosFormularioInterno inicializarConfiguracionFormulario(final RFormularioTramite defForm,
			final byte[] xmlDatosIniciales) {

		// Datos formulario interno: configuracion y datos.
		final DatosFormularioInterno df = new DatosFormularioInterno();
		df.setIndicePaginaActual(ConstantesNumero.N1);

		// Parseamos datos iniciales
		final XmlFormulario xmlForm = UtilsFormulario.xmlToValores(xmlDatosIniciales);
		final List<ValorCampo> valoresCampo = xmlForm.getValores();

		// TODO Revisar inicializacion paginas:
		// - Si existe flujo páginas se deberán inicializar conforme se ejecute el
		// flujo.
		// - Ver gestión dependencias entre campos de distintas páginas
		// - Campos evaluables entre campos de distintas páginas

		// TODO De momento solo se permite 1 sola pagina
		if (defForm.getFormularioInterno().getPaginas().size() > ConstantesNumero.N1) {
			throw new ErrorConfiguracionException("No esta implementada ejecución multipágina");
		}

		// Inicializamos primera página
		final RPaginaFormulario defPagina = defForm.getFormularioInterno().getPaginas().get(0);
		final InicializacionPagina ip = inicializarPagina(defForm.getIdentificador(), defPagina, valoresCampo);

		// Añadimos pagina a datos formularo
		df.addPaginaFormulario(ip.getPagina());
		df.addDependenciasCampos(ip.getDependencias());

		// Marcamos en la configuracion los campos de la lista como evaluables
		// si aparecen como dependencia de otros
		for (final String idCampoEvaluable : df.getCamposEvaluables()) {
			final ConfiguracionCampo configuracionCampoEvaluable = df.getConfiguracionCampo(idCampoEvaluable);
			if (UtilsFormulario.esCampoOculto(configuracionCampoEvaluable)) {
				throw new ErrorConfiguracionException("Un campo oculto no puede aparecer como dependencia de otros");
			}
			configuracionCampoEvaluable.setEvaluar(TypeSiNo.SI);
		}

		return df;
	}

	/**
	 * Inicializa página del formulario (configuración y datos).
	 *
	 * @param idFormulario
	 *            id formulario
	 * @param defPagina
	 *            definición página
	 * @param valoresCampo
	 *            valores campo
	 * @return
	 */
	private InicializacionPagina inicializarPagina(final String idFormulario, final RPaginaFormulario defPagina,
			final List<ValorCampo> valoresCampo) {

		// Añadimos pagina
		final PaginaFormularioData paginaForm = new PaginaFormularioData(idFormulario);

		// Establecemos campos página
		final List<DependenciaCampo> dependenciasPagina = new ArrayList<>();
		for (final RComponente campoDef : UtilsFormulario.devuelveListaCampos(defPagina)) {

			// TODO VER MULTIPAGINA CUANDO SE HABILITEN VARIAS PAGINAS (VALOR DEPENDIENTE DE
			// CAMPOS DE OTRAS PAGINAS)

			// Calculamos valor inicial campo (si no tiene valor inicial, sera valor vacio)
			ValorCampo valorInicialCampo = UtilsFormulario.buscarValorCampo(valoresCampo, campoDef.getIdentificador());
			if (valorInicialCampo == null) {
				valorInicialCampo = UtilsFormulario.crearValorVacio(campoDef);
			}

			// Calculamos dependencias con otros campos
			final DependenciaCampo dependenciasCampo = UtilsFormulario.buscarDependenciasCampo(campoDef);

			// Calculamos configuracion campo
			final ConfiguracionCampo confCampo = configuracionFormularioHelper.obtenerConfiguracionCampo(campoDef);

			// Inicializamos campo: configuracion, valor inicial y dependencias
			paginaForm.inicializaCampo(confCampo, valorInicialCampo);
			dependenciasPagina.add(dependenciasCampo);
		}

		// Devolvemos pagina inicializada
		final InicializacionPagina ip = new InicializacionPagina();
		ip.setPagina(paginaForm);
		ip.setDependencias(dependenciasPagina);
		return ip;
	}

}
