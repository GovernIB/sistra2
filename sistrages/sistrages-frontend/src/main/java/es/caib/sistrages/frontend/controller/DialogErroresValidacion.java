package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogErroresValidacion extends DialogControllerBase {
	private List<ErrorValidacion> listaErrores;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {

			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

			if (!mochilaDatos.isEmpty()) {
				setListaErrores((List<ErrorValidacion>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION));
			}

			if (getListaErrores() == null) {
				setListaErrores(new ArrayList<>());
			}

			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION);

		}

	}

	/**
	 * Cancelar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public List<ErrorValidacion> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(final List<ErrorValidacion> listaErrores) {
		this.listaErrores = listaErrores;
	}

}
