package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFormularioExterno extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private FormularioExternoService formularioService;

	/** Id elemento a tratar. */
	private String id;

	/** Data en formato JSON. */
	private String iData;

	/** Datos elemento. */
	private GestorExternoFormularios data;

	/** Max long propiedades. */
	private static final int MAXLENGTH_PROPIEDADES = 4000;

	private TypePlugin tipoInicialEdicion;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new GestorExternoFormularios();
		} else {
			data = formularioService.getFormularioExterno(new Long(id));
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}
			formularioService.addFormularioExterno(UtilJSF.getIdEntidad(), data);
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}

			formularioService.updateFormularioExterno(data);
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
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (data.getIdentificador() == null || data.getIdentificador().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getDescripcion() == null || data.getDescripcion().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getUrl() == null || data.getUrl().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		final boolean existe = formularioService.existeFormulario(data.getIdentificador(), data.getCodigo());

		if (existe) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}
		return true;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public GestorExternoFormularios getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final GestorExternoFormularios data) {
		this.data = data;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("formulariosExternosDialog");
	}

}
