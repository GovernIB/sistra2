package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroDialogo;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominio extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	// @Inject
	// private DominioService dominioGlobalService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private Dominio data;

	/**
	 * Propiedad seleccionada
	 */
	private Propiedad propiedadSeleccionada;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Dominio();
		} else {
			data = new Dominio();// dominioGlobalService.load(id);
			data.setId(Long.valueOf(id));
			data.setCodigo("Entidad 1");
			data.setDescripcion("Descripc");
			data.setCacheable(true);
			data.setTipo("B");
			final List<Propiedad> propiedades = new ArrayList<>();
			final Propiedad p1 = new Propiedad();
			p1.setCodigo("COD 1");
			p1.setValor("VAL 1");
			propiedades.add(p1);
			final Propiedad p2 = new Propiedad();
			p2.setCodigo("COD 2");
			p2.setValor("VAL 2");
			propiedades.add(p2);
			data.setParametros(propiedades);
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
				final Propiedad propiedad = (Propiedad) respuesta.getResult();
				if (this.data.getParametros() == null) {
					this.data.setParametros(new ArrayList<Propiedad>());
				}
				this.data.getParametros().add(propiedad);
				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");
				break;
			case EDICION:
				// Actualizamos fila actual
				final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);

				this.data.getParametros().remove(posicion);
				this.data.getParametros().add(posicion, propiedadEdicion);
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
	public void nuevaPropiedad() {
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 360, 200);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarPropiedad() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroDialogo.ID.toString(), String.valueOf(this.propiedadSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 360, 200);
	}

	/**
	 * Editar descripcion del dominio.
	 */
	public void editarDescripcion() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroDialogo.DATO.toString(), String.valueOf(this.data.getDescripcion()));
		UtilJSF.openDialog(DialogTraduccion.class, TypeModoAcceso.EDICION, params, true, 460, 250);
	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.getParametros().remove(this.propiedadSeleccionada);

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);
		if (posicion >= this.data.getParametros().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.data.getParametros().remove(posicion);
		this.data.getParametros().add(posicion + 1, propiedad);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getParametros().indexOf(this.propiedadSeleccionada);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.data.getParametros().remove(posicion);
		this.data.getParametros().add(posicion - 1, propiedad);
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		// final DataTable dataTable = (DataTable)
		// FacesContext.getCurrentInstance().getViewRoot().findComponent(":dialogTest:dataTablePropiedades");
		if (this.propiedadSeleccionada == null) {
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
		result.setResult(data.getCodigo());
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

	public void mensaje() {
		UtilJSF.showMessageDialog(TypeNivelGravedad.INFO, "Atento", "Ojo al dato.");
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Dominio getData() {
		return data;
	}

	public void setData(final Dominio data) {
		this.data = data;
	}

	/**
	 * @return the propiedadSeleccionada
	 */
	public Propiedad getPropiedadSeleccionada() {
		return propiedadSeleccionada;
	}

	/**
	 * @param propiedadSeleccionada
	 *            the propiedadSeleccionada to set
	 */
	public void setPropiedadSeleccionada(final Propiedad propiedadSeleccionada) {
		this.propiedadSeleccionada = propiedadSeleccionada;
	}

}
