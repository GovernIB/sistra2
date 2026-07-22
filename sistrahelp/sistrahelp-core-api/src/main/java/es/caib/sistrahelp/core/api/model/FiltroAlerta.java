package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * Filtro para consultas de alertas
 *
 * @author Indra
 *
 */
public class FiltroAlerta extends ModelApi {

	private static final long serialVersionUID = 1L;

	/** Filtro **/
	private String filtroTexto;

	/** ID de la entidad activa **/
	private String idEntidad;

	/** Lista de áreas permitidas **/
	private List<String> listaAreas;

	public FiltroAlerta() {
		super();
	}

	public FiltroAlerta(final String filtroTexto, final String idEntidad, final List<String> listaAreas) {
		super();
		this.filtroTexto = filtroTexto;
		this.idEntidad = idEntidad;
		this.listaAreas = listaAreas;
	}

	public String getFiltroTexto() {
		return filtroTexto;
	}

	public void setFiltroTexto(final String filtroTexto) {
		this.filtroTexto = filtroTexto;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

}

