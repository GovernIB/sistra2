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

	public JScript() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public String getScript() {
		return this.script;
	}

	public void setScript(final String script) {
		this.script = script;
	}

	public static JScript fromModel(final Script model) {
		JScript jModel = null;
		if (model != null) {
			jModel = new JScript();
			jModel.setCodigo(model.getId());
			jModel.setScript(model.getContenido());
		}
		return jModel;
	}

	public Script toModel() {
		final Script mScript = new Script();
		mScript.setId(this.getCodigo());
		mScript.setContenido(this.getScript());
		return mScript;
	}

}
