package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions;

@SuppressWarnings("serial")
public class InvalidCheckinException extends RuntimeException {

  public InvalidCheckinException(String message) {
    super(message);
  }

}