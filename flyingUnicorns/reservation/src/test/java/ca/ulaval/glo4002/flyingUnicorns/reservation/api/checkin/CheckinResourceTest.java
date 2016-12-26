package ca.ulaval.glo4002.flyingUnicorns.reservation.api.checkin;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.Checkin;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinTimeException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.ReservationNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception.UriException;

@RunWith(MockitoJUnitRunner.class)
public class CheckinResourceTest {

  private static final String ANY_PASSENGER_HASH = "AnyHash";
  private static final String WRONG_PASSENGER_HASH = "NotAFlyingUnicorn";

  @Mock
  private Checkin checkin;

  @Mock
  private Passenger passenger;

  @Mock
  private Reservation reservation;

  @Mock
  private ReservationRepository reservationRepository;

  private CheckinResource checkinResource;

  @Before
  public void setUp() {
    checkinResource = new CheckinResource(reservationRepository);
    willReturn(ANY_PASSENGER_HASH).given(checkin).getPassengerToCheckinHash();
    willReturn(passenger).given(reservationRepository).findPassengerByHash(ANY_PASSENGER_HASH);
    willReturn(reservation).given(reservationRepository).findReservationByPassengerHash(ANY_PASSENGER_HASH);
  }

  @Test
  public void validCheckinInformation_checkinPassengerIn_delegateToPassengerChecking() {
    willReturn(true).given(checkin).checkinTimeframeRespected(any(LocalDateTime.class), any(LocalDateTime.class));

    checkinResource.checkPassengerIn(checkin);

    verify(passenger).checkin();
  }

  @Test(expected = PassengerNotFoundException.class)
  public void nonExistentPassenger_checkinPassengerIn_throwPassengerNotFound() {
    willReturn(WRONG_PASSENGER_HASH).given(checkin).getPassengerToCheckinHash();
    willThrow(PassengerNotFoundException.class).given(reservationRepository).findPassengerByHash(WRONG_PASSENGER_HASH);

    checkinResource.checkPassengerIn(checkin);
  }

  @Test(expected = CheckinTimeException.class)
  public void checkinAttempThatDoesNotRespectTimeFrame_throwCheckinTime() {
    willThrow(CheckinTimeException.class).given(checkin).checkinTimeframeRespected(any(LocalDateTime.class), any(LocalDateTime.class));

    checkinResource.checkPassengerIn(checkin);
  }

  @Test(expected = UriException.class)
  public void URISyntaxException_checkinPassengerIn_throwUri() {
    willThrow(UriException.class).given(checkin).checkinTimeframeRespected(any(LocalDateTime.class), any(LocalDateTime.class));

    checkinResource.checkPassengerIn(checkin);
  }

  @Test(expected = ReservationNotFoundException.class)
  public void aPassengerThatDoesNotHaveAReservation_checkinPassengerIn_throwReservationNotFound() {
    willThrow(ReservationNotFoundException.class).given(checkin).checkinTimeframeRespected(any(LocalDateTime.class), any(LocalDateTime.class));

    checkinResource.checkPassengerIn(checkin);
  }

}