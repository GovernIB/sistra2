package es.caib.sistrages.core.service.repository.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.caib.sistrages.core.api.exception.FaltanDatosException;
import es.caib.sistrages.core.api.exception.NoExisteDato;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoOculto;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSeccionReutilizable;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioComponenteSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioPaginaSimple;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.service.repository.model.JAccionPersonalizada;
import es.caib.sistrages.core.service.repository.model.JCampoFormulario;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioCaptcha;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioCasillaVerificacion;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioIndexado;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioSeccionReutilizable;
import es.caib.sistrages.core.service.repository.model.JCampoFormularioTexto;
import es.caib.sistrages.core.service.repository.model.JDominio;
import es.caib.sistrages.core.service.repository.model.JElementoFormulario;
import es.caib.sistrages.core.service.repository.model.JEtiquetaFormulario;
import es.caib.sistrages.core.service.repository.model.JFichero;
import es.caib.sistrages.core.service.repository.model.JFormulario;
import es.caib.sistrages.core.service.repository.model.JIdioma;
import es.caib.sistrages.core.service.repository.model.JImagenFormulario;
import es.caib.sistrages.core.service.repository.model.JLineaFormulario;
import es.caib.sistrages.core.service.repository.model.JLiteral;
import es.caib.sistrages.core.service.repository.model.JLiteralErrorScript;
import es.caib.sistrages.core.service.repository.model.JPaginaFormulario;
import es.caib.sistrages.core.service.repository.model.JPlantillaFormulario;
import es.caib.sistrages.core.service.repository.model.JPlantillaIdiomaFormulario;
import es.caib.sistrages.core.service.repository.model.JScript;
import es.caib.sistrages.core.service.repository.model.JSeccionFormulario;
import es.caib.sistrages.core.service.repository.model.JSeccionReutilizable;
import es.caib.sistrages.core.service.repository.model.JTraduccionLiteral;

/**
 * La clase FormularioSoporteDaoImpl.
 */
@Repository("formularioInternoDao")
public class FormularioInternoDaoImpl implements FormularioInternoDao {

	private static final String FALTA_ID = "Falta el identificador";
	private static final String FALTA_FORMDIS = "Falta el formulario";
	private static final String FALTA_COMPONENTE = "Falta el componente";
	private static final String NO_EXISTE_FORMINT = "No existe el formulario: ";
	private static final String NO_EXISTE_PAGINA = "No existe la pagina: ";
	private static final String NO_EXISTE_LINEA = "No existe la linea: ";
	private static final String NO_EXISTE_COMPONENTE = "No existe el componente: ";
	private static final String NO_EXISTE_PLANTILLA = "No existe la plantilla: ";

