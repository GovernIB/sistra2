package es.caib.sistramit.rest.api.interna;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Evento Auditoria.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RErroresPorTramiteCM", description = "Errores por tramite cuadro mando")
public class RErroresPorTramiteCM {

	@ApiModelProperty(value = "Id tramite")
	private String idTramite;

	@ApiModelProperty(value = "Version")
	private Integer version;

	@ApiModelProperty(value = "Sesiones iniciadas")
	private Long sesionesIniciadas;

	@ApiModelProperty(value = "Sesiones finalizadas")
	private Long sesionesFinalizadas;

	@ApiModelProperty(value = "Numero errores")
	private Long numeroErrores;

	@ApiModelProperty(value = "Sesiones inacabadas")
	private Long sesionesInacabadas;

	@ApiModelProperty(value = "Porcentage")
	private Double porcentage;

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
