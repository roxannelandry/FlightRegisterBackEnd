package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

@SuppressWarnings("serial")
public class OversizedBaggageException extends RuntimeException {

  public OversizedBaggageException(String message) {
    super(message);
  }

}