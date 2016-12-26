package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions;

@SuppressWarnings("serial")
public class PassengerAlreadyExistsException extends RuntimeException {

  public PassengerAlreadyExistsException(String message) {
    super(message);
  }

}