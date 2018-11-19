package es.caib.sistrahelp.core.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * La clase ResultadoPerdidaClave.
 */
@SuppressWarnings("serial")
public final class ResultadoPerdidaClave implements Serializable {

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public ResultadoPerdidaClave() {
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
