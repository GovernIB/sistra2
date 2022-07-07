package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;

/**
 * JPasoDebeSaber
 */
@Entity
@Table(name = "STG_PASDBS")
public class JPasoDebeSaber implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PDB_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PDB_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Instrucciones inicio . **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PDB_INSINI")
	private JLiteral instruccionesInicio;

	/** Script para anexos dinámicos. **/
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PDB_SCRINS")
	private JScript scriptDebeSaber;

	/**
	 * Constructor.
	 */
	public JPasoDebeSaber() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the pasoTramitacion
	 */
	public JPasoTramitacion getPasoTramitacion() {
		return pasoTramitacion;
	}

	/**
	 * @param pasoTramitacion the pasoTramitacion to set
	 */
	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	/**
	 * @return the instruccionesInicio
	 */
	public JLiteral getInstruccionesInicio() {
		return instruccionesInicio;
	}

	/**
	 * @param instruccionesInicio the instruccionesInicio to set
	 */
	public void setInstruccionesInicio(final JLiteral instruccionesInicio) {
		this.instruccionesInicio = instruccionesInicio;
	}

	/**
	 * @return the scriptDebeSaber
	 */
	public JScript getScriptDebeSaber() {
		return scriptDebeSaber;
	}

	/**
	 * @param scriptDebeSaber the scriptDebeSaber to set
	 */
	public void setScriptDebeSaber(JScript scriptDebeSaber) {
		this.scriptDebeSaber = scriptDebeSaber;
	}

	/**
	 * From model.
	 *
	 * @param mpasoDebeSaber
	 * @return
	 */
	public static JPasoDebeSaber fromModel(final TramitePasoDebeSaber mpasoDebeSaber) {
		JPasoDebeSaber jpaso = null;
		if (mpasoDebeSaber != null) {
			jpaso = new JPasoDebeSaber();
			jpaso.setCodigo(mpasoDebeSaber.getIdPasoRelacion());
			jpaso.setScriptDebeSaber(JScript.fromModel(mpasoDebeSaber.getScriptDebeSaber()));
			if (mpasoDebeSaber.getInstruccionesIniciales() != null) {
				jpaso.setInstruccionesInicio(JLiteral.fromModel(mpasoDebeSaber.getInstruccionesIniciales()));
			}
		}
		return jpaso;
	}

	/**
	 * Clonar.
	 *
	 * @param origPasoDebeSaber
	 * @return
	 */
	public static JPasoDebeSaber clonar(final JPasoDebeSaber origPasoDebeSaber,
			final JPasoTramitacion jpasoTramitacion) {
		JPasoDebeSaber jpasoDebeSaber = null;
		if (origPasoDebeSaber != null) {
			jpasoDebeSaber = new JPasoDebeSaber();
			jpasoDebeSaber.setCodigo(null);
			jpasoDebeSaber.setPasoTramitacion(jpasoTramitacion);
			jpasoDebeSaber.setScriptDebeSaber(origPasoDebeSaber.getScriptDebeSaber());
			jpasoDebeSaber.setInstruccionesInicio(JLiteral.clonar(origPasoDebeSaber.getInstruccionesInicio()));
			jpasoDebeSaber.setScriptDebeSaber(JScript.clonar(origPasoDebeSaber.getScriptDebeSaber()));
		}
		return jpasoDebeSaber;
	}

}
