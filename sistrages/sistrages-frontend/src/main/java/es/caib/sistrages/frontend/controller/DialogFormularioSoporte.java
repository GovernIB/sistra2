package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFormularioSoporte extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private List<FormularioSoporte> data;

	/** Data Seleccionada. **/
	private FormularioSoporte dataSeleccionada;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ArrayList<>();

		} else {
			data = new ArrayList<>();// dominioGlobalService.load(id);
			final FormularioSoporte form1 = new FormularioSoporte();
			final Traducciones tradDescripcion = new Traducciones();
			tradDescripcion.add(new Traduccion("ca", "Descripcion ca form1"));
			tradDescripcion.add(new Traduccion("es", "Descripcion es form1"));
			form1.setDescripcion(tradDescripcion);

			final Traducciones tradTipoIncidencia = new Traducciones();
			tradTipoIncidencia.add(new Traduccion("ca", "TipoIncidencia ca form1"));
			tradTipoIncidencia.add(new Traduccion("es", "TipoIncidencia es form1"));
			form1.setTipoIncidencia(tradTipoIncidencia);
			form1.setDestinatario("R");
			form1.setId(1l);

			final FormularioSoporte form2 = new FormularioSoporte();
			final Traducciones tradDescripcion2 = new Traducciones();
			tradDescripcion2.add(new Traduccion("ca", "Descripcion ca form2"));
			tradDescripcion2.add(new Traduccion("es", "Descripcion es form2"));
			form2.setDescripcion(tradDescripcion2);

			final Traducciones tradTipoIncidencia2 = new Traducciones();
			tradTipoIncidencia2.add(new Traduccion("ca", "TipoIncidencia ca form2"));
			tradTipoIncidencia2.add(new Traduccion("es", "TipoIncidencia es form2"));
			form2.setTipoIncidencia(tradTipoIncidencia2);
			form2.setDestinatario("E");
			form2.setEmails("email1@caib.es, email2@caib.es, email3@caib.es");
			data.add(form1);
			data.add(form2);
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
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
				final FormularioSoporte formulario = (FormularioSoporte) respuesta.getResult();
				this.data.add(formulario);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				// Actualizamos fila actual
				final FormularioSoporte propiedadEdicion = (FormularioSoporte) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.indexOf(this.dataSeleccionada);

				this.data.remove(posicion);
				this.data.add(posicion, propiedadEdicion);

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
	 * Crea nueva propiedad.
	 */
	public void nuevoFormulario() {
		// UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true,
		// 360, 200);
	}

	/**
	 * Edita una propiedad.
	 *
	 * @throws JsonProcessingException
	 */
	public void editarFormulario() throws JsonProcessingException {

		if (!verificarFilaSeleccionada())
			return;

		// final Map<String, String> params = new HashMap<>();
		// params.put(TypeParametroDialogo.DATO.toString(),
		// UtilJSON.toJSON(this.propiedadSeleccionada));
		// UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params,
		// true, 360, 200);
	}

	/**
	 * Editar descripcion del dominio.
	 *
	 * @throws JsonProcessingException
	 */
	public void editarDescripcion() throws JsonProcessingException {
		final Map<String, String> params = new HashMap<>();
		final Traducciones traducciones = new Traducciones();
		traducciones.add(new Traduccion("ca", "Traduccion ca"));
		traducciones.add(new Traduccion("es", "Traduccion es"));
		traducciones.add(new Traduccion("en", "Traduccion en"));
		final ObjectMapper mapper = new ObjectMapper();
		params.put(TypeParametroDialogo.DATO.toString(), mapper.writeValueAsString(traducciones));
		final List<String> obligatorios = new ArrayList<>();
		obligatorios.add("ca");
		final List<String> posibles = new ArrayList<>();
		posibles.add("ca");
		posibles.add("es");
		posibles.add("en");
		posibles.add("de");
		params.put("OBLIGATORIOS", mapper.writeValueAsString(obligatorios));
		params.put("POSIBLES", mapper.writeValueAsString(posibles));
		UtilJSF.openDialog(DialogTraduccion.class, TypeModoAcceso.EDICION, params, true, 460, 250);
	}

	/**
	 * Quita un formulario.
	 */
	public void quitarFormulario() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.remove(this.dataSeleccionada);

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.dataSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			/*
			 * if (dominioGlobalService.load(data.getCodigo()) != null) {
			 * UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
			 * "Ya existe dato con ese codigo"); return; } dominioGlobalService.add(data);
			 */

			break;
		case EDICION:
			// dominioGlobalService.update(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
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
	 * @return the data
	 */
	public List<FormularioSoporte> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<FormularioSoporte> data) {
		this.data = data;
	}

	/**
	 * @return the dataSeleccionada
	 */
	public FormularioSoporte getDataSeleccionada() {
		return dataSeleccionada;
	}

	/**
	 * @param dataSeleccionada
	 *            the dataSeleccionada to set
	 */
	public void setDataSeleccionada(final FormularioSoporte dataSeleccionada) {
		this.dataSeleccionada = dataSeleccionada;
	}

}
