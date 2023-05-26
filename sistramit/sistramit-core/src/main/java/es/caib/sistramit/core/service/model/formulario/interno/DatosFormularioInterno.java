package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;

/**
 * Datos que se mantienen en memoria para un formulario interno.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosFormularioInterno implements Serializable {

	/**
	 * Valores iniciales (no se modifican tras cargar formulario).
	 */
	private List<ValorCampo> valoresIniciales = new ArrayList<>();

	/**
	 * Páginas del formulario rellenadas actual y anteriores (configuración y
	 * datos).
	 */
	private final Stack<PaginaFormularioData> paginas = new Stack<>();

	/**
	 * Páginas del formulario descartadas, posterior a la actual (configuración y
	 * datos).
	 */
	private final Map<String, PaginaFormularioData> paginasPosteriores = new LinkedHashMap<>();

	/**
	 * Obtiene una página por identificador.
	 *
	 * @param identificadorPagina
	 *                                identificadorPagina
	 * @return pagina
	 */
	public PaginaFormularioData getPaginaFormulario(final String identificadorPagina) {
		PaginaFormularioData res = null;
		for (final PaginaFormularioData p : paginas) {
			if (p.getIdentificador().equals(identificadorPagina)) {
				res = p;
				break;
			}
		}
		return res;
	}

	/**
	 * Obtiene una página por identificador de la lista de paginas posteriores.
	 *
	 * @param identificadorPagina
	 *                                identificadorPagina
	 * @return pagina
	 */
	public PaginaFormularioData getPaginaPosteriorFormulario(final String identificadorPagina) {
		final PaginaFormularioData res = paginasPosteriores.get(identificadorPagina);
		return res;
	}

	/**
	 * Obtiene una página.
	 *
	 * @param indicePagina
	 *                         indice pagina
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
		return paginas.get(getIndicePaginaActual() - ConstantesNumero.N1);
	}

	/**
	 * Añade una página a la lista de páginas rellenadas.
	 *
	 * @param pagina
	 *                   Pagina
	 */
	public void pushPaginaFormulario(final PaginaFormularioData pagina) {
		paginas.push(pagina);
		if (paginasPosteriores.containsKey(pagina.getIdentificador())) {
			paginasPosteriores.remove(pagina.getIdentificador());
		}
	}

	/**
	 * Elimina pagina actual de las paginas rellenadas, retrocediendo a la anterior.
	 * Mete pagina actual a lista de paginas posteriores por si se avanza a esa
	 * pagina luego.
	 */
	public void popPaginaFormulario() {
		final PaginaFormularioData p = paginas.pop();
		paginasPosteriores.put(p.getIdentificador(), p);
	}

	/**
	 * Método de acceso a paginaActual.
	 *
	 * @return paginaActual
	 */
	public int getIndicePaginaActual() {
		// Ultima pagina cargada
		return paginas.size();
	}

	/**
	 * Método de acceso a paginaActual.
	 *
	 * @return id paginaActual
	 */
	public String getIdentificadorPaginaActual() {
		final PaginaFormularioData pag = paginas.get(paginas.size() - ConstantesNumero.N1);
		return pag.getIdentificador();
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
	 * Obtiene las dependencias de un campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @return lista de id campo para los cuales tiene dependencia
	 */
	public DependenciaCampo getDependenciaCampo(final String idCampo) {
		DependenciaCampo res = null;
		for (final PaginaFormularioData pagina : paginas) {
			for (final DependenciaCampo config : pagina.getDependencias()) {
				if (config.getIdCampo().equals(idCampo)) {
					res = config;
					break;
				}
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix camp amb id: " + idCampo);
		}
		return res;
	}

	/**
	 * Devuelve los valores de los campos accesibles desde el script de un campo de
	 * la pagina actual.
	 *
	 * @param idCampo
	 *                    id campo
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
	 *                    id campo
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
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix camp amb id: " + idCampo);
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
		final List<ValorCampo> res = new ArrayList<>();
		for (int i = ConstantesNumero.N1; i <= getIndicePaginaActual(); i++) {
			res.addAll(getPaginaFormulario(i).getValores());
		}
		return res;
	}

	/**
	 * Devuelve los valores de los campos páginas posteriores a la página actual
	 * (páginas formulario descartadas).
	 *
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> getValoresPosterioresPaginaActual() {
		final List<ValorCampo> res = new ArrayList<>();
		for (final String id : paginasPosteriores.keySet()) {
			res.addAll(paginasPosteriores.get(id).getValores());
		}
		return res;
	}

	/**
	 * Método de acceso a valoresIniciales.
	 *
	 * @return valoresIniciales
	 */
	public List<ValorCampo> getValoresIniciales() {
		return valoresIniciales;
	}

	/**
	 * Método para establecer valoresIniciales.
	 *
	 * @param valoresIniciales
	 *                             valoresIniciales a establecer
	 */
	public void setValoresIniciales(final List<ValorCampo> valoresIniciales) {
		this.valoresIniciales = valoresIniciales;
	}

	/**
	 * Obtiene páginas rellenadas.
	 *
	 * @return Lista ids páginas rellenadas
	 */
	public List<String> getIdsPaginasRellenadas() {
		final List<String> res = new ArrayList<>();
		for (final PaginaFormularioData p : this.paginas) {
			res.add(p.getIdentificador());
		}
		return res;
	}
}
