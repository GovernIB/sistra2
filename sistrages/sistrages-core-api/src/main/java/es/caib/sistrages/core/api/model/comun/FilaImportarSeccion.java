package es.caib.sistrages.core.api.model.comun;

import java.util.Arrays;
import java.util.List;

import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeImportarAccion;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.model.types.TypeImportarExiste;
import es.caib.sistrages.core.api.model.types.TypeImportarResultado;

/**
 * Fila gestor formulario externos importar.
 *
 * @author Indra
 *
 */
public class FilaImportarSeccion extends FilaImportarBase {

	/** FormateadorFormulario . **/
	private SeccionReutilizable seccion;

	/** FormateadorFormulario actual. **/
	private SeccionReutilizable seccionActual;

	/** Visible boton **/
	private boolean visibleBoton = false;

	/** Scripts **/
	private List<ScriptSeccionReutilizable> scripts;

	/**
	 * Indica si está correcto el formateador. Se utiliza principalmente porque en
	 * Importar Trámite, son todos de tipo info y hay que saber cual se tiene que
	 * quitar.
	 **/
	private boolean correcto;

	/** Constructor básico. **/
	public FilaImportarSeccion() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarSeccion(final SeccionReutilizable iSeccion,
			final SeccionReutilizable iSeccionActual, final TypeImportarAccion iAccion,
			final TypeImportarExiste iExiste, final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion,
			final String iMensaje, final boolean correcto, final List<TypeImportarAccion> iacciones) {
		super();
		this.setSeccion(iSeccion);
		this.setSeccionActual(iSeccionActual);
		this.accion = iAccion;
		this.acciones = iacciones;
		this.estado = iEstado;
		this.existe = iExiste;
		this.resultado = iTypeAccion;
		this.mensaje = iMensaje;
		this.setCorrecto(correcto);
	}


	/**
	 * @return the correcto
	 */
	public boolean isCorrecto() {
		return correcto;
	}

	/**
	 * @return the gestor
	 */
	public SeccionReutilizable getSeccion() {
		return seccion;
	}

	/**
	 * @return the gestorActual
	 */
	public SeccionReutilizable getSeccionActual() {
		return seccionActual;
	}

	/**
	 * @param gestorActual the gestorActual to set
	 */
	public void setSeccionActual(SeccionReutilizable gestorActual) {
		this.seccionActual = gestorActual;
	}

	/**
	 * @param gestor the gestor to set
	 */
	public void setSeccion(SeccionReutilizable gestor) {
		this.seccion = gestor;
	}

	/**
	 * @return the visibleBoton
	 */
	public boolean isVisibleBoton() {
		return visibleBoton;
	}

	/**
	 * @param visibleBoton the visibleBoton to set
	 */
	public void setVisibleBoton(boolean visibleBoton) {
		this.visibleBoton = visibleBoton;
	}

	/**
	 * @param correcto the correcto to set
	 */
	public void setCorrecto(final boolean correcto) {
		this.correcto = correcto;
	}

	/**
	 * @return the scripts
	 */
	public List<ScriptSeccionReutilizable> getScripts() {
		return scripts;
	}

	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(List<ScriptSeccionReutilizable> scripts) {
		this.scripts = scripts;
	}

	/**
	 * Crea un elemento FilaImportarSeccion de tipo IT (Importar Tramite) cuando
	 * NO existe la seccion.
	 *
	 * @param seccion
	 * @param seccionActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarSeccion crearITseccion(final SeccionReutilizable seccion,
			final SeccionReutilizable seccionActual, final String mensaje) {

		TypeImportarExiste existe = TypeImportarExiste.NO_EXISTE;
		TypeImportarResultado resultado = TypeImportarResultado.ERROR;
		return new FilaImportarSeccion(seccion, seccionActual, TypeImportarAccion.NADA, existe,
				TypeImportarEstado.REVISADO, resultado , mensaje, false, Arrays.asList(TypeImportarAccion.NADA));

	}

	/**
	 * Crea un elemento FilaImportarSeccion de tipo IT (Importar Tramite) cuando
	 * existe la seccion y es más vieja.
	 *
	 * @param seccion
	 * @param seccionActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarSeccion crearITseccionExiste(final SeccionReutilizable seccion,
			final SeccionReutilizable seccionActual, final String mensaje) {

		TypeImportarExiste existe = TypeImportarExiste.EXISTE;
		TypeImportarResultado resultado = TypeImportarResultado.OK;
		return new FilaImportarSeccion(seccion, seccionActual, TypeImportarAccion.MANTENER, existe,
				TypeImportarEstado.REVISADO, resultado , mensaje, false, Arrays.asList(TypeImportarAccion.MANTENER/*, TypeImportarAccion.REEMPLAZAR*/));

	}

	/**
	 * Crea un elemento de FilaImportarSeccion de tipo IS (Importar seccion) cuando no existe la seccion.
	 * @param data
	 * @param seccionActual
	 * @param idEntidad
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarSeccion crearISnoexiste(SeccionReutilizable data, SeccionReutilizable seccionActual,
			Long idEntidad, final String mensaje) {
		return new FilaImportarSeccion(data, seccionActual, TypeImportarAccion.CREAR, TypeImportarExiste.NO_EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.OK , mensaje, false, Arrays.asList(TypeImportarAccion.CREAR));
	}

	/**
	 * Crea un elemento de FilaImportarSeccion de tipo IS (Importar seccion) cuando existe la seccion.
	 * @param data
	 * @param seccionActual
	 * @param idEntidad
	 * @param mensaje
	 * @param correcto
	 * @return
	 */
	public static FilaImportarSeccion crearISexiste(SeccionReutilizable data, SeccionReutilizable seccionActual, Long idEntidad, String mensaje, boolean correcto) {
		//Este correcto o no, se hará lo mismo
		//Se podía utilizar el correcto para pasar a resultado a info a warning pero vamos a dejar lo mismo.
		return new FilaImportarSeccion(data, seccionActual, TypeImportarAccion.REEMPLAZAR, TypeImportarExiste.EXISTE,
				TypeImportarEstado.REVISADO, TypeImportarResultado.OK , mensaje, false, Arrays.asList(TypeImportarAccion.MANTENER, TypeImportarAccion.REEMPLAZAR));
	}




}
