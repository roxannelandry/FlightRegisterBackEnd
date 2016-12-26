package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

@SuppressWarnings("serial")
public class InvalidBaggagePriceCaculatorException extends RuntimeException {

  public InvalidBaggagePriceCaculatorException(String message) {
    super(message);
  }

}