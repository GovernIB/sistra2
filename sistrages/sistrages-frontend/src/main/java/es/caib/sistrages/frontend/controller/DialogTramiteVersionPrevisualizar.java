package es.caib.sistrages.frontend.controller;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.TramitePrevisualizacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionPrevisualizar extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Servicio. **/
	@Inject
	private SystemService systemService;

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private TramiteVersion data;

	/** Idioma del tramite. **/
	private String idioma;

	/** Idiomas. **/
	private List<TypeIdioma> idiomas = new ArrayList<>();

	/** Valor seleccionado. **/
	private Propiedad valorSeleccionado;

	/** Tramite. **/
	private Tramite tramite;

	/** Plugin rolsac. **/
	private ICatalogoProcedimientosPlugin iplugin;

	/**
	 * JSON con la lista de valores (identificador - valor)
	 */
	private List<Propiedad> parametros = new ArrayList<>();

	/** Url . **/
	private String url;

	/** Tramite seleccionado. **/
	private String tramiteSeleccionado;

	/** Tramites. **/
	private List<DefinicionTramiteCP> tramites;

	/** Url Tramite. **/
	private String urlTramite;

	public String getText(final DefinicionTramiteCP tramite) {

		final StringBuilder texto = new StringBuilder();
		if (!tramite.isVigente()) {
			texto.append("(V) ");
		}
		if (tramite.getProcedimiento().getIdProcedimientoSIA() == null
				|| tramite.getProcedimiento().getIdProcedimientoSIA().isEmpty()) {
			texto.append("(S) ");
		}

		texto.append(tramite.getIdentificador());
		texto.append(" - ");
		texto.append(tramite.getDescripcion());
		String respuesta = texto.toString();
		if (texto.toString().length() >= 100) {
			respuesta = respuesta.substring(0, 100);
		}
		return respuesta;

	}

	/**
	 * Inicialización.
	 *
	 * @throws CatalogoPluginException
	 */
	public void init() throws CatalogoPluginException {
		setData(tramiteService.getTramiteVersion(Long.valueOf(id)));

		tramite = tramiteService.getTramite(this.data.getIdTramite());
		idioma = "ca"; // Ponemos por defecto el catalan.
		for (final TypeIdioma tipo : TypeIdioma.values()) {
			for (final String idi : data.getIdiomasSoportados().split(";")) {
				if (idi.equals(tipo.toString())) {
					idiomas.add(tipo);
				}
			}
		}

		final TramitePrevisualizacion tramitePrevisualizacion = (TramitePrevisualizacion) UtilJSF.getSessionBean()
				.getMochilaDatos().get(Constantes.CLAVE_MOCHILA_TRAMITE + this.data.getIdTramite());
		if (tramitePrevisualizacion != null) {
			tramiteSeleccionado = tramitePrevisualizacion.getProcedimiento();
			idioma = tramitePrevisualizacion.getIdioma();
			parametros = tramitePrevisualizacion.getParametros();
		}

		iplugin = (ICatalogoProcedimientosPlugin) componenteService
				.obtenerPluginEntidad(TypePlugin.CATALOGO_PROCEDIMIENTOS, UtilJSF.getIdEntidad());

		tramites = iplugin.obtenerTramites(tramite.getIdentificador(), this.data.getNumeroVersion(), idioma);
		if (tramiteSeleccionado == null && tramites != null && !tramites.isEmpty()) {
			tramiteSeleccionado = tramites.get(0).getIdentificador();
		}
		calcularUrl(false);
	}

	/**
	 * Previsualizar
	 *
	 * @param previsualizar
	 *            Si es true, se previsualizar, si es false, se copia.
	 */
	public void aceptar() {

		if (!calcularUrl(true)) {
			return;
		}
		setUrl(urlTramite);
	}

	/**
	 * Recalcula la url porque se ha cambiado un campo
	 */
	public void recalcularURL() {
		calcularUrl(false);
	}

	/**
	 * Método que calcula la url.
	 *
	 * @param lanzarError
	 *            Si está a true, manda un error al copiar, si está a false, pone el
	 *            literal en el copy/paste
	 * @return
	 */
	private boolean calcularUrl(final boolean lanzarError) {
		if (tramiteSeleccionado == null) {
			if (lanzarError) {
				addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogTramiteVersionPrevisualizar.error.sinseleccionartramite"));
			} else {
				urlTramite = UtilJSF.getLiteral("dialogTramiteVersionPrevisualizar.error.sinseleccionartramite");
			}
			return false;
		}

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();

		final TramitePrevisualizacion tramitePrevisualizacion = new TramitePrevisualizacion();
		tramitePrevisualizacion.setProcedimiento(tramiteSeleccionado);
		tramitePrevisualizacion.setIdioma(idioma);
		tramitePrevisualizacion.setParametros(parametros);
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE + this.data.getIdTramite(), tramitePrevisualizacion);

		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL.toString());

		final String params = getParamsUrl();
		boolean servicioCatalogo = false;
		for (final DefinicionTramiteCP tram : tramites) {
			if (tram.getIdentificador().equals(tramiteSeleccionado)) {
				servicioCatalogo = tram.getProcedimiento().isServicio();
				break;
			}
		}

		urlTramite = urlBase + "/asistente/iniciarTramite.html?tramite=" + tramite.getIdentificador() + "&version="
				+ data.getNumeroVersion() + "&idioma=" + idioma + "&servicioCatalogo=" + servicioCatalogo
				+ "&idTramiteCatalogo=" + tramiteSeleccionado + params;
		return true;
	}

	/** Avisa al growl que se ha copiado correctamente **/
	public void avisarGrowl() {
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Obtiene la lista de parametros
	 *
	 * @return
	 */
	private String getParamsUrl() {
		final StringBuilder paramsUrl = new StringBuilder();
		if (this.parametros != null && !this.parametros.isEmpty()) {
			for (final Propiedad parametro : this.parametros) {
				if (paramsUrl.length() == 0) {
					paramsUrl.append("&parametros=");
				} else {
					paramsUrl.append("-_-");
				}
				paramsUrl.append(escapeHtml4(parametro.getCodigo()));
				paramsUrl.append("-_-");
				paramsUrl.append(escapeHtml4(parametro.getValor()));
			}
		}
		return paramsUrl.toString();
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
		UtilJSF.openHelp("tramiteVersionPrevisualizarDialog");
	}

	/**
	 * Crea nuevo valor.
	 */
	public void nuevoValor() {
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 430, 120);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarValor() {

		if (!verificarFilaSeleccionadaValor())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.valorSeleccionado));
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 430, 120);
	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		this.parametros.remove(this.valorSeleccionado);
		recalcularURL();

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:

				// Refrescamos datos
				final Propiedad propiedad = (Propiedad) respuesta.getResult();
				boolean duplicado = false;

				for (final Propiedad prop : parametros) {
					if (prop.getCodigo().equals(propiedad.getCodigo())) {
						duplicado = true;
						break;
					}
				}

				if (duplicado) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.parametros.add(propiedad);
				}

				recalcularURL();
				break;

			case EDICION:

				// Actualizamos fila actual
				final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
				boolean duplicadoEdicion = false;

				// Muestra dialogo
				final int posicion = this.parametros.indexOf(this.valorSeleccionado);

				for (final Propiedad prop : parametros) {
					if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
						duplicadoEdicion = true;
						break;
					}
				}

				if (duplicadoEdicion && !valorSeleccionado.getCodigo().equals(propiedadEdicion.getCodigo())) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.parametros.remove(posicion);
					this.parametros.add(posicion, propiedadEdicion);
					this.valorSeleccionado = propiedadEdicion;
				}

				recalcularURL();
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaValor() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.parametros.indexOf(this.valorSeleccionado);
		if (posicion >= this.parametros.size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.parametros.remove(posicion);
		this.parametros.add(posicion + 1, propiedad);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirValor() {
		if (!verificarFilaSeleccionadaValor())
			return;

		final int posicion = this.parametros.indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.parametros.remove(posicion);
		this.parametros.add(posicion - 1, propiedad);
	}

	public void validar() {
		final List<ErrorValidacion> listaErrores = tramiteService.validarVersionTramite(data,
				UtilJSF.getSessionBean().getLang());
		if (!listaErrores.isEmpty()) {
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

			mochilaDatos.put(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION,
					listaErrores.stream().map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			UtilJSF.openDialog(DialogErroresValidacion.class, TypeModoAcceso.CONSULTA, null, true, 1050, 520);
		} else {
			addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.validacion"));

		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public TramiteVersion getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final TramiteVersion data) {
		this.data = data;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public Propiedad getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final Propiedad valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the parametros
	 */
	public List<Propiedad> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            the parametros to set
	 */
	public void setParametros(final List<Propiedad> parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return the idiomas
	 */
	public List<TypeIdioma> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas
	 *            the idiomas to set
	 */
	public void setIdiomas(final List<TypeIdioma> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the tramites
	 */
	public List<DefinicionTramiteCP> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            the tramites to set
	 */
	public void setTramites(final List<DefinicionTramiteCP> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the tramiteSeleccionado
	 */
	public String getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	/**
	 * @param tramiteSeleccionado
	 *            the tramiteSeleccionado to set
	 */
	public void setTramiteSeleccionado(final String tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	/**
	 * @return the urlTramite
	 */
	public final String getUrlTramite() {
		return urlTramite;
	}

	/**
	 * @param urlTramite
	 *            the urlTramite to set
	 */
	public final void setUrlTramite(final String urlTramite) {
		this.urlTramite = urlTramite;
	}

}
