package es.caib.sistrages.frontend.controller.migracion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.Document;

import es.caib.sistrages.core.api.exception.migracion.MigracionException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.migracion.ConstantesMigracion;
import es.caib.sistrages.core.api.model.comun.migracion.ErrorMigracion;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.service.migracion.MigracionService;
import es.caib.sistrages.frontend.controller.ViewControllerBase;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.core.api.util.CsvUtil;

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

	private List<Area> listaArea;

	private Long areaSeleccionada;

	private List<TramiteVersion> listaTramiteVersionSistra;

	private Long tramiteSistraSeleccionado;

	private Long tramiteSeleccionado;

	private Integer versionSistraSeleccionado;

	private String version;

	private List<ErrorMigracion> listaErrores;

	private boolean unificarPantallas;

	private boolean saltaExcepcion = false;

	private boolean disabled = false;

	private String estilo = "display: none;";

	private String portapapeles;

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
			setListaArea(tramiteService.listArea(UtilJSF.getIdEntidad(), ""));

			Collections.sort(listaArea, (o1, o2) -> o1.getIdentificador().compareTo(o2.getIdentificador()));

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

	public void actualizarTramitesS2() {
		if (getAreaSeleccionada() != null) {
			setListaTramite(tramiteService.listTramite(getAreaSeleccionada(), ""));
		}
	}

	public void migrar() {
		if (tramiteSistraSeleccionado != null && versionSistraSeleccionado != null && tramiteSeleccionado != null
				&& StringUtils.isNotEmpty(version)) {
			if (migracionService.isDestinoCorrecto(tramiteSistraSeleccionado,
					Integer.valueOf(versionSistraSeleccionado))) {
				// migrar
				final Map<String, Object> params = new HashMap<>();
				params.put(ConstantesMigracion.IDIOMA, UtilJSF.getSessionBean().getLang());
				params.put(ConstantesMigracion.USERNAME, UtilJSF.getSessionBean().getUserName());
				params.put(ConstantesMigracion.UNIFICAR_PANTALLAS, Boolean.valueOf(unificarPantallas));

				try {
					listaErrores = migracionService.migrarTramiteVersion(tramiteSistraSeleccionado,
							versionSistraSeleccionado, tramiteSeleccionado, Integer.valueOf(version), params);
					if (listaErrores == null || listaErrores.isEmpty()) {
						UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.migracion"));
					} else {
						UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.migracionCompleta"));
						estilo = "display: block;";
					}
					disabled = true;
				} catch (final MigracionException e) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, e.getMessage());
				}

			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
			}
		}
	}

	public StreamedContent getExportarErrores() throws Exception {
		final Document doc = new Document();
		final CsvDocumento csv = new CsvDocumento();
		final String[] cols = { "Tipo", "Elemento", "Descripcion" };
		csv.setColumnas(cols);
		int i = 0;
		for (ErrorMigracion err : listaErrores) {
			csv.addFila();
			csv.setValor(i, "Tipo", err.getTipo().name());
			csv.setValor(i, "Elemento", err.getElemento());
			csv.setValor(i, "Descripcion", err.getDescripcion());
			i++;
		}

		final byte[] contentsFuenteDatosCSV = CsvUtil.exportar(csv);

		final InputStream myInputStream = new ByteArrayInputStream(contentsFuenteDatosCSV);
		return new DefaultStreamedContent(myInputStream, "text/csv", "erroresMigracion.csv");
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

	public final Long getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public final void setAreaSeleccionada(Long areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	public final List<Area> getListaArea() {
		return listaArea;
	}

	public final void setListaArea(List<Area> listaArea) {
		this.listaArea = listaArea;
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

	public final String getEstilo() {
		return estilo;
	}

	public final void setEstilo(String estilo) {
		this.estilo = estilo;
	}

}
