package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.entities.UserEntity;
import de.consol.dus.boundary.mapper.UserMapper;
import de.consol.dus.boundary.repositories.UserRepository;
import de.consol.dus.boundary.requests.CreateUserRequest;
import de.consol.dus.boundary.responses.FruitResponse;
import de.consol.dus.boundary.responses.UserResponse;
import de.consol.dus.boundary.restclients.FruitRestClient;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final FruitRestClient restClient;
    private final MetricRegistry registry;
    private final AtomicLong userCounter = new AtomicLong(0L);

    @Inject
    public UserResource(
        UserRepository repository,
        UserMapper mapper,
        @RestClient FruitRestClient restClient,
        @RegistryType(type = MetricRegistry.Type.APPLICATION) MetricRegistry registry) {
        this.repository = repository;
        this.mapper = mapper;
        this.restClient = restClient;
        this.registry = registry;
    }

    @GET
    @RolesAllowed({ "admin" })
    @Counted(name = "allUsersCounter", description = "How often all users have been fetched")
    @Metered(name = "usersFetchedMeter", description = "Meter information for user fetching endpoint")
    @Timed(name = "allUsersTimer", description = "How long it takes to to fetch all users.")
    public Collection<UserResponse> getAllUsers() {
        return mapper.entitiesToResponses(repository.listAll());
    }

    @GET
    @Path("{name}")
    public UserResponse getUserByName(@PathParam("name") String name) {
        return mapper.entityToResponse(repository.findByName(name).orElse(null));
    }

    @POST
    @Counted(name = "createUserCounter", description = "How often a user has been created")
    @Timed(name = "createUserTimer", description = "How long it has taken to create a user")
    @Transactional
    public UserResponse createUser(@Valid CreateUserRequest request) {
        UserEntity toSave = mapper.requestToEntity(request);
        FruitResponse apple = restClient.getFruitByName("apple");
        repository.persist(toSave);
        Histogram histogram = getUserHistogram();
        histogram.update(userCounter.incrementAndGet());
        return mapper.entityToResponse(toSave);
    }

    private Histogram getUserHistogram() {
        Metadata metadata = Metadata.builder()
            .withName("userHistogram")
            .withDescription("Histogram of user count")
            .withType(MetricType.HISTOGRAM)
            .build();
        return registry.histogram(metadata);
    }

    @DELETE
    @Transactional
    @Path("{name}")
    public UserResponse deleteUserByName(@PathParam("name") String name) {
        Optional<UserEntity> fetched = repository.findByName(name);
        fetched.ifPresent(repository::delete);
        getUserHistogram().update(userCounter.decrementAndGet());
        return mapper.entityToResponse(fetched.orElse(null));
    }

    @Gauge(name = "userGauge", unit = "Users", absolute = true)
    public long userGauge() {
        return userCounter.get();
    }
}