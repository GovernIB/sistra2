package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
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
public class FilaImportarGestor extends FilaImportarBase {

	/** FormateadorFormulario . **/
	private GestorExternoFormularios gestor;

	/** FormateadorFormulario actual. **/
	private GestorExternoFormularios gestorActual;

	/** Configuracion autentiticacion actual **/
	private ConfiguracionAutenticacion configuracionAutenticacionActual;

	/**
	 * Indica si está correcto el formateador. Se utiliza principalmente porque en
	 * Importar Trámite, son todos de tipo info y hay que saber cual se tiene que
	 * quitar.
	 **/
	private boolean correcto;

	/** Constructor básico. **/
	public FilaImportarGestor() {
		super();
	}

	/**
	 * Constructor.
	 */
	public FilaImportarGestor(final GestorExternoFormularios iGestor,
			final GestorExternoFormularios iGestorActual, final TypeImportarAccion iAccion,
			final TypeImportarExiste iExiste, final TypeImportarEstado iEstado, final TypeImportarResultado iTypeAccion,
			final String iMensaje, final boolean correcto, final ConfiguracionAutenticacion config) {
		super();
		this.setGestor(iGestor);
		this.setGestorActual(iGestorActual);
		this.accion = iAccion;
		this.estado = iEstado;
		this.existe = iExiste;
		this.resultado = iTypeAccion;
		this.mensaje = iMensaje;
		this.configuracionAutenticacionActual = config;
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
	public GestorExternoFormularios getGestor() {
		return gestor;
	}

	/**
	 * @return the gestorActual
	 */
	public GestorExternoFormularios getGestorActual() {
		return gestorActual;
	}

	/**
	 * @param gestorActual the gestorActual to set
	 */
	public void setGestorActual(GestorExternoFormularios gestorActual) {
		this.gestorActual = gestorActual;
	}

	/**
	 * @param gestor the gestor to set
	 */
	public void setGestor(GestorExternoFormularios gestor) {
		this.gestor = gestor;
	}

	/**
	 * @return the configuracionAutenticacionActual
	 */
	public ConfiguracionAutenticacion getConfiguracionAutenticacionActual() {
		return configuracionAutenticacionActual;
	}

	/**
	 * @param configuracionAutenticacionActual the configuracionAutenticacionActual to set
	 */
	public void setConfiguracionAutenticacionActual(ConfiguracionAutenticacion configuracionAutenticacionActual) {
		this.configuracionAutenticacionActual = configuracionAutenticacionActual;
	}

	/**
	 * @param correcto the correcto to set
	 */
	public void setCorrecto(final boolean correcto) {
		this.correcto = correcto;
	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * NO existe el formateador o si existe pero está desactivado la
	 * personalizacion.
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarGestor crearITgestor(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, final String mensaje, final ConfiguracionAutenticacion config) {

		TypeImportarExiste existe;
		TypeImportarResultado resultado;
		if (gestorActual == null) {
			existe = TypeImportarExiste.NO_EXISTE;
			resultado = TypeImportarResultado.WARNING;
		} else {
			existe = TypeImportarExiste.EXISTE;
			resultado = TypeImportarResultado.INFO;
		}

		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.NADA, existe,
				TypeImportarEstado.ERROR, resultado , mensaje, false, config);

	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * existe el formateador
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarGestor creaITgestorExisteMalConf(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, String mensaje, final ConfiguracionAutenticacion config) {
		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, mensaje, true, config);
	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * existe el formateador
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarGestor creaITgestorExiste(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, final ConfiguracionAutenticacion config) {
		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, null, true, config);
	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo IT (Importar Tramite) cuando
	 * existe el formateador
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param literal
	 * @return
	 */
	public static FilaImportarGestor creaITgestorExisteSinConfig(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, final String mensaje, final ConfiguracionAutenticacion config) {
		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.REEMPLAZAR,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, mensaje, true, config);
	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo CC (Cuaderno Cargar) cuando
	 * NO existe el formateador o si existe pero está desactivado la
	 * personalizacion.
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarGestor crearCCgestorError(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, final String mensaje, final ConfiguracionAutenticacion config) {

		TypeImportarExiste existe;
		if (gestorActual == null) {
			existe = TypeImportarExiste.NO_EXISTE;
		} else {
			existe = TypeImportarExiste.EXISTE;
		}

		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.NADA, existe,
				TypeImportarEstado.ERROR, TypeImportarResultado.ERROR, mensaje, false, config);

	}

	/**
	 * Crea un elemento FilaImportarFormateador de tipo CC (Cuaderno Cargar) cuando
	 * existe el formateador
	 *
	 * @param gestor
	 * @param gestorActual
	 * @param mensaje
	 * @return
	 */
	public static FilaImportarGestor crearCCformateadorOk(final GestorExternoFormularios gestor,
			final GestorExternoFormularios gestorActual, final ConfiguracionAutenticacion config) {
		return new FilaImportarGestor(gestor, gestorActual, TypeImportarAccion.MANTENER,
				TypeImportarExiste.EXISTE, TypeImportarEstado.REVISADO, TypeImportarResultado.WARNING, null, true, config);
	}

}
