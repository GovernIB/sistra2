package es.caib.sistrages.frontend.controller;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ObjetoNavegacion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Disenyo formulario.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogEstructuraFormulario extends DialogControllerBase {

	@Inject
	FormularioInternoService formIntService;

	/** Id formulario **/
	private String id;

	/** Formulario. **/
	private DisenyoFormulario formulario;

	/** Url ventana propiedades **/
	private String panelPropiedadesUrl;

	private TreeNode arbolEstructura;

	private TreeNode<Object> selectedNode;

	/**
	 * Inicializacion.
	 **/
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			// ...
		} else {
			// ...
		}

		// Recuperacion formulario
		recuperarFormulario(id);

		if (formulario != null) {
			creaArbol();
		}

		panelPropiedadesUrl = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
	}

	/**
	 * Recupera formulario
	 *
	 * @param idForm
	 **/
	private void recuperarFormulario(final String idForm) {
		formulario = formIntService.getFormularioInternoCompleto(Long.parseLong(idForm));
	}

	private void creaArbol() {

		/** Nodo Propiedades. **/
		// final DefaultTreeNode nodoPropiedades = new DefaultTreeNode(
		// new
		// OpcionArbol(UtilJSF.getLiteral("viewDefinicionVersion.indice.propiedades"),
		// UtilJSF.getUrlArbolDefinicionVersion("viewDefinicionVersionPropiedades")));

		arbolEstructura = new DefaultTreeNode("Formulario", null);

		// recorremos paginas
		for (final PaginaFormulario pagina : formulario.getPaginas()) {
			final DefaultTreeNode nodoPagina = new DefaultTreeNode(TypeObjetoFormulario.PAGINA.toString(),
					pagina,
					arbolEstructura);

			for (final LineaComponentesFormulario linea : pagina.getLineas()) {
				final DefaultTreeNode nodoLinea = new DefaultTreeNode(TypeObjetoFormulario.LINEA.toString(),
						linea,
						nodoPagina);

				for (final ComponenteFormulario componente : linea.getComponentes()) {
					final DefaultTreeNode nodoComponente = new DefaultTreeNode(componente.getTipo().toString(),
							componente, nodoLinea);
				}
			}
		}

		setExpandedRecursively(arbolEstructura, true);

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
			for (final Object object : node.getChildren()) {
				TreeNode child = (TreeNode) object;
				setExpandedRecursively(child, expanded);
			}
			node.setExpanded(expanded);
		}
	}

	/**
	 * Cancelar.
	 **/
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void navegar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);

		if (selectedNode != null) {

			ObjetoNavegacion navegacion = null;

			if(selectedNode.getType().equals(TypeObjetoFormulario.LINEA.toString())) {

				LineaComponentesFormulario linea = ((LineaComponentesFormulario) selectedNode.getData());
				navegacion = new ObjetoNavegacion(TypeObjetoFormulario.LINEA, linea.getIdComponente());

			} else if (selectedNode.getType().equals(TypeObjetoFormulario.PAGINA.toString())){

				PaginaFormulario pagina = ((PaginaFormulario) selectedNode.getData());
				navegacion = new ObjetoNavegacion(TypeObjetoFormulario.PAGINA,pagina.getOrden() +"");

			} else { // Componentes
				navegacion = new ObjetoNavegacion(((ComponenteFormulario) selectedNode.getData()).getTipo(), ((ComponenteFormulario) selectedNode.getData()).getIdComponente());
			}

            result.setResult( navegacion);
		}

		UtilJSF.closeDialog(result);
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}


	// -- Getters / Setters

	/**
	 * Obtiene el valor de panelPropiedadesUrl.
	 *
	 * @return el valor de panelPropiedadesUrl
	 */
	public String getPanelPropiedadesUrl() {
		return panelPropiedadesUrl;
	}

	/**
	 * Establece el valor de panelPropiedadesUrl.
	 *
	 * @param opcion
	 *            el nuevo valor de panelPropiedadesUrl
	 */
	public void setPanelPropiedadesUrl(final String opcion) {
		this.panelPropiedadesUrl = opcion;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public DisenyoFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(final DisenyoFormulario formulario) {
		this.formulario = formulario;
	}

	public TreeNode getArbolEstructura() {
		return arbolEstructura;
	}

	public void setArbolEstructura(final TreeNode arbolEstructura) {
		this.arbolEstructura = arbolEstructura;
	}

}
