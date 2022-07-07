package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de plugins (global y de entidad).
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewFormateadorFormulario extends ViewControllerBase {

	/** Filtro. */
	private String filtro;

	/** Lista de datos. */
	private List<FormateadorFormulario> listaDatos;

	/** Dato seleccionado en la lista. */
	private FormateadorFormulario datoSeleccionado;

	/** Id entidad. */
	private Long idEntidad;

	/** Paginacion */
	private Integer paginacion;

	/** FormateadorFormularioService. */
	@Inject
	private FormateadorFormularioService fmtService;

	/**
	 * Inicializacion.
	 */
	public void init() {
		paginacion = UtilJSF.getPaginacion("viewFormateadorFormulario");
		// Entidad activa
		idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera datos
		buscar();
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Busca
		buscar();
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		abrirDialogo(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		abrirDialogo(TypeModoAcceso.EDICION);
	}

	/**
	 * Consultar.
	 */
	public void consultar() {
		abrirDialogo(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		if (fmtService.tieneRelacionesFormateadorFormulario(this.datoSeleccionado.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
			return;
		}

		// Eliminamos
		fmtService.removeFormateadorFormulario(this.datoSeleccionado.getCodigo());

		// Refrescamos datos
		buscar();

		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));

	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("formateadorFormulario");
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (getPermiteEditar()) {
			editar();
		} else {
			consultar();
		}
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAlta() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
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
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				ResultadoError re = this.refrescar();
				if (re.getCodigo() != 1) {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". "
							+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
				} else {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
				}
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			buscar();
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// Filtra
		listaDatos = fmtService.listFormateadorFormulario(idEntidad, filtro, null);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
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
	 * Abre dialogo para editar dato.
	 */
	private void abrirDialogo(final TypeModoAcceso modo) {
		// Verifica si no hay fila seleccionada
		if (modo != TypeModoAcceso.ALTA && !verificarFilaSeleccionada()) {
			return;
		}
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (modo != TypeModoAcceso.ALTA) {
			params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		}
		UtilJSF.openDialog(DialogFormateadorFormulario.class, modo, params, true, 640, 230);
	}

	// ------- GETTERS / SETTERS --------------------------------

	/**
	 * Obtiene el valor de filtro.
	 *
	 * @return el valor de filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * Establece el valor de filtro.
	 *
	 * @param filtro el nuevo valor de filtro
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public List<FormateadorFormulario> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final List<FormateadorFormulario> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public FormateadorFormulario getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final FormateadorFormulario datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewFormateadorFormulario");
	}
}
