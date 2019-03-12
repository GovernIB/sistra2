package es.caib.sistrages.core.api.util;

import java.util.Arrays;
import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Util de importación. De momento, solo para dominios.
 *
 * @author Indra
 *
 */
public class UtilImportacion {

	/**
	 * Constructor vacio
	 */
	private UtilImportacion() {
		// Vacio
	}

	public static FilaImportarDominio getFilaDominio(final Dominio iDominio, final Dominio iDominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final boolean permiteEditar) {
		final FilaImportarDominio fila = new FilaImportarDominio();
		fila.setDominio(iDominio);
		fila.setDominioActual(iDominioActual);
		fila.setFuenteDatos(fd);
		fila.setFuenteDatosContent(fdContent);
		fila.setFuenteDatosActual(fdActual);
		fila.setPermisosEdicion(permiteEditar);
		UtilImportacion.checkDominios(fila);
		UtilImportacion.rellenarDatosPorDefecto(fila);
		return fila;
	}

	/**
	 * Rellena los tipos por defecto. Valores seleccionados y la altura/anchura.
	 */
	private static void rellenarDatosPorDefecto(final FilaImportarDominio fila) {
		if (fila.getDominio() != null) {
			switch (fila.getDominio().getTipo()) {
			case CONSULTA_BD:
				fila.setResultadoJndi(fila.getDominio().getJndi());
				fila.setResultadoSQL(fila.getDominio().getSql());
				fila.setResultadoSQLdecoded(fila.getDominio().getSqlDecoded());
				fila.setAltura(350);
				break;
			case CONSULTA_REMOTA:
				fila.setResultadoURL(fila.getDominio().getUrl());
				fila.setAltura(200);
				break;
			case FUENTE_DATOS:
				fila.setResultadoSQL(fila.getDominio().getSql());
				fila.setResultadoSQLdecoded(fila.getDominio().getSqlDecoded());
				fila.setAltura(370);
				break;
			case LISTA_FIJA:
				fila.setResultadoLista(UtilJSON.toJSON(fila.getDominio().getListaFija()));
				fila.setAltura(400);
				break;
			}

		}
	}

	/**
	 * Compara dos dominios y obtiene las condiciones para la importación.
	 *
	 * Comprobación de permisos: - Si es global tienes que ser superadministrador. -
	 * Si es de entidad, tienes que ser adm. entidad. - Si es de area, tienes que
	 * ser adm.entidad o desarrollador con permiso de adm./des. area.
	 *
	 * Pasos a realizar: <br />
	 *
	 * @param fila
	 * @return
	 */
	private static void checkDominios(final FilaImportarDominio fila) {

		// Prohibido importar dominios de distintos ambitos.
		if (fila.getDominio() != null && fila.getDominioActual() != null
				&& fila.getDominio().getAmbito() != fila.getDominioActual().getAmbito()) {

			fila.setAccion(TypeImportarAccion.ERROR);
			fila.setEstado(TypeImportarEstado.EXISTE);
			fila.setResultado(TypeImportarResultado.ERROR);
			fila.setVisibleBoton(false);
			fila.setMismoTipo(false);
			fila.setMensaje("importar.error.distintosAmbitos");
			return;
		}

		// Si ambos dominios son de tipo Area, tienen que ser el mismo area (sino, es
		// probable que no se vean)
		if (fila.getDominio() != null && fila.getDominioActual() != null
				&& fila.getDominio().getAmbito() == fila.getDominioActual().getAmbito()
				&& fila.getDominio().getAmbito() == TypeAmbito.AREA
				&& isAreaErroneo(fila.getDominioActual(), fila.getDominio())) {

			fila.setAccion(TypeImportarAccion.ERROR);
			fila.setEstado(TypeImportarEstado.EXISTE);
			fila.setResultado(TypeImportarResultado.ERROR);
			fila.setVisibleBoton(false);
			fila.setMismoTipo(false);
			fila.setMensaje("importar.error.ambitoAreaDistintaArea");
			return;
		}

		if (fila.isPermisosEdicion()) {
			checkDominioModoEdicion(fila);
		} else {
			checkDominioModoActualizacion(fila);
		}

	}

