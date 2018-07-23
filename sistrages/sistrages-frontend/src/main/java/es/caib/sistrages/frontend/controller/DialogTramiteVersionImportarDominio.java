package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.FilaImportarDominio;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteVersionImportarDominio extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteVersionImportarDominio.class);

	/** Dominio. */
	private FilaImportarDominio data;

	/** Mostrar info sql. **/
	private boolean mostrarSql = false;

	/** Mostrar info jndi. **/
	private boolean mostrarJndi = false;

	/** Mostrar url. **/
	private boolean mostrarUrl = false;

	/** Mostrar lista. **/
	private boolean mostrarLista = false;

	/** Mostrar FD. **/
	private boolean mostrarFD = false;

	/** Mostrar los textos de reemplazar. **/
	private boolean mostrarReemplazar = true;

	/** Accion. **/
	private String accion;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarDominio) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		checkMostrar();
		if (data.getAccion() == null) {
			accion = TypeImportarAccion.REEMPLAZAR.toString();
		} else {
			accion = data.getAccion().toString();
		}
	}

	/**
	 * Se encarga de revisar que datos se pueden mostrar o no.
	 */
	private void checkMostrar() {
		switch (data.getDominio().getTipo()) {
		case CONSULTA_BD:
			mostrarSql = true;
			mostrarJndi = true;
			break;
		case CONSULTA_REMOTA:
			mostrarUrl = true;
			break;
		case FUENTE_DATOS:
			mostrarFD = true;
			mostrarSql = true;
			break;
		case LISTA_FIJA:
			mostrarLista = true;
			break;
		default:
			mostrarSql = false;
			mostrarJndi = false;
			mostrarUrl = false;
			break;
		}
	}

	/**
	 * Comprueba si se puede visualizar la acción.
	 *
	 * @return
	 */
	public boolean checkAccion(final String valor) {
		boolean visible = false;
		if (this.data.getAcciones() != null && valor != null) {
			for (final TypeImportarAccion tAccion : this.data.getAcciones()) {
				if (tAccion.toString().equals(accion)) {
					visible = true;
					break;
				}
			}
		}
		return visible;
	}

	/**
	 * El evento del combo. Cuando se actualiza a reemplazar, se ven los valores o
	 * sino, se ocultan.
	 */
	public void eventoChangeResultado() {
		this.mostrarReemplazar = (this.accion == TypeImportarAccion.REEMPLAZAR.toString());
	}

	/**
	 * Consultar dominio.
	 */
	public void consultarDominio() {
		if (data.getDominioActual() != null) {
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), data.getDominioActual().getCodigo().toString());
			UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {
		if (data.getAccion() != null) {
			final DialogResult result = new DialogResult();
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			result.setCanceled(false);
			data.setAccion(TypeImportarAccion.fromString(accion));
			result.setResult(data);
			UtilJSF.closeDialog(result);
		}
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * @return the data
	 */
	public FilaImportarDominio getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportarDominio data) {
		this.data = data;
	}

	/**
	 * @return the mostrarSql
	 */
	public boolean isMostrarSql() {
		return mostrarSql;
	}

	/**
	 * @return the mostrarSql
	 */
	public boolean getMostrarSql() {
		return isMostrarSql();
	}

	/**
	 * @param mostrarSql
	 *            the mostrarSql to set
	 */
	public void setMostrarSql(final boolean mostrarSql) {
		this.mostrarSql = mostrarSql;
	}

	/**
	 * @return the mostrarJndi
	 */
	public boolean isMostrarJndi() {
		return mostrarJndi;
	}

	/**
	 * @return the mostrarJndi
	 */
	public boolean getMostrarJndi() {
		return isMostrarJndi();
	}

	/**
	 * @param mostrarJndi
	 *            the mostrarJndi to set
	 */
	public void setMostrarJndi(final boolean mostrarJndi) {
		this.mostrarJndi = mostrarJndi;
	}

	/**
	 * @return the mostrarUrl
	 */
	public boolean isMostrarUrl() {
		return mostrarUrl;
	}

	/**
	 * @return the mostrarUrl
	 */
	public boolean getMostrarUrl() {
		return isMostrarUrl();
	}

	/**
	 * @param mostrarUrl
	 *            the mostrarUrl to set
	 */
	public void setMostrarUrl(final boolean mostrarUrl) {
		this.mostrarUrl = mostrarUrl;
	}

	/**
	 * @return the mostrarLista
	 */
	public boolean isMostrarLista() {
		return mostrarLista;
	}

	/**
	 * @return the mostrarLista
	 */
	public boolean getMostrarLista() {
		return isMostrarLista();
	}

	/**
	 * @param mostrarLista
	 *            the mostrarLista to set
	 */
	public void setMostrarLista(final boolean mostrarLista) {
		this.mostrarLista = mostrarLista;
	}

	/**
	 * @return the mostrarFD
	 */
	public boolean isMostrarFD() {
		return mostrarFD;
	}

	/**
	 * @return the mostrarFD
	 */
	public boolean getMostrarFD() {
		return isMostrarFD();
	}

	/**
	 * @param mostrarFD
	 *            the mostrarFD to set
	 */
	public void setMostrarFD(final boolean mostrarFD) {
		this.mostrarFD = mostrarFD;
	}

	/**
	 * @return the mostrarReemplazar
	 */
	public boolean isMostrarReemplazar() {
		return mostrarReemplazar;
	}

	/**
	 * @param mostrarReemplazar
	 *            the mostrarReemplazar to set
	 */
	public void setMostrarReemplazar(final boolean mostrarReemplazar) {
		this.mostrarReemplazar = mostrarReemplazar;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(final String accion) {
		this.accion = accion;
	}

}
