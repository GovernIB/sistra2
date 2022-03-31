package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarTR extends DialogControllerBase {

	@Inject
	SecurityService securityService;

	@Inject
	TramiteService tramiteService;

	/** Fila Importar. */
	private FilaImportarTramite data;

	/** Fila Importar. */
	private FilaImportarArea filaArea;

	/** Tramites. **/
	private List<Tramite> tramites;

	/** Tramite. **/
	private Long tramite;

	/** Accion. **/
	private String accion;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarTramite) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		filaArea = (FilaImportarArea) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR_AREA);
		cargarDatos();

		if (data.getAccion() != null) {
			accion = data.getAccion().toString();
		}
		if (data.getAccion() == TypeImportarAccion.SELECCIONAR
				&& this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
			for (final Tramite tram : tramites) {
				if (tram.getIdentificador().equals(data.getIdentificador())) {
					tramite = tram.getCodigo();
					break;
				}
			}
		}

		if (data.getAcciones() != null && data.getAcciones().size() == 1) {
			accion = data.getAcciones().get(0).toString();
		}

	}

	/**
	 * Permite escoger sólo los áreas del trámite.
	 */
	private void cargarDatos() {
		if (this.filaArea.getAccion() == TypeImportarAccion.SELECCIONAR) {
			tramites = tramiteService.listTramite(this.filaArea.getArea().getCodigo(), null);
		}
	}

	/** Consultar. **/
	public void consultarTramite() {

		if (this.data.getTramiteActual() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getTramiteActual().getCodigo()));
			UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.CONSULTA, params, true, 520, 200);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		data.setMensaje(null);
		final TypeImportarAccion typeAccion = TypeImportarAccion.fromString(accion);
		if (typeAccion == TypeImportarAccion.CREAR) {
			if (data.getIdentificador() == null || data.getIdentificador().isEmpty() || data.getDescripcion() == null
					|| data.getDescripcion().isEmpty()) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteImportarTR.error.datosvacios"));
				return;
			}
			if (tramiteService.checkIdentificadorRepetido(this.data.getIdentificador(), null, this.filaArea.getArea().getCodigo())) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteImportarTR.error.identificadorRepetido"));
				return;
			}
			data.setTramiteActual(null);
			data.setDestinoIdentificador(this.data.getIdentificador());
			data.setDestinoDescripcion(this.data.getDescripcion());
			this.data.setExiste(TypeImportarExiste.NO_EXISTE);
		}

		if (typeAccion == TypeImportarAccion.SELECCIONAR) {

			for (final Tramite tram : tramites) {
				if (tram.getCodigo().compareTo(tramite) == 0) {

					if (filaArea.getAccion() == TypeImportarAccion.SELECCIONAR
							&& tram.getIdArea().compareTo(filaArea.getArea().getCodigo()) != 0) {
						data.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.cambiaareaTramite"));
					}

					if (filaArea.getAccion() == TypeImportarAccion.CREAR) {
						data.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.cambiaareaTramite"));
					}

					data.setTramiteActual(tram);
					data.setIdentificador(tram.getIdentificador());
					data.setDescripcion(tram.getDescripcion());
					data.setDestinoIdentificador(tram.getIdentificador());
					data.setDestinoDescripcion(tram.getDescripcion());
					break;
				}
			}
			data.setExiste(TypeImportarExiste.EXISTE);
		}

		data.setAccion(typeAccion);

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.data);

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
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
	 * @param data the data to set
	 */
	public void setData(final FilaImportarTramite data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public FilaImportarTramite getData() {
		return data;
	}

	/**
	 * @return the tramites
	 */
	public final List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites the tramites to set
	 */
	public final void setTramites(final List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the tramite
	 */
	public final Long getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public final void setTramite(final Long tramite) {
		this.tramite = tramite;
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

}
