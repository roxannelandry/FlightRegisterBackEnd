package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BaggageResponseDto {

  @JsonProperty("linear_dimension")
  public int linearDimension;

  @JsonProperty("weight")
  public int weight;

  @JsonProperty("price")
  public float price;

  public BaggageResponseDto(int linearDimension, int weight, float price) {
    this.linearDimension = linearDimension;
    this.weight = weight;
    this.price = price;
  }

}