package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarTV extends DialogControllerBase {

	@Inject
	TramiteService tramiteService;

	/** FilaImportar. */
	private FilaImportarTramiteVersion data;

	/** FilaImportar. */
	private FilaImportarTramite filaTramite;

	/** Accion. **/
	private String accion;

	/** Versión seleccionada cuando se selecciona reemplazar. **/
	private Long version;

	/** Lista de versiones del trámite. **/
	private List<TramiteVersion> versiones = new ArrayList<>();

	/** Nueva versión cuando se selecciona crear. **/
	private String nuevaVersion;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData((FilaImportarTramiteVersion) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR));
		filaTramite = (FilaImportarTramite) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR_TRAMITE);
		if (data.getAccion() != null) {
			setAccion(data.getAccion().toString());
		}
		if (data.getAcciones().contains(TypeImportarAccion.REEMPLAZAR)) {
			versiones = tramiteService.listTramiteVersion(filaTramite.getTramiteActual().getCodigo(), null);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		final TypeImportarAccion typeAccion = TypeImportarAccion.fromString(accion);
		if (typeAccion == TypeImportarAccion.INCREMENTAR) {
			if (filaTramite.getAccion() == TypeImportarAccion.CREAR) {
				data.setNumVersion("1");
				data.setDestinoVersion("1");
				data.setDestinoRelease("1");
			} else {
				final int numVersion = tramiteService
						.getTramiteNumVersionMaximo(filaTramite.getTramiteActual().getCodigo());
				data.setNumVersion(String.valueOf(numVersion + 1));
				data.setDestinoVersion(String.valueOf(numVersion + 1));
				data.setDestinoRelease("1");
			}
		}

		if (typeAccion == TypeImportarAccion.REEMPLAZAR) {

			for (final TramiteVersion tramVersion : versiones) {
				if (tramVersion.getCodigo().compareTo(version) == 0) {

					if (tramVersion.getBloqueada()) {
						this.addMessageContext(TypeNivelGravedad.INFO,
								UtilJSF.getLiteral("dialogTramiteImportarTV.versionbloqueada"));
						return;
					}

					data.setNumVersion(String.valueOf(tramVersion.getNumeroVersion()));
					data.setDestinoVersion(String.valueOf(tramVersion.getNumeroVersion()));
					data.setDestinoRelease(String.valueOf(tramVersion.getRelease() + 1));
					data.setTramiteVersionActual(tramVersion);
					break;
				}
			}
		}

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		data.setAccion(typeAccion);
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
	 * @return the data
	 */
	public FilaImportarTramiteVersion getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FilaImportarTramiteVersion data) {
		this.data = data;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(final String accion) {
		this.accion = accion;
	}

	/**
	 * @return the version
	 */
	public final Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public final void setVersion(final Long version) {
		this.version = version;
	}

	/**
	 * @return the versiones
	 */
	public final List<TramiteVersion> getVersiones() {
		return versiones;
	}

	/**
	 * @param versiones the versiones to set
	 */
	public final void setVersiones(final List<TramiteVersion> versiones) {
		this.versiones = versiones;
	}

	/**
	 * @return the nuevaVersion
	 */
	public final String getNuevaVersion() {
		return nuevaVersion;
	}

	/**
	 * @param nuevaVersion the nuevaVersion to set
	 */
	public final void setNuevaVersion(final String nuevaVersion) {
		this.nuevaVersion = nuevaVersion;
	}

}
