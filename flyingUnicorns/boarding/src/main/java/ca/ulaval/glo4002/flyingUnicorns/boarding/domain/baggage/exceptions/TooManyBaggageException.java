package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

@SuppressWarnings("serial")
public class TooManyBaggageException extends RuntimeException {

  public TooManyBaggageException(String message) {
    super(message);
  }

}