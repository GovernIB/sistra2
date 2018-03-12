package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JLiterales
 */
@Entity
@Table(name = "STG_TRADUC")
public class JLiterales implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_TRADUC_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_TRADUC_SEQ", sequenceName = "STG_TRADUC_SEQ")
	@Column(name = "TRA_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	public JLiterales() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

}
