package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class PassengerDto {

  @JsonProperty("passenger_hash")
  public String passengerHash;

  @JsonProperty("first_name")
  public String firstName;

  @JsonProperty("last_name")
  public String lastName;

  @JsonProperty("child")
  public boolean child;

  @JsonProperty("passport_number")
  public String passportNumber;

  @JsonProperty("seat_class")
  public SeatClass seatClass;

  @JsonProperty("is_checkin")
  public boolean isCheckin;

  @JsonProperty("is_vip")
  public boolean isVip;

}