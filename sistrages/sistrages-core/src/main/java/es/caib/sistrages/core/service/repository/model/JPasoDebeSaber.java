package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoDebeSaber
 */
@Entity
@Table(name = "STG_PASDBS")
public class JPasoDebeSaber implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PDB_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PDB_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PDB_INSINI")
	private JLiterales instruccionesInicio;

	public JPasoDebeSaber() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public JLiterales getInstruccionesInicio() {
		return this.instruccionesInicio;
	}

	public void setInstruccionesInicio(final JLiterales instruccionesInicio) {
		this.instruccionesInicio = instruccionesInicio;
	}

}
