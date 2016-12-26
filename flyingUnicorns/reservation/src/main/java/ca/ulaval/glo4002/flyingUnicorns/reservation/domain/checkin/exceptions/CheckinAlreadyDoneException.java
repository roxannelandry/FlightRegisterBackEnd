package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions;

@SuppressWarnings("serial")
public class CheckinAlreadyDoneException extends RuntimeException {

  public CheckinAlreadyDoneException(String message) {
    super(message);
  }

}