package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions;

@SuppressWarnings("serial")
public class BluePrintServerErrorException extends RuntimeException {

  public BluePrintServerErrorException(String message) {
    super(message);
  }

}