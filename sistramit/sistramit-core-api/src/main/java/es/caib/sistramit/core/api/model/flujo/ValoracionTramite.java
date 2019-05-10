package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Valoración trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ValoracionTramite implements ModelApi {

	/**
	 * Indica si se ha valorado el trámite.
	 */
	private TypeSiNo valorado = TypeSiNo.NO;

	/**
	 * Incidencias valoración.
	 */
	private List<ValoracionIncidencia> problemas = new ArrayList<>();

	/**
	 * Método de acceso a valorado.
	 * 
	 * @return valorado
	 */
	public TypeSiNo getValorado() {
		return valorado;
	}

	/**
	 * Método para establecer valorado.
	 * 
	 * @param valorado
	 *            valorado a establecer
	 */
	public void setValorado(final TypeSiNo valorado) {
		this.valorado = valorado;
	}

	/**
	 * Método de acceso a problemas.
	 * 
	 * @return problemas
	 */
	public List<ValoracionIncidencia> getProblemas() {
		return problemas;
	}

	/**
	 * Método para establecer problemas.
	 * 
	 * @param problemas
	 *            problemas a establecer
	 */
	public void setProblemas(final List<ValoracionIncidencia> problemas) {
		this.problemas = problemas;
	}

}
