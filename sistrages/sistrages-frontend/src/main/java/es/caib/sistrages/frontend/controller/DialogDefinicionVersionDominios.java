/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
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

	/** Id elemento a tratar. */
	private Long id;

	/**
	 * tramite version.
	 */
	private TramiteVersion tramiteVersion;

	/**
	 * tipo.
	 */
	private TypeAmbito tipo;

	/**
	 * filtrar.
	 */
	private String filtrar;

	/**
	 * dato seleccionado.
	 */
	private Dominio datoSeleccionado;

	/**
	 * lista dominios.
	 */
	private List<Dominio> listaDominios;

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
	 * Recupera tramite version.
	 *
	 * @param id
	 *            el id de tramite version
	 */
	private void recuperaTramiteVersion(final Long id) {

		/* dominio */
		final Dominio dominio1 = new Dominio();
		dominio1.setId(1L);
		dominio1.setCodigo("1");
		dominio1.setDescripcion("Dominio 1");
		dominio1.setAmbito(TypeAmbito.GLOBAL);
		final Dominio dominio2 = new Dominio();
		dominio2.setId(2L);
		dominio2.setCodigo("2");
		dominio2.setDescripcion("Dominio 2");
		dominio2.setAmbito(TypeAmbito.AREA);
		final Dominio dominio3 = new Dominio();
		dominio3.setId(3L);
		dominio3.setCodigo("3");
		dominio3.setDescripcion("Dominio 3");
		dominio3.setAmbito(TypeAmbito.GLOBAL);
		final Dominio dominio4 = new Dominio();
		dominio4.setId(4L);
		dominio4.setCodigo("4");
		dominio4.setDescripcion("Dominio 4");
		dominio4.setAmbito(TypeAmbito.ENTIDAD);
		final Dominio dominio5 = new Dominio();
		dominio5.setId(5L);
		dominio5.setCodigo("5");
		dominio5.setDescripcion("Dominio 5");
		dominio5.setAmbito(TypeAmbito.GLOBAL);

		listaDominios = new ArrayList<>();
		listaDominios.add(dominio1);
		listaDominios.add(dominio2);
		listaDominios.add(dominio3);
		listaDominios.add(dominio4);
		listaDominios.add(dominio5);

	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
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
	 * Obtiene el valor de filtrar.
	 *
	 * @return el valor de filtrar
	 */
	public String getFiltrar() {
		return filtrar;
	}

	/**
	 * Establece el valor de filtrar.
	 *
	 * @param filtrar
	 *            el nuevo valor de filtrar
	 */
	public void setFiltrar(final String filtrar) {
		this.filtrar = filtrar;
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

}
