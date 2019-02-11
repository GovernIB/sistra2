package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.PaginaFormularioData;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;

/**
 * Datos que se mantienen en memoria para un formulario interno.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosFormularioInterno implements Serializable {

	/**
	 * Indice página actual (empieza en 1).
	 */
	private int indicePaginaActual;

	/**
	 * Páginas del formulario: configuración y datos.
	 */
	private final List<PaginaFormularioData> paginas = new ArrayList<>();

	/**
	 * Dependencias de campos. Para un campo se establece la dependencia con los
	 * otros.
	 */
	private final Map<String, DependenciaCampo> dependencias = new HashMap<>();

	/**
	 * Obtiene una página.
	 *
	 * @param indicePagina
	 *            indice pagina
	 * @return pagina
	 */
	public PaginaFormularioData getPaginaFormulario(final int indicePagina) {
		return paginas.get(indicePagina - ConstantesNumero.N1);
	}

	/**
	 * Obtiene página actual.
	 *
	 * @return paginas
	 */
	public PaginaFormularioData getPaginaActualFormulario() {
		return paginas.get(this.indicePaginaActual - ConstantesNumero.N1);
	}

	/**
	 * Añade una página.
	 *
	 * @param pagina
	 *            Pagina
	 */
	public void addPaginaFormulario(final PaginaFormularioData pagina) {
		paginas.add(pagina);
	}

	/**
	 * Método de acceso a paginaActual.
	 *
	 * @return paginaActual
	 */
	public int getIndicePaginaActual() {
		return indicePaginaActual;
	}

	/**
	 * Método para establecer paginaActual.
	 *
	 * @param pPaginaActual
	 *            paginaActual a establecer
	 */
	public void setIndicePaginaActual(final int pPaginaActual) {
		indicePaginaActual = pPaginaActual;
	}

	/**
	 * Obtiene número de páginas del formulario.
	 *
	 * @return Número de páginas.
	 */
	public int getNumeroPaginas() {
		return paginas.size();
	}

	/**
	 * Indicamos que dependencia tiene un campo respecto a los demás.
	 *
	 * @param dependenciasCampo
	 *            lista de id campo para los cuales tiene dependencia
	 */
	public void addDependenciaCampo(final DependenciaCampo dependenciasCampo) {
		this.dependencias.put(dependenciasCampo.getIdCampo(), dependenciasCampo);
	}

	/**
	 * Indicamos que dependencia tiene un campo respecto a los demás.
	 *
	 * @param dependenciasCampos
	 *            Dependencias campos
	 */
	public void addDependenciasCampos(final List<DependenciaCampo> dependenciasCampos) {
		for (final DependenciaCampo dependenciasCampo : dependenciasCampos) {
			this.dependencias.put(dependenciasCampo.getIdCampo(), dependenciasCampo);
		}
	}

	/**
	 * Obtiene las dependencias de un campo.
	 *
	 * @param idCampo
	 *            Id campo
	 * @return lista de id campo para los cuales tiene dependencia
	 */
	public DependenciaCampo getDependenciaCampo(final String idCampo) {
		return this.dependencias.get(idCampo);
	}

	/**
	 * Devuelve los valores de los campos accesibles desde el script de un campo de
	 * la pagina actual.
	 *
	 * @param idCampo
	 *            id campo
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> getValoresAccesiblesCampo(final String idCampo) {
		final List<ValorCampo> res = new ArrayList<>();

		// Podra tener accesibles los valores de las paginas anteriores
		for (int i = ConstantesNumero.N1; i < getIndicePaginaActual(); i++) {
			res.addAll(getPaginaFormulario(i).getValores());
		}

		// Podra tener accesibles los valores de los campos anteriores en la
		// pagina actual
		for (final ValorCampo vc : getPaginaActualFormulario().getValores()) {
			if (vc.getId().equals(idCampo)) {
				res.add(vc);
				break;
			} else {
				res.add(vc);
			}
		}

		return res;
	}

	/**
	 * Obtiene la configuración de un campo.
	 *
	 * @param idCampo
	 *            id campo
	 * @return Configuración de un campo.
	 */
	public ConfiguracionCampo getConfiguracionCampo(final String idCampo) {
		ConfiguracionCampo res = null;
		for (final PaginaFormularioData pagina : paginas) {
			for (final ConfiguracionCampo config : pagina.getConfiguracion()) {
				if (config.getId().equals(idCampo)) {
					res = config;
					break;
				}
			}
		}
		return res;
	}

	/**
	 * Devuelve los valores de los campos accesibles del formulario desde la página
	 * actual.
	 *
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> getValoresAccesiblesPaginaActual() {
		// TODO FASE2: VER COMO SE GESTIONA EL MUTIPAGINA, SOLO DEBERIA TENER ACCESO A
		// LAS PAGINAS ANTERIORES QUE SE HAYAN RELLENADO.
		// DE MOMENTO SE METEN TODAS LAS PAGINAS.
		final List<ValorCampo> res = new ArrayList<>();
		for (int i = ConstantesNumero.N1; i <= getIndicePaginaActual(); i++) {
			res.addAll(getPaginaFormulario(i).getValores());
		}

		return res;
	}

	/**
	 * Obtiene lista de los campos que son evaluables en el formulario, es decir,
	 * aparecen como dependientes de otros (siempre que no aparezcan como
	 * dependientes exclusivamente de ocultos).
	 *
	 * @return Lista de ids de campo
	 */
	public List<String> getCamposEvaluables() {
		final List<String> camposEvaluables = new ArrayList<>();
		for (final DependenciaCampo dc : dependencias.values()) {
			final ConfiguracionCampo confCampoDependiente = getConfiguracionCampo(dc.getIdCampo());
			final List<String> dependenciasCampo = dc.getDependencias();
			if (!UtilsFormularioInterno.esCampoOculto(confCampoDependiente) && !dependenciasCampo.isEmpty()) {
				camposEvaluables.addAll(dependenciasCampo);
			}
		}
		return camposEvaluables;
	}
}
