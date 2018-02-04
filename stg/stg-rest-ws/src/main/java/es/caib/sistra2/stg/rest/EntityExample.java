package es.caib.sistra2.stg.rest;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class EntityExample implements Serializable {

	private String dato1;

	private String dato2;

	public String getDato1() {
		return dato1;
	}

	public void setDato1(final String dato1) {
		this.dato1 = dato1;
	}

	public String getDato2() {
		return dato2;
	}

	public void setDato2(final String dato2) {
		this.dato2 = dato2;
	}

	public static EntityExample valueOf(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<EntityExample> typeRef = new TypeReference<EntityExample>() {
		};
		EntityExample obj;
		try {
			obj = objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			// TODO PENDIENTE
			throw new RuntimeException(e);
		}
		return obj;
	}

	public String toJson() {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			// TODO PENDIENTE
			throw new RuntimeException(e);
		}
	}

}
