package de.consol.dus.configuration.health;

import de.consol.dus.boundary.restclients.FruitRestClient;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ProcessingException;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
@ApplicationScoped
public class FruitRestReady implements HealthCheck {

  public static final String FRUIT_REST_SERVICE_NAME = "Fruit Rest Service";
  private final FruitRestClient restClient;

  public FruitRestReady(@RestClient FruitRestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public HealthCheckResponse call() {
    try {
      if (Objects.nonNull(restClient.getFruitByName("apple"))) {
        return HealthCheckResponse.up(FRUIT_REST_SERVICE_NAME);
      } else {
        return HealthCheckResponse.down(FRUIT_REST_SERVICE_NAME);
      }
    } catch (ProcessingException e) {
      return HealthCheckResponse.down(FRUIT_REST_SERVICE_NAME);
    }
  }
}
