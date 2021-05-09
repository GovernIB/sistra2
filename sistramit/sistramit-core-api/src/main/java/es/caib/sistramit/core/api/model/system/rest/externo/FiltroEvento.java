package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.system.types.TypeEvento;

/**
 * Filtros para los eventos (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroEvento implements Serializable {
	/** Fecha a partir de la cual recupera eventos. */
	private Date fecha;
	/** Lista eventos a filtrar (opcional). */
	private List<TypeEvento> listaEventos = new ArrayList<>();

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	public List<TypeEvento> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(final List<TypeEvento> listaEventos) {
		this.listaEventos = listaEventos;
	}

}
