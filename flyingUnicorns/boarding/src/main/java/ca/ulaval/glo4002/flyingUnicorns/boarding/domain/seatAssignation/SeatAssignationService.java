package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationResponseDto;

public class SeatAssignationService {

  private FlightService flightService;
  private PassengerService passengerService;

  private SeatAssignatorFactory seatAssignatorFactory;

  private ReservationService reservationRepository;

  public SeatAssignationService(FlightService flightService, ReservationService reservationRepository, PassengerService passengerService,
      SeatAssignatorFactory seatAssignatorFactory) {
    this.flightService = flightService;
    this.reservationRepository = reservationRepository;
    this.passengerService = passengerService;
    this.seatAssignatorFactory = seatAssignatorFactory;
  }

  public SeatAssignationResponseDto assignSeat(SeatAssignationRequestDto seatAssignationDto) {
    ReservationDto reservationDto = reservationRepository.findReservationByPassengerHash(seatAssignationDto.passengerHash);
    Passenger passenger = passengerService.findPassenger(seatAssignationDto.passengerHash);
    SeatAssignator seatAssignator = seatAssignatorFactory.createSeatAssignator(seatAssignationDto.mode);
    Plane plane = flightService.findPlaneFromFlightNumber(reservationDto.flightNumber);
    Seat reservedSeat = seatAssignator.assignSeat(plane, passenger);
    flightService.updatePlane(reservationDto.flightNumber, plane);
    passengerService.savePassenger(passenger);

    return new SeatAssignationResponseDto(reservedSeat.getId(), reservedSeat.getSeatNumber());
  }

}