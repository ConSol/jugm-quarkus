package de.consol.dus.boundary.mapper;

import de.consol.dus.boundary.entities.UserEntity;
import de.consol.dus.boundary.requests.CreateUserRequest;
import de.consol.dus.boundary.responses.UserResponse;
import java.util.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

  UserEntity requestToEntity(CreateUserRequest request);

  UserResponse entityToResponse(UserEntity entity);

  Collection<UserResponse> entitiesToResponses(Collection<UserEntity> entities);
}
