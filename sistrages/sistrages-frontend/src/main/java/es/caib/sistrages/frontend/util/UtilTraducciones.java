package es.caib.sistrages.frontend.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.controller.DialogTraduccion;
import es.caib.sistrages.frontend.controller.DialogTraduccionHTML;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;

/**
 * Utilidades de Traducciones.
 *
 * @author Indra
 *
 */
public final class UtilTraducciones {
	public static String TAMANYO_MAXIMO_REGWEB3 = "200";
	public static String CARACTERES_NOPERMIT_REGWEB3 = ">,%,*,&,:,;,¿,?,/,|,!,<,¡,\",\',\\,\n,\t";

	/** Constructor privado para evitar problema. */
	private UtilTraducciones() {
		// not called
	}

	/**
	 * Abre un dialog de tipo traduccion, utilizando los idiomas soportados a partir
	 * del tramite version.
	 *
	 * @param modoAcceso     Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones   Dato en formato json de tipo Traducciones
	 * @param tramiteVersion La versión del trámite
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final TramiteVersion tramiteVersion) {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas, false, null, null);
	}

	/**
	 * Abre un dialog de tipo traduccion, utilizando los idiomas soportados a partir
	 * del tramite version.
	 *
	 * @param modoAcceso     Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones   Dato en formato json de tipo Traducciones
	 * @param tramiteVersion La versión del trámite
	 * @param opcional       Indica si es opcional el literal o no (en caso de
	 *                       opcional, saldrá un botón para borrar)
	 */
	public static void openDialogTraduccionOpcional(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final TramiteVersion tramiteVersion) {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas, true, null, null);
	}

	/**
	 * Abre un dialog
	 *
	 * @param modoAcceso
	 */
	public static void openDialogTraduccionAlta() {
		openDialogTraduccion(TypeModoAcceso.ALTA, null, null, null, false, null, null);
	}

	/**
	 * Abre un dialog de tipo traduccion.
	 *
	 * @param modoAcceso          Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones        Dato en formato json de tipo Traducciones
	 * @param idiomas             La lista de idiomas que se puede introducir
	 *                            literal. Si no se introduce, se supondrán que son
	 *                            los que tenga traducciones.
	 * @param idiomasObligatorios La lista de idiomas obligatorios. Si no se
	 *                            introduce, se supondrán que son los que tenga
	 *                            traducciones.
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios) {
		openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomasObligatorios, false, null, null);
	}

	/**
	 * Abre un dialog de tipo traduccion.
	 *
	 * @param modoAcceso          Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones        Dato en formato json de tipo Traducciones
	 * @param idiomas             La lista de idiomas que se puede introducir
	 *                            literal. Si no se introduce, se supondrán que son
	 *                            los que tenga traducciones.
	 * @param idiomasObligatorios La lista de idiomas obligatorios. Si no se
	 *                            introduce, se supondrán que son los que tenga
	 *                            traducciones.
	 */
	public static void openDialogTraduccionOpcional(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios) {
		openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomasObligatorios);
	}

	/**
	 * Abre un dialog de tipo traduccion (es el método PADRE que realmente crea el
	 * dialog).
	 *
	 * @param modoAcceso          Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones        Dato en formato json de tipo Traducciones
	 * @param idiomas             La lista de idiomas que se puede introducir
	 *                            literal. Si no se introduce, se supondrán que son
	 *                            los que tenga traducciones.
	 * @param idiomasObligatorios La lista de idiomas obligatorios. Si no se
	 *                            introduce, se supondrán que son los que tenga
	 *                            traducciones.
	 */
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios, final Boolean opcional,
			final String caractNoPerm, String tamanyoMax) {

		final Map<String, String> params = new HashMap<>();
		if (traducciones == null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_LITERALES);
		} else {
			UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_LITERALES, traducciones);
		}

		if (idiomas != null) {
			params.put(TypeParametroVentana.IDIOMAS.toString(), UtilJSON.toJSON(idiomas));
		}

		if (idiomasObligatorios != null) {
			params.put(TypeParametroVentana.OBLIGATORIOS.toString(), UtilJSON.toJSON(idiomasObligatorios));
		}

		if (caractNoPerm != null) {
			params.put(TypeParametroVentana.CARACT_NO_PERMITIDOS.toString(), caractNoPerm);
		}

		if (tamanyoMax != null) {
			params.put(TypeParametroVentana.TAMANYO_MAX.toString(), tamanyoMax);
		}

		// Variable que marca que si es opcional, tiene que aparecer el botón de borrar
		if (opcional != null && opcional) {
			params.put(TypeParametroVentana.ES_OPCIONAL.toString(), "S");
		}

		/** Calculamos la altura según el nº de idiomas a mostrar. **/
		int altura = getAltura(idiomas);
		int ancho = getAnchura(idiomas);

		if (tamanyoMax != null) {
			params.put(TypeParametroVentana.COLS.toString(), "47");
			params.put(TypeParametroVentana.ROWS.toString(), "5");

		} else {
			params.put(TypeParametroVentana.COLS.toString(), "80");
			params.put(TypeParametroVentana.ROWS.toString(), "10");
			ancho = 750;
			int size;
			if (idiomas == null) {
				size = UtilJSF.getSessionBean().getIdiomas().size();
			} else {
				size = idiomas.size();
			}
			altura = altura + (77 * size);
		}
		UtilJSF.openDialog(DialogTraduccion.class, modoAcceso, params, true, ancho, altura);
	}

	/**
	 * Abre un dialog de tipo traduccion (es el método PADRE que realmente crea el
	 * dialog).
	 *
	 * @param modoAcceso          Modo de acceso (ALTA, EDICION o CONSULTA)
	 * @param traducciones        Dato en formato json de tipo Traducciones
	 * @param idiomas             La lista de idiomas que se puede introducir
	 *                            literal. Si no se introduce, se supondrán que son
	 *                            los que tenga traducciones.
	 * @param idiomasObligatorios La lista de idiomas obligatorios. Si no se
	 *                            introduce, se supondrán que son los que tenga
	 *                            traducciones.
	 */
	public static void openDialogTraduccionHTML(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios, final Boolean opcional) {

		final Map<String, String> params = new HashMap<>();
		if (traducciones == null) {
			UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_LITERALES_HTML);
		} else {
			UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_LITERALES_HTML, traducciones);
		}

		if (idiomas != null) {
			params.put(TypeParametroVentana.IDIOMAS.toString(), UtilJSON.toJSON(idiomas));
		}

		if (idiomasObligatorios != null) {
			params.put(TypeParametroVentana.OBLIGATORIOS.toString(), UtilJSON.toJSON(idiomasObligatorios));
		}

		// Variable que marca que si es opcional, tiene que aparecer el botón de borrar
		if (opcional != null && opcional) {
			params.put(TypeParametroVentana.ES_OPCIONAL.toString(), "S");
		}

		UtilJSF.openDialog(DialogTraduccionHTML.class, modoAcceso, params, true, 770, 700);
	}

	/**
	 * Calcula la altura del dialog.
	 *
	 * @param size
	 * @return
	 */
	private static int getAltura(final List<String> idiomas) {
		int altura;
		int size;
		if (idiomas == null) {
			size = UtilJSF.getSessionBean().getIdiomas().size();
		} else {
			size = idiomas.size();
		}
		switch (size) {
		case 1:
			altura = 160;
			break;
		case 2:
			altura = 270;

			break;
		case 3:
			altura = 380;
			break;
		case 4:
			altura = 490;
			break;
		default:
			altura = 160;
		}
		return altura;
	}

	/**
	 * Calcula la altura del dialog.
	 *
	 * @param size
	 * @return
	 */
	private static int getAnchura(final List<String> idiomas) {
		int anchura;
		int size;
		if (idiomas == null) {
			size = UtilJSF.getSessionBean().getIdiomas().size();
		} else {
			size = idiomas.size();
		}
		switch (size) {
		case 1:
			anchura = 495;
			break;
		case 2:
			anchura = 510;

			break;
		case 3:
			anchura = 510;
			break;
		case 4:
			anchura = 510;
			break;
		default:
			anchura = 495;
		}
		return anchura;
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
	 * Obtiene los idiomas en formato lista.
	 *
	 * @param iIdiomas
	 * @return
	 */
	public static List<String> getIdiomas(final String iIdiomas) {
		final List<String> idiomas = new ArrayList<>();
		if (iIdiomas != null) {
			String caracterSeparacion = ";";
			if (iIdiomas.contains(",")) {
				caracterSeparacion = ",";
			}
			for (final String idi : iIdiomas.split(caracterSeparacion)) {
				idiomas.add(idi);
			}

		}
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

	public static void openDialogTraduccion(TypeModoAcceso modoAcceso, Literal traducciones,
			TramiteVersion tramiteVersion, String caractNoPerm, String tamanyoMax) {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas, false, caractNoPerm,
				tamanyoMax);
	}

}
