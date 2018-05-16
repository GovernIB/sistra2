package es.caib.sistrages.core.service.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePasoTasa;

/**
 * JPasoPagos
 */
@Entity
@Table(name = "STG_PASPAG")
public class JPasoPagos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PPG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PPG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoPagos", cascade = { CascadeType.ALL })
	private Set<JPagoTramite> pagosTramite = new HashSet<>(0);

	public JPasoPagos() {
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

	public Set<JPagoTramite> getPagosTramite() {
		return this.pagosTramite;
	}

	public void setPagosTramite(final Set<JPagoTramite> pagosTramite) {
		this.pagosTramite = pagosTramite;
	}

	public static JPasoPagos fromModel(final TramitePasoTasa paso) {
		JPasoPagos jpaso = null;
		if (paso != null) {
			jpaso = new JPasoPagos();
			jpaso.setCodigo(paso.getCodigo());
			if (paso.getTasas() != null) {
				final Set<JPagoTramite> pagos = new HashSet<>(0);
				for (final Tasa tasa : paso.getTasas()) {
					final JPagoTramite pago = JPagoTramite.fromModel(tasa);
					pago.setPasoPagos(jpaso);
					pagos.add(pago);
				}
				jpaso.setPagosTramite(pagos);
			}
		}

		return jpaso;
	}

}
