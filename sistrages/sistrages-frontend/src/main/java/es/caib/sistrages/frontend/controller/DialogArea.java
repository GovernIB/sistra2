package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogArea extends DialogControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private TramiteService areaService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Id entidad.
	 */
	private Long idEntidad;

	/**
	 * Datos elemento.
	 */
	private Area data;

	/**
	 * Inicialización.
	 */
	public void init() {

		idEntidad = UtilJSF.getIdEntidad();

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new Area();
		} else {
			data = areaService.getArea(Long.valueOf(id));
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);

		if (areaService.checkIdentificadorAreaRepetido(data.getIdentificador(), data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.INFO, "ERROR",
					UtilJSF.getLiteral("dialogArea.error.identificadorDuplicado"));
			return;
		}

		final String[] listaExtensiones = data.getEmail().split(Constantes.LISTAS_SEPARADOR);
		for (final String cadena : listaExtensiones) {
			if (!cadena.matches(
					"^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$")
					&& cadena != null && !cadena.isEmpty()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.email.formato"));
				return;
			}
		}

		switch (acceso) {
		case ALTA:
			areaService.addArea(idEntidad, data);
			break;
		case EDICION:
			areaService.updateArea(data);
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
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public Area getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Area data) {
		this.data = data;
	}

}
