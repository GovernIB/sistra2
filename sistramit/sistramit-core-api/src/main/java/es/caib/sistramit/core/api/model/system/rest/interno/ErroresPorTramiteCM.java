package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

/**
 * La clase ErroresPorTramiteCM.
 */
@SuppressWarnings("serial")
public final class ErroresPorTramiteCM implements Serializable {
	private String idTramite;

	private Integer version;

	private Long sesionesIniciadas;

	private Long sesionesFinalizadas;

	private Long numeroErrores;

	private Long sesionesInacabadas;

	private Double porcentage;

	public ErroresPorTramiteCM(String idTramite, Integer version, Long sesionesIniciadas, Long sesionesKo,
			Long sesionesFinalizadas, Long numeroErrores, Long sesionesInacabadas, Double porcentage) {
		super();
		this.idTramite = idTramite;
		this.version = version;
		this.sesionesIniciadas = sesionesIniciadas;
		this.sesionesFinalizadas = sesionesFinalizadas;
		this.numeroErrores = numeroErrores;
		this.sesionesInacabadas = sesionesInacabadas;
		this.porcentage = porcentage;
	}

	public ErroresPorTramiteCM(String idTramite, Integer version) {
		super();
		this.idTramite = idTramite;
		this.version = version;
		this.sesionesIniciadas = null;
		this.sesionesFinalizadas = null;
		this.numeroErrores = null;
	}

	public ErroresPorTramiteCM() {
		super();
	}

	/**
	 * @return the idTramite
	 */
	public final String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public final void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the version
	 */
	public final Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public final void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the sesionesIniciadas
	 */
	public final Long getSesionesIniciadas() {
		return sesionesIniciadas;
	}

	/**
	 * @param sesionesIniciadas the sesionesIniciadas to set
	 */
	public final void setSesionesIniciadas(Long sesionesIniciadas) {
		this.sesionesIniciadas = sesionesIniciadas;
	}

	/**
	 * @return the sesionesFinalizadas
	 */
	public final Long getSesionesFinalizadas() {
		return sesionesFinalizadas;
	}

	/**
	 * @param sesionesFinalizadas the sesionesFinalizadas to set
	 */
	public final void setSesionesFinalizadas(Long sesionesFinalizadas) {
		this.sesionesFinalizadas = sesionesFinalizadas;
	}

	/**
	 * @return the numeroErrores
	 */
	public final Long getNumeroErrores() {
		return numeroErrores;
	}

	/**
	 * @param numeroErrores the numeroErrores to set
	 */
	public final void setNumeroErrores(Long numeroErrores) {
		this.numeroErrores = numeroErrores;
	}

	/**
	 * @return the sesionesInacabadas
	 */
	public final Long getSesionesInacabadas() {
		return sesionesInacabadas;
	}

	/**
	 * @param sesionesInacabadas the sesionesInacabadas to set
	 */
	public final void setSesionesInacabadas(Long sesionesInacabadas) {
		this.sesionesInacabadas = sesionesInacabadas;
	}

	/**
	 * @return the porcentage
	 */
	public final Double getPorcentage() {
		return porcentage;
	}

	/**
	 * @param porcentage the porcentage to set
	 */
	public final void setPorcentage(Double porcentage) {
		this.porcentage = porcentage;
	}

}
