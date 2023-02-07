package es.caib.sistrages.core.api.model;

import es.caib.sistrages.core.api.model.types.TypePaso;

/**
 *
 * Tramite paso.
 *
 * @author Indra
 *
 */

public class TramitePaso extends ModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Id. */
	private Long codigo;

	/** Id de la relacion. **/
	private Long idPasoRelacion;

	/** Trámite version. **/
	private TramiteVersion tramiteVersion;

	/** Tipo del paso de tramitación. **/
	private TypePaso tipo;

	/**
	 * Identificador paso tramitación. Para flujo normalizado será establecido
	 * automáticamente, para flujo personalizado será establecido por desarrollador.
	 */
	private String idPasoTramitacion;

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
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
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
	public TypePaso getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final TypePaso tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the idPasoTramitacion
	 */
	public String getIdPasoTramitacion() {
		return idPasoTramitacion;
	}

	/**
	 * @param idPasoTramitacion
	 *            the idPasoTramitacion to set
	 */
	public void setIdPasoTramitacion(final String codigo) {
		this.idPasoTramitacion = codigo;
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

	/**
	 * @return the idPasoRelacion
	 */
	public Long getIdPasoRelacion() {
		return idPasoRelacion;
	}

	/**
	 * @param idPasoRelacion
	 *            the idPasoRelacion to set
	 */
	public void setIdPasoRelacion(final Long idPasoRelacion) {
		this.idPasoRelacion = idPasoRelacion;
	}

	@Override
	public String toString() {
        return toString("","ca");
	}

	/**
     * Método to string
     * @param tabulacion Indica el texto anterior de la linea para que haya tabulacion.
     * @return El texto
     */
     public String toString(String tabulacion, String idioma) {
           StringBuilder texto = new StringBuilder(tabulacion + "TramitPas. ");
           texto.append(tabulacion +"\t Codi:" + getCodigo() + "\n");
           texto.append(tabulacion +"\t Ordre:" + getOrden() + "\n");
           texto.append(tabulacion +"\t PasFinal:" + isPasoFinal() + "\n");
           texto.append(tabulacion +"\t IdPasoRelacio:" + getIdPasoRelacion() + "\n");
           texto.append(tabulacion +"\t IdPasoTramitacio:" + getIdPasoTramitacion() + "\n");
           if (getDescripcion() != null) {
        	   texto.append(tabulacion +"\t Descripció: \n");
        	   texto.append(getDescripcion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptNavegacion() != null) {
        	   texto.append(tabulacion +"\t ScriptNavegacio: \n");
        	   texto.append(getScriptNavegacion().toString(tabulacion+"\t", idioma)+ "\n");
           }
           if (getScriptVariables() != null) {
        	   texto.append(tabulacion +"\t ScriptVariables: \n");
        	   texto.append(getScriptVariables().toString(tabulacion+"\t", idioma)+ "\n");
           }
           return texto.toString();
     }
}
