package es.caib.sistramit.core.service.repository.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapeo tabla STT_SESION.
 */

@Entity
@Table(name = "STT_SESION")
@SuppressWarnings("serial")
public final class HSesionTramitacion implements IModelApi {

	/** Atributo codigo. */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_SESION_SEQ")
	@SequenceGenerator(name = "STT_SESION_SEQ", sequenceName = "STT_SESION_SEQ", allocationSize = ConstantesNumero.N1)
	@Column(name = "SES_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N20, scale = 0)
	private Long codigo;

	/** Atributo id sesion tramitacion. */
	@Column(name = "SES_IDESTR", nullable = false)
	private String idSesionTramitacion;

	/** Atributo fecha sesion tramitacion. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SES_FECHA", nullable = false)
	private Date fecha;

	/**
	 * Eventos asociados.
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sesionTramitacion", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("codigo ASC")
	private Set<HEventoAuditoria> errores = new HashSet<>(0); // TODO cambiar a eventos

	/**
	 * Método de acceso a codigo.
	 *
	 * @return codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método para establecer codigo.
	 *
	 * @param pCodigo
	 *            codigo a establecer
	 */
	public void setCodigo(final Long pCodigo) {
		codigo = pCodigo;
	}

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 *
	 * @param pIdSesionTramitacion
	 *            idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param pFecha
	 *            fecha a establecer
	 */
	public void setFecha(final Date pFecha) {
		fecha = pFecha;
	}

	/**
	 * Método de acceso a errores.
	 *
	 * @return errores
	 */
	public Set<HEventoAuditoria> getErrores() {
		return errores;
	}

	/**
	 * Añade log interno.
	 *
	 * @param error
	 *            log interno
	 */
	public void addError(final HEventoAuditoria error) {
		error.setSesionTramitacion(this);
		if (!getErrores().contains(error)) {
			getErrores().add(error);
		}
	}

	/**
	 * Borra log interno.
	 *
	 * @param error
	 *            log interno
	 */
	public void removeError(final HEventoAuditoria error) {
		if (!getErrores().contains(error)) {
			getErrores().remove(error);
		}
		error.setSesionTramitacion(null);
	}

	public void setErrores(Set<HEventoAuditoria> errores) {
		this.errores = errores;
	}

}
