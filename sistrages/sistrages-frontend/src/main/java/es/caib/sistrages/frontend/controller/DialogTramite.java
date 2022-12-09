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
	 * Enlace sessionBean.
	 */
	@Inject
	private SessionBean sessionBean;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** Datos elemento. */
	private Tramite data;

	private String portapapeles;

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
		if (idArea == null) {
			if (data.getIdArea() != null) {
				idArea = data.getIdArea().toString();
			}
		}
		if (isIdentificadorRepetido()) {
			addMessageContext(TypeNivelGravedad.INFO, "ERROR",
					UtilJSF.getLiteral("dialogTramite.error.identificadorDuplicado"));
			return;
		}
		String identifCompuesto = sessionBean.getEntidad().getIdentificador() + "."
				+ tramiteService.getArea(Long.valueOf(getIdArea())).getIdentificador() + "." + data.getIdentificador();
		if (identifCompuesto.length() > 100) {
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.identifCompuestoLength", new Object[] { identifCompuesto }));
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
		return tramiteService.checkIdentificadorRepetido(data.getIdentificador(), data.getCodigo(),
				Long.valueOf(idArea));
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
