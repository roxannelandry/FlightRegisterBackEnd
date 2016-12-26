package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception;

@SuppressWarnings("serial")
public class FlightAlreadyExistsException extends RuntimeException {

  public FlightAlreadyExistsException(String message) {
    super(message);
  }

}