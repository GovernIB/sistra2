package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Script;

/**
 * JScript
 */
@Entity
@Table(name = "STG_SCRIPT")
public class JScript implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_SCRIPT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_SCRIPT_SEQ", sequenceName = "STG_SCRIPT_SEQ")
	@Column(name = "SCR_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@Lob
	@Column(name = "SCR_SCRIPT")
	private String script;

	/** Constructor. **/
	public JScript() {
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
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	public void setScript(final String script) {
		this.script = script;
	}

	/**
	 * From model.
	 *
	 * @param mScript
	 * @return
	 */
	public static JScript fromModel(final Script mScript) {
		JScript jScript = null;
		if (mScript != null) {
			jScript = new JScript();
			jScript.setCodigo(mScript.getId());
			jScript.setScript(mScript.getContenido());
		}
		return jScript;
	}

	/**
	 * To model.
	 *
	 * @return
	 */
	public Script toModel() {
		final Script mScript = new Script();
		mScript.setId(this.getCodigo());
		mScript.setContenido(this.getScript());
		return mScript;
	}

	/**
	 * Clona el script
	 *
	 * @param origScript
	 * @return
	 */
	public static JScript clonar(final JScript origScript) {
		JScript jscript = null;
		if (origScript != null) {
			jscript = new JScript();
			jscript.setCodigo(null);
			jscript.setScript(origScript.getScript());
		}
		return jscript;
	}

}
