package es.caib.sistrages.core.api.model.comun;

import java.util.Arrays;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila dominio importar.
 *
 * @author Indra
 *
 */
public class FilaImportarDominio extends FilaImportarBase {

	/** Dominio. **/
	private Dominio dominio;

	/** Dominio. **/
	private Dominio dominioActual;

	/** Indica si es visibleBoton o no el boton. **/
	private boolean visibleBoton;

	/** Indica si el dominio y el dominio actual son del mismo tipo. */
	private boolean mismoTipo;

	/** Altura. **/
	private Integer altura = 330;

	/** Altura. **/
	private Integer anchura = 850;

	/** Resultado jdni. */
	private String resultadoJndi;

	/** Resultado SQL . */
	private String resultadoSQL;

	/** Resultado SQL decoded. */
	private String resultadoSQLdecoded;

	/** Resultado URL. */
	private String resultadoURL;

	/** Resultado Lista. **/
	private String resultadoLista;

	/** Resultado Fuente de datos. **/
	private String resultadoFD;

	/** Resultado distinto. **/
	private String resultadoDistinto;

	/** Fuente datos. **/
	private FuenteDatos fuenteDatos;

	/** Fuente datos actual. **/
	private FuenteDatos fuenteDatosActual;

	/** Fuente Datos content. */
	private byte[] fuenteDatosContent;

	/** Permite editar. **/
	// private boolean permisosEdicion;

	/** En caso de dominio de tipo area, el idArea. **/
	private Long idArea;

	/** En caso de dominio de tipo entidad, el idEntidad. **/
	private Long idEntidad;

	/**
	 * Constructor basico.
	 */
	public FilaImportarDominio() {
		super();
	}

	/**
	 * Constructor básico.
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param mensaje
	 */
	public FilaImportarDominio(final Dominio dominio, final Dominio dominioActual, final FuenteDatos fd,
			final byte[] fdContent, final FuenteDatos fdActual, final String mensaje) {
		this.dominio = dominio;
		this.dominioActual = dominioActual;
		this.fuenteDatos = fd;
		this.fuenteDatosContent = fdContent;
		this.fuenteDatosActual = fdActual;
		this.mensaje = mensaje;
	}

