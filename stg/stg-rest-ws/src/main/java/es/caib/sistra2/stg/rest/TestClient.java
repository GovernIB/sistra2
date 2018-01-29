package es.caib.sistra2.stg.rest;

import java.util.Base64;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.uri.UriComponent;

public class TestClient {

	public static void main(final String[] args) {

		final EntityExample e = new EntityExample();
		e.setDato1("valor 1");
		e.setDato2("valor 2");

		final EntityExample e2 = new EntityExample();
		e2.setDato1("valor x");
		e2.setDato2("valor y");

		final String json = e.toJson();
		final String json2 = e2.toJson();

		Response response = null;

		final String username = "superadmin";
		final String password = "1234";

		final String usernameAndPassword = username + ":" + password;
		final String authorizationHeaderName = "Authorization";
		final String authorizationHeaderValue = "Basic "
				+ Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());

		// Pasar Json como parametro
		// TODO Accept defines a list of acceptable response formats
		response = ClientBuilder.newClient().target("http://localhost:8080/sistra2back/stg-rest/rest/ejemploRest")
				.path("recuperaDato4")
				.queryParam("entity", UriComponent.encode(json, UriComponent.Type.QUERY_PARAM_SPACE_ENCODED))
				.queryParam("entity2", UriComponent.encode(json2, UriComponent.Type.QUERY_PARAM_SPACE_ENCODED))
				.request().header(authorizationHeaderName, authorizationHeaderValue).get();

		System.out.println("Status: " + response.getStatus());
		final EntityExample res1 = response.readEntity(EntityExample.class);
		System.out.println(res1.getDato1() + " - " + res1.getDato2());

		// Pasar Json como parametro
		// {"dato1":"x","dato2":"y"}
		final String jsonStr = "{\"dato1\":\"x\",\"dato2\":\"y\"}";
		response = ClientBuilder.newClient().target("http://localhost:8080/sistra2back/stg-rest/rest/ejemploRest")
				.path("recuperaDato4")
				.queryParam("entity", UriComponent.encode(jsonStr, UriComponent.Type.QUERY_PARAM_SPACE_ENCODED))
				.request().header(authorizationHeaderName, authorizationHeaderValue).get();

		System.out.println("Status: " + response.getStatus());
		final EntityExample res3 = response.readEntity(EntityExample.class);
		System.out.println(res3.getDato1() + " - " + res3.getDato2());

		// Pasar parametros como string
		response = ClientBuilder.newClient().target("http://localhost:8080/sistra2back/stg-rest/rest/ejemploRest")
				.path("recuperaDato2").queryParam("param1", "valor param 1").queryParam("param2", "valor param 2")
				.request().header(authorizationHeaderName, authorizationHeaderValue).get();

		System.out.println("Status: " + response.getStatus());
		final EntityExample res2 = response.readEntity(EntityExample.class);
		System.out.println(res2.getDato1() + " - " + res2.getDato2());

	}

}
