/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

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
	 * Editar Script.
	 */
	public void editarScriptPersonalizacion() {
		cargarDialogScript(this.tramiteVersion.getIdScriptPersonalizacion());
	}

	/**
	 * Editar Script.
	 */
	public void editarScriptInicializacion() {
		cargarDialogScript(this.tramiteVersion.getIdScriptInicializacionTramite());
	}

	/**
	 * Método que se encarga de cargar el dialog de carga dependiendo de si existe
	 * ya el script o no.
	 *
	 * @param id
	 */
	private void cargarDialogScript(final Long id) {
		if (id == null) {
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.ALTA, null, true, 950, 700);
		} else {
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), id.toString());
			UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 950, 700);
		}
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

		final Literal desc1 = new Literal();
		desc1.add(new Traduccion("es", "Trámite 1 - Convocatoria de diciembre de 2017 "));
		desc1.add(new Traduccion("ca", "Tràmit 1 - Convocatòria de desembre de 2017"));
		tramiteVersion.setDescripcion(desc1);

		tramiteVersion.setAutenticado(true);
		tramiteVersion.setNivelQAA(2);
		tramiteVersion.setIdiomasSoportados("es,ca");
		tramiteVersion.setPersistencia(true);
		tramiteVersion.setPersistenciaInfinita(false);
		tramiteVersion.setPersistenciaDias(15);
		tramiteVersion.setIdScriptPersonalizacion(Long.valueOf(2));
		// tramiteVersion.setIdScriptInicializacionTramite(Long.valueOf(3));

		/** tratamiento idiomas para pasarlos de la lista **/
		tramiteVersionIdiomaEsSoportado = tramiteVersion.getIdiomasSoportados().contains("es");
		tramiteVersionIdiomaCaSoportado = tramiteVersion.getIdiomasSoportados().contains("ca");
		tramiteVersionIdiomaEnSoportado = tramiteVersion.getIdiomasSoportados().contains("en");
		tramiteVersionIdiomaDeSoportado = tramiteVersion.getIdiomasSoportados().contains("de");
	}

	/**
	 * Editar descripcion.
	 *
	 * 
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.tramiteVersion.getDescripcion(), null, null);
	}

	/**
	 * Retorno dialogo de la traduccion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Literal traducciones = (Literal) respuesta.getResult();
				this.tramiteVersion.setDescripcion(traducciones);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:

				final Literal traduccionesMod = (Literal) respuesta.getResult();
				this.tramiteVersion.setDescripcion(traduccionesMod);

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
	 * Retorno dialogo del script params iniciales.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoParamsIniciales(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Long idScript = (Long) respuesta.getResult();
				this.tramiteVersion.setIdScriptInicializacionTramite(idScript);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				// No debería cambiar el id del script
				final Long idScriptEdicion = (Long) respuesta.getResult();
				this.tramiteVersion.setIdScriptInicializacionTramite(idScriptEdicion);

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
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoPersonalizacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {

			switch (respuesta.getModoAcceso()) {

			case ALTA:

				final Long idScript = (Long) respuesta.getResult();
				this.tramiteVersion.setIdScriptPersonalizacion(idScript);

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				break;

			case EDICION:
				// No debería cambiar el id del script
				final Long idScriptEdicion = (Long) respuesta.getResult();
				this.tramiteVersion.setIdScriptPersonalizacion(idScriptEdicion);

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
