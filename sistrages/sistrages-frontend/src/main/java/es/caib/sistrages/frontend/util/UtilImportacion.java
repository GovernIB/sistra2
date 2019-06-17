package es.caib.sistrages.frontend.util;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarEntidad;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.util.UtilJSON;

/**
 * Util de importación. De momento, solo para dominios.
 *
 * @author Indra
 *
 */
public class UtilImportacion {

	/**
	 * Calcula el fila importar entidad.
	 *
	 * @param filaEntidad
	 * @param dir3actual
	 * @return
	 */
	public static FilaImportarEntidad getFilaEntidad(final FilaImportarEntidad filaEntidad, final String dir3actual) {
		filaEntidad.setDir3Actual(dir3actual);
		if (filaEntidad.getDir3() == null || !filaEntidad.getDir3().equals(dir3actual)) {
			final Object[] propiedades = new Object[1];
			propiedades[0] = dir3actual;
			filaEntidad.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.error.dir3distinto", propiedades));
			filaEntidad.setResultado(TypeImportarResultado.ERROR);
		} else {
			filaEntidad.setMensaje(UtilJSF.getLiteral("dialogTramiteImportar.ok.entidadcorrecta"));
			filaEntidad.setResultado(TypeImportarResultado.OK);
		}
		return filaEntidad;
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

	public static FilaImportarDominio getFilaDominio(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual,
			final String identificadorAreaSeleccionada, final Long idArea) {

		final FilaImportarDominio fila = UtilImportacion.checkDominios(dominio, dominioActual, fd, fdContent, fdActual,
				identificadorAreaSeleccionada, idArea);
		return UtilImportacion.rellenarDatosPorDefecto(fila);
	}

	/**
	 * Constructor vacio
	 */
	private UtilImportacion() {
		// Vacio
	}

	/**
	 * Metodo que comprueba si tiene permisos el usuario sobre los dominios.
	 *
	 * @return
	 */
	private static boolean checkPermisos(final Dominio dominio) {
		boolean tienePermisosEdicion;

		if (dominio == null) {
			tienePermisosEdicion = false;
		} else {
			switch (dominio.getAmbito()) {
			case AREA:
				tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
						|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR);
				break;
			case ENTIDAD:
				tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
				break;
			case GLOBAL:
				tienePermisosEdicion = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
				break;
			default:
				tienePermisosEdicion = false;
				break;
			}
		}

		return tienePermisosEdicion;
	}

	/**
	 * Rellena los tipos por defecto. Valores seleccionados y la altura/anchura.
	 */
	private static FilaImportarDominio rellenarDatosPorDefecto(final FilaImportarDominio fila) {
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
		return fila;
	}

	private static FilaImportarDominio checkDominios(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual,
			final String identificadorAreaSeleccionada, final Long idArea) {

		// Prohibido importar dominios de distintos ambitos.
		if (dominio != null && dominioActual != null && dominio.getAmbito() != dominioActual.getAmbito()) {

			return FilaImportarDominio.crearITerrorAmbitoAreas(dominio, dominioActual, fd, fdContent, fdActual,
					UtilJSF.getLiteral("importar.error.distintosAmbitos"));

		}

		// Si ambos dominios son de tipo Area, tienen que ser el mismo area (sino, es
		// probable que no se vean)
		if (dominio != null && dominioActual != null && dominio.getAmbito() == TypeAmbito.AREA
				&& isAreaErroneo(dominioActual, dominio)) {

			return FilaImportarDominio.crearITerrorAmbitoAreas(dominio, dominioActual, fd, fdContent, fdActual,
					UtilJSF.getLiteral("importar.error.ambitoAreaDistintaArea"));
		}

		// Si el área existe en BBDD, tiene que ser el mismo area que la seleccionada
		// (sino es un caos)
		if (dominioActual != null && dominioActual.getAmbito() == TypeAmbito.AREA
				&& isAreaErroneo(dominioActual, identificadorAreaSeleccionada)) {

			return FilaImportarDominio.crearITerrorAmbitoAreas(dominio, dominioActual, fd, fdContent, fdActual,
					UtilJSF.getLiteral("importar.error.areaDistintaSeleccionada"));
		}

		// Si ambos dominios son de tipo Entidad, tienen que ser de la misma entidad
		if (dominio != null && dominioActual != null && dominio.getAmbito() == TypeAmbito.ENTIDAD
				&& isEntidadErroneo(dominioActual)) {

			return FilaImportarDominio.crearITerrorAmbitoAreas(dominio, dominioActual, fd, fdContent, fdActual,
					UtilJSF.getLiteral("importar.error.ambitoEntidadesDistintaEntidad"));
		}

		// Si ambos dominios son de tipo FD, la FD debe coincidir la entidad o area
		if (fd != null && fdActual != null && dominioActual != null
				&& dominioActual.getTipo() == TypeDominio.FUENTE_DATOS && isFDErroneo(fd, fdActual, idArea)) {

			return FilaImportarDominio.crearITerrorAmbitoAreas(dominio, dominioActual, fd, fdContent, fdActual,
					UtilJSF.getLiteral("importar.error.ambitoFDDistintaArea"));

		}

		if (checkPermisos(dominio)) {
			return checkDominioModoEdicion(dominio, dominioActual, fd, fdContent, fdActual);
		} else {
			return checkDominioModoSoloActualizacion(dominio, dominioActual, fd, fdContent, fdActual);
		}

	}

