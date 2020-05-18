package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.entities.UserEntity;
import de.consol.dus.boundary.mapper.UserMapper;
import de.consol.dus.boundary.repositories.UserRepository;
import de.consol.dus.boundary.requests.CreateUserRequest;
import de.consol.dus.boundary.responses.UserResponse;
import java.util.Collection;
import java.util.Optional;
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
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

@AllArgsConstructor
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;
    private final UserMapper mapper;

    @GET
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
        repository.persist(toSave);
        return mapper.entityToResponse(toSave);
    }

    @DELETE
    @Transactional
    @Path("{name}")
    public UserResponse deleteUserByName(@PathParam("name") String name) {
        Optional<UserEntity> fetched = repository.findByName(name);
        fetched.ifPresent(repository::delete);
        return mapper.entityToResponse(fetched.orElse(null));
    }
}