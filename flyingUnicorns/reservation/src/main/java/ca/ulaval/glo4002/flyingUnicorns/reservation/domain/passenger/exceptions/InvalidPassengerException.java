package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions;

@SuppressWarnings("serial")
public class InvalidPassengerException extends RuntimeException {

  public InvalidPassengerException(String message) {
    super(message);
  }

}