	/**
	 * Se comprueba que la FD si ambas existen, tenga el mismo ámbito y pertenezcan
	 * al mismo entidad (si es ambito entidad) o area (si es mismo area)
	 *
	 * @param fuenteDatos
	 * @param fuenteDatosActual
	 * @return
	 */
	private static boolean isFDErroneo(final FuenteDatos fuenteDatos, final FuenteDatos fuenteDatosActual,
			final Long idArea) {
		boolean erroneo;
		if (fuenteDatos == null || fuenteDatosActual == null) {
			erroneo = false;
		} else {
			if (fuenteDatos.getAmbito() != fuenteDatosActual.getAmbito()) {
				erroneo = true;
			} else if (fuenteDatosActual.getAmbito() == TypeAmbito.ENTIDAD) {
				erroneo = fuenteDatosActual.getEntidad().getCodigo().compareTo(UtilJSF.getIdEntidad()) != 0;
			} else if (fuenteDatosActual.getAmbito() == TypeAmbito.AREA && idArea != null) {
				erroneo = fuenteDatosActual.getArea().getCodigo().compareTo(idArea) != 0;
			} else {
				erroneo = false;
			}
		}
		return erroneo;
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
	private static FilaImportarDominio checkDominioModoEdicion(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual) {

		FilaImportarDominio fila;
		final String mensaje = null;
		if (dominioActual == null || (dominio != null && !mismaEstructura(dominio, dominioActual, fd, fdActual))) {

			// if (dominioActual == null) {
			// mensaje = UtilJSF.getLiteral("importar.ok.soloimportar");
			// } else {
			// mensaje = UtilJSF.getLiteral("importar.ok.soloreemplazar");
			// }

			fila = FilaImportarDominio.crearITsoloReemplazar(dominio, dominioActual, fd, fdContent, fdActual, mensaje);

		} else {

			// mensaje = UtilJSF.getLiteral("importar.ok.actualizacioncompleta");
			fila = FilaImportarDominio.crearITconPermisos(dominio, dominioActual, fd, fdContent, fdActual, mensaje);

		}
		return fila;
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
	private static FilaImportarDominio checkDominioModoSoloActualizacion(final Dominio dominio,
			final Dominio dominioActual, final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual) {
		FilaImportarDominio fila;
		if (dominioActual != null && mismaEstructura(dominio, dominioActual, fd, fdActual)) {

			// UtilJSF.getLiteral("importar.ok.soloseleccionar")
			fila = FilaImportarDominio.crearITsoloMantener(dominio, dominioActual, fd, fdContent, fdActual, null);

		} else {

			String literal;
			if (dominioActual == null) {
				literal = UtilJSF.getLiteral("importar.error.sinpermisos.creacion");
			} else {
				literal = UtilJSF.getLiteral("importar.error.sinpermisos.actualizacion");
			}
			literal = "";
			fila = FilaImportarDominio.crearITerrorSoloMantener(dominio, dominioActual, fd, fdContent, fdActual,
					literal);

		}
		return fila;
	}

	/**
	 * Comprueba si es la misma estructura. Tienen que tener los mismos valores de
	 * parámetros y, si es también de tipo FD, también la misma estructura.
	 *
	 * @return
	 */
	private static boolean mismaEstructura(final Dominio dominio, final Dominio dominioActual, final FuenteDatos fd,
			final FuenteDatos fdActual) {
		if (dominio.getTipo() == TypeDominio.FUENTE_DATOS
				|| (dominioActual != null && dominioActual.getTipo() == TypeDominio.FUENTE_DATOS)) {
			return mismosValores(dominio.getParametros(), dominioActual.getParametros())
					&& mismosValoresFD(fd, fdActual);
		} else {
			return mismosValores(dominio.getParametros(), dominioActual.getParametros());
		}
	}

	/**
	 * Comprueba si dos fuente de datos tienen la misma estructura.
	 *
	 * @param fd
	 * @param fdActual
	 * @return
	 */
	private static boolean mismosValoresFD(final FuenteDatos fd, final FuenteDatos fdActual) {
		if (fd == null || fdActual == null) {
			return false;
		} else {
			if (fd.getCampos().size() == fdActual.getCampos().size()) {
				boolean retorno = true;
				for (final FuenteDatosCampo campo : fd.getCampos()) {

					boolean noencontrado = true;
					for (final FuenteDatosCampo campo2 : fdActual.getCampos()) {
						if (campo.getIdentificador().equals(campo2.getIdentificador())
								&& campo.isClavePrimaria() == campo2.isClavePrimaria()) {
							noencontrado = false;
							break;
						}
					}

					if (noencontrado) {
						retorno = false;
						break;
					}
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

	/**
	 * Compara la entidad del dominio con el del usuario.
	 *
	 * @param dominio
	 * @return
	 */
	private static boolean isEntidadErroneo(final Dominio dominio) {
		boolean retorno;
		if (dominio == null || dominio.getAmbito() != TypeAmbito.ENTIDAD || dominio.getEntidad() == null
				|| dominio.getEntidad().compareTo(UtilJSF.getIdEntidad()) != 0) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Compara el área del dominio con el área seleccionada.
	 *
	 * @param dominio
	 * @param identificadorArea
	 * @return
	 */
	private static boolean isAreaErroneo(final Dominio dominio, final String identificadorArea) {
		boolean retorno;
		if (dominio.getAreas().size() != 1 || identificadorArea == null || identificadorArea.isEmpty()) {
			retorno = true;
		} else {
			final String identificador1 = ((Area) dominio.getAreas().toArray()[0]).getIdentificador();

			retorno = !identificador1.equals(identificadorArea);

		}
		return retorno;
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
			final String identificador1 = ((Area) dominio.getAreas().toArray()[0]).getIdentificador();
			final String identificador2 = ((Area) dominio2.getAreas().toArray()[0]).getIdentificador();

			retorno = !identificador1.equals(identificador2);
		}
		return retorno;
	}

}