	/**
	 * Constructor básico.
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 */
	public FilaImportarDominio(final Dominio dominio, final Dominio dominioActual, final FuenteDatos fd,
			final byte[] fdContent, final FuenteDatos fdActual) {
		this.dominio = dominio;
		this.dominioActual = dominioActual;
		this.fuenteDatos = fd;
		this.fuenteDatosContent = fdContent;
		this.fuenteDatosActual = fdActual;
		this.mensaje = null;
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
	 * @return the fuenteDatos
	 */
	public FuenteDatos getFuenteDatos() {
		return fuenteDatos;
	}

	/**
	 * @param fuenteDatos
	 *            the fuenteDatos to set
	 */
	public void setFuenteDatos(final FuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	/**
	 * @return the fuenteDatosContent
	 */
	public byte[] getFuenteDatosContent() {
		return fuenteDatosContent;
	}

	/**
	 * @param fuenteDatosContent
	 *            the fuenteDatosContent to set
	 */
	public void setFuenteDatosContent(final byte[] fuenteDatosContent) {
		this.fuenteDatosContent = fuenteDatosContent;
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
	 * @return the fuenteDatosActual
	 */
	public FuenteDatos getFuenteDatosActual() {
		return fuenteDatosActual;
	}

	/**
	 * @param fuenteDatosActual
	 *            the fuenteDatosActual to set
	 */
	public void setFuenteDatosActual(final FuenteDatos fuenteDatosActual) {
		this.fuenteDatosActual = fuenteDatosActual;
	}

	/**
	 * @return the idArea
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the idEntidad
	 */
	public Long getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad
	 *            the idEntidad to set
	 */
	public void setIdEntidad(final Long idEntidad) {
		this.idEntidad = idEntidad;
	}

	/**
	 * @return the resultadoSQLdecoded
	 */
	public String getResultadoSQLdecoded() {
		return resultadoSQLdecoded;
	}

	/**
	 * @param resultadoSQLdecoded
	 *            the resultadoSQLdecoded to set
	 */
	public void setResultadoSQLdecoded(final String resultadoSQLdecoded) {
		this.resultadoSQLdecoded = resultadoSQLdecoded;
	}

	/**
	 * Crea un elemento FilaImportarDominio de tipo IT (Importar Tramite) cuando
	 * tiene un error de tipo:
	 * <ul>
	 * <li>Ambos dominios no tienen el mismo ámbito</li>
	 * <li>Tienen el mismo ámbito de tipo área pero no es la misma.</li>
	 * <li>El ámbito es de tipo área pero no pertenece a la misma área seleccionada.
	 * </li>
	 * </ul>
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarDominio crearITerrorAmbitoAreas(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final String literal) {
		final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
				literal);
		fila.setAccion(TypeImportarAccion.NADA);
		fila.setExiste(TypeImportarExiste.EXISTE);
		fila.setEstado(TypeImportarEstado.ERROR);
		fila.setResultado(TypeImportarResultado.INFO);
		fila.setVisibleBoton(false);
		fila.setMismoTipo(false);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarDominio de tipo IT (Importar Tramite) que sólo
	 * permite reemplazar. Esto pasa cuando:
	 * <ul>
	 * <li>No existe el dominio en BBDD pero se tiene permisos para crearlo</li>
	 * <li>Existe pero cambia la estructura y tenemos permisos para actualizarla.
	 * </li>
	 * </ul>
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarDominio crearITsoloReemplazar(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final String literal) {
		final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
				literal);
		fila.setAccion(TypeImportarAccion.REEMPLAZAR);
		if (fila.getDominioActual() == null) {
			fila.setExiste(TypeImportarExiste.NO_EXISTE);
			fila.setMismoTipo(false);
		} else {
			fila.setExiste(TypeImportarExiste.EXISTE);
			fila.setMismoTipo(fila.getDominio().getTipo() == fila.getDominioActual().getTipo());
		}
		fila.setEstado(TypeImportarEstado.PENDIENTE);
		fila.setResultado(TypeImportarResultado.WARNING);
		fila.setVisibleBoton(true);
		fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR));

		return fila;
	}

	/**
	 * Crea un elemento FilaImportarDominio de tipo IT (Importar Tramite) cuando se
	 * tiene permisos para modificar/crear el dominio (el cual, tiene la misma
	 * estructura que el existe en BBDD). <br />
	 * <br />
	 * Si el dominio existe, podrás reemplazar y mantener, si NO existe sólo podrás
	 * reemplazar.
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarDominio crearITconPermisos(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final String literal) {

		final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
				literal);
		fila.setAccion(TypeImportarAccion.REEMPLAZAR);
		if (fila.getDominioActual() == null) {
			fila.setExiste(TypeImportarExiste.NO_EXISTE);
			fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR));
		} else {
			fila.setExiste(TypeImportarExiste.EXISTE);
			fila.setAcciones(Arrays.asList(TypeImportarAccion.REEMPLAZAR, TypeImportarAccion.MANTENER));
		}
		fila.setEstado(TypeImportarEstado.PENDIENTE);
		fila.setResultado(TypeImportarResultado.WARNING);
		fila.setVisibleBoton(true);
		fila.setMismoTipo((fila.getDominio() != null && fila.getDominioActual() != null
				&& fila.getDominioActual().getTipo() == fila.getDominio().getTipo()));

		return fila;
	}

	/**
	 * Crea un elemento FilaImportarDominio de tipo IT (Importar Tramite) cuando no
	 * se tiene permisos de creación/modificación y sólo se puede mantener el que ya
	 * existe.
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param areaSeleccionada
	 * @param literal
	 * @return
	 */
	public static FilaImportarDominio crearITsoloMantener(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final String literal) {
		final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
				literal);
		fila.setAccion(TypeImportarAccion.NADA);
		fila.setEstado(TypeImportarEstado.REVISADO);
		fila.setExiste(TypeImportarExiste.EXISTE);
		fila.setResultado(TypeImportarResultado.OK);
		fila.setVisibleBoton(false);
		fila.setMismoTipo(true);
		return fila;
	}

	/**
	 * Crea un elemento FilaImportarDominio de tipo IT (Importar Tramite) cuando no
	 * se tiene permisos creación/modificación y da INFO porque hay que crearlo (no
	 * existe el dominio) o ha cambiado la estructura y hay que cambiarlo.
	 *
	 * @param dominio
	 * @param dominioActual
	 * @param fd
	 * @param fdContent
	 * @param fdActual
	 * @param areaSeleccionada
	 * @param literal
	 * @return
	 */
	public static FilaImportarDominio crearITerrorSoloMantener(final Dominio dominio, final Dominio dominioActual,
			final FuenteDatos fd, final byte[] fdContent, final FuenteDatos fdActual, final String literal) {
		final FilaImportarDominio fila = new FilaImportarDominio(dominio, dominioActual, fd, fdContent, fdActual,
				literal);
		fila.setEstado(TypeImportarEstado.ERROR);
		fila.setAccion(TypeImportarAccion.NADA);
		if (fila.getDominioActual() == null) {
			fila.setExiste(TypeImportarExiste.NO_EXISTE);
			fila.setMensaje("importar.error.sinpermisos.creacion");
		} else {
			fila.setExiste(TypeImportarExiste.EXISTE);
			fila.setMensaje("importar.error.sinpermisos.actualizacion");
		}
		fila.setResultado(TypeImportarResultado.INFO);
		fila.setVisibleBoton(false);
		fila.setMismoTipo(true);
		return fila;
	}

}
