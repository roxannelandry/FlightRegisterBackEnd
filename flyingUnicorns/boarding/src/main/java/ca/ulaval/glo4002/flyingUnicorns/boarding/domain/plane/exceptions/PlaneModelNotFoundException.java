package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions;

@SuppressWarnings("serial")
public class PlaneModelNotFoundException extends RuntimeException {

  public PlaneModelNotFoundException(String message) {
    super(message);
  }

}