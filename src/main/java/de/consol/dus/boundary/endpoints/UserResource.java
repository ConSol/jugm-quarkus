package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.responses.UserResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class UserResource {

    public static final String DEFAULT_EMAIL = "john@Doe.com";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getSampleUser() {
        return UserResponse.builder()
            .name("John Doe")
            .email(DEFAULT_EMAIL)
            .build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getUserByName(@PathParam("name") String name) {
        return UserResponse.builder()
            .name(name)
            .email(DEFAULT_EMAIL)
            .build();
    }
}