/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * DialogDefinicionVersionDominios.
 *
 * @author Indra
 */
@ManagedBean
@ViewScoped
public class DialogDefinicionVersionDominios extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private DominioService dominioService;

	/** Id del tramite version. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** tipo. */
	private TypeAmbito tipo;

	/** filtrar. */
	private String filtro;

	/**
	 * dato seleccionado.
	 */
	private Dominio datoSeleccionado;

	/**
	 * lista dominiosId.
	 */
	private List<Dominio> listaDominios;

	/**
	 * Inicialización.
	 */
	public void init() {
		tipo = TypeAmbito.ENTIDAD;
		filtrar();
	}

	/** Filtrar. */
	public void filtrar() {
		Long idFiltro = null;
		if (tipo == TypeAmbito.ENTIDAD) {
			idFiltro = UtilJSF.getIdEntidad();
		}
		if (tipo == TypeAmbito.AREA) {
			idFiltro = Long.valueOf(idArea);
		}
		listaDominios = dominioService.listDominio(tipo, idFiltro, this.filtro);
	}

	/**
	 * Aceptar. Se da por hecho, que siempre se da de alta.
	 */
	public void aceptar() {

		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			return;
		}

		if (dominioService.tieneTramiteVersion(this.datoSeleccionado.getCodigo(), Long.valueOf(this.id))) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.tramiteDominio.yaexiste"));
			return;
		}

		dominioService.addTramiteVersion(datoSeleccionado.getCodigo(), Long.valueOf(id));

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		if (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.ALTA) {
			result.setResult(datoSeleccionado);
		}
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
	public String getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tipo.
	 *
	 * @return el valor de tipo
	 */
	public TypeAmbito getTipo() {
		return tipo;
	}

	/**
	 * Establece el valor de tipo.
	 *
	 * @param tipo
	 *            el nuevo valor de tipo
	 */
	public void setTipo(final TypeAmbito tipo) {
		this.tipo = tipo;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de listaDominios.
	 *
	 * @return el valor de listaDominios
	 */
	public List<Dominio> getListaDominios() {
		return listaDominios;
	}

	/**
	 * Establece el valor de listaDominios.
	 *
	 * @param listaDominios
	 *            el nuevo valor de listaDominios
	 */
	public void setListaDominios(final List<Dominio> listaDominios) {
		this.listaDominios = listaDominios;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

}
