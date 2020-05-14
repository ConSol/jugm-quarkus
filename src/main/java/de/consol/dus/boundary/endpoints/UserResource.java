package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.entities.UserEntity;
import de.consol.dus.boundary.mapper.UserMapper;
import de.consol.dus.boundary.repositories.UserRepository;
import de.consol.dus.boundary.requests.CreateUserRequest;
import de.consol.dus.boundary.responses.UserResponse;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    public static final String DEFAULT_EMAIL = "john@Doe.com";

    private final UserRepository repository;
    private final UserMapper mapper;

    @GET
    public UserResponse getSampleUser() {
        return UserResponse.builder()
            .name("John Doe")
            .email(DEFAULT_EMAIL)
            .build();
    }

    @GET
    @Path("{name}")
    public UserResponse getUserByName(@PathParam("name") String name) {
        return UserResponse.builder()
            .name(name)
            .email(DEFAULT_EMAIL)
            .build();
    }

    @POST
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        UserEntity toSave = mapper.requestToEntity(request);
        repository.persist(toSave);
        return mapper.entityToResponse(toSave);
    }
}