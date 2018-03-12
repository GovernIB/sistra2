package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFormulario;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.frontend.model.OpcionArbol;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de tr&aacute;mites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersion extends ViewControllerBase {

	/**
	 * id.
	 */
	private Long id;

	/**
	 * Arbol *.
	 */
	private TreeNode root;

	/**
	 * nodo seleccionado.
	 */
	private TreeNode selectedNode;

	/**
	 * miga de pan *.
	 */
	private MenuModel breadCrumb;

	/**
	 * raiz de la miga de pan *.
	 */
	private MenuModel breadCrumbRoot;

	/**
	 * url de la opcion del arbol seleccionada *.
	 */
	private String opcionUrl;

	/**
	 * tramite version.
	 */
	private TramiteVersion tramiteVersion;

	/*** esto debe de ir dentro de tramiteVersion ***/

	/**
	 * Lista de pasos.
	 */
	private List<TramitePaso> listaPasos;

	/**
	 * Lista de Formularios.
	 */
	private List<Formulario> listaFormularios;

	/**
	 * Lista de tramites de pasos a rellenar.
	 */
	private List<TramitePasoRellenar> tramitePasosRellenar;

	/**
	 * Lista de documentos.
	 */
	private List<Documento> listaDocumentos;

	/**
	 * Lista de tasas.
	 */
	private List<Tasa> listaTasas;

	/*** fin de dentro ***/

	/**
	 * Crea una nueva instancia de view definicion version.
	 */
	public ViewDefinicionVersion() {
		super();
	}

	/**
	 * Inicializacion.
	 */
	public void init() {
		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		/* inicializa breadcrum */
		breadCrumbRoot = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem("Area 1");
		item.setUrl("#");
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem("Tramite 1");
		item.setUrl("#");
		breadCrumbRoot.addElement(item);

		item = new DefaultMenuItem("Version " + id.toString());
		item.setUrl("#");
		breadCrumbRoot.addElement(item);

		breadCrumbRoot.generateUniqueIds();

		breadCrumb = copyMenuModel(breadCrumbRoot);

		/* recuperamos los datos */
		recuperaTramiteVersion(id);

		/* iniciliza pasos tramite */
		final TramitePasoDebeSaber paso1 = new TramitePasoDebeSaber();
		paso1.setId(1L);
		paso1.setCodigo("1");
		paso1.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.debeSaber");
		paso1.setOrden(1);
		final TramitePasoRellenar paso2 = new TramitePasoRellenar();
		paso2.setId(2L);
		paso2.setCodigo("2");
		paso2.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.rellenar");
		paso2.setOrden(2);
		final TramitePasoAnexar paso3 = new TramitePasoAnexar();
		paso3.setId(3L);
		paso3.setCodigo("3");
		paso3.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.anexarDocumentos");
		paso3.setOrden(3);
		final TramitePasoTasa paso4 = new TramitePasoTasa();
		paso4.setId(4L);
		paso4.setCodigo("4");
		paso4.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.pagarTasas");
		paso4.setOrden(4);
		final TramitePaso paso5 = new TramitePaso();
		paso5.setId(5L);
		paso5.setCodigo("5");
		paso5.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.registrarTramite");
		paso5.setOrden(5);

		listaPasos = new ArrayList<>();
		// DebeSaber
		listaPasos.add(paso1);

		final Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setCodigo("Formulario1");
		final Traducciones traducciones = new Traducciones();
		traducciones.add(new Traduccion("ca", "Datos de la solicitud"));
		traducciones.add(new Traduccion("es", "Datos de la solicitud"));
		formulario1.setDescripcion(traducciones);
		formulario1.setObligatoriedad(TypeFormularioObligatoriedad.OPCIONAL);
		formulario1.setTipo(TypeFormulario.TRAMITE);

		final Formulario formulario2 = new Formulario();
		formulario2.setCodigo("Formulario2");
		final Traducciones traducciones2 = new Traducciones();
		traducciones2.add(new Traduccion("ca", "Dades relacionats a l'interessat per a emplar el formulari"));
		traducciones2.add(new Traduccion("es", "Datos relacionados con el interesado para rellenar el formulario"));
		formulario2.setDescripcion(traducciones2);
		formulario2.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		formulario2.setDebeFirmarse(true);
		formulario2.setScriptFirma(new Script());
		formulario2.setDebePrerregistrarse(true);
		formulario2.setScriptPrerrigistro(new Script());
		formulario2.setScriptDatosIniciales(new Script());

		final Formulario formulario3 = new Formulario();
		formulario3.setCodigo("Formulario3");
		final Traducciones traducciones3 = new Traducciones();
		traducciones3.add(new Traduccion("ca", "Dades relacionats a l'interessat per a emplar el formulari"));
		traducciones3.add(new Traduccion("es", "Datos relacionados con el interesado para rellenar el formulario"));
		formulario3.setDescripcion(traducciones3);
		formulario3.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		formulario3.setDebeFirmarse(true);
		formulario3.setScriptFirma(new Script());
		formulario3.setDebePrerregistrarse(true);
		formulario3.setScriptPrerrigistro(new Script());
		formulario3.setScriptDatosIniciales(new Script());

		final List<Formulario> formularios = new ArrayList<>();
		formularios.add(formulario1);
		formularios.add(formulario2);
		formularios.add(formulario3);
		paso2.setFormulariosTramite(formularios);

		// Rellenar
		listaPasos.add(paso2);

		/* inicializa documentos */
		final Documento documento1 = new Documento();
		documento1.setId(1L);
		documento1.setCodigo("Anexo1");
		final Traducciones traduccionesdoc1 = new Traducciones();
		traduccionesdoc1.add(new Traduccion("ca", "Certificat de penals"));
		traduccionesdoc1.add(new Traduccion("es", "Certificado de penales"));
		documento1.setDescripcion(traduccionesdoc1);
		documento1.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		final Documento documento2 = new Documento();
		documento2.setId(2L);
		documento2.setCodigo("Anexo2");
		final Traducciones traduccionesdoc2 = new Traducciones();
		traduccionesdoc2.add(new Traduccion("ca", "Titols acadèmics"));
		traduccionesdoc2.add(new Traduccion("es", "Titulos academicos"));
		documento2.setDescripcion(traduccionesdoc2);
		documento2.setObligatoriedad(TypeFormularioObligatoriedad.DEPENDIENTE);

		listaDocumentos = new ArrayList<>();
		listaDocumentos.add(documento1);
		listaDocumentos.add(documento2);
		paso3.setDocumentos(listaDocumentos);

		listaPasos.add(paso3);

		/* inicializa tasas */
		final Tasa tasa1 = new Tasa();
		tasa1.setId(1L);
		tasa1.setCodigo("Tasa1");
		tasa1.setDescripcion("Tasa de incripción");
		tasa1.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		tasa1.setTipo("Telemático");
		final Tasa tasa2 = new Tasa();
		tasa2.setId(2L);
		tasa2.setCodigo("Tasa2");
		tasa2.setDescripcion("Tasa de incripción");
		tasa2.setObligatoriedad(TypeFormularioObligatoriedad.OPCIONAL);
		tasa2.setTipo("Telemático");

		listaTasas = new ArrayList<>();
		listaTasas.add(tasa1);
		listaTasas.add(tasa2);
		paso4.setTasas(listaTasas);
		listaPasos.add(paso4);
		listaPasos.add(paso5);

		// Prepara los formularios
		listaFormularios = new ArrayList<>();
		listaFormularios.add(formulario1);
		listaFormularios.add(formulario2);
		listaFormularios.add(formulario3);

		// final Documento documento2 = new Documento();
		// documento2.setId(2L);
		// documento2.setCodigo("Anexo2");
		// documento2.setDescripcion("Titulos academicos");
		// documento2.setObligatoriedad("Obligatorio");

		// listaDocumentos.add(documento2);

		/* inicializa arbol */
		root = new DefaultTreeNode("Root", null);

		root.getChildren()
				.add(new DefaultTreeNode(new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.propiedades"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades"))));

		root.getChildren().add(
				new DefaultTreeNode(new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.controlAcceso"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionControlAcceso"))));

		root.getChildren()
				.add(new DefaultTreeNode(
						new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.dominiosEmpleados"),
								UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionDominios"))));

		final TreeNode nodePasosTramitacion = new DefaultTreeNode(
				new OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.pasosTramitacion"),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPasos")),
				root);

		for (final TramitePaso tramitePaso : listaPasos) {

			if (tramitePaso instanceof TramitePasoDebeSaber) {
				final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
						String.valueOf(tramitePaso.getOrden()) + ". "
								+ UtilJSF.getLiteral(tramitePaso.getDescripcion()),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionDebeSaber", tramitePaso.getId())));

				nodePasosTramitacion.getChildren().add(nodo);
			} else if (tramitePaso instanceof TramitePasoRellenar) {

				final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
						String.valueOf(tramitePaso.getOrden()) + ". "
								+ UtilJSF.getLiteral(tramitePaso.getDescripcion()),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionRellenar", tramitePaso.getId())));

				if (((TramitePasoRellenar) tramitePaso).getFormulariosTramite() != null
						&& !((TramitePasoRellenar) tramitePaso).getFormulariosTramite().isEmpty()) {

					for (final Formulario formulario : ((TramitePasoRellenar) tramitePaso).getFormulariosTramite()) {
						final TreeNode nodoRellenar = new DefaultTreeNode(
								new OpcionArbol(formulario.getCodigo(), UtilJSF.getUrlArbolDefinicionVersion(
										"viewDefinicionVersionFormulario", formulario.getId())));

						nodo.getChildren().add(nodoRellenar);
					}
				}
				nodePasosTramitacion.getChildren().add(nodo);
			} else if (tramitePaso instanceof TramitePasoAnexar) {

				final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
						String.valueOf(tramitePaso.getOrden()) + ". "
								+ UtilJSF.getLiteral(tramitePaso.getDescripcion()),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionAnexarDocumentos",
								tramitePaso.getId())));

				if (((TramitePasoAnexar) tramitePaso).getDocumentos() != null
						&& !((TramitePasoAnexar) tramitePaso).getDocumentos().isEmpty()) {

					for (final Documento documento : ((TramitePasoAnexar) tramitePaso).getDocumentos()) {
						final TreeNode nodoRellenar = new DefaultTreeNode(new OpcionArbol(documento.getCodigo(),
								UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionAnexo", documento.getId())));

						nodo.getChildren().add(nodoRellenar);
					}
				}
				nodePasosTramitacion.getChildren().add(nodo);
			} else if (tramitePaso instanceof TramitePasoTasa) {

				final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
						String.valueOf(tramitePaso.getOrden()) + ". "
								+ UtilJSF.getLiteral(tramitePaso.getDescripcion()),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPagarTasas", tramitePaso.getId())));

				if (((TramitePasoTasa) tramitePaso).getTasas() != null
						&& !((TramitePasoTasa) tramitePaso).getTasas().isEmpty()) {

					for (final Tasa tasa : ((TramitePasoTasa) tramitePaso).getTasas()) {
						final TreeNode nodoRellenar = new DefaultTreeNode(new OpcionArbol(tasa.getCodigo(),
								UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionTasa", tasa.getId())));

						nodo.getChildren().add(nodoRellenar);
					}
				}
				nodePasosTramitacion.getChildren().add(nodo);
			} else {
				final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
						String.valueOf(tramitePaso.getOrden()) + ". "
								+ UtilJSF.getLiteral(tramitePaso.getDescripcion()),
						UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersion" + StringUtils
								.capitalize(StringUtils.substringAfterLast(tramitePaso.getDescripcion(), ".")))));

				nodePasosTramitacion.getChildren().add(nodo);
			}
		}

		setExpandedRecursively(root, true);
	}

	/**
	 * Recupera el valor de root.
	 *
	 * @return el valor de root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Establece la propiedad expandida en un arbol recursivamente.
	 *
	 * @param node
	 *            nodo del arbol
	 * @param expanded
	 *            si se expande
	 */
	private void setExpandedRecursively(final TreeNode node, final boolean expanded) {
		if (node != null) {
			for (final TreeNode child : node.getChildren()) {
				setExpandedRecursively(child, expanded);
			}
			node.setExpanded(expanded);
		}
	}

	/**
	 * Obtiene el valor de breadCrumb.
	 *
	 * @return el valor de breadCrumb
	 */
	public MenuModel getBreadCrumModel() {
		return breadCrumb;
	}

	/**
	 * Obtiene el valor de selectedNode.
	 *
	 * @return el valor de selectedNode
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * Establece el valor de selectedNode.
	 *
	 * @param selectedNode
	 *            el nuevo valor de selectedNode
	 */
	public void setSelectedNode(final TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * M&eacute;todo que se ejecuta cuando se selecciona un nodo del arbol.
	 *
	 * @param event
	 *            evento que se ha producido
	 */
	public void onNodeSelect(final NodeSelectEvent event) {
		if (event != null) {
			final OpcionArbol opcionArbol = (OpcionArbol) event.getTreeNode().getData();
			if (opcionArbol.getUrl() != null) {
				opcionUrl = opcionArbol.getUrl();

				breadCrumb = copyMenuModel(breadCrumbRoot);

				if (breadCrumb != null) {
					creaRutaArbolBreadCrumb(breadCrumb, event.getTreeNode());
					breadCrumb.generateUniqueIds();
				}
			}
		}
	}

	/**
	 * Crea la ruta del arbol para el breadcrumb.
	 *
	 * @param miga
	 *            breadcrumb
	 * @param arbol
	 *            arbol
	 */
	private void creaRutaArbolBreadCrumb(final MenuModel miga, final TreeNode arbol) {
		if (miga != null && arbol != null) {
			if (arbol.getParent() != null) {
				creaRutaArbolBreadCrumb(miga, arbol.getParent());
			}
			if (!"root".equals(arbol.getRowKey())) {
				final OpcionArbol opcionArbol = (OpcionArbol) arbol.getData();
				final DefaultMenuItem item = new DefaultMenuItem(opcionArbol.getName());
				item.setUrl("#");
				breadCrumb.addElement(item);
			}
		}
	}

	/**
	 * Obtiene el valor de opcionUrl.
	 *
	 * @return el valor de opcionUrl
	 */
	public String getOpcionUrl() {
		return opcionUrl;
	}

	/**
	 * Establece el valor de opcionUrl.
	 *
	 * @param opcion
	 *            el nuevo valor de opcionUrl
	 */
	public void setOpcionUrl(final String opcion) {
		this.opcionUrl = opcion;
	}

	/**
	 * Crea un nuevo MenuModel a partir del que se pasa por par&aacute;metro.
	 *
	 * @param menumodel
	 *            MenuModel a copiar
	 * @return nuevo MenuModel
	 */
	private MenuModel copyMenuModel(final MenuModel menumodel) {
		MenuModel res = null;
		if (menumodel != null && !menumodel.getElements().isEmpty()) {
			res = new DefaultMenuModel();
			for (final MenuElement item : menumodel.getElements()) {
				res.addElement(item);
			}
		}
		return res;
	}

	/**
	 * Recupera tramite version.
	 *
	 * @param id
	 *            el id de tramite version
	 */
	private void recuperaTramiteVersion(final Long id) {
		tramiteVersion = new TramiteVersion();
		tramiteVersion.setId(id);

		/* propiedades */
		final Traducciones desc1 = new Traducciones();
		desc1.add(new Traduccion("es", "Trámite 1 - Convocatoria de diciembre de 2017 "));
		desc1.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de desembre de 2017"));
		tramiteVersion.setDescripcion(desc1);

		tramiteVersion.setAutenticado(true);
		tramiteVersion.setNivelQAA(2);
		tramiteVersion.setIdiomasSoportados("es,ca");
		tramiteVersion.setPersistencia(true);
		tramiteVersion.setPersistenciaInfinita(false);
		tramiteVersion.setPersistenciaDias(15);
		tramiteVersion.setIdScriptPersonalizacion(Long.valueOf(2));
		// tramiteVersion.setIdScriptInicializacionTramite(Long.valueOf(3));

		/* control acceso */
		tramiteVersion.setActiva(true);
		tramiteVersion.setDesactivacion(true);
		final Calendar dia = Calendar.getInstance();
		dia.set(2018, 1, 1);
		tramiteVersion.setPlazoInicioDesactivacion(dia.getTime());
		tramiteVersion.setPlazoFinDesactivacion(new Date());
		final Traducciones desact1 = new Traducciones();
		desact1.add(new Traduccion("es", "Mensaje de desactivacion"));
		desact1.add(new Traduccion("ca", "Missatge de desactivació"));
		tramiteVersion.setMensajeDesactivacion(desact1);
		tramiteVersion.setDebug(true);
		tramiteVersion.setLimiteTramitacion(true);
		tramiteVersion.setNumLimiteTramitacion(1);
		tramiteVersion.setIntLimiteTramitacion(2);

		/* dominio */
		final Dominio dominio1 = new Dominio();
		dominio1.setId(1L);
		dominio1.setCodigo("1");
		dominio1.setDescripcion("Dominio 1");
		dominio1.setAmbito("Entidad");
		final Dominio dominio2 = new Dominio();
		dominio2.setId(2L);
		dominio2.setCodigo("2");
		dominio2.setDescripcion("Dominio 2");
		dominio2.setAmbito("Area");
		final Dominio dominio3 = new Dominio();
		dominio3.setId(3L);
		dominio3.setCodigo("3");
		dominio3.setDescripcion("Dominio 3");
		dominio3.setAmbito("Generico");
		final Dominio dominio4 = new Dominio();
		dominio4.setId(4L);
		dominio4.setCodigo("4");
		dominio4.setDescripcion("Dominio 4");
		dominio4.setAmbito("Entidad");
		final Dominio dominio5 = new Dominio();
		dominio5.setId(5L);
		dominio5.setCodigo("5");
		dominio5.setDescripcion("Dominio 5");
		dominio5.setAmbito("Generico");

		final ArrayList<Dominio> listaDominios = new ArrayList<>();
		listaDominios.add(dominio1);
		listaDominios.add(dominio2);
		listaDominios.add(dominio3);
		listaDominios.add(dominio4);
		listaDominios.add(dominio5);

		tramiteVersion.setListaDominios(listaDominios);
	}

	/**
	 * Obtiene el valor de listaPasos.
	 *
	 * @return el valor de listaPasos
	 */
	public List<TramitePaso> getListaPasos() {
		return listaPasos;
	}

	/**
	 * Establece el valor de listaPasos.
	 *
	 * @param listaPasos
	 *            el nuevo valor de listaPasos
	 */
	public void setListaPasos(final List<TramitePaso> listaPasos) {
		this.listaPasos = listaPasos;
	}

	/**
	 * Obtiene el valor de listaFormularios.
	 *
	 * @return el valor de listaFormularios
	 */
	public List<Formulario> getListaFormularios() {
		return listaFormularios;
	}

	/**
	 * Establece el valor de listaFormularios.
	 *
	 * @param listaFormularios
	 *            el nuevo valor de listaFormularios
	 */
	public void setListaFormularios(final List<Formulario> listaFormularios) {
		this.listaFormularios = listaFormularios;
	}

	/**
	 * Obtiene el valor de listaDocumentos.
	 *
	 * @return el valor de listaDocumentos
	 */
	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	/**
	 * Establece el valor de listaDocumentos.
	 *
	 * @param listaDocumentos
	 *            el nuevo valor de listaDocumentos
	 */
	public void setListaDocumentos(final List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	/**
	 * Obtiene el valor de listaTasas.
	 *
	 * @return el valor de listaTasas
	 */
	public List<Tasa> getListaTasas() {
		return listaTasas;
	}

	/**
	 * Establece el valor de listaTasas.
	 *
	 * @param listaTasas
	 *            el nuevo valor de listaTasas
	 */
	public void setListaTasas(final List<Tasa> listaTasas) {
		this.listaTasas = listaTasas;
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion
	 *            el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tramitePasosRellenar
	 */
	public List<TramitePasoRellenar> getTramitePasosRellenar() {
		return tramitePasosRellenar;
	}

	/**
	 * @param tramitePasosRellenar
	 *            the tramitePasosRellenar to set
	 */
	public void setTramitePasosRellenar(final List<TramitePasoRellenar> tramitePasosRellenar) {
		this.tramitePasosRellenar = tramitePasosRellenar;
	}

}
