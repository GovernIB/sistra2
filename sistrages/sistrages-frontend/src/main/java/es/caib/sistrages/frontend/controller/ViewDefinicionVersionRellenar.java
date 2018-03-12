package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.model.types.TypeInterno;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de definici&oacute;n de versi&oacute;n de rellenar.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDefinicionVersionRellenar extends ViewControllerBase {

	/** Data. **/
	private TramitePasoRellenar data;

	/** Id. **/
	private String id;

	/** Dato seleccionado en la lista. */
	private Formulario datoSeleccionado;

	/**
	 * Crea una nueva instancia de ViewDefinicionVersionRellenar.
	 */
	public ViewDefinicionVersionRellenar() {
		super();

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
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

				final Formulario formulario = (Formulario) respuesta.getResult();
				data.getFormulariosTramite().add(formulario);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Formulario formularioMod = (Formulario) respuesta.getResult();
				final int posicion = data.getFormulariosTramite().indexOf(this.datoSeleccionado);

				data.getFormulariosTramite().remove(posicion);
				data.getFormulariosTramite().add(posicion, formularioMod);
				this.datoSeleccionado = formularioMod;

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

	@PostConstruct
	public void init() {
		// TODO tendría que obtener el tramitePasoRellenar a partir de la id.
		id = "1";
		final Formulario formulario1 = new Formulario();
		formulario1.setId(1l);
		formulario1.setCodigo("Formularis");
		final Traducciones traducciones1 = new Traducciones();
		traducciones1.add(new Traduccion("ca", "Formulari 1"));
		traducciones1.add(new Traduccion("es", "Formulario 1"));
		formulario1.setDescripcion(traducciones1);
		formulario1.setObligatoriedad(TypeFormularioObligatoriedad.DEPENDIENTE);
		formulario1.setDebeFirmarse(true);
		formulario1.setScriptFirma(new Script());
		formulario1.setDebePrerregistrarse(true);
		formulario1.setScriptPrerrigistro(new Script());
		formulario1.setScriptDatosIniciales(new Script());
		formulario1.setTipoFormulario(TypeInterno.INTERNO);

		final Formulario formulario2 = new Formulario();
		formulario2.setId(2l);
		formulario2.setCodigo("Formularis2");
		final Traducciones traducciones2 = new Traducciones();
		traducciones2.add(new Traduccion("ca", "Dades relacionats a l'interessat per a emplar el formulari"));
		traducciones2.add(new Traduccion("es", "Datos relacionados con el interesado para rellenar el formulario"));
		formulario2.setDescripcion(traducciones2);
		formulario2.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		formulario2.setDebeFirmarse(true);
		formulario2.setScriptFirma(new Script());
		formulario2.setDebePrerregistrarse(true);
		formulario2.setScriptPrerrigistro(new Script());
		formulario2.setScriptDatosIniciales(new Script());
		formulario2.setTipoFormulario(TypeInterno.EXTERNO);

		final Formulario formulario3 = new Formulario();
		formulario3.setId(3l);
		formulario3.setCodigo("Formularis3");
		final Traducciones traducciones3 = new Traducciones();
		traducciones3.add(new Traduccion("ca", "Dades relacionats a l'interessat per a emplar el formulari"));
		traducciones3.add(new Traduccion("es", "Datos relacionados con el interesado para rellenar el formulario"));
		formulario3.setDescripcion(traducciones3);
		formulario3.setObligatoriedad(TypeFormularioObligatoriedad.OBLIGATORIO);
		formulario3.setDebeFirmarse(true);
		formulario3.setScriptFirma(new Script());
		formulario3.setDebePrerregistrarse(true);
		formulario3.setScriptPrerrigistro(new Script());
		formulario3.setScriptDatosIniciales(new Script());
		formulario3.setTipoFormulario(TypeInterno.INTERNO);
		data = new TramitePasoRellenar();
		final List<Formulario> formularios = new ArrayList<>();
		formularios.add(formulario1);
		formularios.add(formulario2);
		formularios.add(formulario3);
		data.setFormulariosTramite(formularios);
	}

	/**
	 * Abre un di&aacute;logo para anyadir los datos.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogDefinicionVersionRellenar.class, TypeModoAcceso.ALTA, null, true, 600, 200);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		UtilJSF.openDialog(DialogDefinicionVersionFormulario.class, TypeModoAcceso.EDICION, params, true, 1100, 500);
	}

	/**
	 * Eliminar.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		this.data.getFormulariosTramite().remove(this.datoSeleccionado);
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
	 * Sube la propiedad de posición.
	 */
	public void subir() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getFormulariosTramite().indexOf(this.datoSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Formulario formulario = this.data.getFormulariosTramite().remove(posicion);
		this.data.getFormulariosTramite().add(posicion - 1, formulario);

		for (int i = 0; i < this.data.getFormulariosTramite().size(); i++) {
			this.data.getFormulariosTramite().get(i).setOrden(i + 1);
		}
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajar() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getFormulariosTramite().indexOf(this.datoSeleccionado);
		if (posicion >= this.data.getFormulariosTramite().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Formulario formulario = this.data.getFormulariosTramite().remove(posicion);
		this.data.getFormulariosTramite().add(posicion + 1, formulario);

		for (int i = 0; i < this.data.getFormulariosTramite().size(); i++) {
			this.data.getFormulariosTramite().get(i).setOrden(i + 1);
		}
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
	 * @return the datoSeleccionado
	 */
	public Formulario getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Formulario datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the data
	 */
	public TramitePasoRellenar getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramitePasoRellenar data) {
		this.data = data;
	}

}
