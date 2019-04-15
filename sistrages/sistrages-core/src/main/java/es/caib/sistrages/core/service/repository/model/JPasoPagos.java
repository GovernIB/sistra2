package es.caib.sistrages.core.service.repository.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePasoTasa;

/**
 * JPasoPagos
 */
@Entity
@Table(name = "STG_PASPAG")
public class JPasoPagos implements IModelApi {

	/** Serial Version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PPG_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PPG_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Pagos. **/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pasoPagos", cascade = { CascadeType.ALL })
	@OrderBy("orden ASC")
	private Set<JPagoTramite> pagosTramite = new HashSet<>(0);

	/** Indica si se habilita subsanación. **/
	@Column(name = "PPG_SUBSAN", nullable = false, precision = 1, scale = 0)
	private boolean permiteSubsanar;

	/** Constructor. **/
	public JPasoPagos() {
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
	 * @return the permiteSubsanar
	 */
	public final boolean isPermiteSubsanar() {
		return permiteSubsanar;
	}

	/**
	 * @param permiteSubsanar
	 *            the permiteSubsanar to set
	 */
	public final void setPermiteSubsanar(final boolean permiteSubsanar) {
		this.permiteSubsanar = permiteSubsanar;
	}

	/**
	 * @return the pasoTramitacion
	 */
	public JPasoTramitacion getPasoTramitacion() {
		return pasoTramitacion;
	}

	/**
	 * @param pasoTramitacion
	 *            the pasoTramitacion to set
	 */
	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	/**
	 * @return the pagosTramite
	 */
	public Set<JPagoTramite> getPagosTramite() {
		return pagosTramite;
	}

	/**
	 * @param pagosTramite
	 *            the pagosTramite to set
	 */
	public void setPagosTramite(final Set<JPagoTramite> pagosTramite) {
		this.pagosTramite = pagosTramite;
	}

	public static JPasoPagos fromModel(final TramitePasoTasa paso) {
		JPasoPagos jpaso = null;
		if (paso != null) {
			jpaso = new JPasoPagos();
			jpaso.setCodigo(paso.getCodigo());
			jpaso.setPermiteSubsanar(paso.isPermiteSubsanar());
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

	/**
	 * Clonar.
	 *
	 * @param origPasoPagos
	 * @return
	 */
	public static JPasoPagos clonar(final JPasoPagos origPasoPagos, final JPasoTramitacion jpasoTramitacion) {
		JPasoPagos jpasoPagos = null;
		if (origPasoPagos != null) {
			jpasoPagos = new JPasoPagos();
			jpasoPagos.setCodigo(null);
			jpasoPagos.setPasoTramitacion(jpasoTramitacion);
			jpasoPagos.setPermiteSubsanar(origPasoPagos.isPermiteSubsanar());

			if (origPasoPagos.getPagosTramite() != null) {
				int ordenPago = 1;
				jpasoPagos.setPagosTramite(new HashSet<JPagoTramite>());
				final List<JPagoTramite> pagos = new ArrayList<>(origPasoPagos.getPagosTramite());
				Collections.sort(pagos, new Comparator<JPagoTramite>() {
					@Override
					public int compare(final JPagoTramite p1, final JPagoTramite p2) {
						return Integer.compare(p1.getOrden(), p2.getOrden());
					}

				});
				for (final JPagoTramite origPagoTramite : pagos) {
					final JPagoTramite jpago = JPagoTramite.clonar(origPagoTramite, jpasoPagos);
					jpago.setOrden(ordenPago);
					jpasoPagos.getPagosTramite().add(jpago);
					ordenPago++;
				}
			}
		}
		return jpasoPagos;
	}

}
