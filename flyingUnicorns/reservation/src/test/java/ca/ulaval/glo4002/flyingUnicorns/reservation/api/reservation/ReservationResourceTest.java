package ca.ulaval.glo4002.flyingUnicorns.reservation.api.reservation;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.ReservationNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception.UriException;

@RunWith(MockitoJUnitRunner.class)
public class ReservationResourceTest {

  private static final String ANY_VALID_URI = "/reservations/1234";
  private static final String ANY_PASSENGER_HASH = UUID.randomUUID().toString();
  
  private static final int ANY_RESERVATION_NUMBER = 1234;
  private static final int OK_200 = 200;

  @Mock
  private Reservation reservation;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private Passenger passenger;

  @InjectMocks
  private ReservationResource reservationResource;

  @Before
  public void setUp() {
    willReturn(reservation).given(reservationRepository).findReservationByReservationNumber(ANY_RESERVATION_NUMBER);
    willReturn(ANY_RESERVATION_NUMBER).given(reservation).getReservationNumber();

    willReturn(reservation).given(reservationRepository).findReservationByPassengerHash(ANY_PASSENGER_HASH);
    willReturn(passenger).given(reservationRepository).findPassengerByHash(ANY_PASSENGER_HASH);
  }

  @Test
  public void reservation_addingReservation_delegateToReservationRepository() {
    reservationResource.addReservation(reservation);

    verify(reservationRepository).save(reservation);
  }

  @Test
  public void reservation_addingReservation_returnGoodLocationToResponseHeader() throws URISyntaxException {
    Response response = reservationResource.addReservation(reservation);

    URI expectedLocation = new URI(ANY_VALID_URI);
    URI actualLocation = response.getLocation();

    assertEquals(expectedLocation, actualLocation);
  }

  @Test(expected = InvalidReservationException.class)
  public void reservationThatHasInvalidField_addingReservation_throwInvalidReservation() {
    willThrow(InvalidReservationException.class).given(reservationRepository).save(reservation);

    reservationResource.addReservation(reservation);
  }

  @Test(expected = UriException.class)
  public void invalidUri_addingReservation_throwUri() {
    willThrow(UriException.class).given(reservationRepository).save(reservation);

    reservationResource.addReservation(reservation);
  }

  @Test
  public void reservationNumber_gettingReservationByNumber_delegateToReservationRepository() {
    reservationResource.getReservationByReservationNumber(ANY_RESERVATION_NUMBER);

    verify(reservationRepository).findReservationByReservationNumber(ANY_RESERVATION_NUMBER);
  }

  @Test
  public void reservationNumber_gettingReservationByNumber_returnReservationWithSameNumber() {
    Reservation reservationReturn = reservationResource.getReservationByReservationNumber(ANY_RESERVATION_NUMBER);

    assertEquals(reservation, reservationReturn);
  }

  @Test(expected = ReservationNotFoundException.class)
  public void aUnexistintReservationNumber_gettingReservation_throwReservationNotFound() {
    willThrow(ReservationNotFoundException.class).given(reservationRepository).findReservationByReservationNumber(ANY_RESERVATION_NUMBER);

    reservationResource.getReservationByReservationNumber(ANY_RESERVATION_NUMBER);
  }

  @Test
  public void passengerHash_gettingReservationByPassengerHash_delegateToReservationRepository() {
    reservationResource.getReservationByPassengerHash(ANY_PASSENGER_HASH);

    verify(reservationRepository).findReservationByPassengerHash(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerHash_gettingReservationByPassengerHash_returnReservationWithSameNumber() {
    Reservation reservationReturn = reservationResource.getReservationByPassengerHash(ANY_PASSENGER_HASH);

    assertEquals(reservation, reservationReturn);
  }

  @Test(expected = ReservationNotFoundException.class)
  public void aPassengerThatHasNoReservation_gettingReservationByPassengerHash_throwReservationNotFound() {
    willThrow(ReservationNotFoundException.class).given(reservationRepository).findReservationByPassengerHash(ANY_PASSENGER_HASH);

    reservationResource.getReservationByPassengerHash(ANY_PASSENGER_HASH);
  }

  @Test(expected = PassengerNotFoundException.class)
  public void passengerThatDoesNotHaveAReservation_gettingReservationByPassengerHash_throwPassengerNotFound() {
    willThrow(PassengerNotFoundException.class).given(reservationRepository).findReservationByPassengerHash(ANY_PASSENGER_HASH);

    reservationResource.getReservationByPassengerHash(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerHash_gettingPassengerByPassengerHash_delegateToReservationRepository() {
    reservationResource.getPassengerByPassengerHash(ANY_PASSENGER_HASH);

    verify(reservationRepository).findPassengerByHash(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerHash_gettingPassengerByPassengerHash_return200Ok() {
    Response response = reservationResource.getPassengerByPassengerHash(ANY_PASSENGER_HASH);

    assertEquals(OK_200, response.getStatus());
  }

  @Test
  public void passengerHash_gettingPassengerByPassengerHash_returnPassengerAsEntity() {
    Response response = reservationResource.getPassengerByPassengerHash(ANY_PASSENGER_HASH);

    assertEquals(passenger, response.getEntity());
  }

  @Test(expected = PassengerNotFoundException.class)
  public void aPassengerThatDoesNotExist_gettingPassengerByPassengerHash_throwPassengerNotFound() {
    willThrow(PassengerNotFoundException.class).given(reservationRepository).findPassengerByHash(ANY_PASSENGER_HASH);

    reservationResource.getPassengerByPassengerHash(ANY_PASSENGER_HASH);
  }

}