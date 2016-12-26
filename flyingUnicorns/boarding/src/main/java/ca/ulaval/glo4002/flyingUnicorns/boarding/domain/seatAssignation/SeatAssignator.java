package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;

public class SeatAssignator {

  private SeatAssignationStrategy strategy;

  protected SeatAssignationStrategy getSeatAssignationStrategy() {
    return strategy;
  }

  public SeatAssignator(SeatAssignationStrategy strategy) {
    this.strategy = strategy;
  }

  public Seat assignSeat(Plane plane, Passenger passenger) {
    unassignPreviousPassengerSeat(plane, passenger);

    List<Seat> filteredSeatsList = plane.createFilteredSeatsList(passenger);
    Seat seat = strategy.determineSeatToAssign(filteredSeatsList);

    plane.assignSeat(seat);
    passenger.assignSeat(seat);

    return seat;
  }

  private void unassignPreviousPassengerSeat(Plane plane, Passenger passenger) {
    if (passenger.hasAssignedSeat()) {
      plane.unassignSeat(passenger.getSeat());
    }
  }

}