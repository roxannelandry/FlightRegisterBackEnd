package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions;

@SuppressWarnings("serial")
public class ReservationNotFoundException extends RuntimeException {

  public ReservationNotFoundException(String message) {
    super(message);
  }

}