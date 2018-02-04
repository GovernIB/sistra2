package es.caib.sistra2.stg.rest;

import java.io.Serializable;

public class Status implements Serializable {

	private String statusCode;

	private String message;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(final String status) {
		this.statusCode = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
