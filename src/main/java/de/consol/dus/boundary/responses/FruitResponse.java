package de.consol.dus.boundary.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class FruitResponse {
  private String genus;
  private String name;
  private NutritionResponse nutritions;
  private String comment;
}
