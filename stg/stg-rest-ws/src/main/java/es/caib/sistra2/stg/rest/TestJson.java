package es.caib.sistra2.stg.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestJson {

	public static void main(final String[] args) {

		final Status status = new Status();
		status.setStatusCode("1");
		status.setMessage("m");
		final List<EntityExample> result = new ArrayList<EntityExample>();
		final EntityExample e = new EntityExample();
		e.setDato1("dato1");
		e.setDato2("dato2");
		final EntityExample e2 = new EntityExample();
		e2.setDato1("datoX");
		e2.setDato2("datoY");
		result.add(e);
		result.add(e2);

		final RespuestaServerExample r = new RespuestaServerExample();
		r.setStatus(status);
		r.setResult(result);

		final String json = toJson(r);
		System.out.println(json);

		final RespuestaServerExample r2 = fromJson(json);

	}

	public static RespuestaServerExample fromJson(final String json) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final TypeReference<RespuestaServerExample> typeRef = new TypeReference<RespuestaServerExample>() {
		};
		RespuestaServerExample obj;
		try {
			obj = objectMapper.readValue(json, typeRef);
		} catch (final IOException e) {
			// TODO PENDIENTE
			throw new RuntimeException(e);
		}
		return obj;
	}

	public static String toJson(final Object o) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
			return objectMapper.writeValueAsString(o);
		} catch (final JsonProcessingException e) {
			// TODO PENDIENTE
			throw new RuntimeException(e);
		}
	}

}
