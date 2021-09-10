package es.caib.sistrages.core.service.migracion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;
import es.caib.sistrages.core.api.model.comun.migracion.ConstantesMigracion;
import es.caib.sistrages.core.api.model.comun.migracion.ErrorMigracion;
import es.caib.sistrages.core.api.model.comun.migracion.TypeErrorMigracion;
import es.caib.sistrages.core.api.model.migracion.ComponSistra;
import es.caib.sistrages.core.api.model.migracion.DocumSistra;
import es.caib.sistrages.core.api.model.migracion.FormulSistra;
import es.caib.sistrages.core.api.model.migracion.PantalSistra;
import es.caib.sistrages.core.api.model.migracion.TraverSistra;
import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeCampoIndexado;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeEtiqueta;
import es.caib.sistrages.core.api.model.types.TypeFormularioGestor;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypePresentacion;
import es.caib.sistrages.core.api.model.types.TypeSeparadorNumero;
import es.caib.sistrages.core.api.model.types.TypeTamanyo;
import es.caib.sistrages.core.api.service.migracion.MigracionService;
import es.caib.sistrages.core.api.util.UtilDisenyo;
import es.caib.sistrages.core.interceptor.NegocioInterceptor;
import es.caib.sistrages.core.service.component.TramiteComponent;
import es.caib.sistrages.core.service.component.literales.Literales;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.TramiteDao;
import es.caib.sistrages.core.service.repository.dao.TramitePasoDao;
import es.caib.sistrages.core.service.repository.dao.migracion.MigracionDao;

/**
 * La clase MigracionServiceImpl.
 */
@Service
@Transactional
public class MigracionServiceImpl implements MigracionService {

	private static final Long CERO = 0L;
	private static final Long UNO = 1L;
	/**
	 * Constante LOG.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MigracionServiceImpl.class);

	@Autowired
	MigracionDao migracionDao;

	@Autowired
	TramiteDao tramiteDao;

	@Autowired
	TramitePasoDao tramitePasoDao;

	@Autowired
	TramiteComponent tramiteComponent;

	@Autowired
	FormularioInternoDao formIntDao;

	@Autowired
	Literales literales;

	@Override
	@NegocioInterceptor
	public List<Tramite> getTramiteSistra() {
		return migracionDao.getTramiteSistra();
	}

	@Override
	@NegocioInterceptor
	public List<TramiteVersion> getTramiteVersionSistra(final Long pIdTramite) {
		return migracionDao.getTramiteVersionSistra(pIdTramite);
	}

	@Override
	@NegocioInterceptor
	public List<ErrorMigracion> migrarTramiteVersion(final Long pIdTramiteSistra, final int pNumVersionSistra,
			final Long pIdTramite, final int pNumVersion, final Map<String, Object> pParams) {
		final List<ErrorMigracion> listaErrores = new ArrayList<>();

		// recuperamos datos de sistra
		LOG.info("inicio: recuperamos datos de sistra");
		final TraverSistra traverSistra = recuperaDatosSistra(pIdTramiteSistra, pNumVersionSistra);
		LOG.info("fin: recuperamos datos de sistra");

		// recuperamos idioma del usuario
		String idioma = "ca";
		if (pParams != null) {
			idioma = (String) pParams.get(ConstantesMigracion.IDIOMA);
		}

		if (traverSistra != null) {

			boolean unificarPantallas = false;
			if (pParams != null) {
				pParams.put(ConstantesMigracion.IDIOMASPORDEFECTO, traverSistra.getTrvIdisop().replaceAll(",", ";"));

				if (pParams.get(ConstantesMigracion.UNIFICAR_PANTALLAS) != null) {
					unificarPantallas = ((Boolean) pParams.get(ConstantesMigracion.UNIFICAR_PANTALLAS)).booleanValue();
				}
			}

			LOG.info("inicio: createTramiteVersion");
			// creamos tramite version
			final TramiteVersion tramiteVersion = createTramiteVersion(pIdTramite, pNumVersion, pParams);
			LOG.info("fin: createTramiteVersion");

			updatePropiedades(traverSistra, tramiteVersion);

			// debe saber
			updateDebeSaber(traverSistra, tramiteVersion);

			// registrar
			updateRegistrar(traverSistra, tramiteVersion);

			// actualizamos tramite
			tramiteDao.updateTramiteVersion(tramiteVersion);

			LOG.info("inicio: createAnexos");
			// Guardamos anexos
			createAnexos(traverSistra.getListaDocumSistra(), tramiteVersion, idioma, listaErrores);
			LOG.info("fin: createAnexos");

			LOG.info("inicio: createTasas");
			// Guardamos tasas
			createTasas(traverSistra.getListaDocumSistra(), tramiteVersion, idioma, listaErrores);
			LOG.info("fin: createTasas");

			LOG.info("inicio: createFormularios");
			// Guardamos Formularios
			// recuperamos idioma para mostrar los errores si los hubiera
			createFormularios(traverSistra.getListaDocumSistra(), tramiteVersion, unificarPantallas, idioma,
					listaErrores);
			LOG.info("fin: createFormularios");

		} else {
			// no hay datos que importar
			final ErrorMigracion error = new ErrorMigracion(null, literales.getLiteral("migracion", "noData", idioma),
					TypeErrorMigracion.INFO);
			listaErrores.add(error);
		}

		return listaErrores;
	}

	/**
	 * Actualiza registrar.
	 *
	 * @param traverSistra   traversistra
	 * @param tramiteVersion tramiteversion
	 */
	private void updateRegistrar(final TraverSistra traverSistra, final TramiteVersion tramiteVersion) {
		boolean modificado = false;

		final TramitePasoRegistrar pasoRegistrar = tramiteVersion.getPasoRegistrar();
		if (pasoRegistrar != null) {

			if (traverSistra.getScriptRepresentante() != null) {
				final Script scriptRepresentante = new Script();
				scriptRepresentante.setContenido(traverSistra.getScriptRepresentante());
				pasoRegistrar.setScriptRepresentante(scriptRepresentante);
				pasoRegistrar.setAdmiteRepresentacion(true);
				modificado = true;
			}

			if (traverSistra.getScriptPresentador() != null) {
				final Script scriptPresentador = new Script();
				scriptPresentador.setContenido(traverSistra.getScriptPresentador());
				pasoRegistrar.setScriptPresentador(scriptPresentador);
				modificado = true;
			}

			if (traverSistra.getScriptValidarRegistrar() != null) {
				final Script scriptValidarRegistrar = new Script();
				scriptValidarRegistrar.setContenido(traverSistra.getScriptValidarRegistrar());
				pasoRegistrar.setScriptValidarRegistrar(scriptValidarRegistrar);
				modificado = true;
			}

			if (traverSistra.getInstruccionesTramitacion() != null) {
				pasoRegistrar.setInstruccionesFinTramitacion(traverSistra.getInstruccionesTramitacion());
				modificado = true;
			}

			if (traverSistra.getInstruccionesEntregaPresencial() != null) {
				pasoRegistrar.setInstruccionesPresentacion(traverSistra.getInstruccionesEntregaPresencial());
				modificado = true;
			}
		}

		if (modificado) {
			tramitePasoDao.updateTramitePaso(pasoRegistrar);
		}
	}

