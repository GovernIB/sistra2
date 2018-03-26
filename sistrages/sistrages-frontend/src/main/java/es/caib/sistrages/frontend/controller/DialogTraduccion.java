package es.caib.sistrages.frontend.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogTraduccion extends DialogControllerBase {

	/** Texto Catalán. **/
	private String textoCa;
	/** Texto Espanyol. **/
	private String textoEs;
	/** Texto inglés. **/
	private String textoEn;
	/** Texto Alemán. **/
	private String textoDe;

	/** Visible Catalán. **/
	private boolean visibleCa = false;
	/** Visible Español. **/
	private boolean visibleEs = false;
	/** Visible Inglés. **/
	private boolean visibleEn = false;
	/** Visible Alemán. **/
	private boolean visibleDe = false;

	/** Obligatorio catalan. **/
	private boolean requiredCa = false;
	/** Obligatorio español. **/
	private boolean requiredEs = false;
	/** Obligatorio inglés. **/
	private boolean requiredEn = false;
	/** Obligatorio alemán. **/
	private boolean requiredDe = false;

	/** Data en formato JSON. **/
	private String iData;
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

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		try {
			final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
			// if (modo == TypeModoAcceso.ALTA && data == null) {
			if (iData == null || iData.isEmpty()) {
				// Borrar, sólo de prueba.
				data = UtilTraducciones.getTraduccionesPorDefecto();
			} else {
				data = (Literal) UtilJSON.fromJSON(iData, Literal.class);
			}

			if (iIdiomasObligatorios == null || iIdiomasObligatorios.isEmpty()) {
				// Si no tiene los idiomas obligatorios, damos por hecho
				// que todos los idiomas que hay de datos son obligatorios.
				idiomasObligatorios = data.getIdiomas();
			} else {
				final ObjectMapper mapper = new ObjectMapper();
				idiomasObligatorios = mapper.readValue(iIdiomasObligatorios, List.class);
			}

			if (iIdiomasPosibles == null || iIdiomasPosibles.isEmpty()) {
				idiomasPosibles = data.getIdiomas();
			} else {
				final ObjectMapper mapper = new ObjectMapper();
				idiomasPosibles = mapper.readValue(iIdiomasPosibles, List.class);
			}

			// Si en los posibles, no está alguno de los idiomasObligatorios, hay que
			// añadirlo
			for (final String idiomaObligatorio : idiomasObligatorios) {
				if (!idiomasPosibles.contains(idiomaObligatorio)) {
					idiomasPosibles.add(idiomaObligatorio);
				}
			}

			inicializarTextosPermisos();
		} catch (final IOException e) {
			throw new FrontException("Error convirtiendo traducciones desde JSON", e);
		}
	}

	/**
	 * Inicializa los textos, la visiblidad y obligatoriedad.
	 */
	private void inicializarTextosPermisos() {
		for (final String idioma : idiomasPosibles) {

			final TypeIdioma idiomaType = TypeIdioma.fromString(idioma);

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

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (visibleCa) {
			data.add(new Traduccion(TypeIdioma.CATALAN.toString(), textoCa));
		}
		if (visibleEs) {
			data.add(new Traduccion(TypeIdioma.CASTELLANO.toString(), textoEs));
		}
		if (visibleEn) {
			data.add(new Traduccion(TypeIdioma.INGLES.toString(), textoEn));
		}
		if (visibleDe) {
			data.add(new Traduccion(TypeIdioma.ALEMAN.toString(), textoDe));
		}

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
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
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

}
