package de.consol.dus.boundary.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
  private String name;
  private String email;
}
