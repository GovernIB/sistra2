package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de Anexar Documentos.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionAnexarDocumentos extends ViewControllerBase {

	/**
	 * Dato seleccionado en la lista.
	 */
	private Documento datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionAnexarDocumentos.
	 */
	public ViewDefinicionVersionAnexarDocumentos() {
		super();
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Documento getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Documento datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
