package de.consol.dus.boundary.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NutritionResponse {
  private double carbohydrates;
  private double protein;
  private double fat;
  private double calories;
  private double sugar;
}
