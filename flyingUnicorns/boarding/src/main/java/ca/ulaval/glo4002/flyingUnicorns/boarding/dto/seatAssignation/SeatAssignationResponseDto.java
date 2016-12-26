package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class SeatAssignationResponseDto {

  public String seat;

  @JsonIgnore
  public int seatAssignationId;

  public SeatAssignationResponseDto(int seatAssignationId, String seatNumber) {
    this.seatAssignationId = seatAssignationId;
    this.seat = seatNumber;
  }

}