package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.responses.FruitResponse;
import de.consol.dus.boundary.restclients.FruitRestClient;
import java.util.Objects;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("fruits")
@Produces(MediaType.APPLICATION_JSON)
public class FruitResource {

  private final FruitRestClient restClient;

  @Inject
  public FruitResource(@RestClient FruitRestClient restClient) {
    this.restClient = restClient;
  }

  @GET
  @Path("/{name}")
  public FruitResponse getFruitByName(@PathParam("name") String name) {
      FruitResponse response = restClient.getFruitByName(name);
      if (Objects.equals("Ananas", response.getGenus())) {
        response.setComment("Of course it belongs on pizza!");
    }
    return response;
  }
}
