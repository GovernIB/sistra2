package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;

import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominioPing extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Enlace servicio. */
	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	/** Id del dominio a tratar. */
	private String id;

	/** Dominio. **/
	private Dominio dominio;

	/** Muestra o no la tabla de parametros. **/
	private boolean mostrarTablaParametro;

	/** Muestra o no la tabla de datos. **/
	private boolean mostrarTablaDatos;

	/** Muestra o no el botón ping. **/
	private boolean mostrarBotonPing;

	/**
	 * Datos.
	 **/
	private List<Map<String, String>> data = new ArrayList<>();

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		// No se puede comprobar antes porque puede que el dominio no tenga id (al ser
		// de mochila si viene de otra BBDD).
		UtilJSF.checkSecOpenDialog(modo, id);
		dominio = dominioService.loadDominio(Long.valueOf(id));

		// Si es de tipo lista fija, debería mostrar los datos sin parametros y quitar
		// el boton ping
		final boolean isTipoListaFija = dominio.getTipo() == TypeDominio.LISTA_FIJA;
		mostrarTablaParametro = !isTipoListaFija;
		mostrarTablaDatos = isTipoListaFija;
		mostrarBotonPing = !isTipoListaFija;

		// Si es tipo lista fija, rellenar los datos.
		if (isTipoListaFija && dominio.getListaFija() != null && !dominio.getListaFija().isEmpty()) {
			final Map<String, String> datos = new HashMap<>();
			for (final Propiedad prop : dominio.getListaFija()) {
				datos.put(prop.getCodigo(), prop.getValor());
			}
			this.data.add(datos);
		}

		// Si no hay parametros, ocultarlos igualmente
		if (this.dominio.getParametros() == null || this.dominio.getParametros().isEmpty()) {
			mostrarTablaParametro = false;
		}
	}

	/**
	 * Ping
	 */
	public void ping() {

		ValoresDominio valoresDominio = null;
		if (dominio.getTipo() == TypeDominio.FUENTE_DATOS) {
			final List<ValorParametroDominio> params = getValorParametrosDominio();
			valoresDominio = pingFuenteDatos(params);
		}

		if (dominio.getTipo() == TypeDominio.CONSULTA_BD) {
			final List<ValorParametroDominio> params = getValorParametrosDominio();
			valoresDominio = pingConsultaBD(params);
		}

		if (dominio.getTipo() == TypeDominio.CONSULTA_REMOTA) {
			final List<ParametroDominio> params = getParametrosDominio();
			valoresDominio = pingConsultaRemota(params);
		}

		if (valoresDominio != null) {
			if (valoresDominio.isError()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
						valoresDominio.getCodigoError() + " : " + valoresDominio.getDescripcionError());
			} else {
				data = valoresDominio.getDatos();
				mostrarTablaDatos = true;
			}
		}
	}

	/**
	 * Resuelve el ping para consulta remota.
	 *
	 * @param parametros
	 */
	private ValoresDominio pingConsultaRemota(final List<ParametroDominio> parametros) {
		ValoresDominio valores = new ValoresDominio();
		valores.setError(false);
		try {
			if (this.dominio.getAmbito() == TypeAmbito.ENTIDAD) {
				final IDominioPlugin plugin = (IDominioPlugin) configuracionGlobalService
						.obtenerPluginEntidad(TypePlugin.DOMINIO_REMOTO, UtilJSF.getIdEntidad());
				final es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio valoresCommons = plugin
						.invocarDominio(this.dominio.getIdentificador(), this.dominio.getUrl(), parametros);
				valores.setDatos(valoresCommons.getDatos());
			} else if (this.dominio.getAmbito() == TypeAmbito.GLOBAL) {
				final IDominioPlugin plugin = (IDominioPlugin) configuracionGlobalService
						.obtenerPluginGlobal(TypePlugin.DOMINIO_REMOTO);
				final es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio valoresCommons = plugin
						.invocarDominio(this.dominio.getIdentificador(), this.dominio.getUrl(), parametros);
				valores.setDatos(valoresCommons.getDatos());
			} else {
				valores = new ValoresDominio();
			}
		} catch (final Exception e) {
			valores = new ValoresDominio();
			valores.setError(true);
			valores.setCodigoError("REM");
			valores.setDescripcionError(ExceptionUtils.getMessage(e));
		}
		return valores;
	}

	/**
	 * Resuelve el ping para consulta BD.
	 */
	private ValoresDominio pingConsultaBD(final List<ValorParametroDominio> parametros) {
		return dominioService.realizarConsultaBD(dominio.getJndi(), dominio.getSql(), parametros);
	}

	/**
	 * Resuelve el ping para fuente de datos.
	 */
	private ValoresDominio pingFuenteDatos(final List<ValorParametroDominio> parametros) {
		return dominioService.realizarConsultaFuenteDatos(dominio.getIdentificador(), parametros);
	}

	/**
	 * Obtiene los params para la remota
	 * 
	 * @return
	 */
	private List<ParametroDominio> getParametrosDominio() {
		final List<ParametroDominio> params = new ArrayList<>();
		if (dominio.getParametros() != null && !dominio.getParametros().isEmpty()) {
			for (final Propiedad param : dominio.getParametros()) {
				final ParametroDominio valor = new ParametroDominio();
				valor.setCodigo(param.getCodigo());
				valor.setValor(param.getValor());
				params.add(valor);
			}
		}
		return params;
	}

	/**
	 * Obtiene los params.
	 *
	 * @return
	 */
	private List<ValorParametroDominio> getValorParametrosDominio() {
		final List<ValorParametroDominio> params = new ArrayList<>();
		if (dominio.getParametros() != null && !dominio.getParametros().isEmpty()) {
			for (final Propiedad param : dominio.getParametros()) {
				final ValorParametroDominio valor = new ValorParametroDominio();
				valor.setCodigo(param.getCodigo());
				valor.setValor(param.getValor());
				params.add(valor);
			}
		}
		return params;
	}

	/**
	 * Cerrar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	// ------- GETTERS / SETTERS --------------------------------
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
	 * @return the dominio
	 */
	public Dominio getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(final Dominio dominio) {
		this.dominio = dominio;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<Map<String, String>> data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public List<Map<String, String>> getData() {
		return data;
	}

	/**
	 * @return the mostrarTablaParametro
	 */
	public boolean isMostrarTablaParametro() {
		return mostrarTablaParametro;
	}

	/**
	 * @param mostrarTablaParametro
	 *            the mostrarTablaParametro to set
	 */
	public void setMostrarTablaParametro(final boolean mostrarTablaParametro) {
		this.mostrarTablaParametro = mostrarTablaParametro;
	}

	/**
	 * @return the mostrarTablaDatos
	 */
	public boolean isMostrarTablaDatos() {
		return mostrarTablaDatos;
	}

	/**
	 * @param mostrarTablaDatos
	 *            the mostrarTablaDatos to set
	 */
	public void setMostrarTablaDatos(final boolean mostrarTablaDatos) {
		this.mostrarTablaDatos = mostrarTablaDatos;
	}

	/**
	 * @return the mostrarBotonPing
	 */
	public boolean isMostrarBotonPing() {
		return mostrarBotonPing;
	}

	/**
	 * @param mostrarBotonPing
	 *            the mostrarBotonPing to set
	 */
	public void setMostrarBotonPing(final boolean mostrarBotonPing) {
		this.mostrarBotonPing = mostrarBotonPing;
	}

}
