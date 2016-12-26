package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

public class SeatAssignationLegroom implements SeatAssignationStrategy {

  @Override
  public Seat determineSeatToAssign(List<Seat> seats) {
    int minLegRoom = Integer.MIN_VALUE;
    float maxSeatPrice = Float.POSITIVE_INFINITY;

    Seat seatToAssign = null;

    for (Seat seat : seats) {
      if (seat.hasMoreLegRoomThen(minLegRoom) || seat.hasLowerPriceForSameLegroom(minLegRoom, maxSeatPrice)) {
        minLegRoom = seat.getLegRoom();
        maxSeatPrice = seat.getPrice();
        seatToAssign = seat;
      }
    }
    if (seatToAssign == null) {
      throw new NoSeatFoundException("No seat found for this request");
    }

    return seatToAssign;
  }

}