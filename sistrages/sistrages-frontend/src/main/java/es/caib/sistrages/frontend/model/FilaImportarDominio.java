package es.caib.sistrages.frontend.model;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.frontend.model.types.TypeImportarAccion;
import es.caib.sistrages.frontend.model.types.TypeImportarEstado;
import es.caib.sistrages.frontend.model.types.TypeImportarResultado;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Fila dominio importar.
 *
 * @author slromero
 *
 */
public class FilaImportarDominio extends FilaImportar {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FilaImportarDominio.class);

	/** Dominio. **/
	private Dominio dominio;

	/** Dominio. **/
	private Dominio dominioActual;

	/** Indica si es visibleBoton o no el boton. **/
	private boolean visibleBoton;

	/** Indica si el dominio y el dominio actual son del mismo tipo. */
	private boolean mismoTipo;

	/** URL. **/
	private String url;

	/** JDNI. **/
	private String jndi;

	/** SQL. **/
	private String sql;

	/** Altura. **/
	private Integer altura = 330;

	/** Altura. **/
	private Integer anchura = 900;

	/** Resultado jdni. */
	private String resultadoJndi;

	/** Resultado SQL . */
	private String resultadoSQL;

	/** Resultado URL. */
	private String resultadoURL;

	/** Resultado Lista. **/
	private String resultadoLista;

	/** Resultado Fuente de datos. **/
	private String resultadoFD;

	/** Resultado distinto. **/
	private String resultadoDistinto;

	/** Mensaje. **/
	private String mensaje;

	/**
	 * Constructor basico.
	 */
	public FilaImportarDominio() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param iDominio
	 * @param iDominioActual
	 */
	public FilaImportarDominio(final Dominio iDominio, final Dominio iDominioActual) {
		this.dominio = iDominio;
		this.dominioActual = iDominioActual;
		this.checkDominios();
		this.rellenarDatosPorDefecto();
	}

	/**
	 * @return the dominio
	 */
	public Dominio getDominio() {
		return dominio;
	}

	/**
	 * @param dominio
	 *            the dominio to set
	 */
	public void setDominio(final Dominio dominio) {
		this.dominio = dominio;
	}

	/**
	 * @return the visibleBoton
	 */
	public boolean isVisibleBoton() {
		return visibleBoton;
	}

	/**
	 * @return the visibleBoton
	 */
	public boolean getVisibleBoton() {
		return isVisibleBoton();
	}

	/**
	 * @param visibleBoton
	 *            the visibleBoton to set
	 */
	public void setVisibleBoton(final boolean visibleBoton) {
		this.visibleBoton = visibleBoton;
	}

	/**
	 * @return the dominioActual
	 */
	public Dominio getDominioActual() {
		return dominioActual;
	}

	/**
	 * @param dominioActual
	 *            the dominioActual to set
	 */
	public void setDominioActual(final Dominio dominioActual) {
		this.dominioActual = dominioActual;
	}

	/**
	 * @return the mismoTipo
	 */
	public boolean isMismoTipo() {
		return mismoTipo;
	}

	/**
	 * @return the mismoTipo
	 */
	public boolean getMismoTipo() {
		return isMismoTipo();
	}

	/**
	 * @param mismoTipo
	 *            the mismoTipo to set
	 */
	public void setMismoTipo(final boolean mismoTipo) {
		this.mismoTipo = mismoTipo;
	}

	/**
	 * Rellena los tipos por defecto. Valores seleccionados y la altura/anchura.
	 */
	private void rellenarDatosPorDefecto() {
		if (mismoTipo) {
			if (dominio != null) {
				switch (dominio.getTipo()) {
				case CONSULTA_BD:
					this.jndi = dominio.getJndi();
					this.sql = dominio.getSql();
					altura = 300;
					break;
				case CONSULTA_REMOTA:
					this.url = dominio.getUrl();
					altura = 200;
					break;
				case FUENTE_DATOS:
					this.sql = dominio.getSql();
					altura = 200;
					break;
				case LISTA_FIJA:
					altura = 400;
					break;
				}

			}
		} else {
			altura = 200;
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
	 * @return
	 */
	private void checkDominios() {

		// Prohibido importar dominios de distintos ambitos.
		if (dominio != null && dominioActual != null && dominio.getAmbito() != dominioActual.getAmbito()) {

			this.accion = TypeImportarAccion.ERROR;
			this.estado = TypeImportarEstado.EXISTE;
			this.resultado = TypeImportarResultado.ERROR;
			this.visibleBoton = false;
			this.mismoTipo = false;
			this.setMensaje("importar.error.distintosAmbitos");
			return;
		}

		final boolean permisosEdicion = checkPermisos();

		if (permisosEdicion) {
			checkDominioModoEdicion();
		} else {
			checkDominioModoActualizacion();
		}

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
	private void checkDominioModoEdicion() {

		if (dominioActual == null || (dominio != null && !mismaEstructura())) {

			this.accion = TypeImportarAccion.REEMPLAZAR;
			if (dominioActual == null) {
				this.estado = TypeImportarEstado.NO_EXISTE;
				this.mismoTipo = false;
			} else {
				this.estado = TypeImportarEstado.EXISTE;
				this.mismoTipo = dominio.getTipo() == dominioActual.getTipo();
			}
			this.resultado = TypeImportarResultado.WARNING;
			this.visibleBoton = true;
			this.acciones = Arrays.asList(TypeImportarAccion.REEMPLAZAR);
			setMensaje("importar.ok.soloactualizacion");

		} else {

			this.accion = TypeImportarAccion.REEMPLAZAR;
			this.estado = TypeImportarEstado.NO_EXISTE;
			this.resultado = TypeImportarResultado.WARNING;
			this.visibleBoton = true;
			this.mismoTipo = (dominio != null && dominioActual.getTipo() == dominio.getTipo());
			this.acciones = Arrays.asList(TypeImportarAccion.REEMPLAZAR, TypeImportarAccion.MANTENER);
			setMensaje("importar.ok.actualizacioncompleta");
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
	private void checkDominioModoActualizacion() {
		if (dominioActual != null && mismaEstructura()) {

			this.accion = TypeImportarAccion.REVISADO;
			this.estado = TypeImportarEstado.EXISTE;
			this.resultado = TypeImportarResultado.INFO;
			this.visibleBoton = false;
			this.mismoTipo = false;
			setMensaje("importar.ok.soloactualizacion");

		} else {

			this.accion = TypeImportarAccion.ERROR;
			if (dominioActual == null) {
				this.estado = TypeImportarEstado.NO_EXISTE;
				setMensaje("importar.error.sinpermisos.creacion");
			} else {
				this.estado = TypeImportarEstado.EXISTE;
				setMensaje("importar.error.sinpermisos.actualizacion");
			}
			this.resultado = TypeImportarResultado.ERROR;
			this.visibleBoton = false;
			this.mismoTipo = true;

		}
	}

	/**
	 * Comprueba si es la misma estructura. Tienen que tener los mismos valores de
	 * parámetros y, si es también de tipo FD, también la misma estructura.
	 *
	 * @return
	 */
	private boolean mismaEstructura() {
		if (dominio.getTipo() == TypeDominio.FUENTE_DATOS) {
			return mismosValores(dominio.getParametros(), dominioActual.getParametros());
		} else {
			return mismosValores(dominio.getParametros(), dominioActual.getParametros());
		}
	}

	/**
	 * Metodo que comprueba si tiene permisos el usuario sobre los dominios.
	 *
	 * @return
	 */
	private boolean checkPermisos() {
		boolean tienePermisosEdicion;

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

		return tienePermisosEdicion;
	}

	/**
	 * Comprueba si son el mismo valor.
	 *
	 * @param texto1
	 * @param texto2
	 * @return
	 */
	private boolean mismosValores(final String texto1, final String texto2) {
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
	private boolean mismosValores(final List<Propiedad> propiedades1, final List<Propiedad> propiedades2) {
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the jndi
	 */
	public String getJndi() {
		return jndi;
	}

	/**
	 * @param jndi
	 *            the jndi to set
	 */
	public void setJndi(final String jdni) {
		this.jndi = jdni;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            the sql to set
	 */
	public void setSql(final String sql) {
		this.sql = sql;
	}

	/**
	 * @return the altura
	 */
	public Integer getAltura() {
		return altura;
	}

	/**
	 * @param altura
	 *            the altura to set
	 */
	public void setAltura(final Integer altura) {
		this.altura = altura;
	}

	/**
	 * @return the anchura
	 */
	public Integer getAnchura() {
		return anchura;
	}

	/**
	 * @param anchura
	 *            the anchura to set
	 */
	public void setAnchura(final Integer anchura) {
		this.anchura = anchura;
	}

	/**
	 * @return the resultadoJndi
	 */
	public String getResultadoJndi() {
		return resultadoJndi;
	}

	/**
	 * @param resultadoJndi
	 *            the resultadoJndi to set
	 */
	public void setResultadoJndi(final String resultadoJdni) {
		this.resultadoJndi = resultadoJdni;
	}

	/**
	 * @return the resultadoSQL
	 */
	public String getResultadoSQL() {
		return resultadoSQL;
	}

	/**
	 * @param resultadoSQL
	 *            the resultadoSQL to set
	 */
	public void setResultadoSQL(final String resultadoSQL) {
		this.resultadoSQL = resultadoSQL;
	}

	/**
	 * @return the resultadoURL
	 */
	public String getResultadoURL() {
		return resultadoURL;
	}

	/**
	 * @param resultadoURL
	 *            the resultadoURL to set
	 */
	public void setResultadoURL(final String resultadoURL) {
		this.resultadoURL = resultadoURL;
	}

	/**
	 * @return the resultadoLista
	 */
	public String getResultadoLista() {
		return resultadoLista;
	}

	/**
	 * @param resultadoLista
	 *            the resultadoLista to set
	 */
	public void setResultadoLista(final String resultadoLista) {
		this.resultadoLista = resultadoLista;
	}

	/**
	 * @return the resultadoFD
	 */
	public String getResultadoFD() {
		return resultadoFD;
	}

	/**
	 * @param resultadoFD
	 *            the resultadoFD to set
	 */
	public void setResultadoFD(final String resultadoFD) {
		this.resultadoFD = resultadoFD;
	}

	/**
	 * @return the resultadoDistinto
	 */
	public String getResultadoDistinto() {
		return resultadoDistinto;
	}

	/**
	 * @param resultadoDistinto
	 *            the resultadoDistinto to set
	 */
	public void setResultadoDistinto(final String resultadoDistinto) {
		this.resultadoDistinto = resultadoDistinto;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(final String literal) {
		if (literal != null) {
			this.mensaje = UtilJSF.getLiteral(literal);
		}
	}

}
