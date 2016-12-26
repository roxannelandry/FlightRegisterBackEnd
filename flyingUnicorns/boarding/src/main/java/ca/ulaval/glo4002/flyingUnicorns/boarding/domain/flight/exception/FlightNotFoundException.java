package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception;

@SuppressWarnings("serial")
public class FlightNotFoundException extends RuntimeException {

  public FlightNotFoundException(String message) {
    super(message);
  }

}