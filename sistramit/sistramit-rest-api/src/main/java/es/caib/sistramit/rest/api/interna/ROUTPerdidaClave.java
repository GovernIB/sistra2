package es.caib.sistramit.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Perdida de clave
 *
 * @author Indra
 *
 */
@ApiModel(value = "ROUTPerdidaClave", description = "Resultado Perdida Clave")
public class ROUTPerdidaClave {

	/**
	 * resultado.
	 */
	@ApiModelProperty(value = "resultado")
	private int resultado;

	/**
	 * lista de claves
	 */
	@ApiModelProperty(value = "lista de clave")
	private List<RPerdidaClave> listaClaves;

	public int getResultado() {
		return resultado;
	}

	public void setResultado(final int resultado) {
		this.resultado = resultado;
	}

	public List<RPerdidaClave> getListaClaves() {
		return listaClaves;
	}

	public void setListaClaves(final List<RPerdidaClave> listaClaves) {
		this.listaClaves = listaClaves;
	}

}