	/**
	 * Actualiza debe saber.
	 *
	 * @param traverSistra   traversistra
	 * @param tramiteVersion tramiteversion
	 */
	private void updateDebeSaber(final TraverSistra traverSistra, final TramiteVersion tramiteVersion) {
		if (traverSistra.getInstruccionesIniciales() != null) {
			final TramitePasoDebeSaber pasoDebeSaber = tramiteVersion.getPasoDebeSaber();
			if (pasoDebeSaber != null) {
				pasoDebeSaber.setInstruccionesIniciales(traverSistra.getInstruccionesIniciales());

				tramitePasoDao.updateTramitePaso(pasoDebeSaber);
			}
		}
	}

	/**
	 * Actualiza propiedades.
	 *
	 * @param traverSistra   traversistra
	 * @param tramiteVersion tramiteversion
	 */
	private void updatePropiedades(final TraverSistra traverSistra, final TramiteVersion tramiteVersion) {

		// scriptPersonalizacion
		tramiteVersion.setScriptPersonalizacion(createScript(traverSistra.getScriptPersonalizacion()));

		tramiteVersion.setAutenticado(traverSistra.isAutenticado());
		tramiteVersion.setNoAutenticado(traverSistra.isNoAutenticado());

	}

	/**
	 * Crea los formularios.
	 *
	 * @param listaDocumSistra  lista de documtos de sistra
	 * @param tramiteVersion    tramite version
	 * @param unificarPantallas indica si se unifican las pantallas de los
	 *                          formularios en una sola
	 * @param pIdioma           idioma para mostrar los errores
	 * @param listaErrores      lista de errores
	 */
	private void createFormularios(final List<DocumSistra> listaDocumSistra, final TramiteVersion tramiteVersion,
			final boolean unificarPantallas, final String pIdioma, final List<ErrorMigracion> listaErrores) {
		// filtramos de la lista los de tipo formulario
		final List<DocumSistra> listaFormularios = listaDocumSistra.stream()
				.filter(formulario -> ConstantesMigracion.DOCUM_TIPO_FORMULARIO.equals(formulario.getTipo()))
				.collect(Collectors.toList());

		if (listaFormularios != null && !listaFormularios.isEmpty()) {
			final TramitePasoRellenar pasoRellenar = tramiteVersion.getPasoRellenar();

			if (pasoRellenar != null) {
				for (final DocumSistra documSistra : listaFormularios) {
					final FormularioTramite formulario = tramiteComponent.createFormularioTramiteDefault();

					if (documSistra.getIdenti().length() > 20) {
						final ErrorMigracion error2 = errorMigracion(formulario.getIdentificador(), null,
								"migracion.elemento.formulario.disenyoFormulario.identificador.formulario.demasiadolargo",
								pIdioma);
						error2.setTipo(TypeErrorMigracion.WARNING);
						listaErrores.add(error2);
						formulario.setIdentificador(documSistra.getIdenti().substring(0, 20).toUpperCase());
					} else {
						formulario.setIdentificador(documSistra.getIdenti().toUpperCase());
					}
					formulario.setDescripcion(documSistra.getDescripcion());
					formulario.setTipoFormulario(TypeFormularioGestor.INTERNO);

					formulario.setObligatoriedad(TypeFormularioObligatoriedad.fromString(documSistra.getObligatorio()));

					formulario.setScriptObligatoriedad(createScript(documSistra.getScriptObligatorio()));

					formulario.setDebeFirmarse("S".equals(documSistra.getFirmar()));

					formulario.setScriptFirma(createScript(documSistra.getScriptEstablecerFirmantes()));

					formulario.setScriptDatosIniciales(createScript(documSistra.getScriptDatosIniciales()));

					formulario.setScriptParametros(createScript(documSistra.getScriptParametros()));

					formulario.setScriptRetorno(createScript(documSistra.getScriptPostGuardar()));

					final FormularioTramite formularioAlta = tramiteComponent.addFormularioTramite(formulario,
							pasoRellenar.getCodigo());

					// creamos el diseño de formulario
					LOG.info("inicio: createDisenyoFormulario");
					createDisenyoFormulario(documSistra.getFormulario(), formularioAlta.getIdFormularioInterno(),
							tramiteVersion.getIdiomasSoportados(), unificarPantallas, pIdioma,
							new String[] { documSistra.getIdenti() }, listaErrores);
					LOG.info("fin: createDisenyoFormulario");
					//
				}
			}
		}
	}

