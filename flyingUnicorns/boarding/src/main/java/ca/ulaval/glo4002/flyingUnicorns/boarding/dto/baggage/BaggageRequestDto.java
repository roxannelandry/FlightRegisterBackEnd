package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageType;

public class BaggageRequestDto {

  @JsonProperty("linear_dimension")
  public int linearDimension;

  @JsonProperty("linear_dimension_unit")
  public String linearDimensionUnit;

  @JsonProperty("weight")
  public int weight;

  @JsonProperty("weight_unit")
  public String weightUnit;

  @JsonProperty("type")
  public BaggageType type;

}