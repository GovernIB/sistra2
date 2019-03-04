package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarDominio extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportarDominio.class);

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

	/** Mostrar FD actual. **/
	private boolean mostrarFDActual = false;

	/** Mostrar FD Actual botones de ver estructura/datos. **/
	private boolean mostrarFDActualBotones = false;

	/** Mostrar FD Actual mensaje de no existe. **/
	private boolean mostrarFDActualLiteral = false;

	/** Mostrar los textos de reemplazar. **/
	private boolean mostrarReemplazar = true;

	/** La lista de parametros **/
	private List<Propiedad> parametros;

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
		this.mostrarReemplazar = accion.equals(TypeImportarAccion.REEMPLAZAR.toString());
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
			mostrarFDActual = true;
			if (this.data.getFuenteDatosActual() != null) {
				setMostrarFDActualBotones(true);
				setMostrarFDActualLiteral(false);
			} else {
				setMostrarFDActualLiteral(true);
				setMostrarFDActualBotones(false);
			}
			break;
		case LISTA_FIJA:
			mostrarLista = true;
			if (this.data.getResultadoLista() != null && !this.data.getResultadoLista().isEmpty()) {
				parametros = (List<Propiedad>) UtilJSON.fromListJSON(this.data.getResultadoLista(), Propiedad.class);
			}
			break;
		default:
			mostrarSql = false;
			mostrarJndi = false;
			mostrarUrl = false;
			break;
		}
	}

	/**
	 * El evento del combo. Cuando se actualiza a reemplazar, se ven los valores o
	 * sino, se ocultan.
	 */
	public void eventoChangeResultado(final ValueChangeEvent event) {
		this.mostrarReemplazar = event.getNewValue().equals(TypeImportarAccion.REEMPLAZAR.toString());
	}

	/**
	 * Consultar dominio.
	 */
	public void consultarDominio(final Dominio dominio) {
		if (dominio != null) {
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), dominio.getCodigo().toString());
			params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
			if (dominio.getAreas() != null && !dominio.getAreas().isEmpty()) {
				params.put(TypeParametroVentana.AREA.toString(), dominio.getAreas().toArray()[0].toString());
			}
			UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
		}
	}

	/**
	 * Consultar estructura FD.
	 */
	public void consultarEstructura(final Dominio dominio, final FuenteDatos fd) {
		if (dominio != null && dominio.getIdFuenteDatos() != null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_FUENTEDATOS, UtilJSON.toJSON(fd));
			UtilJSF.openDialog(DialogTramiteImportarFDE.class, TypeModoAcceso.CONSULTA, null, true, 770, 400);
		}
	}

	/**
	 * Consultar datos FD.
	 */
	public void consultarDatos() {
		if (data.getDominio() != null && data.getDominio().getIdFuenteDatos() != null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_FUENTEDATOS, UtilJSON.toJSON(data.getFuenteDatos()));
			mochila.put(Constantes.CLAVE_MOCHILA_FUENTEVALORES, data.getFuenteDatosContent());
			UtilJSF.openDialog(DialogTramiteImportarFDD.class, TypeModoAcceso.CONSULTA, null, true, 770, 400);
		}
	}

	/**
	 * Consultar datos FD.
	 */
	public void consultarDatosActual() {
		if (data.getDominioActual() != null && data.getDominioActual().getIdFuenteDatos() != null) {

			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), data.getDominioActual().getIdFuenteDatos().toString());
			UtilJSF.openDialog(DialogFuenteDatos.class, TypeModoAcceso.CONSULTA, params, true, 740, 330);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {
		if (data.getAccion() != null) {
			final DialogResult result = new DialogResult();
			if (data.getResultadoSQL() != null) {
				data.getDominio().setSql(new String(Base64.decodeBase64(data.getResultadoSQL())));
				data.getDominio().setSqlDecoded(data.getResultadoSQL());
				data.setResultadoSQL(data.getResultadoSQL());
				data.setResultadoSQLdecoded(new String(Base64.decodeBase64(data.getResultadoSQL())));

			}
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
	 * @return the mostrarFDActual
	 */
	public boolean isMostrarFDActual() {
		return mostrarFDActual;
	}

	/**
	 * @param mostrarFDActual
	 *            the mostrarFDActual to set
	 */
	public void setMostrarFDActual(final boolean mostrarFDActual) {
		this.mostrarFDActual = mostrarFDActual;
	}

	/**
	 * @return the mostrarFDActualBotones
	 */
	public boolean isMostrarFDActualBotones() {
		return mostrarFDActualBotones;
	}

	/**
	 * @param mostrarFDActualBotones
	 *            the mostrarFDActualBotones to set
	 */
	public void setMostrarFDActualBotones(final boolean mostrarFDActualBotones) {
		this.mostrarFDActualBotones = mostrarFDActualBotones;
	}

	/**
	 * @return the mostrarFDActualLiteral
	 */
	public boolean isMostrarFDActualLiteral() {
		return mostrarFDActualLiteral;
	}

	/**
	 * @param mostrarFDActualLiteral
	 *            the mostrarFDActualLiteral to set
	 */
	public void setMostrarFDActualLiteral(final boolean mostrarFDActualLiteral) {
		this.mostrarFDActualLiteral = mostrarFDActualLiteral;
	}

}
