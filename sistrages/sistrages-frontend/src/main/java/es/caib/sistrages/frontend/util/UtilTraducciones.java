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
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas, false);
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
	 * @param opcional
	 *            Indica si es opcional el literal o no (en caso de opcional, saldrá
	 *            un botón para borrar)
	 */
	public static void openDialogTraduccionOpcional(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final TramiteVersion tramiteVersion) {
		final List<String> idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
		UtilTraducciones.openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomas, true);
	}

	/**
	 * Abre un dialog
	 *
	 * @param modoAcceso
	 */
	public static void openDialogTraduccionAlta() {
		openDialogTraduccion(TypeModoAcceso.ALTA, null, null, null, false);
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
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios) {
		openDialogTraduccion(modoAcceso, traducciones, idiomas, idiomasObligatorios, false);
	}

	/**
	 * Abre un dialog de tipo traduccion (es el método PADRE que realmente crea el
	 * dialog).
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
	public static void openDialogTraduccion(final TypeModoAcceso modoAcceso, final Literal traducciones,
			final List<String> idiomas, final List<String> idiomasObligatorios, final Boolean opcional) {

		final Map<String, String> params = new HashMap<>();
		if (traducciones != null) {
			params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(traducciones));
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

		/** Calculamos la altura según el nº de idiomas a mostrar. **/
		final int altura = getAltura(idiomas);

		UtilJSF.openDialog(DialogTraduccion.class, modoAcceso, params, true, 470, altura);

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
		return altura;
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

}
