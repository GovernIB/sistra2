package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Línea de componentes.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RVersionesPorTramite", description = "Descripcion de RVersionesPorTramite")
public class RVersionesPorTramite {

    /** Componentes. */
	@ApiModelProperty(value = "Lista Versiones")
    private List<Integer> listaVersiones;

	public List<Integer> getListaVersiones() {
		return listaVersiones;
	}

	public void setListaVersiones(List<Integer> listaVersiones) {
		this.listaVersiones = listaVersiones;
	}

}

