package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
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

import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.frontend.model.OpcionArbol;
import es.caib.sistrages.frontend.util.UtilJSF;

// TODO: Auto-generated Javadoc
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
	 * Lista de pasos.
	 */
	private List<TramitePaso> listaPasos;

	/**
	 * Lista de Formularios.
	 */
	private List<Formulario> listaFormularios;

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

		item = new DefaultMenuItem("Version 2");
		item.setUrl("#");
		breadCrumbRoot.addElement(item);

		breadCrumbRoot.generateUniqueIds();

		breadCrumb = copyMenuModel(breadCrumbRoot);

		/* iniciliza pasos tramite */
		final TramitePaso paso1 = new TramitePaso();
		paso1.setId(1L);
		paso1.setCodigo("1");
		paso1.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.debeSaber");
		paso1.setOrden(1);
		final TramitePaso paso2 = new TramitePaso();
		paso2.setId(2L);
		paso2.setCodigo("2");
		paso2.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.rellenar");
		paso2.setOrden(2);
		final TramitePaso paso3 = new TramitePaso();
		paso3.setId(3L);
		paso3.setCodigo("3");
		paso3.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.anexarDocumentos");
		paso3.setOrden(3);
		final TramitePaso paso4 = new TramitePaso();
		paso4.setId(4L);
		paso4.setCodigo("4");
		paso4.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.pagarTasas");
		paso4.setOrden(4);
		final TramitePaso paso5 = new TramitePaso();
		paso5.setId(5L);
		paso5.setCodigo("5");
		paso5.setDescripcion("viewDefinicionVersion.indice.pasosTramitacion.registrarTramites");
		paso5.setOrden(5);

		listaPasos = new ArrayList<>();
		listaPasos.add(paso1);
		listaPasos.add(paso2);
		listaPasos.add(paso3);
		listaPasos.add(paso4);
		listaPasos.add(paso5);

		/* iniciliza formularios */
		final Formulario formulario1 = new Formulario();
		formulario1.setId(1L);
		formulario1.setCodigo("Formulario1");
		formulario1.setDescripcion("Datos de la solicitud");
		formulario1.setObligatoriedad("Obligatorio");
		formulario1.setTipo("Interno");
		final Formulario formulario2 = new Formulario();
		formulario2.setId(2L);
		formulario2.setCodigo("Formulario2");
		formulario2.setDescripcion("Datos relativos al interesado");
		formulario2.setObligatoriedad("Dependiente");
		formulario2.setTipo("Interno");

		listaFormularios = new ArrayList<>();
		listaFormularios.add(formulario1);
		listaFormularios.add(formulario2);

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
			final TreeNode nodo = new DefaultTreeNode(new OpcionArbol(
					String.valueOf(tramitePaso.getOrden()) + ". " + UtilJSF.getLiteral(tramitePaso.getDescripcion()),
					UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersion" + StringUtils
							.capitalize(StringUtils.substringAfterLast(tramitePaso.getDescripcion(), ".")))));

			nodePasosTramitacion.getChildren().add(nodo);

			if (!listaFormularios.isEmpty()
					&& "viewDefinicionVersion.indice.pasosTramitacion.rellenar".equals(tramitePaso.getDescripcion())) {
				for (final Formulario formulario : listaFormularios) {
					final TreeNode nodoRellenar = new DefaultTreeNode(new OpcionArbol(
							UtilJSF.getLiteral("viewDefinicionVersion.indice.pasosTramitacion."
									+ formulario.getCodigo().toLowerCase()),
							UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersion" + formulario.getCodigo())));

					nodo.getChildren().add(nodoRellenar);
				}
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
					// TODO: hay que hacer un bucle para recuperar hasta la raiz del arbol y ademas
					// hay que quitar el 2. o el que toque
					final DefaultMenuItem item = new DefaultMenuItem(opcionArbol.getName());
					item.setUrl("#");
					breadCrumb.addElement(item);
					breadCrumb.generateUniqueIds();
				}
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

}
