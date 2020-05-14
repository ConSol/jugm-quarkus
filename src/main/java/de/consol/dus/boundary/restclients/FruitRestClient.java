package de.consol.dus.boundary.restclients;

import de.consol.dus.boundary.responses.FruitResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/fruit")
@RegisterRestClient(configKey = "fruit-rest")
@Produces(MediaType.APPLICATION_JSON)
public interface FruitRestClient {

  @GET
  @Path("{name}")
  FruitResponse getFruitByName(@PathParam("name") String name);
}