	/**
	 * Crea el disenyo de formulario.
	 *
	 * @param formulSistra       formulario de sistra
	 * @param idFormulario       id. formulario
	 * @param pIdiomasSoportados idiomas soportados
	 * @param unificarPantallas  indica si se unifican las pantallas de los
	 *                           formularios en una sola
	 * @param pIdioma            idioma para mostrar los errores
	 * @param pOpciones          opcion para el error
	 * @param listaErrores       lista de errores
	 */
	private void createDisenyoFormulario(final FormulSistra formulSistra, final Long idFormulario,
			final String pIdiomasSoportados, final boolean unificarPantallas, final String pIdioma,
			final String[] pOpciones, final List<ErrorMigracion> listaErrores) {
		final DisenyoFormulario disenyoFormulario = formIntDao.getFormularioPaginasById(idFormulario);

		// el diseño se crea con una pagina por defecto
		if (formulSistra != null && disenyoFormulario != null && !disenyoFormulario.getPaginas().isEmpty()) {
			PaginaFormulario pagina = disenyoFormulario.getPaginas().get(0);

			// recorremos las paginas del formulario de sistra
			//for (final PantalSistra paginaSistra : formulSistra.getPaginas()) {
			for (int i = 0 ; i < formulSistra.getPaginas().size(); i++) {
				final PantalSistra paginaSistra = formulSistra.getPaginas().get(i);
				pagina.setIdentificador("P"+(i+1));
				if (paginaSistra.getPanExpres() != null) {
					pagina.setScriptNavegacion(
							createScript("/* TODO: Revisar script" + System.getProperty("line.separator")
									+ paginaSistra.getPanExpres() + System.getProperty("line.separator") + "*/"));
					formIntDao.updatePagina(pagina);
				}

				// si unificamos pantallas creamos una seccion por cada pagina
				if (unificarPantallas) {
					final List<ComponSistra> listaComponentesAux = createSeccionPorPagina(
							formulSistra.getPaginas().indexOf(paginaSistra) + 1, pIdiomasSoportados);

					createDisenyoFormularioComponentes(pagina, listaComponentesAux, pIdioma,
							ArrayUtils.add(pOpciones, "Pg. " + pagina.getOrden()), listaErrores);
				} else {
					if (paginaSistra.getPanUltima() != null && paginaSistra.getPanUltima() == 1) {
						pagina.setPaginaFinal(true);
					} else {
						pagina.setPaginaFinal(false);
					}

					formIntDao.updatePagina(pagina);
				}

				// para cada pagina creamos sus componentes
				LOG.info("inicio: createDisenyoFormularioComponentes pag." + pagina.getOrden());
				createDisenyoFormularioComponentes(pagina, paginaSistra.getComponentes(), pIdioma,
						ArrayUtils.add(pOpciones, "Pg. " + pagina.getOrden()), listaErrores);
				LOG.info("fin: createDisenyoFormularioComponentes pag." + pagina.getOrden());

				// gestion para crear la pagina nueva
				if (!unificarPantallas && disenyoFormulario.getPaginas().size() < formulSistra.getPaginas().size()) {

					final PaginaFormulario paginaNueva = new PaginaFormulario();
					paginaNueva.setOrden(disenyoFormulario.getPaginas().size() + 1);
					paginaNueva.setIdentificador("P" + "P"+(i+2));
					paginaNueva.setCodigo(formIntDao.addPagina(idFormulario, paginaNueva));
					disenyoFormulario.getPaginas().add(paginaNueva);

					pagina = paginaNueva;
				}
			}
		}
	}

	/**
	 * Crea una seccion para cada pagina cuando se unifica las pantallas.
	 *
	 * @param pNPagina           num. pagina a mostrar
	 * @param pIdiomasSoportados idiomas soportados
	 * @return seccion
	 */
	private List<ComponSistra> createSeccionPorPagina(final int pNPagina, final String pIdiomasSoportados) {
		final List<ComponSistra> listaAux = new ArrayList<>();
		final ComponSistra seccionAux = new ComponSistra();
		seccionAux.setComType("seccion");
		seccionAux.setComSeclet("P");
		final Literal literalAux = new Literal();

		for (final String idiomaAux : pIdiomasSoportados.split(";")) {
			final Traduccion traduccionAux = new Traduccion(idiomaAux,
					literales.getLiteral("migracion", "pagina", idiomaAux) + " " + pNPagina);
			literalAux.add(traduccionAux);

		}
		seccionAux.setTexto(literalAux);
		listaAux.add(seccionAux);
		return listaAux;
	}

