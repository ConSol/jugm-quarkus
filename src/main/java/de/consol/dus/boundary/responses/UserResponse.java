package de.consol.dus.boundary.responses;

import javax.json.bind.annotation.JsonbPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@JsonbPropertyOrder({"name", "email"})
public class UserResponse {
  private String name;
  private String email;
}
