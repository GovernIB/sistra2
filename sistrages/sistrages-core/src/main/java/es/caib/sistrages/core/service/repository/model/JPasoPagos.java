package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoPagos
 */
@Entity
@Table(name = "STG_PASPAG")
public class JPasoPagos implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PPG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PPG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoPagos")
	private Set<JPagoTramite> pagosTramite = new HashSet<JPagoTramite>(0);

	public JPasoPagos() {
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

	public Set<JPagoTramite> getPagosTramite() {
		return this.pagosTramite;
	}

	public void setPagosTramite(final Set<JPagoTramite> pagosTramite) {
		this.pagosTramite = pagosTramite;
	}

}
