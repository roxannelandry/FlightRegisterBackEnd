package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions;

@SuppressWarnings("serial")
public class CheckinTimeException extends RuntimeException {

  public CheckinTimeException(String message) {
    super(message);
  }

}