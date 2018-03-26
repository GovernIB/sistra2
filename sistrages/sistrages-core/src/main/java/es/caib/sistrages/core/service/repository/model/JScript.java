package es.caib.sistrages.core.service.repository.model;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

	@Column(name = "SCR_SCRIPT")
	private Clob script;

	public JScript() {
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public Clob getScript() {
		return this.script;
	}

	public void setScript(final Clob script) {
		this.script = script;
	}

}
