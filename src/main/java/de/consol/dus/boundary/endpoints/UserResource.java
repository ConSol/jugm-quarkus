package de.consol.dus.boundary.endpoints;

import de.consol.dus.boundary.responses.UserResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getSampleUser() {
        return UserResponse.builder()
            .name("John Doe")
            .email("john@Doe.com")
            .build();
    }
}