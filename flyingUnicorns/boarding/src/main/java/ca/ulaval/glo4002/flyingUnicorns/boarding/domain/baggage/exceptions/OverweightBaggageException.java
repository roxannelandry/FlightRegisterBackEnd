package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

@SuppressWarnings("serial")
public class OverweightBaggageException extends RuntimeException {

  public OverweightBaggageException(String message) {
    super(message);
  }

}