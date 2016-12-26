package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinTimeException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.InvalidCheckinException;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Checkin {

  private static final int LOWER_BOUND_FOR_SELF_CHECKIN_IN_HOURS = 6;
  private static final int UPPER_BOUND_FOR_SELF_CHECKIN_IN_HOURS = 48;

  @JsonProperty("passenger_hash")
  private String passengerToCheckinHash;

  private String checkinId;

  private CheckinMode by;

  private boolean vip;

  @JsonCreator
  public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") CheckinMode by, @JsonProperty("vip") boolean vip) {
    passengerToCheckinHash = passengerHash;
    this.by = by;
    this.vip = vip;

    ensureJsonFieldsAreValid();
    checkinId = UUID.randomUUID().toString();
  }

  private void ensureJsonFieldsAreValid() {
    if (passengerToCheckinHash == null || by == null) {
      throw new InvalidCheckinException("Missing fields when submitting form.");
    }
  }

  public boolean checkinTimeframeRespected(LocalDateTime flightDepartureTime, LocalDateTime currentTime) {
    switch (by) {
    case SELF:
      ensureCheckinNotTooEarly(flightDepartureTime, currentTime);
      ensureCheckinNotTooLate(flightDepartureTime, currentTime);
      return true;
    default:
      return true;
    }
  }

  private void ensureCheckinNotTooEarly(LocalDateTime flightDepartureTime, LocalDateTime currentTime) {
    LocalDateTime timeOfFlightMinusUpperBound = flightDepartureTime.minusHours(UPPER_BOUND_FOR_SELF_CHECKIN_IN_HOURS);

    if (currentTime.isBefore(timeOfFlightMinusUpperBound)) {
      throw new CheckinTimeException(
          "It is too early for self checkin. You may start checking in " + UPPER_BOUND_FOR_SELF_CHECKIN_IN_HOURS + " hours before your flight");
    }
  }

  private void ensureCheckinNotTooLate(LocalDateTime flightDepartureTime, LocalDateTime currentTime) {
    LocalDateTime timeOfTheFlightMinusLowerBound = flightDepartureTime.minusHours(LOWER_BOUND_FOR_SELF_CHECKIN_IN_HOURS);

    if (currentTime.isAfter(timeOfTheFlightMinusLowerBound)) {
      throw new CheckinTimeException(
          "It is too late for self checkin. Your checkin needs to be done " + LOWER_BOUND_FOR_SELF_CHECKIN_IN_HOURS + " hours before your flight");
    }
  }

  public boolean isVip() {
    return vip;
  }
  
  public String getPassengerToCheckinHash() {
    return passengerToCheckinHash;
  }

  public String getCheckinId() {
    return checkinId;
  }

  protected CheckinMode getBy() {
    return by;
  }

}