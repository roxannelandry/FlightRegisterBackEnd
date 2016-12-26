package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationModes;

public class SeatAssignationRequestDto {

  @JsonProperty("passenger_hash")
  public String passengerHash;

  @JsonProperty("mode")
  public SeatAssignationModes mode;

}