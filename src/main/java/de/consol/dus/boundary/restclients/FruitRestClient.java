package de.consol.dus.boundary.restclients;

import de.consol.dus.boundary.responses.FruitResponse;
import java.time.temporal.ChronoUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/fruit")
@RegisterRestClient(configKey = "fruit-rest")
@Produces(MediaType.APPLICATION_JSON)
public interface FruitRestClient {

  @GET
  @Path("{name}")
  @Retry(maxRetries = 5, delay = 1, delayUnit = ChronoUnit.SECONDS)
  @CircuitBreaker(
      requestVolumeThreshold = 4,
      delay = 30,
      delayUnit = ChronoUnit.SECONDS)
  FruitResponse getFruitByName(@PathParam("name") String name);
}
