package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTraduccion extends DialogControllerBase {

	/** Texto Catalan. **/
	private String textoCa;
	/** Texto Espanyol. **/
	private String textoEs;
	/** Texto ingles. **/
	private String textoEn;
	/** Texto Aleman. **/
	private String textoDe;

	/** Visible Cataan. **/
	private boolean visibleCa = false;
	/** Visible Espanyol. **/
	private boolean visibleEs = false;
	/** Visible Ingles. **/
	private boolean visibleEn = false;
	/** Visible Aleman. **/
	private boolean visibleDe = false;

	/** Obligatorio catalan. **/
	private boolean requiredCa = false;
	/** Obligatorio espanyol. **/
	private boolean requiredEs = false;
	/** Obligatorio ingles. **/
	private boolean requiredEn = false;
	/** Obligatorio aleman. **/
	private boolean requiredDe = false;

	/** Idiomas obligatorios en formato JSON. **/
	private String iIdiomasObligatorios;
	/** Idiomas posibles en formato JSON. **/
	private String iIdiomasPosibles;

	/** Parametro de entrada. **/
	private Literal data;
	/**
	 * Lista de idiomas obligatorios opcional (en caso de no pasarlo, todos son
	 * obligatorios.
	 **/
	private List<String> idiomasObligatorios;
	/**
	 * Lista de idiomas posibles a introducir (que debe ser igual o mayor que el
	 * obligatorio).
	 **/
	private List<String> idiomasPosibles;

	/** Variable que indica si se puede borrar. **/
	private String opcional;

	/** Booleano que indica si el botón de borrar puede aparecer. **/
	private boolean borrable;

	/**
	 * Inicializacion.
	 *
	 */
	public void init() {

		if (UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_LITERALES) == null) {
			data = new Literal();
		} else {
			data = (Literal) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_LITERALES);
		}

		if (iIdiomasObligatorios == null || iIdiomasObligatorios.isEmpty()) {
			idiomasObligatorios = UtilJSF.getSessionBean().getIdiomas();
		} else {
			idiomasObligatorios = (List<String>) UtilJSON.fromListJSON(iIdiomasObligatorios, String.class);
		}

		// Se habían creado los idiomas posibles y obligatorios, de tal manera que
		// si se quiere se puede especificar unos mínimos obligatorios y más idiomas
		// posibles. Por ejemplo, especificar el 'ca' como obligorio y como posibles
		// los idiomas 'es', 'ca' y 'en'. Vamos a forzar para que ambos sean los
		// mismos, si se desea volver al cambio anterior, se debería ver versiones
		// anteriores del java.
		idiomasPosibles = idiomasObligatorios;

		inicializarTextosPermisos();

		if ((isAlta() || isEdicion()) && opcional != null && "S".equals(opcional)) {
			borrable = true;
		} else {
			borrable = false;
		}
	}

	/**
	 * Inicializa los textos, la visiblidad y obligatoriedad.
	 */
	private void inicializarTextosPermisos() {
		for (final String idioma : idiomasPosibles) {

			final TypeIdioma idiomaType = TypeIdioma.fromString(idioma);

			if (idiomaType != null) {
				switch (idiomaType) {
				case CATALAN:
					textoCa = data.getTraduccion(idioma);
					visibleCa = true;
					if (idiomasObligatorios.contains(idioma)) {
						requiredCa = true;
					}
					break;
				case CASTELLANO:
					textoEs = data.getTraduccion(idioma);
					visibleEs = true;
					if (idiomasObligatorios.contains(idioma)) {
						requiredEs = true;
					}
					break;
				case INGLES:
					textoEn = data.getTraduccion(idioma);
					visibleEn = true;
					if (idiomasObligatorios.contains(idioma)) {
						requiredEn = true;
					}
					break;
				case ALEMAN:
					textoDe = data.getTraduccion(idioma);
					visibleDe = true;
					if (idiomasObligatorios.contains(idioma)) {
						requiredDe = true;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (visibleCa) {
			if (textoCa == null || textoCa.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.faltaliteral"));
				return;
			}
			data.add(new Traduccion(TypeIdioma.CATALAN.toString(), textoCa));
		}
		if (visibleEs) {
			if (textoEs == null || textoEs.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.faltaliteral"));
				return;
			}
			data.add(new Traduccion(TypeIdioma.CASTELLANO.toString(), textoEs));
		}
		if (visibleEn) {
			if (textoEn == null || textoEn.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.faltaliteral"));
				return;
			}
			data.add(new Traduccion(TypeIdioma.INGLES.toString(), textoEn));
		}
		if (visibleDe) {
			if (textoDe == null || textoDe.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.faltaliteral"));
				return;
			}
			data.add(new Traduccion(TypeIdioma.ALEMAN.toString(), textoDe));
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Borrar.
	 */
	public void borrar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(null);
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
	 * @return the textoCa
	 */
	public String getTextoCa() {
		return textoCa;
	}

	/**
	 * @param textoCa
	 *            the textoCa to set
	 */
	public void setTextoCa(final String textoCa) {
		this.textoCa = textoCa;
	}

	/**
	 * @return the textoEs
	 */
	public String getTextoEs() {
		return textoEs;
	}

	/**
	 * @param textoEs
	 *            the textoEs to set
	 */
	public void setTextoEs(final String textoEs) {
		this.textoEs = textoEs;
	}

	/**
	 * @return the textoEn
	 */
	public String getTextoEn() {
		return textoEn;
	}

	/**
	 * @param textoEn
	 *            the textoEn to set
	 */
	public void setTextoEn(final String textoEn) {
		this.textoEn = textoEn;
	}

	/**
	 * @return the textoDe
	 */
	public String getTextoDe() {
		return textoDe;
	}

	/**
	 * @param textoDe
	 *            the textoDe to set
	 */
	public void setTextoDe(final String textoDe) {
		this.textoDe = textoDe;
	}

	/**
	 * @return the visibleCa
	 */
	public boolean isVisibleCa() {
		return visibleCa;
	}

	/**
	 * @param visibleCa
	 *            the visibleCa to set
	 */
	public void setVisibleCa(final boolean visibleCa) {
		this.visibleCa = visibleCa;
	}

	/**
	 * @return the visibleEs
	 */
	public boolean isVisibleEs() {
		return visibleEs;
	}

	/**
	 * @param visibleEs
	 *            the visibleEs to set
	 */
	public void setVisibleEs(final boolean visibleEs) {
		this.visibleEs = visibleEs;
	}

	/**
	 * @return the visibleEn
	 */
	public boolean isVisibleEn() {
		return visibleEn;
	}

	/**
	 * @param visibleEn
	 *            the visibleEn to set
	 */
	public void setVisibleEn(final boolean visibleEn) {
		this.visibleEn = visibleEn;
	}

	/**
	 * @return the visibleDe
	 */
	public boolean isVisibleDe() {
		return visibleDe;
	}

	/**
	 * @param visibleDe
	 *            the visibleDe to set
	 */
	public void setVisibleDe(final boolean visibleDe) {
		this.visibleDe = visibleDe;
	}

	/**
	 * @return the requiredCa
	 */
	public boolean isRequiredCa() {
		return requiredCa;
	}

	/**
	 * @param requiredCa
	 *            the requiredCa to set
	 */
	public void setRequiredCa(final boolean requiredCa) {
		this.requiredCa = requiredCa;
	}

	/**
	 * @return the requiredEs
	 */
	public boolean isRequiredEs() {
		return requiredEs;
	}

	/**
	 * @param requiredEs
	 *            the requiredEs to set
	 */
	public void setRequiredEs(final boolean requiredEs) {
		this.requiredEs = requiredEs;
	}

	/**
	 * @return the requiredEn
	 */
	public boolean isRequiredEn() {
		return requiredEn;
	}

	/**
	 * @param requiredEn
	 *            the requiredEn to set
	 */
	public void setRequiredEn(final boolean requiredEn) {
		this.requiredEn = requiredEn;
	}

	/**
	 * @return the requiredDe
	 */
	public boolean isRequiredDe() {
		return requiredDe;
	}

	/**
	 * @param requiredDe
	 *            the requiredDe to set
	 */
	public void setRequiredDe(final boolean requiredDe) {
		this.requiredDe = requiredDe;
	}

	/**
	 * @return the iIdiomasObligatorios
	 */
	public String getiIdiomasObligatorios() {
		return iIdiomasObligatorios;
	}

	/**
	 * @param iIdiomasObligatorios
	 *            the iIdiomasObligatorios to set
	 */
	public void setiIdiomasObligatorios(final String iIdiomasObligatorios) {
		this.iIdiomasObligatorios = iIdiomasObligatorios;
	}

	/**
	 * @return the iIdiomasPosibles
	 */
	public String getiIdiomasPosibles() {
		return iIdiomasPosibles;
	}

	/**
	 * @param iIdiomasPosibles
	 *            the iIdiomasPosibles to set
	 */
	public void setiIdiomasPosibles(final String iIdiomasPosibles) {
		this.iIdiomasPosibles = iIdiomasPosibles;

	}

	/**
	 * @return the borrable
	 */
	public boolean isBorrable() {
		return borrable;
	}

	/**
	 * @param borrable
	 *            the borrable to set
	 */
	public void setBorrable(final boolean borrable) {
		this.borrable = borrable;
	}

	/**
	 * @return the opcional
	 */
	public final String getOpcional() {
		return opcional;
	}

	/**
	 * @param opcional
	 *            the opcional to set
	 */
	public final void setOpcional(final String opcional) {
		this.opcional = opcional;
	}

}
