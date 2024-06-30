package org.nikita.jdbctask;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorldResource {

	@GET
	public String sayHello() {
		return "Hello World";
	}
}