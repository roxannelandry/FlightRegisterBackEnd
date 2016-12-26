package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationServiceFromReservation;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

@RunWith(MockitoJUnitRunner.class)
public class PassengerServiceTest {

  private static final String ANY_PASSENGER_HASH = "passenger_hash";

  private static final SeatClass SEAT_CLASS = SeatClass.ECONOMY;

  private PassengerDto passengerDto;

  @Mock
  private PassengerFactory passengerFactory;

  @Mock
  private PassengerRepository passengerRepository;

  @Mock
  private ReservationServiceFromReservation reservationRepository;

  @Mock
  private Passenger passenger;

  @InjectMocks
  private PassengerService passengerService;

  @Before
  public void setup() {
    passengerDto = new PassengerDto();

    willReturn(passenger).given(passengerRepository).findPassenger(ANY_PASSENGER_HASH);
    willReturn(SEAT_CLASS).given(passenger).getSeatClass();
    willReturn(passengerDto).given(reservationRepository).findPassengerByPassengerHash(ANY_PASSENGER_HASH);
    willReturn(passenger).given(passengerFactory).createPassenger(passengerDto);
    willReturn(true).given(passengerRepository).passengerExist(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerAllreadyInRepo_findingPassenger_delegateToPassengerRepository() {
    passengerService.findPassenger(ANY_PASSENGER_HASH);

    verify(passengerRepository).findPassenger(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerNotInRepo_findingPassenger_passengerIsSavedInRepo() {
    willReturn(false).given(passengerRepository).passengerExist(ANY_PASSENGER_HASH);
    willReturn(true).given(passenger).isCheckin();

    passengerService.findPassenger(ANY_PASSENGER_HASH);

    verify(passengerRepository).savePassenger(passenger);
  }

  @Test
  public void passengerHash_findingPassenger_passengerIsReturned() {
    willReturn(passengerDto).given(reservationRepository).findPassengerByPassengerHash(ANY_PASSENGER_HASH);

    Passenger passengerReturned = passengerService.findPassenger(ANY_PASSENGER_HASH);

    assertEquals(passenger, passengerReturned);
  }

  @Test
  public void passenger_savingPassenger_delegateToPassengerRepository() {
    passengerService.savePassenger(passenger);

    verify(passengerRepository).savePassenger(passenger);
  }

}