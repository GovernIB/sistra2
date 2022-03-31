package es.caib.sistrages.frontend.controller.migracion;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.migracion.ConstantesMigracion;
import es.caib.sistrages.core.api.model.comun.migracion.ErrorMigracion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.service.migracion.MigracionService;
import es.caib.sistrages.frontend.controller.ViewControllerBase;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Migracion
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewMigracion extends ViewControllerBase {

	@Inject
	private MigracionService migracionService;

	@Inject
	private TramiteService tramiteService;

	private List<Tramite> listaTramiteSistra;

	private List<Tramite> listaTramite;

	private List<TramiteVersion> listaTramiteVersionSistra;

	private Long tramiteSistraSeleccionado;

	private Long tramiteSeleccionado;

	private Integer versionSistraSeleccionado;

	private String version;

	private List<ErrorMigracion> listaErrores;

	private boolean unificarPantallas;

	private boolean saltaExcepcion = false;

	private boolean disabled = false;

	/**
	 * Inicializacion.
	 */
	public void init() {

		final Long idEntidad = UtilJSF.getIdEntidad();

		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		try {

			setListaTramiteSistra(migracionService.getTramiteSistra());

			setListaTramite(tramiteService.listTramiteByEntidad(UtilJSF.getIdEntidad()));

			Collections.sort(listaTramite, (o1, o2) -> o1.getIdentificador().compareTo(o2.getIdentificador()));

		} catch (Exception ex) {
			UtilJSF.loggearErrorFront("Error en la migracion", ex);
			saltaExcepcion = true;

		}

	}

	public void actualizarVersion() {

		if (getTramiteSistraSeleccionado() != null) {
			setListaTramiteVersionSistra(migracionService.getTramiteVersionSistra(getTramiteSistraSeleccionado()));
		}

	}

	public void migrar() {
		if (tramiteSistraSeleccionado != null && versionSistraSeleccionado != null && tramiteSeleccionado != null
				&& StringUtils.isNotEmpty(version)) {
			if (!tramiteService.tieneTramiteNumVersionRepetida(tramiteSeleccionado, Integer.valueOf(version))) {
				// migrar
				final Map<String, Object> params = new HashMap<>();
				params.put(ConstantesMigracion.IDIOMA, UtilJSF.getSessionBean().getLang());
				params.put(ConstantesMigracion.USERNAME, UtilJSF.getSessionBean().getUserName());
				params.put(ConstantesMigracion.UNIFICAR_PANTALLAS, Boolean.valueOf(unificarPantallas));

				listaErrores = migracionService.migrarTramiteVersion(tramiteSistraSeleccionado,
						versionSistraSeleccionado, tramiteSeleccionado, Integer.valueOf(version), params);

				if (listaErrores == null || listaErrores.isEmpty()) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.migracion"));
				} else {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.migracionCompleta"));
				}
				disabled = true;
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
			}
		}
	}

	/***
	 * Función que se encarga de retornar el contenido de la etiqueta style en
	 * funcion del tipo de elemento para mostrar o ocultar elementos. Depende de
	 * saltaExcepcion, variable que contiene true si ocurre una excepción con la
	 * base de datos de sistra1
	 *
	 * @elemento posibles valores: 1 hace referencia a la etiqueta que contiene el
	 *           mensaje de error. Oculta por defecto. otros hace referencia a los
	 *           elementos visibles por defecto (desplegables, botones ...)
	 */
	public String setVisibleExcepcion(int elemento) {
		if (elemento == 1) {
			if (!saltaExcepcion) {
				return "display:none;";
			} else {
				return "color:red; display:flex; justify-content:center;";
			}
		} else {
			if (!saltaExcepcion) {
				return "";
			} else {
				return "display:none;";
			}
		}
	}

	public List<Tramite> getListaTramiteSistra() {
		return listaTramiteSistra;
	}

	public void setListaTramiteSistra(final List<Tramite> listaTramiteSistra) {
		this.listaTramiteSistra = listaTramiteSistra;
	}

	public List<TramiteVersion> getListaTramiteVersionSistra() {
		return listaTramiteVersionSistra;
	}

	public void setListaTramiteVersionSistra(final List<TramiteVersion> listaTramiteVersionSistra) {
		this.listaTramiteVersionSistra = listaTramiteVersionSistra;
	}

	public List<Tramite> getListaTramite() {
		return listaTramite;
	}

	public void setListaTramite(final List<Tramite> listaTramite) {
		this.listaTramite = listaTramite;
	}

	public Long getTramiteSistraSeleccionado() {
		return tramiteSistraSeleccionado;
	}

	public void setTramiteSistraSeleccionado(final Long tramiteSistraSeleccionado) {
		this.tramiteSistraSeleccionado = tramiteSistraSeleccionado;
	}

	public Integer getVersionSistraSeleccionado() {
		return versionSistraSeleccionado;
	}

	public void setVersionSistraSeleccionado(final Integer versionSistraSeleccionado) {
		this.versionSistraSeleccionado = versionSistraSeleccionado;
	}

	public Long getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	public void setTramiteSeleccionado(final Long tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public List<ErrorMigracion> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(final List<ErrorMigracion> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public boolean isUnificarPantallas() {
		return unificarPantallas;
	}

	public void setUnificarPantallas(final boolean unificarPantallas) {
		this.unificarPantallas = unificarPantallas;
	}

	public final boolean isDisabled() {
		return disabled;
	}

	public final void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
