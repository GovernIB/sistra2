package es.caib.sistrages.frontend.controller;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.comun.TramitePrevisualizacion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
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

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private TramiteVersion data;

	/** Idioma del tramite. **/
	private String idioma;

	/** Idiomas. **/
	private List<TypeIdioma> idiomas = new ArrayList<>();

	/** Procedimiento. **/
	private String procedimiento;

	/** Valor seleccionado. **/
	private Propiedad valorSeleccionado;

	/** Tramite. **/
	private Tramite tramite;

	/**
	 * JSON con la lista de valores (identificador - valor)
	 */
	private List<Propiedad> parametros = new ArrayList<>();

	/** Url . **/
	private String url;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData(tramiteService.getTramiteVersion(Long.valueOf(id)));

		tramite = tramiteService.getTramite(this.data.getIdTramite());
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
			procedimiento = tramitePrevisualizacion.getProcedimiento();
			idioma = tramitePrevisualizacion.getIdioma();
			parametros = tramitePrevisualizacion.getParametros();
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();

		final TramitePrevisualizacion tramitePrevisualizacion = new TramitePrevisualizacion();
		tramitePrevisualizacion.setProcedimiento(procedimiento);
		tramitePrevisualizacion.setIdioma(idioma);
		tramitePrevisualizacion.setParametros(parametros);
		mochila.put(Constantes.CLAVE_MOCHILA_TRAMITE + this.data.getIdTramite(), tramitePrevisualizacion);

		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL.toString());

		final String params = getParamsUrl();

		setUrl(urlBase + "/asistente/iniciarTramite.html?tramite=" + tramite.getIdentificador() + "&version="
				+ data.getNumeroVersion() + "&idioma=" + idioma + "&idTramiteCatalogo=" + procedimiento + params);

	}

	private String getParamsUrl() {
		final StringBuilder paramsUrl = new StringBuilder();
		if (this.parametros != null && !this.parametros.isEmpty()) {
			for (final Propiedad parametro : this.parametros) {
				paramsUrl.append("&");
				paramsUrl.append(escapeHtml4(parametro.getCodigo()));
				paramsUrl.append("=");
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
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.parametros.add(propiedad);
				}

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
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.parametros.remove(posicion);
					this.parametros.add(posicion, propiedadEdicion);
					this.valorSeleccionado = propiedadEdicion;
				}

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.parametros.remove(posicion);
		this.parametros.add(posicion - 1, propiedad);
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
	 * @return the procedimiento
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento
	 *            the procedimiento to set
	 */
	public void setProcedimiento(final String procedimiento) {
		this.procedimiento = procedimiento;
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

}
