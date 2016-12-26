package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

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
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationServiceFromReservation;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationResponseDto;

@RunWith(MockitoJUnitRunner.class)
public class SeatAssignationServiceTest {

  private static final String ANY_PASSENGER_HASH = "1234";
  private static final String ANY_FLIGHT_NUMBER = "ABC-123";
  private static final String ANY_SEAT_NUMBER = "S-15";

  private static final int ANY_SEAT_ASSIGNATION_ID = 1;

  private static final SeatAssignationModes ANY_ASSIGNATION_MODES = SeatAssignationModes.RANDOM;

  private SeatAssignationRequestDto seatAssignationRequestDto;
  private SeatAssignationResponseDto seatAssignationResponseDto;

  private ReservationDto reservationDto;

  private SeatAssignationService seatAssignationService;

  @Mock
  private Passenger passenger;

  @Mock
  private FlightService flightService;

  @Mock
  private Plane plane;

  @Mock
  private Seat seat;

  @Mock
  private SeatAssignatorFactory seatAssignatorFactory;

  @Mock
  private ReservationServiceFromReservation reservationRepository;

  @Mock
  private SeatAssignator seatAssignator;

  @Mock
  private PassengerService passengerService;

  @Mock
  private SeatAssignationStrategy seatAssignationStrategy;

  @Before
  public void setup() {
    seatAssignationRequestDto = new SeatAssignationRequestDto();
    seatAssignationResponseDto = new SeatAssignationResponseDto(ANY_SEAT_ASSIGNATION_ID, ANY_SEAT_NUMBER);
    reservationDto = new ReservationDto();

    seatAssignationService = new SeatAssignationService(flightService, reservationRepository, passengerService, seatAssignatorFactory);
    seatAssignationRequestDto.passengerHash = ANY_PASSENGER_HASH;
    seatAssignationRequestDto.mode = ANY_ASSIGNATION_MODES;
    reservationDto.flightNumber = ANY_FLIGHT_NUMBER;

    willReturn(passenger).given(passengerService).findPassenger(ANY_PASSENGER_HASH);
    willReturn(false).given(passenger).hasAssignedSeat();
    willReturn(reservationDto).given(reservationRepository).findReservationByPassengerHash(seatAssignationRequestDto.passengerHash);
    willReturn(passenger).given(passengerService).findPassenger(seatAssignationRequestDto.passengerHash);
    willReturn(seatAssignator).given(seatAssignatorFactory).createSeatAssignator(ANY_ASSIGNATION_MODES);
    willReturn(plane).given(flightService).findPlaneFromFlightNumber(ANY_FLIGHT_NUMBER);
    willReturn(seat).given(seatAssignator).assignSeat(plane, passenger);
    willReturn(ANY_SEAT_ASSIGNATION_ID).given(seat).getId();
    willReturn(ANY_SEAT_NUMBER).given(seat).getSeatNumber();
  }

  @Test
  public void seatAssignationService_assigningSeatWithValidSeatAssignationDto_delegateToreservationRepository() {
    seatAssignationService.assignSeat(seatAssignationRequestDto);

    verify(reservationRepository).findReservationByPassengerHash(seatAssignationRequestDto.passengerHash);
  }

  @Test
  public void seatAssignationService_assigningSeatWithValidSeatAssignationDto_delegateToPassengerService() {
    seatAssignationService.assignSeat(seatAssignationRequestDto);

    verify(passengerService).findPassenger(seatAssignationRequestDto.passengerHash);
  }

  @Test
  public void seatAssignationService_assigningSeatWithValidSeatAssignationDto_delegateFlightServiceFindPlaneByFlightNumber() {
    seatAssignationService.assignSeat(seatAssignationRequestDto);

    verify(flightService).findPlaneFromFlightNumber(ANY_FLIGHT_NUMBER);
  }

  @Test
  public void seatAssignationService_assigningSeatWithValidSeatAssignationDto_delegateToSeatAssignatorAssignSeat() {
    seatAssignationService.assignSeat(seatAssignationRequestDto);

    verify(seatAssignator).assignSeat(plane, passenger);
  }

  @Test
  public void seatAssignationService_assigningSeatWithValidSeatAssignationDto_ReturnSeatAssignationResponseDtoWithExpectedValues() {
    SeatAssignationResponseDto seatAssignationReturned = seatAssignationService.assignSeat(seatAssignationRequestDto);

    assertEquals(seatAssignationResponseDto.seatAssignationId, seatAssignationReturned.seatAssignationId);
    assertEquals(seatAssignationResponseDto.seat, seatAssignationReturned.seat);
  }

}