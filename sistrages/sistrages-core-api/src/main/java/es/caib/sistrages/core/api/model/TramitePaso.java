package es.caib.sistrages.core.api.model;

/**
 *
 * Tramite paso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class TramitePaso extends ModelApi {

	/** Id. */
	private Long id;

	/** Trámite version. **/
	private TramiteVersion tramiteVersion;

	/** Tipo del paso de tramitación. **/
	private TramiteTipo tipo;

	/**
	 * Identificador paso tramitación. Para flujo normalizado será establecido
	 * automáticamente, para flujo personalizado será establecido por desarrollador.
	 */
	private String codigo;

	/**
	 * Descripción del paso de tramitación. En flujo normalizado será establecido
	 * automáticamente.
	 */
	private Literal descripcion;

	/** Orden paso. **/
	private int orden;

	/** Indica que paso es final. */
	private boolean pasoFinal;

	/** Para flujo personalizado permite establecer script navegación. */
	private Script scriptNavegacion;

	/**
	 * Para flujo personalizado permite almacenamiento de variables a usar entre
	 * pasos.
	 */
	private Script scriptVariables;

	/**
	 * Crea una nueva instancia de TramitePaso.
	 */
	public TramitePaso() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the tipo
	 */
	public TramiteTipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TramiteTipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public Literal getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(final Literal descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(final int orden) {
		this.orden = orden;
	}

	/**
	 * @return the pasoFinal
	 */
	public boolean isPasoFinal() {
		return pasoFinal;
	}

	/**
	 * @param pasoFinal
	 *            the pasoFinal to set
	 */
	public void setPasoFinal(final boolean pasoFinal) {
		this.pasoFinal = pasoFinal;
	}

	/**
	 * @return the scriptNavegacion
	 */
	public Script getScriptNavegacion() {
		return scriptNavegacion;
	}

	/**
	 * @param scriptNavegacion
	 *            the scriptNavegacion to set
	 */
	public void setScriptNavegacion(final Script scriptNavegacion) {
		this.scriptNavegacion = scriptNavegacion;
	}

	/**
	 * @return the scriptVariables
	 */
	public Script getScriptVariables() {
		return scriptVariables;
	}

	/**
	 * @param scriptVariables
	 *            the scriptVariables to set
	 */
	public void setScriptVariables(final Script scriptVariables) {
		this.scriptVariables = scriptVariables;
	}

}
