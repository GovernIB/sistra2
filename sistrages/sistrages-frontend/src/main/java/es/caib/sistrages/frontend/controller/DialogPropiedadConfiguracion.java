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

	/** Servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Id elemento a tratar. */
	private String id;

	/** Descripcion. */
	private String descripcion;

	/** Datos elemento. */
	private ConfiguracionGlobal data;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new ConfiguracionGlobal();
			descripcion = "";
		} else {
			if (id != null) {
				data = configuracionGlobalService.getConfiguracionGlobal(Long.valueOf(id));

				if (data.isNoModificable()) {
					descripcion = data.getDescripcion() + " ("
							+ UtilJSF.getLiteral("dialogPropiedadConfiguracion.noModificable") + ")";
				} else {
					descripcion = data.getDescripcion();
				}
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
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.duplicated"));
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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("propiedadesConfiguracionDialog");
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}
