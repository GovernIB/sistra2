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

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PDB_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PDB_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "PDB_INSINI")
	private JLiteral instruccionesInicio;

	public JPasoDebeSaber() {
		// Constructor vacio.
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public JLiteral getInstruccionesInicio() {
		return this.instruccionesInicio;
	}

	public void setInstruccionesInicio(final JLiteral instruccionesInicio) {
		this.instruccionesInicio = instruccionesInicio;
	}

	public static JPasoDebeSaber fromModel(final TramitePasoDebeSaber mpasoDebeSaber) {
		JPasoDebeSaber jpaso = null;
		if (mpasoDebeSaber != null) {
			jpaso = new JPasoDebeSaber();
			jpaso.setCodigo(mpasoDebeSaber.getIdPasoRelacion());
			if (mpasoDebeSaber.getInstruccionesIniciales() != null) {
				jpaso.setInstruccionesInicio(JLiteral.fromModel(mpasoDebeSaber.getInstruccionesIniciales()));
			}
		}
		return jpaso;
	}

}