	/**
	 * Crea disenyo formulario componentes.
	 *
	 * @param pagina                 pagina
	 * @param listaComponentesSistra lista componentes sistra
	 * @param pIdioma                idioma
	 * @param pOpciones              opciones
	 * @param listaErrores           lista errores
	 */
	private void createDisenyoFormularioComponentes(final PaginaFormulario pagina,
			final List<ComponSistra> listaComponentesSistra, final String pIdioma, final String[] pOpciones,
			final List<ErrorMigracion> listaErrores) {
		LineaComponentesFormulario lineaComponente = null;
		ComponenteFormulario componente = null;

		for (final ComponSistra componenteSistra : listaComponentesSistra) {

			final String comType = componenteSistra.getComType();
			if ("seccion".equals(comType)) {
				lineaComponente = (LineaComponentesFormulario) insertaLineaComponenteBloque(pagina,
						TypeObjetoFormulario.SECCION);
				componente = lineaComponente.getComponentes().get(0);
				if (!StringUtils.isEmpty(componenteSistra.getComSeclet())) {
					((ComponenteFormularioSeccion) componente).setLetra(componenteSistra.getComSeclet());
				}
				if (componenteSistra.getTexto() != null) {
					componente.setTexto(componenteSistra.getTexto());
				}
				formIntDao.updateComponente(componente);
			} else if ("label".equals(comType)) {
				lineaComponente = (LineaComponentesFormulario) insertaLineaComponenteBloque(pagina,
						TypeObjetoFormulario.ETIQUETA);
				componente = lineaComponente.getComponentes().get(0);
				if (componenteSistra.getComNomlog() != null) {
					componente.setIdComponente(componenteSistra.getComNomlog().toUpperCase());
				}
				if (componenteSistra.getTexto() != null) {
					componente.setTexto(componenteSistra.getTexto());
				}
				if (componenteSistra.getComLbltipo() != null) {
					TypeEtiqueta tipoEtiqueta = null;
					if ("IN".equals(componenteSistra.getComLbltipo())) {
						tipoEtiqueta = TypeEtiqueta.INFO;
					} else if ("AL".equals(componenteSistra.getComLbltipo())) {
						tipoEtiqueta = TypeEtiqueta.WARNING;
					} else if ("ER".equals(componenteSistra.getComLbltipo())) {
						tipoEtiqueta = TypeEtiqueta.ERROR;
					} else if ("NO".equals(componenteSistra.getComLbltipo())) {
						tipoEtiqueta = TypeEtiqueta.ETIQUETA;
					}

					((ComponenteFormularioEtiqueta) componente).setTipoEtiqueta(tipoEtiqueta);
				}
				formIntDao.updateComponente(componente);
			} else if ("textbox".equals(comType)) {
				TypeObjetoFormulario tipo;
				if (componenteSistra.getComOculto() != null && componenteSistra.getComOculto().compareTo(1l) == 0) {
					tipo = TypeObjetoFormulario.CAMPO_OCULTO;

					// Comprueba que no esté relleno el script de validacion
					if (componenteSistra.getComExpval() != null) {
						final ErrorMigracion error2 = errorMigracion(componente.getIdComponente(), pOpciones,
								"elemento.formulario.disenyoFormulario.pagina.elemento.ocultoconscriptvalidacion",
								pIdioma);
						error2.setTipo(TypeErrorMigracion.WARNING);
						listaErrores.add(error2);
					}
				} else {
					tipo = TypeObjetoFormulario.CAMPO_TEXTO;
				}

				lineaComponente = createComponent(pagina, lineaComponente, componenteSistra, tipo, pIdioma, pOpciones,
						listaErrores);

			} else if ("combobox".equals(comType) || "radiobutton".equals(comType) || "listbox".equals(comType)) {
				lineaComponente = createComponent(pagina, lineaComponente, componenteSistra,
						TypeObjetoFormulario.SELECTOR, pIdioma, pOpciones, listaErrores);
			} else if ("checkbox".equals(comType)) {
				lineaComponente = createComponent(pagina, lineaComponente, componenteSistra,
						TypeObjetoFormulario.CHECKBOX, pIdioma, pOpciones, listaErrores);
			} else {
				// tipo no soportado
				String id = null;
				if (componenteSistra.getComNomlog() != null) {
					id = componenteSistra.getComNomlog().toUpperCase();
				} else {
					id = "-";
				}

				final ErrorMigracion error = errorMigracion(id, pOpciones,
						"elemento.formulario.disenyoFormulario.pagina.elemento", pIdioma);
				error.setTipo(TypeErrorMigracion.WARNING);
				listaErrores.add(error);
			}
		}

	}

