package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioResolucionService;
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
	private DominioResolucionService dominioResolucionService;

	/** Id del dominio a tratar. */
	private String id;

	/** Dominio. **/
	private Dominio dominio;

	/** Muestra o no la tabla de parametros. **/
	private boolean mostrarTablaParametro;

	/** Muestra o no la tabla de datos. **/
	private boolean mostrarTablaDatos;

	/** Muestra o no la tabla de ficheros. **/
	private boolean mostrarTablaFicheros;

	/** Valores dominio. **/
	private ValoresDominio valoresDominio;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		// No se puede comprobar antes porque puede que el dominio no tenga id
		// (al ser
		// de mochila si viene de otra BBDD).
		UtilJSF.checkSecOpenDialog(modo, id);
		dominio = dominioService.loadDominio(Long.valueOf(id));

		// Si es de tipo lista fija, debería mostrar los datos sin parametros y
		// quitar
		// el boton ping
		final boolean isTipoListaFija = dominio.getTipo() == TypeDominio.LISTA_FIJA;
		mostrarTablaParametro = !isTipoListaFija;
		mostrarTablaDatos = isTipoListaFija;

		// Si es tipo lista fija, rellenar los datos.
		if (isTipoListaFija && dominio.getListaFija() != null && !dominio.getListaFija().isEmpty()) {

			valoresDominio = dominioResolucionService.realizarConsultaListaFija(this.dominio.getAmbito(),
					UtilJSF.getIdEntidad(), this.dominio.getIdentificador(), this.dominio.getUrl(),
					this.dominio.getParametros());

		}

	}

	/**
	 * Ping
	 */
	public void ping() {

		try {
			if (dominio.getTipo() == TypeDominio.FUENTE_DATOS) {
				valoresDominio = pingFuenteDatos(getValorParametrosDominio());
			}

			if (dominio.getTipo() == TypeDominio.CONSULTA_BD) {
				valoresDominio = pingConsultaBD(getValorParametrosDominio());
			}

			if (dominio.getTipo() == TypeDominio.CONSULTA_REMOTA) {
				valoresDominio = pingConsultaRemota(getParametrosDominio());
			}

			if (valoresDominio != null) {
				if (valoresDominio.isError()) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
							valoresDominio.getCodigoError() + " : " + valoresDominio.getDescripcionError());
				} else {
					if (valoresDominio.getFicheros().isEmpty()) {
						mostrarTablaDatos = true;
						mostrarTablaFicheros = false;
					} else {
						mostrarTablaDatos = false;
						mostrarTablaFicheros = true;
					}
				}
			}
		} catch (final Exception e) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, "Error:" + e.getLocalizedMessage());
		}
	}

	/**
	 * Resuelve el ping para consulta remota.
	 *
	 * @param parametros
	 */
	private ValoresDominio pingConsultaRemota(final List<ParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaRemota(this.dominio.getAmbito(), UtilJSF.getIdEntidad(),
				this.dominio.getIdentificador(), this.dominio.getUrl(), parametros);
	}

	/**
	 * Resuelve el ping para consulta BD.
	 */
	private ValoresDominio pingConsultaBD(final List<ValorParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaBD(dominio.getJndi(), dominio.getSqlDecoded(), parametros);
	}

	/**
	 * Resuelve el ping para fuente de datos.
	 */
	private ValoresDominio pingFuenteDatos(final List<ValorParametroDominio> parametros) {
		return dominioResolucionService.realizarConsultaFuenteDatos(dominio.getIdentificador(), parametros);
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
	 * @return the valoresDominio
	 */
	public ValoresDominio getValoresDominio() {
		return valoresDominio;
	}

	/**
	 * @param valoresDominio
	 *            the valoresDominio to set
	 */
	public void setValoresDominio(final ValoresDominio valoresDominio) {
		this.valoresDominio = valoresDominio;
	}

	/**
	 * @return the mostrarTablaFicheros
	 */
	public boolean isMostrarTablaFicheros() {
		return mostrarTablaFicheros;
	}

	/**
	 * @param mostrarTablaFicheros
	 *            the mostrarTablaFicheros to set
	 */
	public void setMostrarTablaFicheros(final boolean mostrarTablaFicheros) {
		this.mostrarTablaFicheros = mostrarTablaFicheros;
	}

}
