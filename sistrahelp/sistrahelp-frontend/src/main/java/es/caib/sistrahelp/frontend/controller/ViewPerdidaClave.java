package es.caib.sistrahelp.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.PerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewAuditoriaTramites.
 */
@ManagedBean
@ViewScoped
public class ViewPerdidaClave extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	/**
	 * lista datos.
	 */
	private List<PerdidaClave> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private PerdidaClave datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroPerdidaClave filtros;

	/**
	 * Inicializa.
	 */
	public void init() {
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new FiltroPerdidaClave(convierteListaAreas());
	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		// Normaliza filtro
		normalizarFiltro();

		// Buscar
		this.buscar();
	}

	private void normalizarFiltro() {
		// filtros.setDatoFormulario(StringUtils.trim(filtros.getDatoFormulario()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra
		final ResultadoPerdidaClave datosRes = helpDeskService.obtenerClaveTramitacion(filtros);

		if (datosRes.getResultado() == -1) {
			listaDatos = null;
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("warning.perdidaClave.tamanyosuperado"));
		} else if (datosRes.getResultado() == 0) {
			listaDatos = null;
		} else if (datosRes.getResultado() == 1) {
			listaDatos = datosRes.getListaClaves();
		}
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Obtiene el valor de filaSeleccionada.
	 *
	 * @return el valor de filaSeleccionada
	 */
	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	/**
	 * Verificar fila seleccionada.
	 *
	 * @return true, if successful
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Convierte lista areas.
	 *
	 * @return the lista de
	 */
	private List<String> convierteListaAreas() {
		List<String> resultado = null;

		final List<Area> lista = UtilJSF.getSessionBean().getListaAreasEntidad();

		if (lista != null && !lista.isEmpty()) {
			resultado = new ArrayList<>();
			for (final Area area : lista) {
				resultado.add(area.getIdentificador());
			}
		}

		return resultado;

	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public List<PerdidaClave> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos
	 *            el nuevo valor de listaDatos
	 */
	public void setListaDatos(final List<PerdidaClave> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public PerdidaClave getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final PerdidaClave datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public FiltroPerdidaClave getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros
	 *            el nuevo valor de filtros
	 */
	public void setFiltros(final FiltroPerdidaClave filtros) {
		this.filtros = filtros;
	}

}
