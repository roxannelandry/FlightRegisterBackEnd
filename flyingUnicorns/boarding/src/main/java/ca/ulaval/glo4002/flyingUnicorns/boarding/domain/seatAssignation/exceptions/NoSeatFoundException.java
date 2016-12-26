package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions;

@SuppressWarnings("serial")
public class NoSeatFoundException extends RuntimeException {

  public NoSeatFoundException(String message) {
    super(message);
  }

}