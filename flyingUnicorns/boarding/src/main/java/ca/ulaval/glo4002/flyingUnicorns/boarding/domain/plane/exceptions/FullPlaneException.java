package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions;

@SuppressWarnings("serial")
public class FullPlaneException extends RuntimeException {

  public FullPlaneException(String message) {
    super(message);
  }

}