package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaneInformationDto {

  @JsonProperty("model")
  public String model;

  @JsonProperty("manufacturer")
  public String manufacturer;

  @JsonProperty("number_of_seats")
  public int numberOfSeats;

  @JsonProperty("cargo_weight")
  public int cargoWeight;

}