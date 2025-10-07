package es.caib.sistramit.core.api.model.system.rest.externo;

import es.caib.sistramit.core.api.model.flujo.PersonaDesglosado;

import java.io.Serializable;

/**
 * Información ticket acceso FH.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class InfoAccesoFH implements Serializable {

	/** Identificador actuación en FH. Si se informa, tras finalizar el trámite se invocará a componente FH para indicar que se ha realizado registro.*/
	private String idActuacionFH;

	/** Interesado. */
	private PersonaDesglosado interesado;

	/** Representante (en caso de existir representación). */
	private PersonaDesglosado representante;

	/** Trámite a iniciar por FH. */
	private InfoTramiteFH tramiteFH;

	/** DIR3 del FH. */
	private String dir3FH;

	public PersonaDesglosado getInteresado() {
		return interesado;
	}

	public void setInteresado(PersonaDesglosado interesado) {
		this.interesado = interesado;
	}

	public PersonaDesglosado getRepresentante() {
		return representante;
	}

	public void setRepresentante(PersonaDesglosado representante) {
		this.representante = representante;
	}

	public InfoTramiteFH getTramiteFH() {
		return tramiteFH;
	}

	public void setTramiteFH(InfoTramiteFH tramiteFH) {
		this.tramiteFH = tramiteFH;
	}

	public String getDir3FH() {
		return dir3FH;
	}

	public void setDir3FH(String dir3FH) {
		this.dir3FH = dir3FH;
	}

	public String getIdActuacionFH() {
		return idActuacionFH;
	}

	public void setIdActuacionFH(String idActuacionFH) {
		this.idActuacionFH = idActuacionFH;
	}
}
