package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

public class SeatAssignationLandscape implements SeatAssignationStrategy {

  @Override
  public Seat determineSeatToAssign(List<Seat> seats) {
    float maxSeatPrice = Float.POSITIVE_INFINITY;

    boolean hasWindow = false;
    boolean hasClearView = false;

    Seat seatToAssign = null;

    for (Seat seat : seats) {
      if ((seat.hasWindow() && !hasWindow) || (seat.hasClearView() && !hasClearView)
          || seat.hasLowerPriceForSameView(hasWindow, hasClearView, maxSeatPrice)) {

        maxSeatPrice = seat.getPrice();
        hasWindow = seat.hasWindow();
        hasClearView = seat.hasClearView();
        seatToAssign = seat;
      }
    }

    if (seatToAssign == null) {
      throw new NoSeatFoundException("No seat found for this request");
    }

    return seatToAssign;
  }

}