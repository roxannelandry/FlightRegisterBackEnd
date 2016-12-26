package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import java.util.List;
import java.util.Random;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

public class SeatAssignationRandom implements SeatAssignationStrategy {

  @Override
  public Seat determineSeatToAssign(List<Seat> seats) {
    if (seats.isEmpty()) {
      throw new NoSeatFoundException("No seat found for this request");
    }

    Random rand = new Random();
    return seats.get(rand.nextInt(seats.size()));
  }

}