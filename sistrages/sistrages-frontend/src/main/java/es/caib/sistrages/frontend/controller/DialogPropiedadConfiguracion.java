package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogPropiedadConfiguracion extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private ConfiguracionGlobal data;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ConfiguracionGlobal();
		} else {
			if (id != null) {
				data = configuracionGlobalService.getConfiguracionGlobal(Long.valueOf(id));
			}
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
			if (configuracionGlobalService.getConfiguracionGlobal(data.getPropiedad()) != null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.duplicated"));
				return;
			}

			configuracionGlobalService.addConfiguracionGlobal(data);

			break;
		case EDICION:
			configuracionGlobalService.updateConfiguracionGlobal(data);
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

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public ConfiguracionGlobal getData() {
		return data;
	}

	public void setData(final ConfiguracionGlobal data) {
		this.data = data;
	}

}
