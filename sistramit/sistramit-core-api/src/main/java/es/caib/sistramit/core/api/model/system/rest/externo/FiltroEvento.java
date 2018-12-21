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

	private Date fecha;
	private List<TypeEvento> listaEventos;

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

	public List<String> getListaEventosToString() {
		List<String> resultado = null;
		if (listaEventos != null) {
			resultado = new ArrayList<>();
			for (final TypeEvento evento : listaEventos) {
				resultado.add(evento.toString());
			}
		}
		return resultado;
	}

}
