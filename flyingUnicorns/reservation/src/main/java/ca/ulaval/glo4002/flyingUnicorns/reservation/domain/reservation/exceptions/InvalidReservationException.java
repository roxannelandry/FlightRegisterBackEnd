package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions;

@SuppressWarnings("serial")
public class InvalidReservationException extends RuntimeException {

  public InvalidReservationException(String message) {
    super(message);
  }

}