	/**
	 * Comprueba si el area de dos dominios son iguales
	 *
	 * @param dominio
	 * @param dominio2
	 * @return
	 */
	private static boolean isAreaErroneo(final Dominio dominio, final Dominio dominio2) {
		boolean retorno;
		if (dominio.getAreas().size() != 1 || dominio2.getAreas().size() != 1) {
			retorno = true;
		} else {
			final Long idArea = Long.valueOf(dominio.getAreas().toArray()[0].toString());
			final Long idArea2 = Long.valueOf(dominio2.getAreas().toArray()[0].toString());
			retorno = idArea.compareTo(idArea2) != 0; // Si son iguales, el valor 0 y por tanto, será false

		}
		return retorno;
	}

	/**
	 * El perfil comprueba si se puede mantener el dominio actual o bien hay que
	 * forzar una actualización.
	 *
	 * Si no existe dominio actual (BBDD) o no mantiene estructura, sólo se le
	 * permite reemplazar (actualizando si quiere los params). En caso contrario,
	 * puede mantener o reemplazar.
	 *
	 * Realmente sólo permite reemplazar cuando el tramite que se importa es
	 * compatible con los parametros del dominio (o es compatible o se fuerza para
	 * que no pierda compatibilidad y falle).
	 *
	 */
	private static void checkDominioModoEdicion(final FilaImportarDominio fila) {

		if (fila.getDominioActual() == null || (fila.getDominio() != null && !mismaEstructura(fila))) {

			fila.setAccion(TypeImportarAccion.REEMPLAZAR);
			if (fila.getDominioActual() == null) {
				fila.setEstado(TypeImportarEstado.NO_EXISTE);
				fila.setMismoTipo(false);
			} else {
				fila.setEstado(TypeImportarEstado.EXISTE);
				fila.setMismoTipo(fila.getDominio().getTipo() == fila.getDominioActual().getTipo());
			}
			fila.setResultado(TypeImportarResultado.WARNING);
			fila.setVisibleBoton(true);
			fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR));
			fila.setMensaje("importar.ok.soloactualizacion");

		} else {

			fila.setAccion(TypeImportarAccion.REEMPLAZAR);
			if (fila.getDominioActual() == null) {
				fila.setEstado(TypeImportarEstado.NO_EXISTE);
				fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR));
			} else {
				fila.setEstado(TypeImportarEstado.EXISTE);
				fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR, TypeImportarAccion.MANTENER));
			}
			fila.setResultado(TypeImportarResultado.WARNING);
			fila.setVisibleBoton(true);
			fila.setMismoTipo((fila.getDominio() != null && fila.getDominioActual() != null
					&& fila.getDominioActual().getTipo() == fila.getDominio().getTipo()));

			fila.setMensaje("importar.ok.actualizacioncompleta");
		}

	}

	/**
	 * Comprueba si puede actualizar el dominio. Los pasos que debe cumplir son:
	 * <br />
	 *
	 * - El dominio en BBDD (actual) tiene que existir.<br />
	 * - Mantiene la misma estructura. <br />
	 *
	 * En caso de cumplirlo, se informará al usuario (y se actualizará la info).
	 * <br />
	 * En caso de no cumplir alguna de las condiciones, se dará un error de
	 * permisos. <br />
	 *
	 */
	private static void checkDominioModoActualizacion(final FilaImportarDominio fila) {
		if (fila.getDominioActual() != null && mismaEstructura(fila)) {

			fila.setAccion(TypeImportarAccion.REVISADO);
			fila.setEstado(TypeImportarEstado.EXISTE);
			fila.setResultado(TypeImportarResultado.OK);
			fila.setVisibleBoton(false);
			fila.setMismoTipo(false);
			fila.setMensaje("importar.ok.soloactualizacion");

		} else {

			fila.setAccion(TypeImportarAccion.ERROR);
			if (fila.getDominioActual() == null) {
				fila.setEstado(TypeImportarEstado.NO_EXISTE);
				fila.setMensaje("importar.error.sinpermisos.creacion");
			} else {
				fila.setEstado(TypeImportarEstado.EXISTE);
				fila.setMensaje("importar.error.sinpermisos.actualizacion");
			}
			fila.setResultado(TypeImportarResultado.ERROR);
			fila.setVisibleBoton(false);
			fila.setMismoTipo(true);

		}
	}

	/**
	 * Comprueba si es la misma estructura. Tienen que tener los mismos valores de
	 * parámetros y, si es también de tipo FD, también la misma estructura.
	 *
	 * @return
	 */
	private static boolean mismaEstructura(final FilaImportarDominio fila) {
		if (fila.getDominio().getTipo() == TypeDominio.FUENTE_DATOS
				|| (fila.getDominioActual() != null && fila.getDominioActual().getTipo() == TypeDominio.FUENTE_DATOS)) {
			return mismosValores(fila.getDominio().getParametros(), fila.getDominioActual().getParametros())
					&& mismosValoresFD(fila);
		} else {
			return mismosValores(fila.getDominio().getParametros(), fila.getDominioActual().getParametros());
		}
	}

	/**
	 * Comprueba si dos fuente de datos tienen la misma estructura.
	 *
	 * @param fila
	 * @return
	 */
	private static boolean mismosValoresFD(final FilaImportarDominio fila) {
		if (fila.getFuenteDatos() == null || fila.getFuenteDatosActual() == null) {
			return false;
		} else {
			if (fila.getFuenteDatos().getCampos().size() == fila.getFuenteDatosActual().getCampos().size()) {
				boolean retorno = true;
				for (final FuenteDatosCampo campo : fila.getFuenteDatos().getCampos()) {
					for (final FuenteDatosCampo campo2 : fila.getFuenteDatosActual().getCampos()) {
						if (campo.getIdentificador().equals(campo2.getIdentificador())
								&& campo.isClavePrimaria() == campo2.isClavePrimaria()) {
							break;
						}
					}
					// Solo llegará aquí si no entre por el if del bucle.
					retorno = false;
				}
				return retorno;

			} else {
				return false;
			}
		}
	}

	/**
	 * Comprueba si son el mismo valor.
	 *
	 * @param texto1
	 * @param texto2
	 * @return
	 */
	private static boolean mismosValores(final String texto1, final String texto2) {
		boolean iguales;
		if (texto1 == null) {
			if (texto2 == null) {
				iguales = true;
			} else {
				iguales = false;
			}
		} else {
			if (texto2 == null) {
				iguales = false;
			} else {
				iguales = texto1.equals(texto2);
			}
		}
		return iguales;
	}

	/**
	 * Comprueba si son el mismo valor.
	 *
	 * @param propiedades1
	 * @param propiedades2
	 * @return
	 */
	private static boolean mismosValores(final List<Propiedad> propiedades1, final List<Propiedad> propiedades2) {
		boolean iguales;
		if (propiedades1 == null) {
			if (propiedades2 == null) {
				iguales = true;
			} else {
				iguales = false;
			}
		} else {
			if (propiedades2 == null) {
				iguales = false;
			} else {
				if (propiedades1.size() == propiedades2.size()) {
					iguales = true;
					for (final Propiedad propiedad1 : propiedades1) {
						boolean encontrado = false;
						for (final Propiedad propiedad2 : propiedades2) {
							if (mismosValores(propiedad1.getCodigo(), propiedad2.getCodigo())
									&& mismosValores(propiedad1.getValor(), propiedad2.getValor())) {
								encontrado = true;
								break;
							}
						}
						if (!encontrado) {
							iguales = false;
							break;
						}
					}
				} else {
					iguales = false;
				}
			}
		}
		return iguales;
	}
}
