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
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogAuditoriaTramites extends DialogControllerBase {

	private EventoAuditoriaTramitacion dato;

	private Entry<String, String> valorSeleccionado;

	private String copiar;

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
	 * @return the copiar
	 */
	public String getCopiar() {
		return copiar;
	}

	/**
	 * @param copiar the copiar to set
	 */
	public void setCopiar(String copiar) {
		this.copiar = copiar;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public Entry<String, String> getValorSeleccionado() {
		return valorSeleccionado;
	}

	/** Avisa al growl que se ha copiado correctamente **/
	public void avisarGrowl() {
		UtilJSF.addMessageContext(es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad.INFO,
				UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * @param valorSeleccionado the valorSeleccionado to set
	 */
	public void setValorSeleccionado(Entry<String, String> valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
		if (valorSeleccionado != null) {
			this.copiar = valorSeleccionado.getValue();
		}
	}

}
