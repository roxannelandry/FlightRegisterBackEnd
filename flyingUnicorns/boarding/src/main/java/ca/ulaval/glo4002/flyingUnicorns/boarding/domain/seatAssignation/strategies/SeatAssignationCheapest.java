package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

public class SeatAssignationCheapest implements SeatAssignationStrategy {

  @Override
  public Seat determineSeatToAssign(List<Seat> seats) {
    float maxPriceValue = Float.POSITIVE_INFINITY;

    Seat seatToAssign = null;

    for (Seat seat : seats) {
      if (seat.comparePrice(maxPriceValue)) {
        maxPriceValue = seat.getPrice();
        seatToAssign = seat;
      }
    }

    if (seatToAssign == null) {
      throw new NoSeatFoundException("No seat found for this request");
    }

    return seatToAssign;
  }

}