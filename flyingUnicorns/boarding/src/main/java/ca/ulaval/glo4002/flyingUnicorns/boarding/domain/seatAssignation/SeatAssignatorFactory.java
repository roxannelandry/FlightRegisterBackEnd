package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.InvalidSeatAssignationMode;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationCheapest;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationLandscape;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationLegroom;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationRandom;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;

public class SeatAssignatorFactory {

  public SeatAssignator createSeatAssignator(SeatAssignationModes mode) {
    return new SeatAssignator(createAssignationStrategy(mode));
  }

  private SeatAssignationStrategy createAssignationStrategy(SeatAssignationModes mode) {
    switch (mode) {
    case RANDOM:
      return new SeatAssignationRandom();
    case CHEAPEST:
      return new SeatAssignationCheapest();
    case LEGS:
      return new SeatAssignationLegroom();
    case LANDSCAPE:
      return new SeatAssignationLandscape();
    default:
      throw new InvalidSeatAssignationMode("Invalid seat assignation mode");
    }
  }

}