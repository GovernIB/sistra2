package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.ScriptSeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeScriptSeccionReutilizable;

/**
 * JScript Seccion Reutilizable
 */
@Entity
@Table(name = "STG_SCRSRU")
public class JScriptSeccionReutilizable implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_SCRSRU_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_SCRSRU_SEQ", sequenceName = "STG_SCRSRU_SEQ")
	@Column(name = "SSR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SSR_CODSCR")
	private JScript script;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SSR_CODSRU", nullable = true)
	private JSeccionReutilizable seccionReutilizable;

	@Column(name = "SSR_TIPSCR")
	private String tipoScript;

	/** Constructor. **/
	public JScriptSeccionReutilizable() {
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
	 * @return the script
	 */
	public JScript getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(JScript script) {
		this.script = script;
	}

	/**
	 * @return the seccionReutilizable
	 */
	public JSeccionReutilizable getSeccionReutilizable() {
		return seccionReutilizable;
	}

	/**
	 * @param seccionReutilizable the seccionReutilizable to set
	 */
	public void setSeccionReutilizable(JSeccionReutilizable seccionReutilizable) {
		this.seccionReutilizable = seccionReutilizable;
	}

	/**
	 * @return the tipoScript
	 */
	public String getTipoScript() {
		return tipoScript;
	}

	/**
	 * @param tipoScript the tipoScript to set
	 */
	public void setTipoScript(String tipoScript) {
		this.tipoScript = tipoScript;
	}

	/**
	 * From model.
	 *
	 * @param mScript
	 * @return
	 */
	public static JScriptSeccionReutilizable fromModel(final ScriptSeccionReutilizable mScript) {
		JScriptSeccionReutilizable jScript = null;
		if (mScript != null) {
			jScript = new JScriptSeccionReutilizable();
			jScript.setCodigo(mScript.getCodigo());
			jScript.setScript(JScript.fromModel(mScript.getScript()));
			jScript.setSeccionReutilizable(JSeccionReutilizable.fromModel(mScript.getSeccionReutilizable()));
			jScript.setTipoScript(mScript.getTipoScript().toString());
		}
		return jScript;
	}

	/**
	 * To model.
	 *
	 * @return
	 */
	public ScriptSeccionReutilizable toModel() {
		final ScriptSeccionReutilizable mScript = new ScriptSeccionReutilizable();
		mScript.setCodigo(this.getCodigo());
		mScript.setScript(this.getScript().toModel());
		// mScript.setSeccionReutilizable(this.getSeccionReutilizable().toModel());
		mScript.setIdSeccionReutilizable(this.getSeccionReutilizable().getCodigo());
		mScript.setTipoScript(TypeScriptSeccionReutilizable.fromString(this.getTipoScript()));
		return mScript;
	}

}
