package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions;

@SuppressWarnings("serial")
public class PassengerNotCheckedInException extends RuntimeException {

  public PassengerNotCheckedInException(String message) {
    super(message);
  }

}