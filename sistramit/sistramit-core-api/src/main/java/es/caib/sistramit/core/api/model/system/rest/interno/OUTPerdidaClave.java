package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;
import java.util.List;

/**
 * La clase OUTPerdidaClave. (RestApiInternaService)
 */
@SuppressWarnings("serial")
public final class OUTPerdidaClave implements Serializable {

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public OUTPerdidaClave() {
		super();
	}

	/**
	 * id sesion tramitacion.
	 */
	private int resultado;

	private List<PerdidaClave> listaClaves;

	public int getResultado() {
		return resultado;
	}

	public void setResultado(final int resultado) {
		this.resultado = resultado;
	}

	public List<PerdidaClave> getListaClaves() {
		return listaClaves;
	}

	public void setListaClaves(final List<PerdidaClave> listaClaves) {
		this.listaClaves = listaClaves;
	}

}
