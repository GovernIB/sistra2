package es.caib.sistrages.frontend.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Traducciones;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.frontend.controller.DialogTraduccion;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;

/**
 * Utilidades de Traducciones.
 *
 * @author Indra
 *
 */
public final class UtilTraducciones {

	/** Idioma español. */
	public static final String LANG_CASTELLANO = "es";
	/** Idioma catalan. **/
	public static final String LANG_CATALAN = "ca";
	/** Idioma ingles. **/
	public static final String LANG_INGLES = "en";
	/** Idioma aleman. **/
	public static final String LANG_ALEMAN = "de";

	/** Constructor privado para evitar problema. */
	private UtilTraducciones() {
		// not called
	}

	/**
	 * Abre un dialog de tipo traduccion.
	 *
	 * @param modoAcceso
	 *            Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones
	 *            Dato en formato json de tipo Traducciones
	 * @param idiomas
	 *            La lista de idiomas que se puede introducir literal. Si no se
	 *            introduce, se supondrán que son los que tenga traducciones.
	 * @param idiomasObligatorios
	 *            La lista de idiomas obligatorios. Si no se introduce, se supondrán
	 *            que son los que tenga traducciones.
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, Traducciones traducciones,
			List<String> idiomas, List<String> idiomasObligatorios) {

		final Map<String, String> params = new HashMap<>();

		if (traducciones == null || traducciones.getIdiomas() == null || traducciones.getIdiomas().isEmpty()) {
			traducciones = getTraduccionesPorDefecto();
		}

		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(traducciones));

		if (idiomas == null) {
			idiomas = traducciones.getIdiomas();
		}

		if (idiomasObligatorios == null) {
			idiomasObligatorios = traducciones.getIdiomas();
		}

		params.put("OBLIGATORIOS", UtilJSON.toJSON(idiomasObligatorios));
		params.put("IDIOMAS", UtilJSON.toJSON(idiomas));

		/** Calculamos la altura según el nº de idiomas a mostrar. **/
		int altura;
		switch (idiomas.size()) {
		case 1:
			altura = 120;
			break;
		case 2:
			altura = 200;
			break;
		case 3:
			altura = 280;
			break;
		case 4:
			altura = 360;
			break;
		default:
			altura = 100;
		}
		UtilJSF.openDialog(DialogTraduccion.class, modoAcceso, params, true, 460, altura);

	}

	/**
	 * Devuelve el objeto Traducciones con los idiomas mínimos por defecto (el
	 * catalán y el castellano).
	 *
	 * @return
	 */
	public static Traducciones getTraduccionesPorDefecto() {
		final Traducciones traducciones = new Traducciones();
		traducciones.add(new Traduccion(UtilTraducciones.LANG_CATALAN, ""));
		traducciones.add(new Traduccion(UtilTraducciones.LANG_CASTELLANO, ""));
		return traducciones;
	}

	/**
	 * Convierte los idiomas soportados de un tramite version en la lista de idiomas
	 * soportados.
	 *
	 * @param tramiteVersion
	 * @return
	 */
	public static List<String> getIdiomasSoportados(final TramiteVersion tramiteVersion) {
		List<String> idiomas;
		if (tramiteVersion == null || tramiteVersion.getIdiomasSoportados() == null
				|| tramiteVersion.getIdiomasSoportados().isEmpty()) {
			idiomas = new ArrayList<>();
			idiomas.add(UtilTraducciones.LANG_CATALAN);
			idiomas.add(UtilTraducciones.LANG_CASTELLANO);

		} else {
			idiomas = Arrays.asList(tramiteVersion.getIdiomasSoportados().split(";"));
		}
		return idiomas;
	}

	/**
	 * Obtiene los idiomas por defecto.
	 *
	 * @return
	 */
	public static List<String> getIdiomasPorDefecto() {
		final List<String> idiomas = new ArrayList<>();
		idiomas.add(UtilTraducciones.LANG_CATALAN);
		idiomas.add(UtilTraducciones.LANG_CASTELLANO);
		return idiomas;
	}
}
