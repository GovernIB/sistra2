/*
 *
 */
package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDefinicionVersionPropiedades extends DialogControllerBase {

	/** Id elemento a tratar. */
	private Long id;

	/**
	 * tramite version.
	 */
	private TramiteVersion tramiteVersion;

	/**
	 * tramite version idioma Es soportado.
	 */
	private boolean tramiteVersionIdiomaEsSoportado;

	/**
	 * tramite version idioma Ca soportado.
	 */
	private boolean tramiteVersionIdiomaCaSoportado;

	/**
	 * tramite version idioma En soportado.
	 */
	private boolean tramiteVersionIdiomaEnSoportado;

	/**
	 * tramite version idioma De soportado.
	 */
	private boolean tramiteVersionIdiomaDeSoportado;

	/**
	 * Inicialización.
	 */
	public void init() {
		recuperaTramiteVersion(Long.valueOf(1));
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			break;
		case EDICION:
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		// result.setResult(data.getCodigo());
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
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion
	 *            el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * Recupera tramite version.
	 *
	 * @param id
	 *            el id de tramite version
	 */
	private void recuperaTramiteVersion(final Long id) {
		tramiteVersion = new TramiteVersion();
		tramiteVersion.setId(id);

		final Traducciones desc1 = new Traducciones();
		desc1.add(new Traduccion("es", "Trámite 1 - Convocatoria de diciembre de 2017 "));
		desc1.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de desembre de 2017"));
		tramiteVersion.setDescripcion(desc1);

		tramiteVersion.setAutenticado(true);
		tramiteVersion.setNivelQAA(2);
		tramiteVersion.setIdiomasSoportados("es,ca");
		tramiteVersion.setPersistencia(true);
		tramiteVersion.setPersistenciaInfinita(false);
		tramiteVersion.setPersistenciaDias(15);
		tramiteVersion.setIdScriptPersonalizacion(Long.valueOf(1));

		/** tratamiento idiomas para pasarlos de la lista **/
		tramiteVersionIdiomaEsSoportado = tramiteVersion.getIdiomasSoportados().contains("es");
		tramiteVersionIdiomaCaSoportado = tramiteVersion.getIdiomasSoportados().contains("ca");
		tramiteVersionIdiomaEnSoportado = tramiteVersion.getIdiomasSoportados().contains("en");
		tramiteVersionIdiomaDeSoportado = tramiteVersion.getIdiomasSoportados().contains("de");
	}

	/**
	 * Editar descripcion.
	 */
	public void editarDescripcion() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Verifica si el idioma Es está soportado.
	 *
	 * @return true, si es tramite version idioma es soportado
	 */
	public boolean isTramiteVersionIdiomaEsSoportado() {
		return tramiteVersionIdiomaEsSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaEsSoportado.
	 *
	 * @param tramiteVersionIdiomaEsSoportado
	 *            el nuevo valor de tramiteVersionIdiomaEsSoportado
	 */
	public void setTramiteVersionIdiomaEsSoportado(final boolean tramiteVersionIdiomaEsSoportado) {
		this.tramiteVersionIdiomaEsSoportado = tramiteVersionIdiomaEsSoportado;
	}

	/**
	 * Verifica si el idioma Ca está soportado.
	 *
	 * @return true, si es tramite version idioma ca soportado
	 */
	public boolean isTramiteVersionIdiomaCaSoportado() {
		return tramiteVersionIdiomaCaSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaCaSoportado.
	 *
	 * @param tramiteVersionIdiomaCaSoportado
	 *            el nuevo valor de tramiteVersionIdiomaCaSoportado
	 */
	public void setTramiteVersionIdiomaCaSoportado(final boolean tramiteVersionIdiomaCaSoportado) {
		this.tramiteVersionIdiomaCaSoportado = tramiteVersionIdiomaCaSoportado;
	}

	/**
	 * Verifica si el idioma En está soportado.
	 *
	 * @return true, si es tramite version idioma en soportado
	 */
	public boolean isTramiteVersionIdiomaEnSoportado() {
		return tramiteVersionIdiomaEnSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaEnSoportado.
	 *
	 * @param tramiteVersionIdiomaEnSoportado
	 *            el nuevo valor de tramiteVersionIdiomaEnSoportado
	 */
	public void setTramiteVersionIdiomaEnSoportado(final boolean tramiteVersionIdiomaEnSoportado) {
		this.tramiteVersionIdiomaEnSoportado = tramiteVersionIdiomaEnSoportado;
	}

	/**
	 * Verifica si el idioma De está soportado.
	 *
	 * @return true, si es tramite version idioma de soportado
	 */
	public boolean isTramiteVersionIdiomaDeSoportado() {
		return tramiteVersionIdiomaDeSoportado;
	}

	/**
	 * Establece el valor de tramiteVersionIdiomaDeSoportado.
	 *
	 * @param tramiteVersionIdiomaDeSoportado
	 *            el nuevo valor de tramiteVersionIdiomaDeSoportado
	 */
	public void setTramiteVersionIdiomaDeSoportado(final boolean tramiteVersionIdiomaDeSoportado) {
		this.tramiteVersionIdiomaDeSoportado = tramiteVersionIdiomaDeSoportado;
	}

}
