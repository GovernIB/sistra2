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

	private String caracteresNoPermitidos;

	private String tamanyoMax;

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
					if (idiomasObligatorios.contains(idioma) && (opcional == null || !"S".equals(opcional))) {
						requiredCa = true;
					}
					break;
				case CASTELLANO:
					textoEs = data.getTraduccion(idioma);
					visibleEs = true;
					if (idiomasObligatorios.contains(idioma) && (opcional == null || !"S".equals(opcional))) {
						requiredEs = true;
					}
					break;
				case INGLES:
					textoEn = data.getTraduccion(idioma);
					visibleEn = true;
					if (idiomasObligatorios.contains(idioma) && (opcional == null || !"S".equals(opcional))) {
						requiredEn = true;
					}
					break;
				case ALEMAN:
					textoDe = data.getTraduccion(idioma);
					visibleDe = true;
					if (idiomasObligatorios.contains(idioma) && (opcional == null || !"S".equals(opcional))) {
						requiredDe = true;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private static final String LITERAL_ERROR_FALTA_LITERAL = "error.faltaliteral";
	private static final String LITERAL_ERROR_ETIQUETA_HTML = "error.etiquetaHTML";
	private static final String LITERAL_ERROR_EXCEDE_LONGITUD = "error.excedeLongitud";
	private static final String LITERAL_ERROR_CONTIENE_CHAR = "error.contieneChar";

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		boolean isAlgunLiteralRelleno = true;
		// Si es opcional, o está to do escrito o nada.
		if (opcional != null && "S".equals(opcional)) {
			isAlgunLiteralRelleno = isAlgoRelleno();
		}

		if (opcional == null || !"S".equals(opcional) || isAlgunLiteralRelleno) {
			if (visibleCa) {
				if (textoCa == null || textoCa.isEmpty()) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_FALTA_LITERAL));
					return;
				}
				if (contieneEtiquetaHTML(textoCa)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_ETIQUETA_HTML));
					return;
				}
				if (excedeLongitud(textoCa)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_EXCEDE_LONGITUD));
					return;
				}
				if (contieneCharNoPermitido(textoCa)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_CONTIENE_CHAR));
					return;
				}
				data.add(new Traduccion(TypeIdioma.CATALAN.toString(), textoCa));
			}
			if (visibleEs) {
				if (textoEs == null || textoEs.isEmpty()) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_FALTA_LITERAL));
					return;
				}
				if (contieneEtiquetaHTML(textoEs)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_ETIQUETA_HTML));
					return;
				}

				if (excedeLongitud(textoEs)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_EXCEDE_LONGITUD));
					return;
				}
				if (contieneCharNoPermitido(textoEs)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_CONTIENE_CHAR));
					return;
				}
				data.add(new Traduccion(TypeIdioma.CASTELLANO.toString(), textoEs));
			}
			if (visibleEn) {
				if (textoEn == null || textoEn.isEmpty()) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_FALTA_LITERAL));
					return;
				}
				if (excedeLongitud(textoEn)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_EXCEDE_LONGITUD));
					return;
				}
				if (contieneCharNoPermitido(textoEn)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_CONTIENE_CHAR));
					return;
				}
				data.add(new Traduccion(TypeIdioma.INGLES.toString(), textoEn));

			}
			if (visibleDe) {
				if (textoDe == null || textoDe.isEmpty()) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_FALTA_LITERAL));
					return;
				}
				if (contieneEtiquetaHTML(textoDe)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_ETIQUETA_HTML));
					return;
				}
				if (excedeLongitud(textoDe)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_EXCEDE_LONGITUD));
					return;
				}
				if (contieneCharNoPermitido(textoDe)) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral(LITERAL_ERROR_CONTIENE_CHAR));
					return;
				}
				data.add(new Traduccion(TypeIdioma.ALEMAN.toString(), textoDe));
			}
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

	}

	/**
	 * Comprueba si algún dato esta relleno.
	 *
	 * @return
	 */
	private boolean isAlgoRelleno() {
		boolean relleno = false;
		if (visibleCa) {
			if (textoCa != null && !textoCa.isEmpty()) {
				relleno = true;
			}
		}
		if (visibleEs) {
			if (textoEs != null && !textoEs.isEmpty()) {
				relleno = true;
			}
		}
		if (visibleEn) {
			if (textoEn != null && !textoEn.isEmpty()) {
				relleno = true;
			}
		}
		if (visibleDe) {
			if (textoDe != null && !textoDe.isEmpty()) {
				relleno = true;
			}
		}
		return relleno;
	}

	/**
	 * Comprueba si contienen los caracteres < y >
	 *
	 * @param texto
	 * @return
	 */
	private boolean contieneEtiquetaHTML(final String texto) {
		return texto.contains("<") && texto.contains(">");
	}

	/**
	 * Comprueba si se excede la longitud.
	 * @param texto
	 * @return
	 */
	private boolean excedeLongitud(final String texto) {
		boolean excede = false;
		//4000 es el tamaño máximo en tradidi
		if (tamanyoMax == null && texto.length() > 4000) {
				excede = true;
		} else if (tamanyoMax != null && texto.length() > Integer.parseInt(tamanyoMax)) {
				excede = true;
		}
		return excede;
	}

	/**
	 * Comprueba si tiene el caracter no permitido.
	 * @param texto
	 * @return
	 */
	private boolean contieneCharNoPermitido(final String texto) {
		boolean contiene = false;
		if (caracteresNoPermitidos != null) {
			for(String caracter : caracteresNoPermitidos.split(",")) {
				if (texto.contains(caracter)) {
					contiene = true;
					break;
				}
			}
		}
		return contiene;
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
	 * @param textoCa the textoCa to set
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
	 * @param textoEs the textoEs to set
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
	 * @param textoEn the textoEn to set
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
	 * @param textoDe the textoDe to set
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
	 * @param visibleCa the visibleCa to set
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
	 * @return the caracteresNoPermitidos
	 */
	public final String getCaracteresNoPermitidos() {
		return caracteresNoPermitidos;
	}

	/**
	 * @param caracteresNoPermitidos the caracteresNoPermitidos to set
	 */
	public final void setCaracteresNoPermitidos(String caracteresNoPermitidos) {
		this.caracteresNoPermitidos = caracteresNoPermitidos;
	}

	/**
	 * @return the longitudMaxima
	 */

	/**
	 * @param visibleEs the visibleEs to set
	 */
	public void setVisibleEs(final boolean visibleEs) {
		this.visibleEs = visibleEs;
	}

	/**
	 * @return the tamanyoMax
	 */
	public final String getTamanyoMax() {
		return tamanyoMax;
	}

	/**
	 * @param tamanyoMax the tamanyoMax to set
	 */
	public final void setTamanyoMax(String tamanyoMax) {
		this.tamanyoMax = tamanyoMax;
	}

	/**
	 * @return the visibleEn
	 */
	public boolean isVisibleEn() {
		return visibleEn;
	}

	/**
	 * @param visibleEn the visibleEn to set
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
	 * @param visibleDe the visibleDe to set
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
	 * @param requiredCa the requiredCa to set
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
	 * @param requiredEs the requiredEs to set
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
	 * @param requiredEn the requiredEn to set
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
	 * @param requiredDe the requiredDe to set
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
	 * @param iIdiomasObligatorios the iIdiomasObligatorios to set
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
	 * @param iIdiomasPosibles the iIdiomasPosibles to set
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
	 * @param borrable the borrable to set
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
	 * @param opcional the opcional to set
	 */
	public final void setOpcional(final String opcional) {
		this.opcional = opcional;
	}

}
