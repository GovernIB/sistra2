package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramite extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private TramiteService tramiteService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	private String idArea;

	/**
	 * Datos elemento.
	 */
	private Tramite data;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		if (modo == TypeModoAcceso.ALTA) {
			data = new Tramite();
		} else {
			data = tramiteService.getTramite(Long.valueOf(id));
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (isIdentificadorRepetido()) {
			UtilJSF.showMessageDialog(TypeNivelGravedad.INFO, "ERROR",
					UtilJSF.getLiteral("dialogTramite.error.identificadorDuplicado"));
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			tramiteService.addTramite(Long.valueOf(getIdArea()), data);
			break;
		case EDICION:
			tramiteService.updateTramite(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getIdentificador());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Comprueba si el identificador esta repetido.
	 * 
	 * @return
	 */
	private boolean isIdentificadorRepetido() {
		return tramiteService.checkIdentificadorRepetido(data.getIdentificador(), data.getCodigo());
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

	public Tramite getData() {
		return data;
	}

	public void setData(final Tramite data) {
		this.data = data;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

}
