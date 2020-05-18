package de.consol.dus.boundary.restclients;

import de.consol.dus.boundary.responses.FruitResponse;
import de.consol.dus.boundary.responses.NutritionResponse;
import java.time.temporal.ChronoUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/fruit")
@RegisterRestClient(configKey = "fruit-rest")
@Produces(MediaType.APPLICATION_JSON)
public interface FruitRestClient {

  @GET
  @Path("{name}")
  @Retry(maxRetries = 5, delay = 1, delayUnit = ChronoUnit.SECONDS)
  @Fallback(FruitFallback.class)
  FruitResponse getFruitByName(@PathParam("name") String name);

  final class FruitFallback implements FallbackHandler<FruitResponse> {

    @Override
    public FruitResponse handle(ExecutionContext context) {
      return FruitResponse.builder()
          .name("Fallback Apple")
          .nutritions(NutritionResponse.builder()
              .calories(133.7)
              .carbohydrates(66.6)
              .fat(42.0)
              .build())
          .comment("This is good & evil")
          .build();
    }
  }
}
