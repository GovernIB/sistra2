package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de dominios (ambito global, entidad y area).
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewDominios extends ViewControllerBase {

	/**
	 * Inicializacion.
	 */
	public void init() {

		if (ambito == null) {
			return;
		}

		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);

		switch (typeAmbito) {
		case AREA:
			final Dominio dominio1 = new Dominio();
			dominio1.setCacheable(true);
			dominio1.setId(1l);
			dominio1.setCodigo("GOIB_ORGANISME");
			dominio1.setDescripcion("Area - Dominio que retorna el organisme de la entitat");
			dominio1.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominio2 = new Dominio();
			dominio2.setCacheable(true);
			dominio2.setId(2l);
			dominio2.setCodigo("GOIB_PSTDOCUME");
			dominio2.setDescripcion("Llistat de punts de lliurament de documentació");
			dominio2.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominio3 = new Dominio();
			dominio3.setCacheable(false);
			dominio3.setCodigo("GOIB_ENTBNCOL");
			dominio3.setId(3l);
			dominio3.setDescripcion("Llistat de entitats bancaries");
			dominio3.setTipo(TypeDominio.CONSULTA_REMOTA);

			listaDatos = new ArrayList<>();
			listaDatos.add(dominio1);
			listaDatos.add(dominio2);
			listaDatos.add(dominio3);
			break;
		case ENTIDAD:
			final Dominio dominioEntidad1 = new Dominio();
			dominioEntidad1.setCacheable(true);
			dominioEntidad1.setId(1l);
			dominioEntidad1.setCodigo("GOIB_ORGANISME");
			dominioEntidad1.setDescripcion("Entidad - Dominio que retorna el organisme de la entitat");
			dominioEntidad1.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominioEntidad2 = new Dominio();
			dominioEntidad2.setCacheable(true);
			dominioEntidad2.setId(2l);
			dominioEntidad2.setCodigo("GOIB_PSTDOCUME");
			dominioEntidad2.setDescripcion("Llistat de punts de lliurament de documentació");
			dominioEntidad2.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominioEntidad3 = new Dominio();
			dominioEntidad3.setCacheable(false);
			dominioEntidad3.setCodigo("GOIB_ENTBNCOL");
			dominioEntidad3.setId(3l);
			dominioEntidad3.setDescripcion("Llistat de entitats bancaries");
			dominioEntidad3.setTipo(TypeDominio.CONSULTA_REMOTA);

			listaDatos = new ArrayList<>();
			listaDatos.add(dominioEntidad1);
			listaDatos.add(dominioEntidad2);
			listaDatos.add(dominioEntidad3);
			break;
		case GLOBAL:
			final Dominio dominioGlobal1 = new Dominio();
			dominioGlobal1.setCacheable(true);
			dominioGlobal1.setId(1l);
			dominioGlobal1.setCodigo("GOIB_ORGANISME");
			dominioGlobal1.setDescripcion("Global - Dominio que retorna el organisme de la entitat");
			dominioGlobal1.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominioGlobal2 = new Dominio();
			dominioGlobal2.setCacheable(true);
			dominioGlobal2.setId(2l);
			dominioGlobal2.setCodigo("GOIB_PSTDOCUME");
			dominioGlobal2.setDescripcion("Llistat de punts de lliurament de documentació");
			dominioGlobal2.setTipo(TypeDominio.CONSULTA_BD);
			final Dominio dominioGlobal3 = new Dominio();
			dominioGlobal3.setCacheable(false);
			dominioGlobal3.setCodigo("GOIB_ENTBNCOL");
			dominioGlobal3.setId(3l);
			dominioGlobal3.setDescripcion("Llistat de entitats bancaries");
			dominioGlobal3.setTipo(TypeDominio.CONSULTA_REMOTA);

			listaDatos = new ArrayList<>();
			listaDatos.add(dominioGlobal1);
			listaDatos.add(dominioGlobal2);
			listaDatos.add(dominioGlobal3);
			break;
		}

	}

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/**
	 * Lista de datos.
	 */
	private List<Dominio> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Dominio datoSeleccionado;

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		// Filtra
		final List<Dominio> dominioesFiltradas = new ArrayList<>();
		for (final Dominio dominio : this.listaDatos) {
			if (dominio.getDescripcion() != null
					&& dominio.getDescripcion().toLowerCase().contains(filtro.toLowerCase())) {
				dominioesFiltradas.add(dominio);
			}
		}

		this.listaDatos = dominioesFiltradas;
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		// Muestra mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.filtro.ok"));
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.ALTA, params, true, 740, 650);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.EDICION, params, true, 740, 650);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		listaDatos.remove(this.datoSeleccionado);
		// Refrescamos datos
		filtrar();
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
	}

	/**
	 * Ping.
	 */
	public void ping() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Refrescar cache.
	 */
	public void refrescarCache() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		final String message = null;

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
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

	/**
	 * @return the listaDatos
	 */
	public List<Dominio> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<Dominio> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
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

}
