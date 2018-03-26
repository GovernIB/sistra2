package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de tramites version.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramitesVersion extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {
		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		/* inicializa breadcrum */
		breadCrumb = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem("Area 1");
		item.setUrl("#");
		breadCrumb.addElement(item);

		item = new DefaultMenuItem("Tramite 1");
		item.setUrl("#");
		breadCrumb.addElement(item);

		breadCrumb.generateUniqueIds();

		final TramiteVersion tramiteVersion1 = new TramiteVersion();
		tramiteVersion1.setId(Long.valueOf(1));
		tramiteVersion1.setNumeroVersion(1);
		tramiteVersion1.setTipoFlujo(TypeFlujo.NORMAL);

		final Literal desc1 = new Literal();
		desc1.add(new Traduccion("es", "Trámite 1 - Convocatoria de diciembre de 2017"));
		desc1.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de desembre de 2017"));
		tramiteVersion1.setDescripcion(desc1);

		tramiteVersion1.setActiva(false);
		tramiteVersion1.setRelease(5);
		tramiteVersion1.setCodigoUsuarioBloqueo("usuario1");

		final TramiteVersion tramiteVersion2 = new TramiteVersion();
		tramiteVersion2.setId(Long.valueOf(2));
		tramiteVersion2.setNumeroVersion(2);
		tramiteVersion2.setTipoFlujo(TypeFlujo.NORMAL);

		final Literal desc2 = new Literal();
		desc2.add(new Traduccion("es", "Trámite 1 - Convocatoria de febrero de 2018"));
		desc2.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de febrer de 2018"));
		tramiteVersion2.setDescripcion(desc2);

		tramiteVersion2.setActiva(false);
		tramiteVersion2.setRelease(20);

		final TramiteVersion tramiteVersion3 = new TramiteVersion();
		tramiteVersion3.setId(Long.valueOf(3));
		tramiteVersion3.setNumeroVersion(3);
		tramiteVersion3.setTipoFlujo(TypeFlujo.PERSONALIZADO);

		final Literal desc3 = new Literal();
		desc3.add(new Traduccion("es", "Trámite 1 - Convocatoria de junio de 2018"));
		desc3.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de juny de 2018"));
		tramiteVersion3.setDescripcion(desc3);

		tramiteVersion3.setActiva(true);
		tramiteVersion3.setRelease(12);

		listaDatos = new ArrayList<>();
		listaDatos.add(tramiteVersion1);
		listaDatos.add(tramiteVersion2);
		listaDatos.add(tramiteVersion3);

	}

	/**
	 * Breadcrumb.
	 **/
	private MenuModel breadCrumb;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<TramiteVersion> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private TramiteVersion datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<TramiteVersion> tramiteVersionesFiltradas = new ArrayList<>();
		for (final TramiteVersion tramiteVersion : this.listaDatos) {
			if (tramiteVersion.getDescripcion() != null && tramiteVersion.getDescripcion()
					.getTraduccion(getSesion().getLang()).toLowerCase().contains(filtro.toLowerCase())) {
				tramiteVersionesFiltradas.add(tramiteVersion);
			}
		}

		this.listaDatos = tramiteVersionesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(datoSeleccionado.getId().toString()));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.EDICION.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Bloquear version.
	 */
	public void bloquear() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Cuaderno.
	 */
	public void cuaderno() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Importar versión.
	 */
	public void importar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Historial.
	 */
	public void historial() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Regrescar.
	 */
	public void refrescar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Duplicar.
	 */
	public void duplicar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		listaDatos.remove(this.datoSeleccionado);
		// Refrescamos datos
		filtrar();
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
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
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<TramiteVersion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<TramiteVersion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public TramiteVersion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final TramiteVersion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the breadCrumb
	 */
	public MenuModel getBreadCrumb() {
		return breadCrumb;
	}

	/**
	 * @param breadCrumb
	 *            the breadCrumb to set
	 */
	public void setBreadCrumb(final MenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

}
