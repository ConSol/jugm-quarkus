package de.consol.dus.boundary.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {

  @NotNull
  @Size(max = 24)
  private String name;

  @NotNull
  @Email
  private String email;
}
