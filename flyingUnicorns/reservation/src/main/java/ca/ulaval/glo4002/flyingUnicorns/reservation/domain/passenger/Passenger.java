package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinAlreadyDoneException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.InvalidPassengerException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;

@Entity
@JsonPropertyOrder({ "passenger_hash", "first_name", "last_name", "child", "passport_number", "seat_class" })
public class Passenger {

  private static final int ADULT_AGE = 18;

  @Id
  private String passengerHash;

  @Column
  @JsonProperty("first_name")
  private String firstName;

  @Column
  @JsonProperty("last_name")
  private String lastName;

  @Column
  @JsonProperty("passport_number")
  private String passportNumber;

  @Column
  @JsonProperty("seat_class")
  private String seatClass;

  @Column
  private boolean isCheckin;

  @Column
  private int age;

  @Column
  private boolean isVip;

  public Passenger() { // for Jackson & Hibernate
  }

  @JsonCreator
  public Passenger(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, @JsonProperty("age") int age,
      @JsonProperty("passport_number") String passportNumber, @JsonProperty("seat_class") String seatClass) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.passportNumber = passportNumber;
    this.seatClass = seatClass;
    this.isCheckin = false;

    passengerHash = UUID.randomUUID().toString();
    ensureJsonFieldsAreValid();
  }

  private void ensureJsonFieldsAreValid() {
    if (firstName == null || lastName == null || age == 0 || passportNumber == null || seatClass == null) {
      throw new InvalidReservationException("Missing fields when submitting reservation form, in passenger section.");
    }
  }

  public void checkin() {
    ensureHasValidCheckinInformation();

    if (isCheckin) {
      throw new CheckinAlreadyDoneException("This passenger already checked in");
    }

    isCheckin = true;
  }

  private void ensureHasValidCheckinInformation() {
    if (firstName.isEmpty() || lastName.isEmpty() || passportNumber.isEmpty()) {
      throw new InvalidPassengerException("Missing fields when submitting checkin, for this passenger");
    }
  }

  public boolean hasHash(String passengerHash) {
    return this.passengerHash.equals(passengerHash);
  }

  @JsonProperty("passenger_hash")
  public String getPassengerHash() {
    return passengerHash;
  }

  @JsonProperty("child")
  protected boolean isChild() {
    return age < ADULT_AGE;
  }

  @JsonProperty("is_checkin")
  protected boolean isCheckin() {
    return isCheckin;
  }

  @JsonProperty("is_vip")
  protected boolean isVip() {
    return isVip;
  }

  protected void setPassengerHash(String passengerHash) {
    this.passengerHash = passengerHash;
  }

  public void setVipProperty(boolean isVip) {
    this.isVip = isVip;
  }

}