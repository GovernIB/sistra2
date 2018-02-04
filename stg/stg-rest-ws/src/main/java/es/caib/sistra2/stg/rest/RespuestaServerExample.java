package es.caib.sistra2.stg.rest;

import java.io.Serializable;
import java.util.List;

public class RespuestaServerExample implements Serializable {

	private Status status;

	private List<EntityExample> result;

	public Status getStatus() {
		return status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public List<EntityExample> getResult() {
		return result;
	}

	public void setResult(final List<EntityExample> result) {
		this.result = result;
	}

}
