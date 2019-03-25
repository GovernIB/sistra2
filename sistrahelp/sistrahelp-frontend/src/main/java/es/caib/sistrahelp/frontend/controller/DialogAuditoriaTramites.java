package es.caib.sistrahelp.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogAuditoriaTramites extends DialogControllerBase {

	private EventoAuditoriaTramitacion dato;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		if (modo == TypeModoAcceso.CONSULTA) {
			dato = (EventoAuditoriaTramitacion) UtilJSF.getSessionBean().getMochilaDatos()
					.get(Constantes.CLAVE_MOCHILA_EVENTO);
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_EVENTO);
		}
	}

	public boolean getMostrarDetalle() {
		boolean resultado = false;
		if (TypeEvento.ERROR.equals(dato.getTipoEvento()) || dato.getPropiedadesEvento() != null) {
			resultado = true;
		}
		return resultado;
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

	public EventoAuditoriaTramitacion getDato() {
		return dato;
	}

	public void setDato(final EventoAuditoriaTramitacion dato) {
		this.dato = dato;
	}
	
	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogoAuditoriaTramites");
	}

}
