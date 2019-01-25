package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dependencia de un campo respecto al resto de campos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DependenciaCampo implements Serializable {

	/**
	 * Id campo.
	 */
	private final String idCampo;

	/**
	 * Dependencias con campos en el script de autorrellenable.
	 */
	private final List<String> dependenciasAutorrellenable = new ArrayList<>();
	/**
	 * Dependencias con campos en el script de estado.
	 */
	private final List<String> dependenciasEstado = new ArrayList<>();
	/**
	 * Dependencias con campos si es un selector.
	 */
	private final List<String> dependenciasSelector = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param pIdCampo
	 *            Id campo
	 */
	public DependenciaCampo(final String pIdCampo) {
		super();
		idCampo = pIdCampo;
	}

	/**
	 * Añade dependencia para autorrellenable.
	 *
	 * @param pIdCampo
	 *            Id campo
	 */
	public void addDependenciaAutorrellenable(final String pIdCampo) {
		dependenciasAutorrellenable.add(pIdCampo);
	}

	/**
	 * Añade dependencias para autorrellenable.
	 *
	 * @param idCampos
	 *            Id campo
	 */
	public void addDependenciasAutorrellenable(final List<String> idCampos) {
		dependenciasAutorrellenable.addAll(idCampos);
	}

	/**
	 * Añade dependencia para estado.
	 *
	 * @param pIdCampo
	 *            Id campo
	 */
	public void addDependenciaEstado(final String pIdCampo) {
		dependenciasEstado.add(pIdCampo);
	}

	/**
	 * Añade dependencia para estado.
	 *
	 * @param idCampos
	 *            Id campo
	 */
	public void addDependenciasEstado(final List<String> idCampos) {
		dependenciasEstado.addAll(idCampos);
	}

	/**
	 * Añade dependencia para selector.
	 *
	 * @param pIdCampo
	 *            Id campo
	 */
	public void addDependenciaSelector(final String pIdCampo) {
		dependenciasSelector.add(pIdCampo);
	}

	/**
	 * Añade dependencia para selector.
	 *
	 * @param idCampos
	 *            Id campo
	 */
	public void addDependenciasSelector(final List<String> idCampos) {
		dependenciasSelector.addAll(idCampos);
	}

	/**
	 * Indica si campo es dependiente del campo pasado como parámetro.
	 *
	 * @param pIdCampo
	 *            id campo
	 * @return boolean
	 */
	public boolean isDependiente(final String pIdCampo) {
		boolean res = false;
		if (dependenciasAutorrellenable.contains(pIdCampo)) {
			res = true;
		} else if (dependenciasEstado.contains(pIdCampo)) {
			res = true;
		} else if (dependenciasSelector.contains(pIdCampo)) {
			res = true;
		}
		return res;
	}

	/**
	 * Indica si campo es dependiente de otros campos.
	 *
	 * @return boolean
	 */
	public boolean isDependiente() {
		final int i = dependenciasAutorrellenable.size() + dependenciasEstado.size() + dependenciasSelector.size();
		return (i > 0);
	}

	/**
	 * Devuelve lista de campos dependientes.
	 *
	 * @return Lista de campos dependientes
	 */
	public List<String> getDependencias() {
		final List<String> res = new ArrayList<>();
		res.addAll(dependenciasAutorrellenable);
		for (final String depEstado : dependenciasEstado) {
			if (!res.contains(depEstado)) {
				res.add(depEstado);
			}
		}
		for (final String depSelector : dependenciasSelector) {
			if (!res.contains(depSelector)) {
				res.add(depSelector);
			}
		}
		return res;
	}

	/**
	 * Devuelve lista de campos dependientes del script de autorrellenable.
	 *
	 * @return Lista de campos dependientes
	 */
	public List<String> getDependenciasAutorrellenable() {
		return dependenciasAutorrellenable;
	}

	/**
	 * Devuelve lista de campos dependientes del script de estado.
	 *
	 * @return Lista de campos dependientes
	 */
	public List<String> getDependenciasEstado() {
		return dependenciasEstado;
	}

	/**
	 * Devuelve lista de campos dependientes del selector.
	 *
	 * @return Lista de campos dependientes
	 */
	public List<String> getDependenciasSelector() {
		return dependenciasSelector;
	}

	/**
	 * Método de acceso a idCampo.
	 *
	 * @return idCampo
	 */
	public String getIdCampo() {
		return idCampo;
	}
}
