package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;

/**
 * Dialogo fichero.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogFichero extends DialogControllerBase {

	/** Id fichero. **/
	private String id;

	/** Fichero. **/
	private Fichero data;

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Fichero getData() {
		return data;
	}

	public void setData(Fichero data) {
		this.data = data;
	}

}
