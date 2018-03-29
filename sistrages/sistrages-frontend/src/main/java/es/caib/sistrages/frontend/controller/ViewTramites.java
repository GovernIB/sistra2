package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.service.AreaService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de tramites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramites extends ViewControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private AreaService areaService;

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		/*
		 * areas = new DefaultTreeNode("Root", null);
		 *
		 * final Area area1 = new Area(); area1.setId(1l); area1.setCodigo("1");
		 * area1.setDescripcion("Area 1"); areas.getChildren().add(new
		 * DefaultTreeNode(area1));
		 *
		 * final Area area2 = new Area(); area2.setId(2l); area2.setCodigo("2");
		 * area2.setDescripcion("Area 2"); areas.getChildren().add(new
		 * DefaultTreeNode(area2));
		 *
		 * final Area area3 = new Area(); area3.setId(3l); area3.setCodigo("3");
		 * area3.setDescripcion("Area 3"); areas.getChildren().add(new
		 * DefaultTreeNode(area3));
		 *
		 * final Area area4 = new Area(); area4.setId(4l); area4.setCodigo("4");
		 * area4.setDescripcion("Area 4"); areas.getChildren().add(new
		 * DefaultTreeNode(area4));
		 *
		 * final Area area5 = new Area(); area5.setId(5l); area5.setCodigo("5");
		 * area5.setDescripcion("Area 5"); areas.getChildren().add(new
		 * DefaultTreeNode(area5));
		 *
		 * final Area area6 = new Area(); area6.setId(6l); area6.setCodigo("6");
		 * area6.setDescripcion("Area 6"); areas.getChildren().add(new
		 * DefaultTreeNode(area6));
		 */
		tramites = new ArrayList<>();
		final Tramite tramite1 = new Tramite();
		tramite1.setId(1l);
		tramite1.setCodigo("TRAMIT1");
		final Literal trad1 = new Literal();
		trad1.add(new Traduccion("ca", "Inscripció a les proves en catalá"));
		trad1.add(new Traduccion("es", "Inscripció a les proves en catalá"));
		tramite1.setDescripcion(trad1);
		tramite1.setActivo(true);
		tramite1.setBloqueada(false);
		final Calendar calendar = Calendar.getInstance();
		tramite1.setModificacion(calendar.getTime());
		final Tramite tramite2 = new Tramite();
		tramite2.setId(2l);
		tramite2.setCodigo("TRAMIT2");
		final Literal trad2 = new Literal();
		trad2.add(new Traduccion("ca", "Solicitud d'admisió de FP a distància"));
		trad2.add(new Traduccion("es", "Solicitud d'admisió de FP a distància"));
		tramite2.setDescripcion(trad2);
		tramite2.setActivo(false);
		tramite2.setBloqueada(false);
		calendar.set(2016, 2, 2);
		tramite2.setModificacion(calendar.getTime());
		final Tramite tramite3 = new Tramite();
		tramite3.setId(3l);
		tramite3.setCodigo("TRAMIT3");
		final Literal trad3 = new Literal();
		trad3.add(new Traduccion("ca", "Solicitud d'admisió d'escolarització"));
		trad3.add(new Traduccion("es", "Solicitud d'admisió d'escolarització"));
		tramite3.setDescripcion(trad3);
		tramite3.setActivo(true);
		tramite3.setBloqueada(true);
		calendar.set(2014, 3, 3);
		tramite3.setModificacion(calendar.getTime());
		tramites.add(tramite1);
		tramites.add(tramite2);
		tramites.add(tramite3);

		this.buscarAreas();

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(((Area) this.areaSeleccionada.getData()).getId()));
		UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.EDICION, params, true, 520, 120);
	}

	/**
	 * Abre dialogo de nueva Area.
	 */
	public void nuevaArea() {
		UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.ALTA, null, true, 520, 120);
	}

	/**
	 * Abre dialogo para eliminar dato.
	 */
	public void eliminarArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		this.areaService.remove(area.getId());
		this.buscarAreas();
	}

	/**
	 * Dominios de area.
	 */
	public void dominioArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + area.getId());
	}

	/**
	 * Fuente datos area.
	 */
	public void datosArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + area.getId());
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.tramiteSeleccionada.getId()));
		UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.EDICION, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para editar versiones de tramite.
	 */
	public void versionesTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.tramiteSeleccionada.getCodigo());
		UtilJSF.redirectJsfPage("/secure/app/viewTramitesVersion.xhtml");

	}

	/**
	 * Abre dialogo de nueva Area.
	 */
	public void nuevaTramite() {
		UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.ALTA, null, true, 540, 220);
	}

	/**
	 * Abre dialogo para eliminar dato.
	 */
	public void eliminarTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();
		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				this.buscarAreas();

				// Mensaje
				message = UtilJSF.getLiteral("test.altaOk");
				break;
			case EDICION:
				this.buscarAreas();

				// Mensaje
				message = "Update realizado";
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Aqui
	 */
	private void buscarAreas() {

		areas = new DefaultTreeNode("Root", null);
		for (final Area area : areaService.list(this.filtro)) {
			areas.getChildren().add(new DefaultTreeNode(area));
		}
	}

	/**
	 * Método publico de filtrar
	 */
	public void filtrar() {
		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		this.buscarAreas();
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				// this.filtrar();
				// Mensaje
				message = UtilJSF.getLiteral("test.altaOk");
				break;
			case EDICION:
				// Actualizamos fila actual
				final String id = (String) respuesta.getResult();
				// final Area dataUpdated = testService.load(id);
				// this.areaSeleccionada.setDescripcion(dataUpdated.getDescripcion());
				// Mensaje
				// TODO Ver acceso literales desde codigo
				message = "Update realizado";
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Procedimientos tramite.
	 */
	public void procedimientosTramite() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaArea() {
		boolean filaSeleccionada = true;
		if (this.areaSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaTramite() {
		boolean filaSeleccionada = true;
		if (this.tramiteSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Area seleccionada.
	 *
	 * @param event
	 */
	public void onRowUnselectArea(final NodeSelectEvent event) {
		this.areaSeleccionada = null;
	}

	/**
	 * Area seleccionada.
	 *
	 * @param event
	 */
	public void onRowSelectArea(final NodeSelectEvent event) {
		this.areaSeleccionada = (DefaultTreeNode) event.getTreeNode();
		final Area area = (Area) event.getTreeNode().getData();
		if (area.getDescripcion().contains("1") || area.getDescripcion().contains("3")
				|| area.getDescripcion().contains("6")) {
			tramites = new ArrayList<>();
			final Tramite tramite1 = new Tramite();
			tramite1.setId(1l);
			tramite1.setCodigo("TRAMIT1");
			final Literal trad1 = new Literal();
			trad1.add(new Traduccion("ca", "Inscripció a les proves en catalá"));
			trad1.add(new Traduccion("es", "Inscripció a les proves en catalá"));
			tramite1.setDescripcion(trad1);
			tramite1.setActivo(true);
			final Tramite tramite2 = new Tramite();
			tramite2.setId(2l);
			tramite2.setCodigo("TRAMIT2");
			final Literal trad2 = new Literal();
			trad2.add(new Traduccion("ca", "Solicitud d'admisió de FP a distància"));
			trad2.add(new Traduccion("es", "Solicitud d'admisió de FP a distància"));
			tramite2.setDescripcion(trad2);
			tramite2.setActivo(false);
			tramites.add(tramite1);
			tramites.add(tramite2); // org.primefaces.context.RequestContext.getCurrentInstance().update("dataTableTramites");
		} else if (area.getDescripcion().contains("2") || area.getDescripcion().contains("6")) {
			tramites = new ArrayList<>();
			final Tramite tramite1 = new Tramite();
			tramite1.setId(1l);
			tramite1.setCodigo("TRAMIT1");
			final Literal trad1 = new Literal();
			trad1.add(new Traduccion("ca", "Inscripció a les proves en catalá"));
			trad1.add(new Traduccion("es", "Inscripció a les proves en catalá"));
			tramite1.setDescripcion(trad1);
			tramite1.setActivo(true);
			final Tramite tramite2 = new Tramite();
			tramite2.setId(2l);
			tramite2.setCodigo("TRAMIT2");
			final Literal trad2 = new Literal();
			trad2.add(new Traduccion("ca", "Solicitud d'admisió de FP a distància"));
			trad2.add(new Traduccion("es", "Solicitud d'admisió de FP a distància"));
			tramite2.setDescripcion(trad2);
			tramite2.setActivo(false);
			final Tramite tramite3 = new Tramite();
			tramite3.setId(3l);
			tramite3.setCodigo("TRAMIT3");
			final Literal trad3 = new Literal();
			trad3.add(new Traduccion("ca", "Solicitud d'admisió d'escolarització"));
			trad3.add(new Traduccion("es", "Solicitud d'admisió d'escolarització"));
			tramite3.setDescripcion(trad3);
			tramite3.setActivo(true);
			tramites.add(tramite1);
			tramites.add(tramite2);
			tramites.add(tramite3);
		} else {
			tramites = new ArrayList<>();
			final Tramite tramite2 = new Tramite();
			tramite2.setId(2l);
			tramite2.setCodigo("TRAMIT2");
			final Literal trad2 = new Literal();
			trad2.add(new Traduccion("ca", "Solicitud d'admisió de FP a distància"));
			trad2.add(new Traduccion("es", "Solicitud d'admisió de FP a distància"));
			tramite2.setDescripcion(trad2);
			tramite2.setActivo(false);
			final Tramite tramite3 = new Tramite();
			tramite3.setId(3l);
			tramite3.setCodigo("TRAMIT3");
			final Literal trad3 = new Literal();
			trad3.add(new Traduccion("ca", "Solicitud d'admisió d'escolarització"));
			trad3.add(new Traduccion("es", "Solicitud d'admisió d'escolarització"));
			tramite3.setDescripcion(trad3);
			tramite3.setActivo(true);
			tramites.add(tramite2);
			tramites.add(tramite3);
		}

	}

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private TreeNode areas;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DefaultTreeNode areaSeleccionada;

	/**
	 * Lista de datos.
	 */
	private List<Tramite> tramites;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Tramite tramiteSeleccionada;

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
	 * @return the areas
	 */
	public TreeNode getAreas() {
		return areas;
	}

	/**
	 * @param areas
	 *            the areas to set
	 */
	public void setAreas(final TreeNode areas) {
		this.areas = areas;
	}

	/**
	 * @return the areaSeleccionada
	 */
	public DefaultTreeNode getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * @param areaSeleccionada
	 *            the areaSeleccionada to set
	 */
	public void setAreaSeleccionada(final DefaultTreeNode areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * @return the tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            the tramites to set
	 */
	public void setTramites(final List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the tramiteSeleccionada
	 */
	public Tramite getTramiteSeleccionada() {
		return tramiteSeleccionada;
	}

	/**
	 * @param tramiteSeleccionada
	 *            the tramiteSeleccionada to set
	 */
	public void setTramiteSeleccionada(final Tramite tramiteSeleccionada) {
		this.tramiteSeleccionada = tramiteSeleccionada;
	}

}
