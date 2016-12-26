package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;

@RunWith(MockitoJUnitRunner.class)
public class ReservationTest {

  private static final String FLIGHT_NUMBER = "A5346";
  private static final String ANY_PASSENGER_HASH = "FIRST_PASSENGER_HASH";

  private static final int INVALID_RESERVATION_NUMBER = 0;
  private static final int VALID_RESERVATION_NUMBER = 2;
  
  private static final LocalDateTime FLIGHT_DATE = LocalDateTime.now();
  
  private static final List<Passenger> EMPTY_PASSENGER_LIST = new ArrayList<Passenger>();

  private Reservation reservation;
  
  private List<Passenger> passengers;

  @Mock
  private Passenger passenger;

  @Before
  public void setUp() {
    passengers = new ArrayList<Passenger>();
    passengers.clear();
    passengers.add(passenger);
    
    reservation = new Reservation(VALID_RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
  }

  @Test(expected = InvalidReservationException.class)
  public void emptyReservationNumber_constructReservation_throwInvalidReservation() {
    new Reservation(INVALID_RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullFlightNumber_constructReservation_throwInvalidReservation() {
    new Reservation(VALID_RESERVATION_NUMBER, null, FLIGHT_DATE, passengers);
  }

  @Test(expected = InvalidReservationException.class)
  public void nullFlightDate_constructReservation_throwInvalidReservation() {
    new Reservation(VALID_RESERVATION_NUMBER, FLIGHT_NUMBER, null, passengers);
  }

  @Test(expected = InvalidReservationException.class)
  public void emptyPassengerList_constructReservation_throwInvalidReservation() {
    new Reservation(VALID_RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, EMPTY_PASSENGER_LIST);
  }

  @Test
  public void passengerInReservation_checkingIfPassengerInReservation_returnTrue() {
    willReturn(true).given(passenger).hasHash(ANY_PASSENGER_HASH);

    boolean containsPassenger = reservation.hasPassenger(ANY_PASSENGER_HASH);

    assertTrue(containsPassenger);
  }

  @Test
  public void passengerNotInReservation_checkingIfPassengerInReservation_returnFalse() {
    willReturn(false).given(passenger).hasHash(ANY_PASSENGER_HASH);

    boolean containsPassenger = reservation.hasPassenger(ANY_PASSENGER_HASH);

    assertFalse(containsPassenger);
  }

}