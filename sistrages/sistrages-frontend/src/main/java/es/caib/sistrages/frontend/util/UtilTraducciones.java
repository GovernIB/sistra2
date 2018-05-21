package es.caib.sistrages.frontend.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.util.UtilJSON;
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

	/** Constructor privado para evitar problema. */
	private UtilTraducciones() {
		// not called
	}

	/**
	 * Abre un dialog de tipo traduccion, utilizando los idiomas soportados a partir
	 * del tramite version.
	 *
	 * @param modoAcceso
	 *            Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones
	 *            Dato en formato json de tipo Traducciones
	 * @param tramiteVersion
	 *            La versión del trámite
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final TramiteVersion tramiteVersion) {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas);
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
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, Literal traducciones, List<String> idiomas,
			List<String> idiomasObligatorios) {

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
			altura = 160;
			break;
		case 2:
			altura = 250;
			break;
		case 3:
			altura = 340;
			break;
		case 4:
			altura = 430;
			break;
		default:
			altura = 160;
		}
		UtilJSF.openDialog(DialogTraduccion.class, modoAcceso, params, true, 470, altura);

	}

	/**
	 * Devuelve el objeto Traducciones con los idiomas mínimos por defecto (el
	 * catalán y el castellano).
	 *
	 * @return
	 */
	public static Literal getTraduccionesPorDefecto() {
		final Literal traducciones = new Literal();
		traducciones.add(new Traduccion(TypeIdioma.CATALAN.toString(), ""));
		traducciones.add(new Traduccion(TypeIdioma.CASTELLANO.toString(), ""));
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
			idiomas.add(TypeIdioma.CATALAN.toString());
			idiomas.add(TypeIdioma.CASTELLANO.toString());

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
		idiomas.add(TypeIdioma.CATALAN.toString());
		idiomas.add(TypeIdioma.CASTELLANO.toString());
		return idiomas;
	}

	/**
	 * Obtiene los idiomas por defecto.
	 *
	 * @return
	 */
	public static String getIdiomasPorDefectoTramite() {
		return TypeIdioma.CATALAN.toString() + ";" + TypeIdioma.CASTELLANO.toString();
	}
}
