package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinAlreadyDoneException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.InvalidPassengerException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;

public class PassengerTest {

  private static final String ANY_VALID_FIRSTNAME = "AnyFirstName";
  private static final String ANY_VALID_LASTNAME = "AnyLastName";
  private static final String ANY_VALID_PASSPORT_NUMBER = "12345";

  private static final String ANY_INVALID_FIRSTNAME = "";
  private static final String ANY_INVALID_LASTNAME = "";
  private static final String ANY_INVALID_PASSPORT_NUMBER = "";

  private static final String ANY_PASSENGER_HASH = "PASSENGER_HASH";
  private static final String ANY_OTHER_PASSENGER_HASH = "OTHER_PASSENGER_HASH";
  private static final String ANY_SEAT_CLASS = "FIRST_CLASS";
  
  private static final int ANY_CHILD_AGE = 17;
  private static final int ANY_ADULT_AGE = 18;

  private Passenger adultPassenger;
  private Passenger childPassenger;
  private Passenger invalidPassenger;

  @Before
  public void setup() {
    adultPassenger = new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_ADULT_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);
    childPassenger = new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_CHILD_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullFirstNamePassenger_constructPassenger_throwInvalidReservation() {
    new Passenger(null, ANY_VALID_LASTNAME, ANY_ADULT_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullLastNamePassenger_constructPassenger_throwInvalidReservation() {
    new Passenger(ANY_VALID_FIRSTNAME, null, ANY_ADULT_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);
  }

  @Test(expected = InvalidReservationException.class)
  public void passengerWithAgeZero_constructPassenger_throwInvalidReservation() {
    new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, 0, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullPassportNumberPassenger_constructPassenger_throwInvalidReservation() {
    new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_ADULT_AGE, null, ANY_SEAT_CLASS);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullSeatClassPassenger_constructPassenger_throwInvalidReservation() {
    new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_ADULT_AGE, ANY_VALID_PASSPORT_NUMBER, null);
  }

  @Test
  public void passengerNotCheckedIn_checkinPassenger_isCheckinTrue() {
    adultPassenger.checkin();

    assertTrue(adultPassenger.isCheckin());
  }

  @Test(expected = CheckinAlreadyDoneException.class)
  public void passengerChecksInTwice_throwCheckinAlreadyDone() {
    adultPassenger.checkin();
    adultPassenger.checkin();
  }

  @Test
  public void constructPassenger_passengerHashNotNull() {
    assertNotEquals(adultPassenger.getPassengerHash(), null);
  }

  @Test
  public void childPassenger_constructPassenger_isChildFieldSetToTrue() {
    assertTrue(childPassenger.isChild());
  }

  @Test
  public void adultPassenger_constructPassenger_isChildFieldSetToFalse() {
    assertFalse(adultPassenger.isChild());
  }

  @Test
  public void passengerWithHash_checkingIfHasHash_returnTrue() {
    adultPassenger.setPassengerHash(ANY_PASSENGER_HASH);

    boolean hasHash = adultPassenger.hasHash(ANY_PASSENGER_HASH);

    assertTrue(hasHash);
  }

  @Test
  public void passengerWithOtherHash_checkingIfHasHash_returnFalse() {
    adultPassenger.setPassengerHash(ANY_OTHER_PASSENGER_HASH);

    boolean hasHash = adultPassenger.hasHash(ANY_PASSENGER_HASH);

    assertFalse(hasHash);
  }

  @Test(expected = InvalidPassengerException.class)
  public void passengerWithEmptyFirstName_tryingToCheckin_throwInvalidPassenger() {
    invalidPassenger = new Passenger(ANY_INVALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_CHILD_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);

    invalidPassenger.checkin();
  }

  @Test(expected = InvalidPassengerException.class)
  public void passengerWithEmptyLastName_tryingToCheckin_throwInvalidPassenger() {
    invalidPassenger = new Passenger(ANY_VALID_FIRSTNAME, ANY_INVALID_LASTNAME, ANY_CHILD_AGE, ANY_VALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);

    invalidPassenger.checkin();
  }

  @Test(expected = InvalidPassengerException.class)
  public void passengerWithEmptyPassport_tryingToCheckin_throwInvalidPassenger() {
    invalidPassenger = new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, ANY_CHILD_AGE, ANY_INVALID_PASSPORT_NUMBER, ANY_SEAT_CLASS);

    invalidPassenger.checkin();
  }

  @Test
  public void validPassenger_settingPassengerToVip_hasVIPstatus() {
    adultPassenger.setVipProperty(true);

    assertTrue(adultPassenger.isVip());
  }

}