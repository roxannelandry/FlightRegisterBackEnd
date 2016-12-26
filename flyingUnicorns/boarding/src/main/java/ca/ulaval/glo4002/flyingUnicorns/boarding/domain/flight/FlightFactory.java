package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneWebService;

public class FlightFactory {

  private PlaneWebService planeService;

  public FlightFactory(PlaneWebService planeService) {
    this.planeService = planeService;
  }

  public Flight createFlight(String flightNumber) {
    Plane plane = planeService.createPlane(flightNumber);
    return new Flight(flightNumber, plane);
  }

}