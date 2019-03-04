package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.LiteralScript;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogScriptLiteral extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Id script. **/
	private String idScript;

	/** Datos elemento. */
	private LiteralScript data;

	/** Literal. **/
	private String literal;

	/** Idiomas json. **/
	private String iIdiomas;

	/** Idiomas. **/
	private List<String> idiomas;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		idiomas = (List<String>) UtilJSON.fromListJSON(iIdiomas, String.class);

		if (modo == TypeModoAcceso.ALTA) {
			data = new LiteralScript();
		} else {
			final Object json = UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_SCRIPT_LITERAL);
			data = (LiteralScript) UtilJSON.fromJSON(json.toString(), LiteralScript.class);
			setLiteral(data.getLiteral().getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas));
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setLiteral(literales);
			setLiteral(literales.getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas));
		}
	}

	/**
	 * Editar descripcion del literal script.
	 */
	public void editarDescripcion() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getLiteral(), idiomas, idiomas);
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
	 * @return the idScript
	 */
	public String getIdScript() {
		return idScript;
	}

	/**
	 * @param idScript
	 *            the idScript to set
	 */
	public void setIdScript(final String idScript) {
		this.idScript = idScript;
	}

	/**
	 * @return the data
	 */
	public LiteralScript getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final LiteralScript data) {
		this.data = data;
	}

	/**
	 * @return the literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * @param literal
	 *            the literal to set
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	/**
	 * @return the iIdiomas
	 */
	public String getiIdiomas() {
		return iIdiomas;
	}

	/**
	 * @param iIdiomas
	 *            the iIdiomas to set
	 */
	public void setiIdiomas(final String iIdiomas) {
		this.iIdiomas = iIdiomas;
	}

}
