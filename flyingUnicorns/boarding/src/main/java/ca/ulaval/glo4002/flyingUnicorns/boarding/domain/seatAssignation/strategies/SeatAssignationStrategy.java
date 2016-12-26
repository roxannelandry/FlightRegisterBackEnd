package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;

public interface SeatAssignationStrategy {

  public Seat determineSeatToAssign(List<Seat> seats);

}