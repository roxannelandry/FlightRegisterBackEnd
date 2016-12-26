package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;

public class FlightService {

  private FlightFactory flightFactory;
  private FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository, FlightFactory flightFactory) {
    this.flightRepository = flightRepository;
    this.flightFactory = flightFactory;
  }

  public Flight findFlightByFlightNumber(String flightNumber) {
    if (!flightRepository.flightExists(flightNumber)) {
      createFlightFromBlueprint(flightNumber);
    }

    return flightRepository.findFlightByFlightNumber(flightNumber);
  }

  private Flight createFlightFromBlueprint(String flightNumber) {
    Flight flight = flightFactory.createFlight(flightNumber);

    flightRepository.save(flight);

    return flight;
  }

  public void ensureBaggageCanBeAdded(String flightNumber, int weightInGrams) {
    Flight flight = findFlightByFlightNumber(flightNumber);
    flight.ensureBaggageCanBeAdded(weightInGrams);
  }

  public void addBaggageWeight(String flightNumber, int weightInGrams) {
    Flight flight = findFlightByFlightNumber(flightNumber);

    flight.addBaggageWeight(weightInGrams);

    flightRepository.save(flight);
  }

  public Plane findPlaneFromFlightNumber(String flightNumber) {
    return findFlightByFlightNumber(flightNumber).getPlane();
  }

  public void updatePlane(String flightNumber, Plane plane) {
    Flight flight = flightRepository.findFlightByFlightNumber(flightNumber);
    flight.updatePlane(plane);
  }

}