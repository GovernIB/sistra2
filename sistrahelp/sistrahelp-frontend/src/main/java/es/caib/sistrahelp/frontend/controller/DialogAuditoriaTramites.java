package es.caib.sistrahelp.frontend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogAuditoriaTramites extends DialogControllerBase {

	private EventoAuditoriaTramitacion dato;

	private Entry<String, String> valorSeleccionado;

	private String portapapeles;

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

	public void sistrages() {
		final Map<String, String> params = new HashMap<>();
		params.put("TRAMITE", dato.getIdTramite());
		params.put("VERSION", dato.getVersionTramite().toString());
		UtilJSF.openDialog(DialogDefinicionVersion.class, TypeModoAcceso.CONSULTA, params, true, 1300, 550);
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
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

	/**
	 * @return the valorSeleccionado
	 */
	public Entry<String, String> getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}
