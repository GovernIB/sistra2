package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la recuperacion de eventos
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroEvento", description = "Filtro para la recuperacion de eventos")
public class RFiltroEvento {

	@ApiModelProperty(value = "Fecha evento", required = true)
	private Date fecha;

	@ApiModelProperty(value = "Tipo Evento")
	private List<String> listaEventos;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	public List<String> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(final List<String> listaEventos) {
		this.listaEventos = listaEventos;
	}

}
