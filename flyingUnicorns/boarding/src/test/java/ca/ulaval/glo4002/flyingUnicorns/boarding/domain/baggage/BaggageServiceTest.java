package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationServiceFromReservation;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;

@RunWith(MockitoJUnitRunner.class)
public class BaggageServiceTest {

  private static final String ANY_FLIGHT_NUMBER = "AnyFlightNumber";
  private static final String ANY_PASSENGER_HASH = "AnyPassengerHash";

  private AllowedBaggageDto returnedAllowedBaggageDto;

  private AllowedBaggageDto allowedBaggageDto;

  private BaggageRequestDto baggageDto;

  @Mock
  private Baggage baggage;

  @Mock
  private Baggage disallowedBaggage;

  @Mock
  private BaggageAssembler baggageAssembler;

  @Mock
  private ReservationServiceFromReservation reservationRepository;

  @Mock
  private PassengerService passengerService;

  @Mock
  private PassengerRepository passengerRepository;

  @Mock
  private Passenger passenger;

  @Mock
  private FlightService flightService;

  private BaggageService baggageService;

  @Before
  public void setUp() {
    allowedBaggageDto = new AllowedBaggageDto();

    baggageDto = new BaggageRequestDto();

    baggageService = new BaggageService(baggageAssembler, passengerService, reservationRepository, flightService, passengerRepository);

    willReturn(passenger).given(passengerService).findPassenger(ANY_PASSENGER_HASH);
    willReturn(ANY_FLIGHT_NUMBER).given(reservationRepository).findFlightNumberByPassengerHash(ANY_PASSENGER_HASH);
    willReturn(baggage).given(baggageAssembler).assembleBaggage(baggageDto, passenger);
    willReturn(allowedBaggageDto).given(baggageAssembler).assembleAllowedBaggageDto();
  }

  @Test
  public void baggageDto_registerBaggage_delegateToPassengerService() {
    baggageService.registerBaggage(ANY_PASSENGER_HASH, baggageDto);

    verify(passengerService).findPassenger(ANY_PASSENGER_HASH);
  }

  @Test
  public void baggageDto_registerBaggage_delegateToBaggageAssembler() {
    baggageService.registerBaggage(ANY_PASSENGER_HASH, baggageDto);

    verify(baggageAssembler).assembleBaggage(baggageDto, passenger);
  }

  @Test
  public void baggageDto_registerBaggage_delegateToPassenger() {
    baggage = new Baggage();
    willReturn(baggage).given(baggageAssembler).assembleBaggage(baggageDto, passenger);

    baggageService.registerBaggage(ANY_PASSENGER_HASH, baggageDto);

    verify(passenger).registerBaggage(baggage);
  }

  @Test
  public void baggageDto_registerBaggage_delegateToCreateAllowedBaggageDtoFromBaggageAssembler() {
    baggageService.registerBaggage(ANY_PASSENGER_HASH, baggageDto);

    verify(baggageAssembler).assembleAllowedBaggageDto();
  }

  @Test
  public void baggageDto_registerBaggage_createAllowedBaggageDto() {
    returnedAllowedBaggageDto = baggageAssembler.assembleAllowedBaggageDto();
    willReturn(allowedBaggageDto).given(baggageAssembler).assembleAllowedBaggageDto();

    baggageService.registerBaggage(ANY_PASSENGER_HASH, baggageDto);

    assertEquals(allowedBaggageDto, returnedAllowedBaggageDto);
  }

  @Test
  public void passengerHash_getPassengerBaggages_delegateToFindPassengerFromPassengerService() {
    baggageService.getPassengerBaggages(ANY_PASSENGER_HASH);

    verify(passengerService).findPassenger(ANY_PASSENGER_HASH);
  }

  @Test
  public void passengerHash_getPassengerBaggages_delegateToBaggageAssembler() {
    baggageService.getPassengerBaggages(ANY_PASSENGER_HASH);

    verify(baggageAssembler).assembleFromPassenger(passenger);
  }

}