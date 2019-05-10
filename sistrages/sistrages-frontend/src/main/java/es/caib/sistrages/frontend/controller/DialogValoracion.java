package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogValoracion extends DialogControllerBase {

	@Inject
	private EntidadService entidadService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private IncidenciaValoracion data;

	/** Descripcion. **/
	private String descripcion;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new IncidenciaValoracion();

		} else {
			if (id != null) {
				data = entidadService.loadValoracion(Long.valueOf(id));
				if (data != null && data.getDescripcion() != null) {
					descripcion = data.getDescripcion().getTraduccion(UtilJSF.getSessionBean().getLang());
				}
			}
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setDescripcion(literales);
			descripcion = literales.getTraduccion(UtilJSF.getSessionBean().getLang());
		}

	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccionAlta();
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), null, null);
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (entidadService.existeIdentificadorValoracion(data.getIdentificador(), UtilJSF.getIdEntidad(),
				data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogValoracion.error.codigoIdentificador"));
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			entidadService.addValoracion(UtilJSF.getIdEntidad(), data);
			break;
		case EDICION:
			entidadService.updateValoracion(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
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
	 * @return the data
	 */
	public IncidenciaValoracion getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final IncidenciaValoracion data) {
		this.data = data;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public final String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

}
