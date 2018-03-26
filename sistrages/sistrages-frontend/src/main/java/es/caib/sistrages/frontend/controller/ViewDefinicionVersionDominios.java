package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de dominios empleados.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionDominios extends ViewControllerBase {

	/** Aqui se debería de pasar la id. **/
	private String id;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Dominio datoSeleccionado;

	/**
	 * Tramite Version.
	 */
	private TramiteVersion tramiteVersion;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionDominios.
	 */
	public ViewDefinicionVersionDominios() {
		super();
	}

	@PostConstruct
	public void init() {

		// A partir de la id, se cargaría el tramite version.
		this.tramiteVersion = new TramiteVersion();
		/* dominio */
		final Dominio dominio1 = new Dominio();
		dominio1.setId(1L);
		dominio1.setCodigo("1");
		dominio1.setDescripcion("Dominio 1");
		dominio1.setAmbito(TypeAmbito.GLOBAL);
		final Dominio dominio2 = new Dominio();
		dominio2.setId(2L);
		dominio2.setCodigo("2");
		dominio2.setDescripcion("Dominio 2");
		dominio2.setAmbito(TypeAmbito.AREA);
		final Dominio dominio3 = new Dominio();
		dominio3.setId(3L);
		dominio3.setCodigo("3");
		dominio3.setDescripcion("Dominio 3");
		dominio3.setAmbito(TypeAmbito.GLOBAL);
		final Dominio dominio4 = new Dominio();
		dominio4.setId(4L);
		dominio4.setCodigo("4");
		dominio4.setDescripcion("Dominio 4");
		dominio4.setAmbito(TypeAmbito.ENTIDAD);
		final Dominio dominio5 = new Dominio();
		dominio5.setId(5L);
		dominio5.setCodigo("5");
		dominio5.setDescripcion("Dominio 5");
		dominio5.setAmbito(TypeAmbito.GLOBAL);

		final ArrayList<Dominio> listaDominios = new ArrayList<>();
		listaDominios.add(dominio1);
		listaDominios.add(dominio2);
		listaDominios.add(dominio3);
		listaDominios.add(dominio4);
		listaDominios.add(dominio5);

		tramiteVersion.setListaDominios(listaDominios);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void anyadir() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf("1"));
		UtilJSF.openDialog(DialogDefinicionVersionDominios.class, TypeModoAcceso.ALTA, params, true, 950, 450);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				// this.filtrar();
				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");
				break;
			case EDICION:
				// Actualizamos fila actual
				// final String id = (String) respuesta.getResult();
				// final Area dataUpdated = testService.load(id);
				// this.areaSeleccionada.setDescripcion(dataUpdated.getDescripcion());
				// Mensaje
				message = UtilJSF.getLiteral("info.modificado.ok");
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
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void quitar() {

		if (!verificarFilaSeleccionada())
			return;

		tramiteVersion.getListaDominios().remove(this.datoSeleccionado);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void consultar() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), datoSeleccionado.getId().toString());
		params.put(TypeParametroVentana.AMBITO.toString(), TypeAmbito.ENTIDAD.toString());
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 950, 450);
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
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

}
