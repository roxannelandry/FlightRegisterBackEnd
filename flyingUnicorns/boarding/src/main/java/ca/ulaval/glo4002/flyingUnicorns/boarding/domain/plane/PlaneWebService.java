package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import javax.ws.rs.core.Response;

public interface PlaneWebService {

  public Plane createPlane(String flightNumber);

  public Response getPlaneInformation(String model);

}