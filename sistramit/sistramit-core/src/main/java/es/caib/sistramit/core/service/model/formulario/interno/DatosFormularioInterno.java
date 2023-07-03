package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;

/**
 * Datos que se mantienen en memoria para un formulario interno.
 *
 * @author Indra
 *
 */

// TODO LEL VER COMO ENCAPSULAR ACCESO PAG FOR Y PAG ELE

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
	private final Stack<PaginaData> paginas = new Stack<>();

	/**
	 * Páginas del formulario descartadas, posterior a la actual (configuración y
	 * datos).
	 */
	private final Map<String, PaginaData> paginasPosteriores = new LinkedHashMap<>();

	/**
	 * Datos de elemento de un componente lista de elementos que se está editando
	 * (cada vez que se edite un elemento se cambiará).
	 */
	private DatosEdicionElemento datosEdicionElemento = null;

	public DatosFormularioInterno(List<ValorCampo> valoresIniciales, PaginaData paginaData) {
		// Establece valores iniciales
		this.valoresIniciales = valoresIniciales;
		// Añadimos pagina a datos formulario
		pushPaginaFormulario(paginaData);
	}


	/**
	 * Obtiene una página formulario por identificador de la lista de paginas posteriores.
	 *
	 * @param identificadorPagina
	 *                                identificadorPagina
	 * @return pagina
	 */
	public PaginaData obtenerPaginaFormularioDataPosterior(final String identificadorPagina) {
		final PaginaData res = paginasPosteriores.get(identificadorPagina);
		return res;
	}

	/**
	 * Obtiene página actual (página formulario o elemento).
	 *
	 * @param elemento
	 *                   si se accede a la página principal o al detalle de un
	 *                   elemento
	 *
	 * @return paginas
	 */
	public PaginaData obtenerPaginaDataActual(final boolean elemento) {
		PaginaData paginaActual;
		if (!elemento) {
			paginaActual = paginas.get(obtenerIndicePaginaFormularioActual() - ConstantesNumero.N1);
		} else {
			paginaActual = datosEdicionElemento.getPaginaElemento();
		}
		return paginaActual;
	}

	/**
	 * Añade una página a la lista de páginas rellenadas.
	 *
	 * @param pagina
	 *                   Pagina
	 */
	public void pushPaginaFormulario(final PaginaData pagina) {
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
		final PaginaData p = paginas.pop();
		paginasPosteriores.put(p.getIdentificador(), p);
	}

	/**
	 * Método de acceso a paginaActual.
	 *
	 * @return paginaActual
	 */
	public int obtenerIndicePaginaFormularioActual() {
		// Ultima pagina cargada
		return paginas.size();
	}

	/**
	 * Obtiene las dependencias de un campo.
	 *
	 * @param idCampo
	 *                     Id campo
	 * @param elemento
	 *                     Indica si es elemento
	 * @return lista de id campo para los cuales tiene dependencia
	 */
	public DependenciaCampo obtenerDependenciaCampo(final String idCampo, final boolean elemento) {
		DependenciaCampo res;
		if (!elemento) {
			res = getDependenciaCampoPaginaFormulario(idCampo);
		} else {
			res = getDependenciaCampoPaginaElemento(idCampo);
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
	public List<ValorCampo> obtenerValoresAccesiblesCampoPaginaFormulario(final String idCampo) {
		final List<ValorCampo> res = new ArrayList<>();

		// Podra tener accesibles los valores de las paginas anteriores
		for (int i = ConstantesNumero.N1; i < obtenerIndicePaginaFormularioActual(); i++) {
			res.addAll(obtenerPaginaFormularioData(i).getValores());
		}

		// Podra tener accesibles los valores de los campos anteriores en la
		// pagina actual
		for (final ValorCampo vc : obtenerPaginaDataActual(false).getValores()) {
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
	 * Devuelve los valores de los campos accesibles del formulario desde la página
	 * actual.
	 *
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> obtenerValoresAccesiblesPaginaFormularioActual() {
		final List<ValorCampo> res = new ArrayList<>();
		for (int i = ConstantesNumero.N1; i <= obtenerIndicePaginaFormularioActual(); i++) {
			res.addAll(obtenerPaginaFormularioData(i).getValores());
		}
		return res;
	}

	/**
	 * Devuelve los valores de los campos páginas posteriores a la página actual
	 * (páginas formulario descartadas).
	 *
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> obtenerValoresPosterioresPaginaFormularioActual() {
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
	public List<ValorCampo> obtenerValoresIniciales() {
		return valoresIniciales;
	}


	/**
	 * Obtiene páginas rellenadas.
	 *
	 * @return Lista ids páginas rellenadas
	 */
	public List<String> obtenerIdsPaginasFormularioRellenadas() {
		final List<String> res = new ArrayList<>();
		for (final PaginaData p : this.paginas) {
			res.add(p.getIdentificador());
		}
		return res;
	}

	/**
	 * Devuelve los valores de los campos accesibles desde el script de un campo de
	 * la pagina de elemento que se está editando.
	 *
	 * @param idCampo
	 *                    id campo
	 * @return Valores de los campos.
	 */
	public List<ValorCampo> obtenerEdicionElementoValoresAccesiblesCampo(final String idCampo) {


		chekEdicionElemento();

		// Valores accesibles en página principal desde campo LEL
		final List<ValorCampo> valoresPaginaPrincipal = obtenerValoresAccesiblesCampoPaginaFormulario(datosEdicionElemento.getIdCampoListaElementos());

		// Valores accesibles en página elemento desde el campo que se está evaluando
		final List<ValorCampo> valoresPaginaElemento = new ArrayList<ValorCampo>();
		for (final ValorCampo vc : datosEdicionElemento.getPaginaElemento().getValores()) {
			if (vc.getId().equals(idCampo)) {
				valoresPaginaElemento.add(vc);
				break;
			} else {
				valoresPaginaElemento.add(vc);
			}
		}

		// Retornamos valores
		final List<ValorCampo> res = new ArrayList<ValorCampo>();
		res.addAll(valoresPaginaPrincipal);
		res.addAll(valoresPaginaElemento);
		return res;
	}


	/**
	 * Obtiene valores accesibles desde página elemento.
	 * @return valores accesibles
	 */
	public List<ValorCampo> obtenerEdicionElementoValoresAccesiblesPagina() {

		chekEdicionElemento();

		// Valores accesibles en página principal desde campo LEL
		final List<ValorCampo> valoresPaginaPrincipal = obtenerValoresAccesiblesCampoPaginaFormulario(datosEdicionElemento.getIdCampoListaElementos());

		// Valores accesibles en página elemento (todos los de la página)
		final List<ValorCampo> valoresPaginaElemento = new ArrayList<ValorCampo>();
		for (final ValorCampo vc : datosEdicionElemento.getPaginaElemento().getValores()) {
			valoresPaginaElemento.add(vc);
		}

		// Retornamos valores
		final List<ValorCampo> res = new ArrayList<ValorCampo>();
		res.addAll(valoresPaginaPrincipal);
		res.addAll(valoresPaginaElemento);
		return res;

	}

	/**
	 * Devuelve id lista elementos que se está editando.
	 * @return id
	 */
	public String obtenerEdicionElementoIdCampoListaElementos() {
		chekEdicionElemento();
		return datosEdicionElemento.getIdCampoListaElementos();
	}

	// ------------------ FUNCIONES INTERNAS -------------------------
	/**
	 * Dependencia campo pagina elemento.
	 *
	 * @param idCampo
	 *                    id Campo
	 * @return Dependencia campo
	 */
	private DependenciaCampo getDependenciaCampoPaginaElemento(final String idCampo) {
		DependenciaCampo res = null;
		chekEdicionElemento();
		for (final DependenciaCampo config : datosEdicionElemento.getPaginaElemento().getDependencias()) {
			if (config.getIdCampo().equals(idCampo)) {
				res = config;
				break;
			}
		}
		if (res == null) {
			throw new ErrorConfiguracionException("No existeix camp amb id: " + idCampo);
		}
		return res;
	}

	/**
	 * Verifica si se está editando un elemento.
	 */
	private void chekEdicionElemento() {
		if (datosEdicionElemento == null) {
			throw new ErrorConfiguracionException("No existen datos de edición de elemento");
		}
	}

	/**
	 * Dependencia campo pagina formulario.
	 *
	 * @param idCampo
	 *                    id Campo
	 * @return Dependencia campo
	 */
	private DependenciaCampo getDependenciaCampoPaginaFormulario(final String idCampo) {
		// Obtiene dependencias paginas anteriores
		DependenciaCampo res = null;
		for (final PaginaData pagina : paginas) {
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
	 * Indica que se va a editar un elemento.
	 *
	 * @param datosEdicionElemento
	 *                                 datosEdicionElemento a establecer
	 */
	public void inicializarEdicionElemento(final DatosEdicionElemento datosEdicionElemento) {
		this.datosEdicionElemento = datosEdicionElemento;
		chekEdicionElemento();
	}

	/**
	 * Indica que se finaliza edición elemento.
	 *
	 */
	public void resetearEdicionElemento() {
		this.datosEdicionElemento = null;
	}

	/**
	 * Verifica si se está editando un elemento.
	 * @return indica si se está editando elemento nuevo
	 */
	public boolean isEdicionElementoElementoNuevo() {
		chekEdicionElemento();
		// Indica si es nuevo (no tiene asignado indice)
		return datosEdicionElemento.getIndiceElemento() == null;
	}

	/**
	 * Obtiene índice elemento que se esta modificando.
	 * @return indice
	 */
	public Integer obtenerEdicionElementoIndiceElemento() {
		return datosEdicionElemento.getIndiceElemento();
	}




	// ----------------------------------------------------------------------------------------
	//	FUNCIONES PRIVADAS
	// ----------------------------------------------------------------------------------------

	/**
	 * Obtiene una página.
	 *
	 * @param indicePagina
	 *                         indice pagina
	 * @return pagina
	 */
	private PaginaData obtenerPaginaFormularioData(final int indicePagina) {
		return paginas.get(indicePagina - ConstantesNumero.N1);
	}


}