	/**
	 * Crea component.
	 *
	 * @param pagina           pagina
	 * @param lineaComponente  linea componente
	 * @param componenteSistra componente sistra
	 * @param tipoObjetoForm   tipo objeto form
	 * @param pIdioma          idioma
	 * @param pOpciones        opciones
	 * @param listaErrores     lista errores
	 * @return linea componente formulario
	 */
	private LineaComponentesFormulario createComponent(final PaginaFormulario pagina,
			LineaComponentesFormulario lineaComponente, final ComponSistra componenteSistra,
			final TypeObjetoFormulario tipoObjetoForm, final String pIdioma, final String[] pOpciones,
			final List<ErrorMigracion> listaErrores) {

		// miramos si hay que insertar nueva linea
		if (lineaComponente == null || !lineaComponente.cabeComponente(componenteSistra.getComColspn().intValue())
				|| componenteSistra.getComPosici() != 1) {
			lineaComponente = (LineaComponentesFormulario) insertaLineaComponenteBloque(pagina,
					TypeObjetoFormulario.LINEA);
		}

		final ComponenteFormulario componente = (ComponenteFormulario) insertaCampo(pagina, lineaComponente,
				tipoObjetoForm);

		if (componenteSistra.getComNomlog() != null) {
			componente.setIdComponente(componenteSistra.getComNomlog().toUpperCase());
		}

		// Solo si es no oculto, se puede setear lo siguiente.
		if (isNotOculto(componenteSistra)) {
			if (componenteSistra.getTexto() != null) {
				componente.setTexto(componenteSistra.getTexto());
			}

			if (componenteSistra.getAyuda() != null) {
				componente.setAyuda(componenteSistra.getAyuda());
			}

			if (componenteSistra.getComColspn() != null) {
				componente.setNumColumnas(componenteSistra.getComColspn().intValue());
			}

			componente.setNoMostrarTexto(UNO.equals(componenteSistra.getComSinetq()));

			if (componenteSistra.getComAlineacion() != null) {
				componente.setAlineacionTexto(TypeAlineacionTexto.fromString(componenteSistra.getComAlineacion()));
			}
		}

		final String comType = componenteSistra.getComType();
		if ("combobox".equals(comType)) {
			final ComponenteFormularioCampoSelector cCombobox = (ComponenteFormularioCampoSelector) componente;

			cCombobox.setTipoCampoIndexado(TypeCampoIndexado.DESPLEGABLE);

			cCombobox.setObligatorio(UNO.equals(componenteSistra.getComObliga()));

			cCombobox.setIndiceAlfabetico(UNO.equals(componenteSistra.getComLdeind()));

			if (componenteSistra.getListaValores() != null && !componenteSistra.getListaValores().isEmpty()) {
				cCombobox.setTipoListaValores(TypeListaValores.FIJA);
				cCombobox.setListaValorListaFija(componenteSistra.getListaValores());
			} else if (componenteSistra.getComExpvpo() != null) {
				cCombobox.setTipoListaValores(TypeListaValores.SCRIPT);

				cCombobox.setScriptValoresPosibles(
						createScript("/* TODO: Revisar script" + System.getProperty("line.separator")
								+ componenteSistra.getComExpvpo() + System.getProperty("line.separator") + "*/"));
			}

		} else if ("radiobutton".equals(comType)) {
			final ComponenteFormularioCampoSelector cRadioButton = (ComponenteFormularioCampoSelector) componente;

			cRadioButton.setTipoCampoIndexado(TypeCampoIndexado.UNICA);

			if (componenteSistra.getListaValores() != null && !componenteSistra.getListaValores().isEmpty()) {
				cRadioButton.setTipoListaValores(TypeListaValores.FIJA);
				cRadioButton.setListaValorListaFija(componenteSistra.getListaValores());
			} else if (componenteSistra.getComExpvpo() != null) {
				cRadioButton.setTipoListaValores(TypeListaValores.SCRIPT);
				cRadioButton.setScriptValoresPosibles(
						createScript("/* TODO: Revisar script" + System.getProperty("line.separator")
								+ componenteSistra.getComExpvpo() + System.getProperty("line.separator") + "*/"));
			}

			cRadioButton.setOrientacion(componenteSistra.getComOrient());
		} else if ("listbox".equals(comType)) {
			final ComponenteFormularioCampoSelector cListBox = (ComponenteFormularioCampoSelector) componente;

			cListBox.setTipoCampoIndexado(TypeCampoIndexado.MULTIPLE);

			if (componenteSistra.getListaValores() != null && !componenteSistra.getListaValores().isEmpty()) {
				cListBox.setTipoListaValores(TypeListaValores.FIJA);
				cListBox.setListaValorListaFija(componenteSistra.getListaValores());
			} else if (componenteSistra.getComExpvpo() != null) {
				cListBox.setTipoListaValores(TypeListaValores.SCRIPT);
				cListBox.setScriptValoresPosibles(
						createScript("/* TODO: Revisar script" + System.getProperty("line.separator")
								+ componenteSistra.getComExpvpo() + System.getProperty("line.separator") + "*/"));
			}
		} else if ("textbox".equals(comType) && isNotOculto(componenteSistra)) {
			// El tipo oculto se ignora
			final ComponenteFormularioCampoTexto cTextBox = ((ComponenteFormularioCampoTexto) componente);
			if (componenteSistra.getComTxtipo() == null) {
				cTextBox.setTipoCampoTexto(TypeCampoTexto.NORMAL);
				final ErrorMigracion error1 = errorMigracion(componente.getIdComponente(), pOpciones,
						"elemento.formulario.disenyoFormulario.pagina.elemento.revisar", pIdioma);
				error1.setTipo(TypeErrorMigracion.WARNING);
				listaErrores.add(error1);
			} else {
				switch (componenteSistra.getComTxtipo()) {
				case "FE":
					cTextBox.setTipoCampoTexto(TypeCampoTexto.FECHA);
					break;
				case "HO":
					cTextBox.setTipoCampoTexto(TypeCampoTexto.HORA);
					break;
				case "IM":
					cTextBox.setTipoCampoTexto(TypeCampoTexto.NUMERO);
					cTextBox.setNumeroDigitosEnteros(5);
					cTextBox.setNumeroDigitosDecimales(2);
					cTextBox.setNumeroSeparador(TypeSeparadorNumero.PUNTO_Y_COMA);

					final ErrorMigracion error1 = errorMigracion(componente.getIdComponente(), pOpciones,
							"elemento.formulario.disenyoFormulario.pagina.elemento.revisar", pIdioma);
					error1.setTipo(TypeErrorMigracion.WARNING);
					listaErrores.add(error1);

					break;
				case "NO":
					if (componenteSistra.getTipoEmail() != null && componenteSistra.getTipoEmail()) {
						cTextBox.setTipoCampoTexto(TypeCampoTexto.EMAIL);

						if (componenteSistra.getMaxlength() != null) {
							cTextBox.setNormalTamanyo(componenteSistra.getMaxlength());
						}
					} else if (componenteSistra.getTipoExpRegular() != null && componenteSistra.getTipoExpRegular()) {
						cTextBox.setTipoCampoTexto(TypeCampoTexto.EXPRESION);
						cTextBox.setExpresionRegular(componenteSistra.getExpRegular());
					} else {
						cTextBox.setTipoCampoTexto(TypeCampoTexto.NORMAL);

						cTextBox.setNormalMultilinea(UNO.equals(componenteSistra.getComMultil()));

						if (cTextBox.isNormalMultilinea()) {
							if (componenteSistra.getComFilas() != null) {
								cTextBox.setNormalNumeroLineas(componenteSistra.getComFilas().intValue());
							}
							if (componenteSistra.getComColumn() != null) {
								cTextBox.setNormalTamanyo(componenteSistra.getComColumn().intValue());
							}
						}

						if (componenteSistra.getMaxlength() != null) {
							cTextBox.setNormalTamanyo(componenteSistra.getMaxlength());
						}
					}

					if (componenteSistra.isRequired() != null) {
						cTextBox.setObligatorio(componenteSistra.isRequired().booleanValue());
					}

					break;
				case "NU":
					cTextBox.setTipoCampoTexto(TypeCampoTexto.NUMERO);
					cTextBox.setNumeroDigitosEnteros(5);
					cTextBox.setNumeroDigitosDecimales(0);
					cTextBox.setNumeroSeparador(TypeSeparadorNumero.PUNTO_Y_COMA);

					final ErrorMigracion error2 = errorMigracion(componente.getIdComponente(), pOpciones,
							"elemento.formulario.disenyoFormulario.pagina.elemento.revisar", pIdioma);
					error2.setTipo(TypeErrorMigracion.WARNING);
					listaErrores.add(error2);

					break;
				default:
					cTextBox.setTipoCampoTexto(TypeCampoTexto.NORMAL);
					break;
				}
			}

		}

		StringBuilder scriptAutorelleno = null;
		if (componenteSistra.getComExpaur() != null) {
			scriptAutorelleno = new StringBuilder("/* TODO: Revisar script autorrellenable");
			scriptAutorelleno.append(System.getProperty("line.separator"));
			scriptAutorelleno.append(componenteSistra.getComExpaur());
			scriptAutorelleno.append(System.getProperty("line.separator"));
			scriptAutorelleno.append("*/");
			scriptAutorelleno.append(System.getProperty("line.separator"));
		}

		if (componenteSistra.getComExpaut() != null) {
			if (scriptAutorelleno == null) {
				scriptAutorelleno = new StringBuilder();
			}
			scriptAutorelleno.append("/* TODO: Revisar script autocalculo");
			scriptAutorelleno.append(System.getProperty("line.separator"));
			scriptAutorelleno.append(componenteSistra.getComExpaut());
			scriptAutorelleno.append(System.getProperty("line.separator"));
			scriptAutorelleno.append("*/");
		}

		if (scriptAutorelleno != null && componente instanceof ComponenteFormularioCampo) {
			((ComponenteFormularioCampo) componente)
					.setScriptAutorrellenable(createScript(scriptAutorelleno.toString()));
		}

		if (isNotOculto(componenteSistra)) {

			if (componenteSistra.getComExpval() != null) {
				final StringBuilder scriptValidacion = new StringBuilder("/* TODO: Revisar script validacion");
				scriptValidacion.append(System.getProperty("line.separator"));
				scriptValidacion.append(componenteSistra.getComExpval());
				scriptValidacion.append(System.getProperty("line.separator"));
				scriptValidacion.append("*/");

				if (componente instanceof ComponenteFormularioCampo) {
					final Script scriptValidacionAux = createScript(scriptValidacion.toString());

					if (scriptValidacionAux != null && componenteSistra.getMensajeValidacion() != null) {
						final List<LiteralScript> literalScript = new ArrayList<>();
						if (componenteSistra.getComNomlog().length() > 20) {
							final ErrorMigracion error2 = errorMigracion(componenteSistra.getComNomlog(), null,
									"migracion.elemento.formulario.disenyoFormulario.identificador.literalscript.demasiadolargo",
									pIdioma);
							error2.setTipo(TypeErrorMigracion.WARNING);
							listaErrores.add(error2);
						}
						// componenteSistra.getComNomlog()
						literalScript.add(createLiteralScript(componenteSistra.getComNomlog().toUpperCase(),
								componenteSistra.getMensajeValidacion()));
						scriptValidacionAux.setMensajes(literalScript);
					}

					((ComponenteFormularioCampo) componente).setScriptValidacion(scriptValidacionAux);
				}
			}

			if (componenteSistra.getComExpdep() != null) {
				final StringBuilder scriptEstado = new StringBuilder("/* TODO: Revisar script estado");
				scriptEstado.append(System.getProperty("line.separator"));
				scriptEstado.append(componenteSistra.getComExpdep());
				scriptEstado.append(System.getProperty("line.separator"));
				scriptEstado.append("*/");

				if (componente instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) componente)
							.setScriptSoloLectura(createScript(scriptEstado.toString()));
				}
			}

		}

		formIntDao.updateComponente(componente);

		return lineaComponente;
	}

	/**
	 * Comprueba si es oculto un componente de sistra.
	 *
	 * @param componenteSistra
	 * @return
	 */
	private boolean isNotOculto(final ComponSistra componenteSistra) {
		return componenteSistra.getComOculto() == null || CERO.compareTo(componenteSistra.getComOculto()) == 0;
	}

	/**
	 * Inserta campo.
	 *
	 * @param pagina    pagina
	 * @param linea     linea
	 * @param tipoCampo tipo campo
	 * @return objeto formulario
	 */
	private ObjetoFormulario insertaCampo(final PaginaFormulario pagina, final LineaComponentesFormulario linea,
			final TypeObjetoFormulario tipoCampo) {
		final String posicionamiento = ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR;

		final Integer orden = UtilDisenyo.ordenInsercionComponente(linea, null, posicionamiento);

		final ObjetoFormulario componente = formIntDao.addComponente(tipoCampo, pagina.getCodigo(), linea.getCodigo(),
				orden, posicionamiento);

		// actualizamos modelo
		pagina.getLineas().get(linea.getOrden() - 1).addComponente((ComponenteFormulario) componente);

		return componente;
	}

	/**
	 * Inserta linea componente bloque.
	 *
	 * @param pagina    pagina
	 * @param tipoCampo tipo campo
	 * @return objeto formulario
	 */
	private ObjetoFormulario insertaLineaComponenteBloque(final PaginaFormulario pagina,
			final TypeObjetoFormulario tipoCampo) {
		final String posicionamiento = ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR;

		final Integer ordenLinea = UtilDisenyo.ordenInsercionLinea(pagina, null, posicionamiento);

		final ObjetoFormulario componente = formIntDao.addComponente(tipoCampo, pagina.getCodigo(), null, ordenLinea,
				posicionamiento);

		// actualizamos modelo (si habia saltos en el orden de linea puede que el orden
		// inicial no sea el que toca)
		pagina.addLinea((LineaComponentesFormulario) componente);

		return componente;
	}

	/**
	 * Crea las tasas.
	 *
	 * @param listaDocumSistra lista de documtos sistra
	 * @param tramiteVersion   tramite version
	 */
	private void createTasas(final List<DocumSistra> listaDocumSistra, final TramiteVersion tramiteVersion,
			final String pIdioma, final List<ErrorMigracion> listaErrores) {
		final List<DocumSistra> listaTasas = listaDocumSistra.stream()
				.filter(tasa -> ConstantesMigracion.DOCUM_TIPO_TASA.equals(tasa.getTipo()))
				.collect(Collectors.toList());

		if (listaTasas != null && !listaTasas.isEmpty()) {
			final TramitePasoTasa pasoTasa = tramiteVersion.getPasoTasa();

			if (pasoTasa != null) {
				for (final DocumSistra documSistra : listaTasas) {
					final Tasa tasa = tramiteComponent.createTasaDefault();
					if (documSistra.getIdenti().length() > 20) {
						final ErrorMigracion error2 = errorMigracion(documSistra.getIdenti(), null,
								"migracion.elemento.formulario.disenyoFormulario.identificador.tasa.demasiadolargo",
								pIdioma);
						error2.setTipo(TypeErrorMigracion.WARNING);
						listaErrores.add(error2);
						tasa.setIdentificador(documSistra.getIdenti().substring(0, 20).toUpperCase());
					} else {
						tasa.setIdentificador(documSistra.getIdenti().toUpperCase());
					}

					tasa.setDescripcion(documSistra.getDescripcion());

					tasa.setObligatoriedad(TypeFormularioObligatoriedad.fromString(documSistra.getObligatorio()));

					tasa.setScriptObligatoriedad(createScript(documSistra.getScriptObligatorio()));

					tasa.setScriptPago(createScript(documSistra.getScriptPago()));

					tramiteComponent.addTasaTramite(tasa, pasoTasa.getCodigo());
				}
			}
		}
	}

	/**
	 * Crea los anexos.
	 *
	 * @param listaDocumSistra lista de documentos de sistra
	 * @param tramiteVersion   tramite version
	 */
	private void createAnexos(final List<DocumSistra> listaDocumSistra, final TramiteVersion tramiteVersion,
			final String pIdioma, final List<ErrorMigracion> listaErrores) {
		final List<DocumSistra> listaAnexos = listaDocumSistra.stream()
				.filter(anex -> ConstantesMigracion.DOCUM_TIPO_ANEXO.equals(anex.getTipo()))
				.collect(Collectors.toList());

		if (listaAnexos != null && !listaAnexos.isEmpty()) {
			final TramitePasoAnexar pasoAnexar = tramiteVersion.getPasoAnexar();

			if (pasoAnexar != null) {
				for (final DocumSistra documSistra : listaAnexos) {
					final Documento documento = tramiteComponent.createDocumentoDefault();
					if (documSistra.getIdenti().length() > 20) {
						final ErrorMigracion error2 = errorMigracion(documSistra.getIdenti(), null,
								"migracion.elemento.formulario.disenyoFormulario.identificador.documento.demasiadolargo",
								pIdioma);
						error2.setTipo(TypeErrorMigracion.WARNING);
						listaErrores.add(error2);
						documento.setIdentificador(documSistra.getIdenti().substring(0, 20).toUpperCase());
					} else {
						documento.setIdentificador(documSistra.getIdenti().toUpperCase());
					}

					documento.setDescripcion(documSistra.getDescripcion());

					documento.setObligatoriedad(TypeFormularioObligatoriedad.fromString(documSistra.getObligatorio()));

					documento.setScriptObligatoriedad(createScript(documSistra.getScriptObligatorio()));

					documento.setAyudaURL(documSistra.getAyudaURL());

					if ("S".equals(documSistra.getTelematico())) {
						documento.setTipoPresentacion(TypePresentacion.ELECTRONICA);

						documento.setDebeFirmarDigitalmente("S".equals(documSistra.getFirmar()));

						documento.setScriptFirmarDigitalmente(createScript(documSistra.getScriptEstablecerFirmantes()));

						if (documSistra.getExtensiones() != null) {
							documento.setExtensiones(documSistra.getExtensiones().replaceAll(",", ";"));
						}

						documento.setDebeConvertirPDF("S".equals(documSistra.getDebeConvertirPDF()));

						if (documSistra.getTamanyoMaximo() != null && documSistra.getTamanyoMaximo() > 9999) {
							documento.setTamanyoMaximo(documSistra.getTamanyoMaximo() / 1000);
							documento.setTipoTamanyo(TypeTamanyo.MEGABYTES);
						} else {
							documento.setTamanyoMaximo(documSistra.getTamanyoMaximo());
							if (documSistra.getTamanyoMaximo() != null) {
								documento.setTipoTamanyo(TypeTamanyo.KILOBYTES);
							}
						}

						if ("S".equals(documSistra.getGenerico())) {
							documento.setNumeroInstancia(documSistra.getNumeroInstancia());
						}
					} else {
						documento.setTipoPresentacion(TypePresentacion.PRESENCIAL);
					}

					tramiteComponent.addDocumentoTramite(documento, pasoAnexar.getCodigo());
				}
			}
		}
	}

	/**
	 * Crea un tramite version con los valores por defecto.
	 *
	 * @param pIdTramite  id tramite
	 * @param pNumVersion num version
	 * @param pParams     params
	 * @return tramite version
	 */
	private TramiteVersion createTramiteVersion(final Long pIdTramite, final int pNumVersion,
			final Map<String, Object> pParams) {
		String idiomas = null;
		String user = null;

		// creamos tramite version
		if (pParams != null) {
			idiomas = (String) pParams.get(ConstantesMigracion.IDIOMASPORDEFECTO);
			user = (String) pParams.get(ConstantesMigracion.USERNAME);
		}

		final TramiteVersion tramiteVersion = tramiteComponent.createTramiteVersionDefault(pNumVersion, idiomas, user);

		tramiteVersion.setListaPasos(tramiteComponent.createNormalizado());

		tramiteVersion.setCodigo(tramiteComponent.addTramiteVersion(tramiteVersion, String.valueOf(pIdTramite), user));

		tramiteVersion.setListaPasos(tramitePasoDao.getTramitePasos(tramiteVersion.getCodigo()));

		return tramiteVersion;
	}

	/**
	 * Recupera datos de sistra.
	 *
	 * @param pIdTramiteSistra  id. tramite sistra
	 * @param pNumVersionSistra num. version sistra
	 * @return the traver sistra
	 */
	private TraverSistra recuperaDatosSistra(final Long pIdTramiteSistra, final int pNumVersionSistra) {
		// recuperamos el codigo de tramite version de sistra
		final TraverSistra traverSistra = migracionDao.getTramiteVersionSistra(pIdTramiteSistra, pNumVersionSistra);

		if (traverSistra != null) {
			// recuperamos formularios,anexos y pagos de sistra
			traverSistra.setListaDocumSistra(migracionDao.getDocumSistra(traverSistra.getTrvCodigo()));
			// recuperamos diseño del formulario
			for (final DocumSistra documSistra : traverSistra.getListaDocumSistra()) {
				if (ConstantesMigracion.DOCUM_TIPO_FORMULARIO.equals(documSistra.getTipo())) {
					documSistra.setFormulario(migracionDao.getFormSistra(documSistra));
				}
			}
		}

		return traverSistra;
	}

	/**
	 * Crea un error de migracion.
	 *
	 * @param pElemento        elemento
	 * @param pOpciones        opciones
	 * @param pLiteralOpciones literal opciones
	 * @param pIdioma          idioma
	 * @return error migracion
	 */
	private ErrorMigracion errorMigracion(final String pElemento, final String[] pOpciones,
			final String pLiteralOpciones, final String pIdioma) {

		final StringBuilder elemento = new StringBuilder();

		if (pOpciones != null) {
			elemento.append(pOpciones[0] + " > ");

			for (int i = 1; i < pOpciones.length; i++) {
				elemento.append(pOpciones[i] + " > ");
			}
		}

		if (pElemento != null) {
			elemento.append(pElemento);
		}

		String[] parametros = { pElemento };
		parametros = ArrayUtils.addAll(pOpciones, parametros);

		final String descripcion = literales.getLiteral("migracion", pLiteralOpciones, parametros, pIdioma);

		return new ErrorMigracion(elemento.toString(), descripcion);
	}

	/**
	 * Crea script.
	 *
	 * @param script script
	 * @return script
	 */
	private Script createScript(final String script) {
		Script res = null;
		if (StringUtils.isNotEmpty(script)) {
			res = new Script();
			res.setContenido(script);
		}
		return res;
	}

	/**
	 * Crea literal script.
	 *
	 * @param literal literal
	 * @return literal script
	 */
	private LiteralScript createLiteralScript(final String identificador, final Literal literal) {
		LiteralScript res = null;
		if (literal != null) {
			res = new LiteralScript();
			if (identificador.length() > 20) {
				res.setIdentificador(identificador.substring(0, 20));
			} else {
				res.setIdentificador(identificador);
			}
			res.setLiteral(literal);
		}
		return res;
	}
}
