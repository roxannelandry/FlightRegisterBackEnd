package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions;

@SuppressWarnings("serial")
public class InvalidSeatAssignationMode extends RuntimeException {

  public InvalidSeatAssignationMode(String message) {
    super(message);
  }

}