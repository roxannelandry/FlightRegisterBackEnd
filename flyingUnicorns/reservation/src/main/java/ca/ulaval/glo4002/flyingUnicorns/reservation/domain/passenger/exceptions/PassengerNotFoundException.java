package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions;

@SuppressWarnings("serial")
public class PassengerNotFoundException extends RuntimeException {

  public PassengerNotFoundException(String message) {
    super(message);
  }

}