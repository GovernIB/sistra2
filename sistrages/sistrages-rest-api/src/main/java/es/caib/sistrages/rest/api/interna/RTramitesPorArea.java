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
@ApiModel(value = "RLineaComponentes", description = "Descripcion de RLineaComponentes")
public class RTramitesPorArea {

    /** Componentes. */
	@ApiModelProperty(value = "Lista Tramites")
    private List<String> listaTramites;

	public List<String> getListaTramites() {
		return listaTramites;
	}

	public void setListaTramites(List<String> listaTramites) {
		this.listaTramites = listaTramites;
	}

}

