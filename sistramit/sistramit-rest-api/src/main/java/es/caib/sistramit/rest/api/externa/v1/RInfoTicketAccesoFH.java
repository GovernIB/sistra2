package es.caib.sistramit.rest.api.externa.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Datos para la obtención de ticket de acceso
 *
 * @author Indra
 *
 */
@ApiModel(value = "RInfoTicketAccesoFH", description = "Datos para la obtención de ticket de acceso para FH")
public class RInfoTicketAccesoFH {

	/** Identificador actuación en FH. Si se informa, tras finalizar el trámite se invocará a componente FH para indicar que se ha realizado registro.*/
	@ApiModelProperty(value = "Identificador actuación en FH", required = false)
	private String idActuacionFH;

	/** Funcionario habilitado. */
	@ApiModelProperty(value = "Funcionario habilitado", required = true)
	private RFuncionarioHabilitadoInfo funcionarioHabilitado;

	/** Interesado. */
	@ApiModelProperty(value = "Interesado", required = true)
	private RPersonaInfo interesado;

	/** Representante (en caso de existir representación). */
	@ApiModelProperty(value = "Representante (en caso de existir representación)", required = false)
	private RPersonaInfo representante;

	/** Trámite a iniciar por el FH. */
	@ApiModelProperty(value = "Trámite a iniciar por el FH", required = true)
	private RTramiteFH tramiteFH;

	public RFuncionarioHabilitadoInfo getFuncionarioHabilitado() {
		return funcionarioHabilitado;
	}

	public void setFuncionarioHabilitado(RFuncionarioHabilitadoInfo funcionarioHabilitado) {
		this.funcionarioHabilitado = funcionarioHabilitado;
	}

	public RPersonaInfo getInteresado() {
		return interesado;
	}

	public void setInteresado(RPersonaInfo interesado) {
		this.interesado = interesado;
	}

	public RPersonaInfo getRepresentante() {
		return representante;
	}

	public void setRepresentante(RPersonaInfo representante) {
		this.representante = representante;
	}

	public RTramiteFH getTramiteFH() {
		return tramiteFH;
	}

	public void setTramiteFH(RTramiteFH tramiteFH) {
		this.tramiteFH = tramiteFH;
	}

	public String getIdActuacionFH() {
		return idActuacionFH;
	}

	public void setIdActuacionFH(String idActuacionFH) {
		this.idActuacionFH = idActuacionFH;
	}
}
