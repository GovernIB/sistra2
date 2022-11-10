package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.service.SeccionReutilizableService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDisenyoSeccionReutilizable extends DialogControllerBase {

	private SeccionReutilizable seccion;
	private List<SeccionReutilizable> secciones;

	@Inject
	SeccionReutilizableService seccionesService;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo != TypeModoAcceso.CONSULTA) {
			secciones = seccionesService.listSeccionReutilizable(UtilJSF.getIdEntidad(), null, true);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (seccion != null) {
			// Retornamos resultado
			final DialogResult result = new DialogResult();
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			result.setResult(seccion);
			UtilJSF.closeDialog(result);
		}
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
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("dialogDisenyoSeccionReutilizable");
	}

	/**
	 * @return the seccion
	 */
	public SeccionReutilizable getSeccion() {
		return seccion;
	}

	/**
	 * @param seccion the seccion to set
	 */
	public void setSeccion(SeccionReutilizable seccion) {
		this.seccion = seccion;
	}

	/**
	 * @return the secciones
	 */
	public List<SeccionReutilizable> getSecciones() {
		return secciones;
	}

	/**
	 * @param secciones the secciones to set
	 */
	public void setSecciones(List<SeccionReutilizable> secciones) {
		this.secciones = secciones;
	}

}
