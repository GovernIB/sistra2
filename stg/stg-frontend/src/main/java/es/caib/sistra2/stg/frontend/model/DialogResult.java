package es.caib.sistra2.stg.frontend.model;

import es.caib.sistra2.stg.frontend.model.types.TypeModoAcceso;

/**
 * Resultado de ventana de dialogo.
 * @author Indra
 *
 */
public class DialogResult {
	
	/**
	 * Modo de acceso al que se ha accedido dialogo.
	 */
	private TypeModoAcceso modoAcceso;
	
	/**
	 * Indica si se ha cancelado;
	 */
	private boolean canceled;
	
	/**
	 * Resultado dialogo.
	 */
	private Object result;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public TypeModoAcceso getModoAcceso() {
		return modoAcceso;
	}

	public void setModoAcceso(TypeModoAcceso modoAcceso) {
		this.modoAcceso = modoAcceso;
	}
	
	

}