	/**
	 * entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	FicheroExternoDao ficheroExternoDao;

	/**
	 * Crea una nueva instancia de FormularioInternoDaoImpl.
	 */
	public FormularioInternoDaoImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormularioById(java.lang.Long)
	 */
	@Override
	public DisenyoFormulario getFormularioById(final Long pId) {
		return getJFormularioById(pId).toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormularioPaginasById(java.lang.Long)
	 */
	@Override
	public DisenyoFormulario getFormularioPaginasById(final Long pId) {
		final JFormulario jForm = getJFormularioById(pId);

		jForm.getPaginas();

		final DisenyoFormulario formInt = jForm.toModel();

		for (final JPaginaFormulario jPagina : jForm.getPaginas()) {
			formInt.getPaginas().add(jPagina.toModel());
		}

		// ordenamos lista de paginas por campo orden
		if (!formInt.getPaginas().isEmpty() && formInt.getPaginas().size() > 1) {
			Collections.sort(formInt.getPaginas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
		}

		for (final JPlantillaFormulario jPlantilla : jForm.getPlantillas()) {
			formInt.getPlantillas().add(jPlantilla.toModel());
		}

		return formInt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getFormPagById(java.lang.Long)
	 */
	@Override
	public DisenyoFormulario getFormularioCompletoById(final Long pId, final boolean sinSecciones) {
		final JFormulario jForm = getJFormularioById(pId);

		final DisenyoFormulario formInt = jForm.toModel();

		for (final JPaginaFormulario jPagina : jForm.getPaginas()) {
			formInt.getPaginas().add(getContenidoPaginaById(jPagina.getCodigo(), sinSecciones));
		}

		for (final JPlantillaFormulario jPlantilla : jForm.getPlantillas()) {
			formInt.getPlantillas().add(jPlantilla.toModelCompleto());
		}

		// ordenamos lista de paginas por campo orden
		if (!formInt.getPaginas().isEmpty() && formInt.getPaginas().size() > 1) {
			Collections.sort(formInt.getPaginas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
		}

		return formInt;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * addFormulario(es.caib.sistrages.core.api.model.FormularioTramite, boolean)
	 */
	@Override
	public Long addFormulario(final FormularioTramite pFormTra, final boolean crearPaginaInicial) {
		if (pFormTra == null) {
			throw new FaltanDatosException(FALTA_FORMDIS);
		}

		final JFormulario jForm = JFormulario.createDefault(JLiteral.fromModel(pFormTra.getDescripcion()));

		if (crearPaginaInicial) {
			final JPaginaFormulario jPagina = JPaginaFormulario.createDefault(jForm);

			final Set<JPaginaFormulario> paginas = new HashSet<>();
			paginas.add(jPagina);
			jForm.setPaginas(paginas);
		}

		entityManager.persist(jForm);

		return jForm.getCodigo();
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * addFormulario(es.caib.sistrages.core.api.model.FormularioTramite, boolean)
	 */
	@Override
	public Long addFormulario(final SeccionReutilizable seccion) {
		if (seccion == null) {
			throw new FaltanDatosException(FALTA_FORMDIS);
		}

		JLiteral jliteral = new JLiteral();
		Set<JTraduccionLiteral> traduccionLiterales = new HashSet<>();
		JTraduccionLiteral jtradCat = new JTraduccionLiteral();
		jtradCat.setIdioma(new JIdioma(TypeIdioma.CATALAN.toString()));
		jtradCat.setLiteral("Secció reutilitzable " + seccion.getIdentificador());
		jtradCat.setTraduccion(jliteral);

		JTraduccionLiteral jtradEsp = new JTraduccionLiteral();
		jtradEsp.setIdioma(new JIdioma(TypeIdioma.CASTELLANO.toString()));
		jtradEsp.setLiteral("Sección reutilizable " + seccion.getIdentificador());
		jtradEsp.setTraduccion(jliteral);

		traduccionLiterales.add(jtradEsp);
		traduccionLiterales.add(jtradCat );

		jliteral.setTraduccionLiterales(traduccionLiterales);
		final JFormulario jForm = JFormulario.createDefault(jliteral);


		final JPaginaFormulario jPagina = JPaginaFormulario.createDefault(jForm);

		final Set<JPaginaFormulario> paginas = new HashSet<>();
		paginas.add(jPagina);
		jForm.setPaginas(paginas);


		entityManager.persist(jForm);

		return jForm.getCodigo();

		/*

		final JFormulario jForm = JFormulario.createDefault(null);
		jForm.setMostrarCabecera(false);

		final JPaginaFormulario jPagina = JPaginaFormulario.createDefault(jForm);

		final Set<JPaginaFormulario> paginas = new HashSet<>();
		paginas.add(jPagina);
		jForm.setPaginas(paginas);

		entityManager.persist(jForm);


		ObjetoFormulario obj = this.addComponente(TypeObjetoFormulario.SECCION, jPagina.getCodigo(), null, 0, null, null, false, null);
		ComponenteFormularioSeccion compSeccion = (ComponenteFormularioSeccion) ((LineaComponentesFormulario) obj).getComponentes().get(0);
		compSeccion.setIdComponente("SEC_" + seccion.getIdentificador());
		Literal texto = new Literal();
		texto.add(new Traduccion("es", "Sección reutilizable"));
		texto.add(new Traduccion("ca", "Secció reutilitzable"));
		compSeccion.setTexto(texto);
		compSeccion.setLetra("A");
		this.updateComponente(compSeccion);
		return jForm.getCodigo();
		*/
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * addFormulario(es.caib.sistrages.core.api.model.FormularioTramite)
	 */
	@Override
	public Long addFormulario(final FormularioTramite pFormTra) {
		return addFormulario(pFormTra, true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * updateFormulario(es.caib.sistrages.core.api.model.DisenyoFormulario)
	 */
	private void createFormulario(final DisenyoFormulario pFormInt) {

		JFormulario jForm = getJFormularioById(pFormInt.getCodigo());

		// Mergeamos datos
		jForm.setPermitirAccionesPersonalizadas(pFormInt.isPermitirAccionesPersonalizadas());
		jForm.setPermitirGuardarSinFinalizar(pFormInt.isPermitirGuardarSinFinalizar());

		if (pFormInt.getScriptPlantilla() == null) {
			jForm.setScriptPlantilla(null);
		} else {
			if (pFormInt.getScriptPlantilla().getCodigo() != null) {
				final JScript script = JScript.merge(jForm.getScriptPlantilla(), pFormInt.getScriptPlantilla());
				jForm.setScriptPlantilla(script);
			} else {
				jForm.setScriptPlantilla(JScript.fromModel(pFormInt.getScriptPlantilla()));
			}
		}

		jForm.setMostrarCabecera(pFormInt.isMostrarCabecera());

		if (pFormInt.isMostrarCabecera()) {
			jForm.setTextoCabecera(JLiteral.mergeModel(jForm.getTextoCabecera(), pFormInt.getTextoCabecera()));
		} else {
			jForm.setTextoCabecera(null);
		}

		pFormInt.getPaginas().get(0).setCodigo(null);
		Long idPagina = this.addPagina(pFormInt.getCodigo(), pFormInt.getPaginas().get(0));
		pFormInt.getPaginas().get(0).setCodigo(idPagina);
		entityManager.merge(jForm);
		entityManager.flush();


		if (pFormInt.getPaginas().get(0).getLineas() != null) {
			Integer ordenLinea = 1;
			//Reordenamos las lineas
			Collections.sort(pFormInt.getPaginas().get(0).getLineas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));

			for( LineaComponentesFormulario linea : pFormInt.getPaginas().get(0).getLineas()) {


				ObjetoFormulario jLinea = null;

				if (crearLinea(linea)) {
					jLinea = this.addComponente(TypeObjetoFormulario.LINEA, idPagina, null, ordenLinea, null, null, false, null);
				}

				Collections.sort(linea.getComponentes(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));


				if (linea.getComponentes() != null) {
					Integer ordenComponente = 0;
					Long idLinea = null;
					if (jLinea != null) {
						idLinea = jLinea.getCodigo();
					}
					for(ComponenteFormulario componente : linea.getComponentes()) {


						ObjetoFormulario comp = this.addComponente(componente.getTipo(), idPagina, idLinea, idLinea == null ? ordenLinea : componente.getOrden(), null, componente, false, null);
						entityManager.flush();

						componente.setCodigo(getCodigo(comp));
						componente.setOrden(ordenComponente);
						if (componente.getTipo() == TypeObjetoFormulario.SELECTOR && (componente instanceof ComponenteFormularioCampoSelector) && ((ComponenteFormularioCampoSelector)componente).getCodDominio() != null) {
							ComponenteFormularioCampoSelector compSelector = ((ComponenteFormularioCampoSelector)componente);
							Long idDominio = getDominio(compSelector.getDominioAmbito(), compSelector.getDominioIdentificador(), compSelector.getDominioEntidad(), compSelector.getDominioArea());
							if (idDominio != null) {
								((ComponenteFormularioCampoSelector)componente).setCodDominio(idDominio);
							}
						}
						this.updateComponente(componente);
						ordenComponente++;
					}
				}
				ordenLinea++;
			}
		}
		entityManager.flush();
		jForm = getJFormularioById(pFormInt.getCodigo());


		final List<JPlantillaFormulario> borrar = JFormulario.listRemovePlantillasModel(jForm, pFormInt);

		jForm = JFormulario.mergePlantillasModel(jForm, pFormInt);

		entityManager.merge(jForm);

		for (final JPlantillaFormulario jPlantillaFormulario : borrar) {

			entityManager.remove(jPlantillaFormulario);

			for (final JPlantillaIdiomaFormulario jPlantillaidiomaFormulario : jPlantillaFormulario
					.getPlantillaIdiomaFormulario()) {
				ficheroExternoDao.marcarBorrar(jPlantillaidiomaFormulario.getFichero().getCodigo());
			}

		}
	}

	/**
	 * Obtiene la id del dominio
	 * @param dominioAmbito
	 * @param dominioIdentificador
	 * @param dominioEntidad
	 * @param dominioArea
	 * @return
	 */
	private Long getDominio(String dominioAmbito, String dominioIdentificador, String dominioEntidad,
			String dominioArea) {
		Long idDominio = null;
		String sql;
		TypeAmbito ambito = TypeAmbito.fromString(dominioAmbito);
		switch (ambito) {
		case AREA:
			sql = "SELECT j.codigo FROM JDominio j WHERE j.ambito like '"+TypeAmbito.AREA.toString()+"' AND j.identificador LIKE :identificador AND j.area.identificador LIKE :idArea";
			break;
		case ENTIDAD:
			sql = "SELECT j.codigo FROM JDominio j WHERE j.ambito like '"+TypeAmbito.ENTIDAD.toString()+"' AND j.identificador LIKE :identificador AND j.entidad.identificador LIKE :idEntidad";
			break;
		case GLOBAL:
			sql = "SELECT j.codigo FROM JDominio j WHERE j.ambito like '"+TypeAmbito.GLOBAL.toString()+"' AND j.identificador LIKE :identificador";
			break;
			default:
				sql = "";
		}
		Query query = entityManager.createQuery(sql);
		query.setParameter("identificador", dominioIdentificador);
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idArea", dominioArea);
		}
		if (ambito == TypeAmbito.AREA) {
			query.setParameter("idEntidad", dominioEntidad);
		}

		List<Long> idDominios = query.getResultList();
		if (idDominios != null && !idDominios.isEmpty()) {
			idDominio = idDominios.get(0);
		}
		return idDominio;
	}

	private boolean crearLinea(LineaComponentesFormulario linea) {
		boolean crear = true;
		if (linea.getComponentes() != null && !linea.getComponentes().isEmpty()) {
			crear = ! ( linea.getComponentes().size() == 1 &&  (linea.getComponentes().get(0).getTipo() == TypeObjetoFormulario.CAPTCHA ||
					linea.getComponentes().get(0).getTipo() == TypeObjetoFormulario.ETIQUETA ||
					linea.getComponentes().get(0).getTipo() == TypeObjetoFormulario.SECCION ) );
		}
		return crear;
	}

	private Long getCodigo(ObjetoFormulario comp) {
		if (comp instanceof LineaComponentesFormulario) {
			if (((LineaComponentesFormulario) comp).getComponentes() == null || ((LineaComponentesFormulario) comp).getComponentes().isEmpty()) {
				return comp.getCodigo();
			} else {
				return ((LineaComponentesFormulario) comp).getComponentes().get(0).getCodigo();
			}
		}
		return comp.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * updateFormulario(es.caib.sistrages.core.api.model.DisenyoFormulario)
	 */
	@Override
	public void updateFormulario(final DisenyoFormulario pFormInt) {

		JFormulario jForm = getJFormularioById(pFormInt.getCodigo());

		// Mergeamos datos
		jForm.setPermitirAccionesPersonalizadas(pFormInt.isPermitirAccionesPersonalizadas());
		jForm.setPermitirGuardarSinFinalizar(pFormInt.isPermitirGuardarSinFinalizar());

		if (pFormInt.getScriptPlantilla() == null) {
			jForm.setScriptPlantilla(null);
		} else {
			if (pFormInt.getScriptPlantilla().getCodigo() != null) {
				final JScript script = JScript.merge(jForm.getScriptPlantilla(), pFormInt.getScriptPlantilla());
				jForm.setScriptPlantilla(script);
			} else {
				jForm.setScriptPlantilla(JScript.fromModel(pFormInt.getScriptPlantilla()));
			}
		}

		jForm.setMostrarCabecera(pFormInt.isMostrarCabecera());

		if (pFormInt.isMostrarCabecera()) {
			jForm.setTextoCabecera(JLiteral.mergeModel(jForm.getTextoCabecera(), pFormInt.getTextoCabecera()));
		} else {
			jForm.setTextoCabecera(null);
		}

		jForm = JFormulario.mergePaginasModel(jForm, pFormInt);

		final List<JPlantillaFormulario> borrar = JFormulario.listRemovePlantillasModel(jForm, pFormInt);

		jForm = JFormulario.mergePlantillasModel(jForm, pFormInt);

		entityManager.merge(jForm);

		for (final JPlantillaFormulario jPlantillaFormulario : borrar) {

			entityManager.remove(jPlantillaFormulario);

			for (final JPlantillaIdiomaFormulario jPlantillaidiomaFormulario : jPlantillaFormulario
					.getPlantillaIdiomaFormulario()) {
				ficheroExternoDao.marcarBorrar(jPlantillaidiomaFormulario.getFichero().getCodigo());
			}

		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getPaginaById(final Long pId) {
		return getJPaginaById(pId).toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getContenidoPaginaById(java.lang.Long)
	 */
	@Override
	public PaginaFormulario getContenidoPaginaById(final Long pId, final boolean sinSeccionesReutilizables) {
		final JPaginaFormulario jPagina = getJPaginaById(pId);

		final PaginaFormulario pagina = jPagina.toModel();

		List<LineaComponentesFormulario> lineas = getLineas(jPagina, sinSeccionesReutilizables);
		pagina.getLineas().addAll(lineas);

		// ordenamos lista de lineas por campo orden
		if (!pagina.getLineas().isEmpty() && pagina.getLineas().size() > 1) {
			Collections.sort(pagina.getLineas(),
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
		}

		return pagina;
	}

	/**
	 * Método recursivo para obtener las lineas de una página.
	 * @param jPagina
	 * @param sinSeccionesReutilizables
	 * @return
	 */
	private List<LineaComponentesFormulario> getLineas(final JPaginaFormulario jPagina, final boolean sinSeccionesReutilizables) {

		List<LineaComponentesFormulario> lineas = new ArrayList<>();
		for (final JLineaFormulario jLinea : jPagina.getLineasFormulario()) {
			final LineaComponentesFormulario linea = jLinea.toModel();
			boolean sinSeccionReutilizable = true;

			for (final JElementoFormulario jComponente : jLinea.getElementoFormulario()) {
				final ComponenteFormulario componente = componenteFormularioToModel(jComponente);
				if (componente != null) {
					//Si es sin secciones reutilizables, hay que sustituir el componente seccion reutilizable por el disenyo asociado
					if (componente.getTipo() == TypeObjetoFormulario.SECCION_REUTILIZABLE && sinSeccionesReutilizables && jComponente.getSeccionReutilizableFormulario() != null) {
						//Si tiene seccion reutilizable y está activo, hay que obtener todas las lineas
						sinSeccionReutilizable = false;
						JSeccionReutilizable jseccion = entityManager.find(JSeccionReutilizable.class, jComponente.getSeccionReutilizableFormulario().getSeccionReutilizable().getCodigo());
						JPaginaFormulario jpaginaSeccion = (JPaginaFormulario) jseccion.getFormularioAsociado().getPaginas().toArray()[0];
						List<LineaComponentesFormulario> lineasSeccion = getLineas(jpaginaSeccion, false);

						//Hay que reemplazar la sección que haya, por los datos almacenados (la letra y el texto).
						if (lineasSeccion != null) {
							for(LineaComponentesFormulario lin : lineasSeccion) {
								if (lin.getComponentes() != null) {
									for(ComponenteFormulario comp : lin.getComponentes()) {
										if (comp instanceof ComponenteFormularioSeccion) {
											((ComponenteFormularioSeccion) comp).setLetra(jComponente.getSeccionReutilizableFormulario().getLetra());
											if (jComponente.getTexto() != null && jComponente.getTexto().getTraduccionLiterales() != null) {
												Literal literal = new Literal();
												literal.setCodigo(jComponente.getCodigo());
												for(  JTraduccionLiteral trad : jComponente.getTexto().getTraduccionLiterales()) {
													Traduccion traduccion = new Traduccion();
													traduccion.setIdioma(trad.getIdioma().getIdentificador());
													traduccion.setLiteral(trad.getLiteral());
													literal.add(traduccion);
												}
												((ComponenteFormularioSeccion) comp).setTexto(literal);
											}
											break;
										}
									}
								}
							}
						}
						lineas.addAll(lineasSeccion);
						break;
					} else {
						linea.getComponentes().add(componente);
					}
				}
			}

			// ordenamos lista de componentes por campo orden
			if (linea != null && !linea.getComponentes().isEmpty() && linea.getComponentes().size() > 1) {
				Collections.sort(linea.getComponentes(),
						(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));
			}

			if (sinSeccionReutilizable) {
				lineas.add(linea);
			}

		}
		return lineas;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getComponenteById(java.lang.Long)
	 */
	@Override
	public ComponenteFormulario getComponenteById(final Long pId) {
		return componenteFormularioToModel(getJElementoById(pId));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * addComponente(es.caib.sistrages.core.api.model.types.TypeObjetoFormulario,
	 * java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public ObjetoFormulario addComponente(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina,
			final Long pIdLinea, final Integer pOrden, final String pPosicion, final Object objeto, boolean isTipoSeccion, String identificadorSeccion) {
		JLineaFormulario jLineaSeleccionada = null;
		ObjetoFormulario objetoResultado = null;
		Integer nuevoOrden = null;

		if (pIdPagina != null) {
			final JPaginaFormulario jPagina = getJPaginaById(pIdPagina);

			if (pIdLinea != null) {
				jLineaSeleccionada = getJLineaById(pIdLinea);
			}

			switch (pTipoObjeto) {
			case LINEA:
				// si el orden es inferior o igual al tamaño de la lista de lineas hay que
				// hacer hueco
				// creaHuecoEnLineas(jPagina, pOrden);
				nuevoOrden = creaHuecoEntreLineas(jPagina, pOrden);
				final JLineaFormulario jLineaCreada = JLineaFormulario.createDefault(nuevoOrden, jPagina);
				entityManager.persist(jLineaCreada);
				entityManager.merge(jPagina);
				objetoResultado = jLineaCreada.toModel();
				break;
			case CAPTCHA:
				nuevoOrden = creaHuecoEntreLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaCaptcha = JLineaFormulario.createDefault(nuevoOrden, jPagina);
				if (jLineaBloqueCreadaCaptcha != null) {
					final JCampoFormularioCaptcha jObjFormSeccion = JCampoFormularioCaptcha.createDefault(1,
							jLineaBloqueCreadaCaptcha, isTipoSeccion, identificadorSeccion);

					entityManager.persist(jLineaBloqueCreadaCaptcha);
					jObjFormSeccion.getElementoFormulario().setCaptchaFormulario(jObjFormSeccion);
					entityManager.persist(jObjFormSeccion);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaCaptcha.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormSeccion.toModel());
				}
				break;
			case CAMPO_TEXTO:
				// creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				nuevoOrden = creaHuecoEntreComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioTexto jObjFormCampoTexto = JCampoFormularioTexto.createDefault(nuevoOrden,
						jLineaSeleccionada, isTipoSeccion, identificadorSeccion);
				jObjFormCampoTexto.getCampoFormulario().setCampoFormularioTexto(jObjFormCampoTexto);
				jObjFormCampoTexto.getCampoFormulario().getElementoFormulario()
						.setCampoFormulario(jObjFormCampoTexto.getCampoFormulario());
				entityManager.persist(jObjFormCampoTexto);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormCampoTexto.toModel();
				break;
			case CAMPO_OCULTO:
				nuevoOrden = creaHuecoEntreComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormulario jObjFormCampoOculto = JCampoFormulario
						.createDefault(TypeObjetoFormulario.CAMPO_OCULTO, nuevoOrden, jLineaSeleccionada, isTipoSeccion, identificadorSeccion);
				jObjFormCampoOculto.getElementoFormulario().setCampoFormulario(jObjFormCampoOculto);
				entityManager.persist(jObjFormCampoOculto);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormCampoOculto.toModel(ComponenteFormularioCampoOculto.class);
				break;
			case CHECKBOX:
				// creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				nuevoOrden = creaHuecoEntreComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioCasillaVerificacion jObjFormCasillaVerificacion = JCampoFormularioCasillaVerificacion
						.createDefault(nuevoOrden, jLineaSeleccionada, isTipoSeccion, identificadorSeccion);
				jObjFormCasillaVerificacion.getCampoFormulario()
						.setCampoFormularioCasillaVerificacion(jObjFormCasillaVerificacion);
				jObjFormCasillaVerificacion.getCampoFormulario().getElementoFormulario()
						.setCampoFormulario(jObjFormCasillaVerificacion.getCampoFormulario());
				entityManager.persist(jObjFormCasillaVerificacion);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormCasillaVerificacion.toModel();
				break;
			case SELECTOR:
				// creaHuecoEnComponentes(jLineaSeleccionada, pOrden);
				nuevoOrden = creaHuecoEntreComponentes(jLineaSeleccionada, pOrden);
				final JCampoFormularioIndexado jObjFormSelector = JCampoFormularioIndexado.createDefault(nuevoOrden,
						jLineaSeleccionada, isTipoSeccion,  identificadorSeccion);
				jObjFormSelector.getCampoFormulario().setCampoFormularioIndexado(jObjFormSelector);
				jObjFormSelector.getCampoFormulario().getElementoFormulario()
						.setCampoFormulario(jObjFormSelector.getCampoFormulario());
				entityManager.persist(jObjFormSelector);
				entityManager.merge(jLineaSeleccionada);
				objetoResultado = jObjFormSelector.toModel();
				break;
			case SECCION:
				// creaHuecoEnLineas(jPagina, pOrden);
				nuevoOrden = creaHuecoEntreLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaSeccion = JLineaFormulario.createDefault(nuevoOrden, jPagina);
				if (jLineaBloqueCreadaSeccion != null) {
					final JSeccionFormulario jObjFormSeccion = JSeccionFormulario.createDefault(1,
							jLineaBloqueCreadaSeccion, isTipoSeccion, identificadorSeccion);

					entityManager.persist(jLineaBloqueCreadaSeccion);
					jObjFormSeccion.getElementoFormulario().setSeccionFormulario(jObjFormSeccion);
					entityManager.persist(jObjFormSeccion);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaSeccion.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormSeccion.toModel());
				}
				break;
			case SECCION_REUTILIZABLE:
				// creaHuecoEnLineas(jPagina, pOrden);
				nuevoOrden = creaHuecoEntreLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaSeccionReutil = JLineaFormulario.createDefault(nuevoOrden, jPagina);
				if (jLineaBloqueCreadaSeccionReutil != null) {
					final JCampoFormularioSeccionReutilizable jObjFormSeccion = JCampoFormularioSeccionReutilizable.createDefault(1,
							jLineaBloqueCreadaSeccionReutil, isTipoSeccion, identificadorSeccion);
					Long idSeccionReutilizable = ((SeccionReutilizable)objeto).getCodigo();
					JSeccionReutilizable jseccionReutilizable = entityManager.find(JSeccionReutilizable.class, idSeccionReutilizable);
					jObjFormSeccion.setSeccionReutilizable(jseccionReutilizable);
					jObjFormSeccion.setLetra(getLetraSRU(nuevoOrden));

					entityManager.persist(jLineaBloqueCreadaSeccionReutil);
					jObjFormSeccion.getElementoFormulario().setSeccionReutilizableFormulario(jObjFormSeccion);
					entityManager.persist(jObjFormSeccion);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaSeccionReutil.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormSeccion.toModel());
				}
				break;
			case ETIQUETA:
				// creaHuecoEnLineas(jPagina, pOrden);
				nuevoOrden = creaHuecoEntreLineas(jPagina, pOrden);
				final JLineaFormulario jLineaBloqueCreadaEtiqueta = JLineaFormulario.createDefault(nuevoOrden, jPagina);
				if (jLineaBloqueCreadaEtiqueta != null) {
					final JEtiquetaFormulario jObjFormEtiqueta = JEtiquetaFormulario.createDefault(1,
							jLineaBloqueCreadaEtiqueta, isTipoSeccion, identificadorSeccion);

					jObjFormEtiqueta.getElementoFormulario().setEtiquetaFormulario(jObjFormEtiqueta);
					entityManager.persist(jLineaBloqueCreadaEtiqueta);
					entityManager.persist(jObjFormEtiqueta);
					entityManager.merge(jPagina);

					objetoResultado = jLineaBloqueCreadaEtiqueta.toModel();
					((LineaComponentesFormulario) objetoResultado).getComponentes().add(jObjFormEtiqueta.toModel());
				}
				break;

			case PAGINA:
			case LISTA_ELEMENTOS:
			case IMAGEN:
				default:
				break;
			}


		}

		return objetoResultado;

	}

	private String getLetraSRU(Integer nuevoOrden) {
		return "?";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * removeLineaFormulario(java.lang.Long)
	 */
	@Override
	public void removeLineaFormulario(final Long pId) {
		final JLineaFormulario jLinea = getJLineaById(pId);
		final JPaginaFormulario jPagina = jLinea.getPaginaFormulario();

		jPagina.removeLinea(jLinea);

		if (!jPagina.getLineasFormulario().isEmpty()) {
			final List<JLineaFormulario> lista = new ArrayList<>();
			lista.addAll(jPagina.getLineasFormulario());
			Collections.sort(lista,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));

			int orden = 1;
			for (final JLineaFormulario linea : lista) {

				if (orden != linea.getOrden()) {
					linea.setOrden(orden);
					entityManager.merge(linea);
				}

				orden++;

			}
		}

		entityManager.merge(jPagina);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * removeComponenteFormulario(java.lang.Long)
	 */
	@Override
	public void removeComponenteFormulario(final Long pId) {
		final JElementoFormulario jElemento = getJElementoById(pId);
		final JLineaFormulario jLineaFormulario = jElemento.getLineaFormulario();

		jLineaFormulario.removeElemento(jElemento);

		if (!jLineaFormulario.getElementoFormulario().isEmpty()) {

			final List<JElementoFormulario> lista = new ArrayList<>();
			lista.addAll(jLineaFormulario.getElementoFormulario());
			Collections.sort(lista,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));

			int orden = 1;
			for (final JElementoFormulario elemento : lista) {

				if (orden != elemento.getOrden()) {
					elemento.setOrden(orden);
					entityManager.merge(elemento);
				}

				orden++;

			}
		}

		entityManager.merge(jLineaFormulario);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * updateComponente(es.caib.sistrages.core.api.model.ComponenteFormulario)
	 */
	@Override
	public ObjetoFormulario updateComponente(final ComponenteFormulario pComponente) {

		ObjetoFormulario objetoResultado = null;

		if (pComponente != null) {
			final JElementoFormulario jElemento = getJElementoById(pComponente.getCodigo());

			jElemento.setAyuda(JLiteral.mergeModel(jElemento.getAyuda(), pComponente.getAyuda()));
			jElemento.setTexto(JLiteral.mergeModel(jElemento.getTexto(), pComponente.getTexto()));
			jElemento.setIdentificador(pComponente.getIdComponente());
			jElemento.setNumeroColumnas(pComponente.getNumColumnas());
			jElemento.setNoMostrarTexto(pComponente.isNoMostrarTexto());
			jElemento.setAlineacionTexto(pComponente.getAlineacionTexto().toString());

			// hacemos la parte de campo
			if (TypeObjetoFormulario.CAMPO_TEXTO.equals(pComponente.getTipo())
					|| TypeObjetoFormulario.CAMPO_OCULTO.equals(pComponente.getTipo())
					|| TypeObjetoFormulario.CHECKBOX.equals(pComponente.getTipo())
					|| TypeObjetoFormulario.SELECTOR.equals(pComponente.getTipo())) {
				final JCampoFormulario jCampo = jElemento.getCampoFormulario();
				final ComponenteFormularioCampo campo = (ComponenteFormularioCampo) pComponente;

				if (campo.getScriptAutorrellenable() == null) {
					jCampo.setScriptAutocalculado(null);
				} else {
					if (campo.getScriptAutorrellenable().getCodigo() != null) {
						jCampo.setScriptAutocalculado(
								JScript.merge(jCampo.getScriptAutocalculado(), campo.getScriptAutorrellenable()));
					} else {
						jCampo.setScriptAutocalculado(JScript.fromModel(campo.getScriptAutorrellenable()));
					}
				}

				if (campo.getScriptSoloLectura() == null) {
					jCampo.setScriptSoloLectura(null);
				} else {
					if (campo.getScriptSoloLectura().getCodigo() != null) {
						jCampo.setScriptSoloLectura(
								JScript.merge(jCampo.getScriptSoloLectura(), campo.getScriptSoloLectura()));
					} else {
						jCampo.setScriptSoloLectura(JScript.fromModel(campo.getScriptSoloLectura()));
					}
				}

				if (campo.getScriptValidacion() == null) {
					jCampo.setScriptValidaciones(null);
				} else {
					if (campo.getScriptValidacion().getCodigo() != null) {
						jCampo.setScriptValidaciones(
								JScript.merge(jCampo.getScriptValidaciones(), campo.getScriptValidacion()));
					} else {
						jCampo.setScriptValidaciones(JScript.fromModel(campo.getScriptValidacion()));
					}
				}

				jCampo.setObligatorio(campo.isObligatorio());
				jCampo.setSoloLectura(campo.isSoloLectura());
				jCampo.setNoModificable(campo.isNoModificable());
			}


			switch (pComponente.getTipo()) {
			case CAPTCHA:
				final JCampoFormularioCaptcha jCaptcha = jElemento.getCaptchaFormulario();
				//final ComponenteFormularioCampoCaptcha captcha = (ComponenteFormularioCampoCaptcha) pComponente;
				//jSeccion.setLetra(captcha.getLetra());
				entityManager.merge(jCaptcha);
				objetoResultado = jCaptcha.toModel();
				break;
			case SECCION:
				final JSeccionFormulario jSeccion = jElemento.getSeccionFormulario();
				final ComponenteFormularioSeccion seccion = (ComponenteFormularioSeccion) pComponente;
				jSeccion.setLetra(seccion.getLetra());
				entityManager.merge(jSeccion);
				objetoResultado = jSeccion.toModel();
				break;
			case SECCION_REUTILIZABLE:
				final JCampoFormularioSeccionReutilizable jSeccionReutilizable = jElemento.getSeccionReutilizableFormulario();
				final ComponenteFormularioCampoSeccionReutilizable seccionReutilizable = (ComponenteFormularioCampoSeccionReutilizable) pComponente;
				jSeccionReutilizable.setLetra(seccionReutilizable.getLetra());
				entityManager.merge(jSeccionReutilizable);
				objetoResultado = jSeccionReutilizable.toModel();
				break;
			case IMAGEN:
				final JImagenFormulario jImagen = jElemento.getImagenFormulario();
				// final ComponenteFormularioImagen imagen = (ComponenteFormularioImagen)
				// pComponente;
				entityManager.merge(jImagen);
				objetoResultado = jImagen.toModel();
				break;
			case ETIQUETA:
				final JEtiquetaFormulario jEtiqueta = jElemento.getEtiquetaFormulario();
				final ComponenteFormularioEtiqueta etiqueta = (ComponenteFormularioEtiqueta) pComponente;
				jEtiqueta.setTipo(etiqueta.getTipoEtiqueta().toString());
				entityManager.merge(jEtiqueta);
				objetoResultado = jEtiqueta.toModel();
				break;
			case CAMPO_TEXTO:
				final JCampoFormularioTexto jCampoTexto = jElemento.getCampoFormulario().getCampoFormularioTexto();
				final ComponenteFormularioCampoTexto campoTexto = (ComponenteFormularioCampoTexto) pComponente;

				jCampoTexto.setOculto(campoTexto.isOculto());
				jCampoTexto.setTipo(campoTexto.getTipoCampoTexto().name());

				switch (campoTexto.getTipoCampoTexto()) {
				case NORMAL:
					jCampoTexto.setNormalTamanyo(campoTexto.getNormalTamanyo());
					jCampoTexto.setNormalMultilinea(campoTexto.isNormalMultilinea());
					jCampoTexto.setNormalNumeroLineas(campoTexto.getNormalNumeroLineas());
					jCampoTexto.setForzarMayusculas(campoTexto.isForzarMayusculas());
					break;
				case NUMERO:
					jCampoTexto.setNumeroDigitosEnteros(campoTexto.getNumeroDigitosEnteros());
					jCampoTexto.setNumeroDigitosDecimales(campoTexto.getNumeroDigitosDecimales());
					jCampoTexto.setNumeroSeparador(campoTexto.getNumeroSeparador().toString());
					jCampoTexto.setNumeroRangoMinimo(campoTexto.getNumeroRangoMinimo());
					jCampoTexto.setNumeroRangoMaximo(campoTexto.getNumeroRangoMaximo());
					jCampoTexto.setNumeroConSigno(campoTexto.isNumeroConSigno());
					jCampoTexto.setPermiteRango(campoTexto.isPermiteRango());
					break;
				case ID:
					jCampoTexto.setIdentDni(campoTexto.isIdentDni());
					jCampoTexto.setIdentNie(campoTexto.isIdentNie());
					jCampoTexto.setIdentNifOtros(campoTexto.isIdentNifOtros());
					jCampoTexto.setIdentNif(campoTexto.isIdentNif());
					jCampoTexto.setIdentNss(campoTexto.isIdentNss());
					break;
				case TELEFONO:
					jCampoTexto.setTelefonoFijo(campoTexto.isTelefonoFijo());
					jCampoTexto.setTelefonoMovil(campoTexto.isTelefonoMovil());
					break;
				case EXPRESION:
					jCampoTexto.setNormalExpresionRegular(campoTexto.getExpresionRegular());
					break;
				case EMAIL:
					jCampoTexto.setNormalTamanyo(campoTexto.getNormalTamanyo());
					break;
				}

				jCampoTexto.setPermiteRango(campoTexto.isPermiteRango());
				entityManager.merge(jCampoTexto);
				objetoResultado = jCampoTexto.toModel();
				break;
			case CAMPO_OCULTO:
				final JCampoFormulario jCampoOculto = jElemento.getCampoFormulario();
				entityManager.merge(jCampoOculto);
				objetoResultado = jCampoOculto.toModel(ComponenteFormularioCampoOculto.class);
				break;
			case CHECKBOX:
				final JCampoFormularioCasillaVerificacion jCampoCheckbox = jElemento.getCampoFormulario()
						.getCampoFormularioCasillaVerificacion();
				final ComponenteFormularioCampoCheckbox campoCheckbox = (ComponenteFormularioCampoCheckbox) pComponente;

				jCampoCheckbox.setValorChecked(campoCheckbox.getValorChecked());
				jCampoCheckbox.setValorNoChecked(campoCheckbox.getValorNoChecked());

				entityManager.merge(jCampoCheckbox);
				objetoResultado = jCampoCheckbox.toModel();
				break;
			case SELECTOR:
				JCampoFormularioIndexado jCampoIndexado = jElemento.getCampoFormulario().getCampoFormularioIndexado();
				final ComponenteFormularioCampoSelector campoIndexado = (ComponenteFormularioCampoSelector) pComponente;

				jCampoIndexado.setTipoCampoIndexado(campoIndexado.getTipoCampoIndexado().name());
				jCampoIndexado.setTipoListaValores(campoIndexado.getTipoListaValores().toString());
				jCampoIndexado.setIndiceAlfabetico(campoIndexado.isIndiceAlfabetico());
				jCampoIndexado.setOrientacion(campoIndexado.getOrientacion());
				if (TypeListaValores.FIJA.equals(campoIndexado.getTipoListaValores())) {
					jCampoIndexado = JCampoFormularioIndexado.mergeListaValoresFijaModel(jCampoIndexado, campoIndexado);
				} else if (!campoIndexado.getListaValorListaFija().isEmpty()) {
					// no es fija y hay datos por lo que hay que eliminarlos
					campoIndexado.getListaValorListaFija().clear();
					jCampoIndexado = JCampoFormularioIndexado.mergeListaValoresFijaModel(jCampoIndexado, campoIndexado);
				}

				if (TypeListaValores.DOMINIO.equals(campoIndexado.getTipoListaValores())) {
					JDominio dominio = null;
					if (campoIndexado.getCodDominio() != null) {
						dominio = entityManager.find(JDominio.class, campoIndexado.getCodDominio());
					}
					jCampoIndexado.setDominio(dominio);
					jCampoIndexado.setCampoDominioCodigo(campoIndexado.getCampoDominioCodigo());
					jCampoIndexado.setCampoDominioDescripcion(campoIndexado.getCampoDominioDescripcion());
					jCampoIndexado = JCampoFormularioIndexado.mergeListaParametrosDominioModel(jCampoIndexado,
							campoIndexado);
				} else {
					jCampoIndexado.setDominio(null);
					jCampoIndexado.setCampoDominioCodigo(null);
					jCampoIndexado.setCampoDominioDescripcion(null);
					campoIndexado.getListaParametrosDominio().clear();
					jCampoIndexado = JCampoFormularioIndexado.mergeListaParametrosDominioModel(jCampoIndexado,
							campoIndexado);
				}

				if (TypeListaValores.SCRIPT.equals(campoIndexado.getTipoListaValores())) {
					if (campoIndexado.getScriptValoresPosibles() == null) {
						jCampoIndexado.setScriptValoresPosibles(null);
					} else {
						if (campoIndexado.getScriptValoresPosibles().getCodigo() != null) {
							final JScript scriptValores = JScript.merge(jCampoIndexado.getScriptValoresPosibles(),
									campoIndexado.getScriptValoresPosibles());
							jCampoIndexado.setScriptValoresPosibles(scriptValores);
						} else {
							jCampoIndexado.setScriptValoresPosibles(
									JScript.fromModel(campoIndexado.getScriptValoresPosibles()));
						}
					}
				}

				entityManager.merge(jCampoIndexado);
				objetoResultado = jCampoIndexado.toModel();
				break;
			}

		}

		return objetoResultado;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * updateOrdenComponente(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateOrdenComponente(final Long pId, final Integer pOrden) {
		final JElementoFormulario jElemento = getJElementoById(pId);
		jElemento.setOrden(pOrden);
		entityManager.merge(jElemento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * updateOrdenLinea(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateOrdenLinea(final Long pId, final Integer pOrden) {
		final JLineaFormulario jLinea = getJLineaById(pId);
		jLinea.setOrden(pOrden);
		entityManager.merge(jLinea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * getListaPlantillaIdiomaFormularioById(java.lang.Long)
	 */
	@Override
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(final Long pId) {
		final List<PlantillaIdiomaFormulario> lista = new ArrayList<>();

		final JPlantillaFormulario jPlantilla = getJPlantillaFormularioById(pId);
		for (final JPlantillaIdiomaFormulario jPlantillaIdioma : jPlantilla.getPlantillaIdiomaFormulario()) {
			lista.add(jPlantillaIdioma.toModel());
		}

		return lista;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * uploadPlantillaIdiomaFormulario(java.lang.Long,
	 * es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario)
	 */
	@Override
	public PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(final Long idPlantilla,
			final PlantillaIdiomaFormulario plantilla) {
		JPlantillaIdiomaFormulario jPlantillaIdioma;

		if (plantilla.getCodigo() != null) {
			jPlantillaIdioma = entityManager.find(JPlantillaIdiomaFormulario.class, plantilla.getCodigo());
			if (jPlantillaIdioma.getFichero() != null) {
				jPlantillaIdioma.getFichero().setNombre(plantilla.getFichero().getNombre());
				jPlantillaIdioma.getFichero().setPublico(plantilla.getFichero().isPublico());
			} else {
				jPlantillaIdioma.setFichero(JFichero.fromModel(plantilla.getFichero()));
			}
			entityManager.merge(jPlantillaIdioma);
		} else {
			jPlantillaIdioma = JPlantillaIdiomaFormulario.fromModel(plantilla);
			jPlantillaIdioma.setPlantillaFormulario(entityManager.find(JPlantillaFormulario.class, idPlantilla));
			jPlantillaIdioma.setIdioma(entityManager.find(JIdioma.class, plantilla.getIdioma()));
			entityManager.persist(jPlantillaIdioma);
		}

		return jPlantillaIdioma.toModel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.service.repository.dao.FormularioInternoDao#
	 * removePlantillaIdiomaFormulario(java.lang.Long)
	 */
	@Override
	public void removePlantillaIdiomaFormulario(final Long idPlantilla) {
		final JPlantillaIdiomaFormulario jPlantillaIdioma = entityManager.find(JPlantillaIdiomaFormulario.class,
				idPlantilla);
		if (jPlantillaIdioma == null) {
			throw new NoExisteDato("No existe plantilla idioma " + idPlantilla);
		}

		entityManager.remove(jPlantillaIdioma);

	}

	private JFormulario getJFormularioById(final Long pId) {
		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jForm = entityManager.find(JFormulario.class, pId);

		if (jForm == null) {
			throw new NoExisteDato(NO_EXISTE_FORMINT + pId);
		}
		return jForm;
	}

	private JPaginaFormulario getJPaginaById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JPaginaFormulario jPagina = entityManager.find(JPaginaFormulario.class, pId);

		if (jPagina == null) {
			throw new NoExisteDato(NO_EXISTE_PAGINA + pId);
		}

		return jPagina;
	}

	private JLineaFormulario getJLineaById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JLineaFormulario jLinea = entityManager.find(JLineaFormulario.class, pId);

		if (jLinea == null) {
			throw new NoExisteDato(NO_EXISTE_LINEA + pId);
		}

		return jLinea;
	}

	private JElementoFormulario getJElementoById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_COMPONENTE);
		}

		final JElementoFormulario jElemento = entityManager.find(JElementoFormulario.class, pId);

		if (jElemento == null) {
			throw new NoExisteDato(NO_EXISTE_COMPONENTE + ": " + pId);
		}

		return jElemento;
	}

	private JPlantillaFormulario getJPlantillaFormularioById(final Long pId) {

		if (pId == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JPlantillaFormulario jPlantilla = entityManager.find(JPlantillaFormulario.class, pId);

		if (jPlantilla == null) {
			throw new NoExisteDato(NO_EXISTE_PLANTILLA + pId);
		}

		return jPlantilla;
	}

	/**
	 * pasa un componente de BD a modelo.
	 *
	 * @param jComponente componente de bd
	 * @return componente de modelo
	 */
	private ComponenteFormulario componenteFormularioToModel(final JElementoFormulario jComponente) {
		ComponenteFormulario componente = null;
		// miramos si es seccion
		if (jComponente.getSeccionFormulario() != null) {
			componente = jComponente.getSeccionFormulario().toModel();
		}

		//miramos si es una seccion reutilizable
		if (jComponente.getSeccionReutilizableFormulario() != null) {
			componente = jComponente.getSeccionReutilizableFormulario().toModel();
		}


		// miramos si es seccion
		if (jComponente.getCaptchaFormulario() != null) {
			componente = jComponente.getCaptchaFormulario().toModel();
		}

		// miramos si es imagen
		if (jComponente.getImagenFormulario() != null) {
			componente = jComponente.getImagenFormulario().toModel();
		}

		// miramos si es etiqueta
		if (jComponente.getEtiquetaFormulario() != null) {
			componente = jComponente.getEtiquetaFormulario().toModel();
		}

		// miramos si es campo de texto
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioTexto() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioTexto().toModel();
		}

		// miramos si es campo de checkbox
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioCasillaVerificacion() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioCasillaVerificacion().toModel();
		}

		// miramos si es campo selector
		if (jComponente.getCampoFormulario() != null
				&& jComponente.getCampoFormulario().getCampoFormularioIndexado() != null) {
			componente = jComponente.getCampoFormulario().getCampoFormularioIndexado().toModel();
		}

		// miramos si es campo oculto
		if (jComponente.getCampoFormulario() != null
				&& TypeObjetoFormulario.fromString(jComponente.getTipo()).equals(TypeObjetoFormulario.CAMPO_OCULTO)) {
			componente = jComponente.getCampoFormulario().toModel(ComponenteFormularioCampoOculto.class);
		}

		return componente;
	}

	/**
	 * Crea un hueco en la lista de lineas.
	 *
	 * @param pJPagina pagina que tiene la lista de lineas
	 * @param pInicio  inicio para el hueco
	 * @return true, si tiene exito
	 */
	private boolean creaHuecoEnLineas(final JPaginaFormulario pJPagina, final Integer pInicio) {
		return actualizaEntreLineas(pJPagina, pInicio, 1);
	}

	private boolean quitaHuecoEnLineas(final JPaginaFormulario pJPagina, final Integer pInicio) {
		return actualizaEntreLineas(pJPagina, pInicio, -1);
	}

	private boolean actualizaEntreLineas(final JPaginaFormulario pJPagina, final Integer pInicio,
			final Integer pFactor) {
		boolean resultado = false;

		// creamos hueco si es necesario
		if (!pJPagina.getLineasFormulario().isEmpty() && pInicio <= pJPagina.getLineasFormulario().size()) {
			final String sql = "UPDATE JLineaFormulario p SET p.orden = p.orden + :factor WHERE p.paginaFormulario.codigo = :idPagina and p.orden >= :inicio";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("factor", pFactor);
			query.setParameter("idPagina", pJPagina.getCodigo());
			query.setParameter("inicio", pInicio);
			final int update = query.executeUpdate();
			resultado = (update > 0);
		}

		return resultado;
	}

	private Integer creaHuecoEntreLineas(final JPaginaFormulario pJPagina, final Integer pInicio) {
		Integer pOrden = pInicio;
		if (!pJPagina.getLineasFormulario().isEmpty()) {
			final List<JLineaFormulario> lista = new ArrayList<>();
			lista.addAll(pJPagina.getLineasFormulario());
			Collections.sort(lista,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));

			int orden = 1;
			for (final JLineaFormulario linea : lista) {
				if (pInicio != null && pInicio == linea.getOrden()) {
					pOrden = orden;
					orden++;
				}

				if (orden != linea.getOrden()) {
					linea.setOrden(orden);
					entityManager.merge(linea);
				}

				orden++;

			}
		}
		return pOrden;
	}

	/**
	 * Crea un hueco en la lista de componentes.
	 *
	 * @param pJLineaSeleccionada linea seleccionada
	 * @param pInicio             inicio
	 * @return true, si tiene exito
	 */
	private boolean actualizaEntreComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio,
			final Integer pFactor) {
		boolean resultado = false;

		// creamos hueco si es necesario
		if (!pJLineaSeleccionada.getElementoFormulario().isEmpty()
				&& pInicio <= pJLineaSeleccionada.getElementoFormulario().size()) {
			final String sql = "UPDATE JElementoFormulario p SET p.orden = p.orden + :factor WHERE p.lineaFormulario.codigo = :idLinea and p.orden >= :inicio";
			final Query query = entityManager.createQuery(sql);
			query.setParameter("factor", pFactor);
			query.setParameter("idLinea", pJLineaSeleccionada.getCodigo());
			query.setParameter("inicio", pInicio);
			final int update = query.executeUpdate();
			resultado = (update > 0);
		}
		return resultado;
	}

	private boolean creaHuecoEnComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio) {
		return actualizaEntreComponentes(pJLineaSeleccionada, pInicio, 1);
	}

	private boolean quitaHuecoEnComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio) {
		return actualizaEntreComponentes(pJLineaSeleccionada, pInicio, -1);
	}

	private Integer creaHuecoEntreComponentes(final JLineaFormulario pJLineaSeleccionada, final Integer pInicio) {
		Integer pOrden = pInicio;
		if (!pJLineaSeleccionada.getElementoFormulario().isEmpty()) {
			final List<JElementoFormulario> lista = new ArrayList<>();
			lista.addAll(pJLineaSeleccionada.getElementoFormulario());
			Collections.sort(lista,
					(o1, o2) -> Integer.valueOf(o1.getOrden()).compareTo(Integer.valueOf(o2.getOrden())));

			int orden = 1;
			for (final JElementoFormulario elemento : lista) {
				if (pInicio != null && pInicio == elemento.getOrden()) {
					pOrden = orden;
					orden++;
				}

				if (orden != elemento.getOrden()) {
					elemento.setOrden(orden);
					entityManager.merge(elemento);
				}

				orden++;

			}
		}
		return pOrden;
	}

	@Override
	public Long addPagina(final Long idFormulario, final PaginaFormulario paginaFormulario) {

		if (idFormulario == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jFormulario = entityManager.find(JFormulario.class, idFormulario);
		final JPaginaFormulario jpagina = JPaginaFormulario.fromModel(paginaFormulario);
		jpagina.setOrden(paginaFormulario.getOrden());
		jpagina.setPaginaFinal(paginaFormulario.isPaginaFinal());
		jpagina.setIdentificador(paginaFormulario.getIdentificador());
		if (paginaFormulario.getScriptValidacion() != null) {
			jpagina.setScriptValidacion(JScript.fromModel(Script.clonar(paginaFormulario.getScriptValidacion())));
		}
		jpagina.setFormulario(jFormulario);
		entityManager.persist(jpagina);
		return jpagina.getCodigo();
	}

	@Override
	public Long addPlantilla(final Long idFormulario, final PlantillaFormulario mplantilla) {
		if (idFormulario == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		final JFormulario jFormulario = entityManager.find(JFormulario.class, idFormulario);
		final JPlantillaFormulario jplantilla = JPlantillaFormulario.fromModel(mplantilla);
		jplantilla.setFormulario(jFormulario);
		entityManager.persist(jplantilla);
		return jplantilla.getCodigo();
	}

	@Override
	public void removeFormulario(final Long codigo) {
		final JFormulario jFormulario = entityManager.find(JFormulario.class, codigo);
		if (jFormulario.getPaginas() != null) {
			for (final JPaginaFormulario pagina : jFormulario.getPaginas()) {
				if (pagina.getScriptNavegacion() != null) {
					if (pagina.getScriptNavegacion().getLiterales() != null && !pagina.getScriptNavegacion().getLiterales().isEmpty()) {
						for(JLiteralErrorScript literal : pagina.getScriptNavegacion().getLiterales()) {
							entityManager.remove(literal);
						}
					}
					pagina.getScriptNavegacion().setLiterales(null);
					entityManager.remove(pagina.getScriptNavegacion());
				}

				if (pagina.getScriptValidacion() != null) {
					if (pagina.getScriptValidacion().getLiterales() != null && !pagina.getScriptValidacion().getLiterales().isEmpty()) {
						for(JLiteralErrorScript literal : pagina.getScriptValidacion().getLiterales()) {
							entityManager.remove(literal);
						}
					}
					pagina.getScriptValidacion().setLiterales(null);
					entityManager.remove(pagina.getScriptValidacion());

				}
				entityManager.remove(pagina);
			}
		}

		if (jFormulario.getPlantillas() != null) {
			for (final JPlantillaFormulario plantilla : jFormulario.getPlantillas()) {
				if (plantilla.getPlantillaIdiomaFormulario() != null) {
					for (final JPlantillaIdiomaFormulario plantillaIdioma : plantilla.getPlantillaIdiomaFormulario()) {
						entityManager.remove(plantillaIdioma);
					}
				}
				entityManager.remove(plantilla);
			}
		}

		if (jFormulario.getPlantillas() != null) {

			for (final JAccionPersonalizada accion : jFormulario.getAccionesPersonalizadas()) {
				entityManager.remove(accion);
			}
		}

		entityManager.remove(jFormulario);
	}

	@Override
	public DisenyoFormularioSimple getFormularioInternoSimple(final Long idFormularioTramite, final Long idFormulario,
			final String idComponente, final String idPaginaActual, final boolean cargarPaginasPosteriores, final boolean isSeccion, final String identificadorSeccion) {
		final DisenyoFormularioSimple disenyo = new DisenyoFormularioSimple();
		final String sqlPaginas;

		if (idFormularioTramite != null) {
			sqlPaginas = "Select pagina.codigo, pagina.identificador, pagina.orden from JPaginaFormulario pagina where pagina.formulario.codigo in (select formTra.formulario.codigo from JFormularioTramite formTra where formTra.codigo = :idFormularioTramite ) order by pagina.orden asc ";
		} else {
			sqlPaginas = "Select pagina.codigo, pagina.identificador, pagina.orden from JPaginaFormulario pagina where pagina.formulario.codigo = :idFormulario order by pagina.orden asc ";
		}
		final Query query = entityManager.createQuery(sqlPaginas);
		if (idFormularioTramite != null) {
			query.setParameter("idFormularioTramite", idFormularioTramite);
		} else {
			query.setParameter("idFormulario", idFormulario);
		}
		final List<Object[]> idPaginas = query.getResultList();
		boolean salir = false;

		if (!idPaginas.isEmpty()) {
			boolean cargarComponentes = true;
			for (final Object[] objectPagina : idPaginas) {

				final DisenyoFormularioPaginaSimple pagina = new DisenyoFormularioPaginaSimple();
				final Long idPagina = (Long) objectPagina[0];
				pagina.setCodigo(idPagina);
				if (objectPagina[1] != null) {
					pagina.setAlias((String) objectPagina[1]);
				}
				if (objectPagina[2] != null) {
					pagina.setOrden((Integer) objectPagina[2]);
				}

				// Filtramos por tipo (solo los que son de tipo textbox, checkbox, captcha y
				// selector)
				if (cargarComponentes) {
					final String sqlComponentes = "select elemento.codigo, elemento.identificador, identificador.tipo from JElementoFormulario elemento where elemento.lineaFormulario.paginaFormulario.codigo = :idPagina and elemento.tipo IN ('CT','SE','CK','CP','OC') order by elemento.lineaFormulario.orden, elemento.orden ";
					final Query queryComponentes = entityManager.createQuery(sqlComponentes);
					queryComponentes.setParameter("idPagina", idPagina);
					final List<Object[]> componentes = queryComponentes.getResultList();
					if (!componentes.isEmpty()) {
						for (final Object[] comp : componentes) {

							final DisenyoFormularioComponenteSimple componente = new DisenyoFormularioComponenteSimple();

							final Long codigo = Long.valueOf(comp[0].toString());
							final String identificador = comp[1].toString();
							final String tipo = comp[2].toString();

							componente.setIdentificador(identificador);
							componente.setTipo(TypeObjetoFormulario.fromString(tipo));

							pagina.getComponentes().add(componente);

							if (idComponente != null && codigo.equals(Long.valueOf(idComponente))) {
								salir = true;
								break;
							}
						}

					}
				}

				// Será la página seleccionable dependiendo si cargarComponentes esta a false
				// (significa que no tiene y la pagina se puede seleccionar) o todo lo contrario
				pagina.setSeleccionable(!cargarComponentes);
				disenyo.getPaginas().add(pagina);

				/**
				 * Salir por componente (salir==true) o por misma pagina (y no activo lo de
				 * cargarPaginasPosteriores).
				 **/
				if (salir) {
					break;
				}

				if (idPaginaActual != null && Long.valueOf(idPaginaActual).compareTo(idPagina) == 0) {
					if (cargarPaginasPosteriores) {
						cargarComponentes = false;
					} else {
						break;
					}
				}
			}

		}

		final String sqlIdentificador;

		if (isSeccion) {
			disenyo.setIdentificador(identificadorSeccion);
		} else {
			if (idFormularioTramite != null) {
				sqlIdentificador = "Select formTramite.identificador from JFormularioTramite formTramite where formTramite.codigo = :idFormularioTramite  ";
			} else {
				sqlIdentificador = "Select formTramite.identificador from JFormularioTramite formTramite where formTramite.formulario.codigo = :idFormulario  ";
			}

			final Query queryIdentificador = entityManager.createQuery(sqlIdentificador);

			if (idFormularioTramite != null) {
				queryIdentificador.setParameter("idFormularioTramite", idFormularioTramite);
			} else {
				queryIdentificador.setParameter("idFormulario", idFormulario);
			}

			final String identificador = (String) queryIdentificador.getSingleResult();
			disenyo.setIdentificador(identificador);
		}



		return disenyo;

	}

	@Override
	public String getIdentificadorFormularioInterno(final Long idFormulario) {
		final String sqlPaginas = "Select form.identificador from JFormularioTramite form where form.formulario.codigo = :idFormulario ";
		final Query query = entityManager.createQuery(sqlPaginas);
		query.setParameter("idFormulario", idFormulario);

		String resultado = null;

		List<String> oresultado = query.getResultList();
		if (oresultado != null && oresultado.size() > 0) {
			resultado = oresultado.get(0).toString();
		}
		return resultado;
	}

	@Override
	public boolean isIdElementoFormularioDuplicated(final Long idFormulario, final Long codElemento,
			final String identificador) {
		final String sql = "Select e from JElementoFormulario e where e.lineaFormulario.paginaFormulario.formulario.codigo = :idFormulario and"
				+ " e.codigo <> :codElemento and e.identificador = :identificador ";

		final Query query = entityManager.createQuery(sql);
		query.setParameter("idFormulario", idFormulario);
		query.setParameter("codElemento", codElemento);
		query.setParameter("identificador", identificador);

		final boolean res = !query.getResultList().isEmpty();

		return res;

	}

	@Override
	public ObjetoFormulario copyCutComponenteFormulario(final Long idPagina, final Long idLinea, final Integer orden,
			final String posicion, final Long idComponenteOriginal, final boolean cut) {

		// Obtenemos el componente original y añadimos un componente del mismo tipo
		final JElementoFormulario componenteOriginal = entityManager.find(JElementoFormulario.class,
				idComponenteOriginal);
		final JLineaFormulario jlineaFormulario = entityManager.find(JLineaFormulario.class, idLinea);
		final JPaginaFormulario jpagina = entityManager.find(JPaginaFormulario.class, idPagina);
		final JElementoFormulario elem = JElementoFormulario.clonar(componenteOriginal, jlineaFormulario, jpagina,
				false);
		elem.setOrden(orden);
		if (cut) {
			elem.setIdentificador(elem.getIdentificador());
		} else {
			if (elem.getIdentificador().startsWith("SRE_")) {
				int posicionGuion = elem.getIdentificador().indexOf('_', 4);
				elem.setIdentificador(elem.getIdentificador().substring(0, posicionGuion+1) + System.currentTimeMillis());
			} else {
				elem.setIdentificador("ID_" + System.currentTimeMillis());
			}
		}
		entityManager.persist(elem);
		entityManager.flush();

		// Reordenamos (ya que ha sido introducido en una posición, que puede ser en
		// medio de otros componentes,
		// y debemos desplazar una posición todos los elementos que estén a su derecha)
		reordenar(idLinea, elem.getCodigo(), orden);

		if (cut) { // Si se corta, hay que borrar el componente original
			this.removeComponenteFormulario(idComponenteOriginal);
		}

		return getComponenteById(elem.getCodigo());
	}

	/**
	 * Método privado que desplaza todos los elementos de una linea que sean mayores
	 * a un orden. La idea es que se ha pegado un elemento en una posición y tots
	 * los de su derecha tendrían que moverse una posición para hacerle hueco.
	 *
	 * @param idLinea    Id de la linea.
	 * @param idElemento Id del elemento recién copiado que debe mantener su
	 *                   posición
	 * @param orden      Posición a partir del cual hay que mover al resto
	 */
	private void reordenar(final Long idLinea, final Long idElemento, final int orden) {
		final JLineaFormulario jlineaFormulario = entityManager.find(JLineaFormulario.class, idLinea);
		for (final JElementoFormulario elemento : jlineaFormulario.getElementoFormulario()) {
			if (elemento.getOrden() >= orden && elemento.getCodigo().compareTo(idElemento) != 0) {
				elemento.setOrden(elemento.getOrden() + 1);
				entityManager.merge(elemento);
			}
		}
	}

	@Override
	public ObjetoFormulario copyCutLineaFormulario(final Long idPagina, final Long idLinea, final Integer orden,
			final String posicionamiento, final boolean cut) {
		final JLineaFormulario jlineaFormularioOriginal = entityManager.find(JLineaFormulario.class, idLinea);
		final JPaginaFormulario jpagina = entityManager.find(JPaginaFormulario.class, idPagina);
		final JLineaFormulario jlinea = JLineaFormulario.clonar(jlineaFormularioOriginal, jpagina, false);
		jlinea.setOrden(orden);
		if (!cut) { // Vamos, copiamos y por tanto, hay que recalcular el identificador de todos los
					// componentes.
			int contador = 0; // El contador es pq parece que el system.currentTimeMillis() es el mismo en
								// todos los identificador
			for (final JElementoFormulario elemento : jlinea.getElementoFormulario()) {
				elemento.setIdentificador("ID_" + contador + System.currentTimeMillis());
				contador++;
			}
		}
		entityManager.persist(jlinea);
		entityManager.flush();

		// Reordenamos (ya que ha sido introducido en una posición, que puede ser en
		// medio de otras lineas,
		// y debemos desplazar una posición todas las lineas que estén por debajo)
		reordenarLinea(idPagina, jlinea.getCodigo(), orden);

		if (cut) { // Si se corta, hay que borrar el componente original
			this.removeLineaFormulario(idLinea);
		}
		final LineaComponentesFormulario linea = jlinea.toModel();
		for (final JElementoFormulario elemento : jlinea.getElementoFormulario()) {
			if (elemento.getSeccionFormulario() != null) {
				linea.getComponentes().add(elemento.getSeccionFormulario().toModel());
			}
			if (elemento.getEtiquetaFormulario() != null) {
				linea.getComponentes().add(elemento.getEtiquetaFormulario().toModel());
			}
			if (elemento.getImagenFormulario() != null) {
				linea.getComponentes().add(elemento.getImagenFormulario().toModel());
			}
			if (elemento.getCampoFormulario() != null
					&& elemento.getCampoFormulario().getCampoFormularioCasillaVerificacion() != null) {
				linea.getComponentes()
						.add(elemento.getCampoFormulario().getCampoFormularioCasillaVerificacion().toModel());
			}
			if (elemento.getCampoFormulario() != null
					&& elemento.getCampoFormulario().getCampoFormularioIndexado() != null) {
				linea.getComponentes().add(elemento.getCampoFormulario().getCampoFormularioIndexado().toModel());
			}
			if (elemento.getCampoFormulario() != null
					&& elemento.getCampoFormulario().getCampoFormularioTexto() != null) {
				linea.getComponentes().add(elemento.getCampoFormulario().getCampoFormularioTexto().toModel());
			}

			if (elemento.getCampoFormulario() != null
					&& TypeObjetoFormulario.fromString(elemento.getTipo()).equals(TypeObjetoFormulario.CAMPO_OCULTO)) {
				linea.getComponentes()
						.add(elemento.getCampoFormulario().toModel(ComponenteFormularioCampoOculto.class));
			}

		}

		return linea;
	}

	/**
	 * Método privado que desplaza todas las líneas de una página que sean mayores a
	 * un orden. La idea es que se ha pegado una linea en una posición y tots los de
	 * abajo tendrían que moverse una posicion.
	 *
	 * @param idPagina Id de la página
	 * @param idLinea  Id de la linea que se ha copiado
	 * @param orden    Orden donde se ha copiado la linea a copiar
	 */
	private void reordenarLinea(final Long idPagina, final Long idLinea, final int orden) {
		final JPaginaFormulario jpagina = entityManager.find(JPaginaFormulario.class, idPagina);
		for (final JLineaFormulario linea : jpagina.getLineasFormulario()) {
			if (linea.getOrden() >= orden && linea.getCodigo().compareTo(idLinea) != 0) {
				linea.setOrden(linea.getOrden() + 1);
				entityManager.merge(linea);
			}
		}
	}

	@Override
	public void borrarReferencias(final Long idTramite, final Long idAreaAntigua) {

		// Obtenemos los ids de formularios que pertenezcan al tramite
		final List<Long> idTramitesFormularios = getTramitesFormularioByIdtramite(idTramite);

		// Buscamos todos los componentes de tipo selector.
		for (final Long idTramiteFormulario : idTramitesFormularios) {
			final List<JCampoFormularioIndexado> selectores = getSelectoresByIdTramite(idTramiteFormulario);

			// Miramos en todos los selector, cual tiene un selector con un dominio del area
			// antigua y lo
			// quitamos
			for (final JCampoFormularioIndexado selector : selectores) {
				if (selector.getDominio() != null
						&& selector.getDominio().getAmbito().equals(TypeAmbito.AREA.toString())
						&& selector.getDominio().getArea() != null) {
					boolean contieneArea = false;
					if (selector.getDominio().getArea() != null && selector.getDominio().getArea().getCodigo().compareTo(idAreaAntigua) == 0) {
							contieneArea = true;
					}

					if (contieneArea) {
						selector.setDominio(null);
						entityManager.merge(selector);
					}
				}
			}
		}
	}

	/**
	 * Devuelve los selectores que pertenecen al
	 *
	 * @param idTramite
	 * @return
	 */
	private List<Long> getTramitesFormularioByIdtramite(final Long idTramite) {

		final String sqlTramites = "Select distinct formularioTram.formulario.codigo from  JFormularioTramite formularioTram inner join formularioTram.pasosRellenar c where c.pasoTramitacion.versionTramite.tramite.codigo = :idTramite";
		final Query query = entityManager.createQuery(sqlTramites);
		query.setParameter("idTramite", idTramite);

		return query.getResultList();
	}

	/**
	 * Devuelve los selectores que pertenecen al
	 *
	 * @param idTramite
	 * @return
	 */
	private List<JCampoFormularioIndexado> getSelectoresByIdTramite(final Long idTramite) {

		final String sqlSelectores = "Select distinct selector from JCampoFormularioIndexado selector where selector.campoFormulario.elementoFormulario.lineaFormulario.paginaFormulario.formulario.codigo = :idTramite ";
		final Query query = entityManager.createQuery(sqlSelectores);
		query.setParameter("idTramite", idTramite);

		return query.getResultList();
	}

	@Override
	public void updatePagina(final PaginaFormulario paginaFormulario) {

		if (paginaFormulario != null && paginaFormulario.getCodigo() == null) {
			throw new FaltanDatosException(FALTA_ID);
		}

		if (paginaFormulario != null && paginaFormulario.getCodigo() != null) {
			final JPaginaFormulario jpagina = entityManager.find(JPaginaFormulario.class, paginaFormulario.getCodigo());

			jpagina.setOrden(paginaFormulario.getOrden());
			jpagina.setIdentificador(paginaFormulario.getIdentificador());
			jpagina.setPaginaFinal(paginaFormulario.isPaginaFinal());
			if (paginaFormulario.getScriptValidacion() != null) {
				jpagina.setScriptValidacion(JScript.fromModel(Script.clonar(paginaFormulario.getScriptValidacion())));
			}
			if (paginaFormulario.getScriptNavegacion() != null) {
				jpagina.setScriptNavegacion(JScript.fromModel(Script.clonar(paginaFormulario.getScriptNavegacion())));
			}

			entityManager.merge(jpagina);
			entityManager.flush();
		}
	}

	@Override
	public List<ScriptInfo> getScriptsInfo(final Long idFormularioInterno, final FormularioTramite formulario) {
		final List<ScriptInfo> scripts = new ArrayList<>();

		// JCampoFormularioIndexado
		final String sqlCampoFormularioIndexado = "Select distinct campo.campoFormulario.elementoFormulario.identificador, campo.campoFormulario.elementoFormulario.tipo , campo.scriptValoresPosibles.codigo  from JCampoFormularioIndexado campo where campo.campoFormulario.elementoFormulario.lineaFormulario.paginaFormulario.formulario.codigo = :idFormularioInterno and campo.scriptValoresPosibles is not null";
		final Query queryCFI = entityManager.createQuery(sqlCampoFormularioIndexado);
		queryCFI.setParameter("idFormularioInterno", idFormularioInterno);
		final List<Object[]> resultadosCFI = queryCFI.getResultList();
		if (resultadosCFI != null) {
			for (final Object[] resultadoCFI : resultadosCFI) {
				final String identificador = resultadoCFI[0].toString();
				final String tipo = resultadoCFI[1].toString();
				final Long idScript = (Long) resultadoCFI[2];
				scripts.add(new ScriptInfo(idScript, TypeScriptFormulario.SCRIPT_VALORES_POSIBLES, formulario,
						ScriptInfo.TYPE_COMPONENTE.SELECTOR, identificador));
			}
		}

		// JCampoFormulario
		final String sqlCampoFormulario = "Select distinct campo.elementoFormulario.identificador, campo.elementoFormulario.tipo , campo.scriptAutocalculado.codigo , campo.scriptSoloLectura.codigo , campo.scriptValidaciones.codigo from JCampoFormulario campo where campo.elementoFormulario.lineaFormulario.paginaFormulario.formulario.codigo = :idFormularioInterno and (campo.scriptAutocalculado is not null or campo.scriptSoloLectura is not null or campo.scriptValidaciones is not null )";
		final Query queryCF = entityManager.createQuery(sqlCampoFormulario);
		queryCF.setParameter("idFormularioInterno", idFormularioInterno);
		final List<Object[]> resultadosCF = queryCF.getResultList();
		if (resultadosCF != null) {
			for (final Object[] resultadoCF : resultadosCF) {
				final String identificador = resultadoCF[0].toString();
				final String tipo = resultadoCF[1].toString();
				final Long idScript1 = (Long) resultadoCF[2];
				final Long idScript2 = (Long) resultadoCF[3];
				final Long idScript3 = (Long) resultadoCF[4];

				if (idScript1 != null) {
					scripts.add(new ScriptInfo(idScript1, TypeScriptFormulario.SCRIPT_AUTORELLENABLE, formulario,
							ScriptInfo.getEnum(tipo), identificador));
				}
				if (idScript2 != null) {
					scripts.add(new ScriptInfo(idScript2, TypeScriptFormulario.SCRIPT_ESTADO, formulario,
							ScriptInfo.getEnum(tipo), identificador));
				}
				if (idScript3 != null) {
					scripts.add(new ScriptInfo(idScript3, TypeScriptFormulario.SCRIPT_VALIDACION_CAMPO, formulario,
							ScriptInfo.getEnum(tipo), identificador));
				}
			}
		}

		// JPaginaFormulario
		final String sqlPagina = "Select distinct paginaFormulario.orden, paginaFormulario.scriptValidacion.codigo from JPaginaFormulario paginaFormulario where paginaFormulario.formulario.codigo = :idFormularioInterno and paginaFormulario.scriptValidacion is not null";
		final Query queryPAG = entityManager.createQuery(sqlPagina);
		queryPAG.setParameter("idFormularioInterno", idFormularioInterno);
		final List<Object[]> resultadosPAG = queryPAG.getResultList();
		if (resultadosPAG != null) {
			for (final Object[] resultadoPAG : resultadosPAG) {
				final String orden = resultadoPAG[0].toString();
				final Long idScript = (Long) resultadoPAG[1];
				scripts.add(new ScriptInfo(idScript, TypeScriptFormulario.SCRIPT_VALIDACION_PAGINA, formulario,
						ScriptInfo.TYPE_COMPONENTE.PAGINA, orden));
			}
		}

		// JPaginaFormulario
		final String sqlFormulario = "Select distinct formulario.scriptPlantilla.codigo  from JFormulario formulario where formulario.codigo = :idFormularioInterno and formulario.scriptPlantilla is not null";
		final Query queryFORM = entityManager.createQuery(sqlFormulario);
		queryFORM.setParameter("idFormularioInterno", idFormularioInterno);
		final List<Long> resultadosFORM = queryFORM.getResultList();
		if (resultadosFORM != null) {
			for (final Long idScript : resultadosFORM) {
				// final Long idScript = resultadoFR[0];
				scripts.add(new ScriptInfo(idScript, TypeScriptFormulario.SCRIPT_PLANTILLA_PDF_DINAMICA, formulario,
						ScriptInfo.TYPE_COMPONENTE.FORMULARI, ""));
			}
		}
		return scripts;
	}

	@Override
	public void guardarPagina(final PaginaFormulario pagina) {
		final JPaginaFormulario jPagina = getJPaginaById(pagina.getCodigo());
		jPagina.setIdentificador(pagina.getIdentificador());
		jPagina.setPaginaFinal(pagina.isPaginaFinal());
		jPagina.setScriptNavegacion(JScript.fromModel(pagina.getScriptNavegacion()));
		jPagina.setScriptValidacion(JScript.fromModel(pagina.getScriptValidacion()));
		entityManager.merge(jPagina);
	}

	@Override
	public Long importarFormularioSeccion(FilaImportarSeccion filaSeccion) {

		if (filaSeccion.getAccion() == TypeImportarAccion.CREAR || filaSeccion.getAccion() == TypeImportarAccion.REEMPLAZAR) {

			Long idFormulario;
			if (filaSeccion.getAccion() == TypeImportarAccion.CREAR) {
				idFormulario = this.addFormulario(filaSeccion.getSeccion());
			} else {
				idFormulario = filaSeccion.getSeccionActual().getIdFormularioAsociado();
			}


			//Importamos los nuevos datos.
			filaSeccion.getSeccion().getDisenyoFormulario().setCodigo(idFormulario);
			if (filaSeccion.getAccion() == TypeImportarAccion.CREAR || filaSeccion.getAccion() == TypeImportarAccion.IMPORTAR || filaSeccion.getAccion() == TypeImportarAccion.REEMPLAZAR ) {
				//Vaciamos los datos.
				vaciarFormulario(idFormulario);
				this.createFormulario(filaSeccion.getSeccion().getDisenyoFormulario());
			}

			return idFormulario;

		} else if (filaSeccion.getAccion() == TypeImportarAccion.NADA) {
			return null;
		} else {
			return null;
		}

	}

	private void vaciarFormulario(Long idFormulario) {
		entityManager.flush();
		JFormulario jformulario = entityManager.find(JFormulario.class, idFormulario);
		if (jformulario.getPaginas() != null) {
			for(JPaginaFormulario jpagina : jformulario.getPaginas()) {
				if (jpagina.getLineasFormulario() != null) {

					for (Iterator<JLineaFormulario> iteratorLinea = jpagina.getLineasFormulario().iterator(); iteratorLinea.hasNext();) {
						JLineaFormulario jlinea = iteratorLinea.next();
						if (jlinea.getElementoFormulario() != null && !jlinea.getElementoFormulario().isEmpty()) {

							Object[] jelementos = jlinea.getElementoFormulario().toArray();
							for (Object jobject : jelementos) {
								JElementoFormulario jelemento = (JElementoFormulario) jobject;
								jlinea.removeElemento(jelemento);
								entityManager.remove(jelemento);
							}
							entityManager.flush();
						}
						//jlinea.setPaginaFormulario(null);
						entityManager.remove(jlinea);
						entityManager.flush();
					}
				}
				jpagina.setFormulario(null);
				jformulario.getPaginas().remove(jpagina);
				entityManager.merge(jpagina);
				entityManager.remove(jpagina);
				entityManager.flush();
			}
		}

		entityManager.merge(jformulario);
		entityManager.flush();
	}

}
