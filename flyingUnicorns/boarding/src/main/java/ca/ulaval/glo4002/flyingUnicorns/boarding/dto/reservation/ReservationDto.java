package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

public class ReservationDto {

  @JsonProperty("reservation_number")
  public int reservationNumber;

  @JsonProperty("flight_number")
  public String flightNumber;

  @JsonProperty("flight_date")
  public String flightDate;

  @JsonProperty("passengers")
  public List<PassengerDto> passengers;

}