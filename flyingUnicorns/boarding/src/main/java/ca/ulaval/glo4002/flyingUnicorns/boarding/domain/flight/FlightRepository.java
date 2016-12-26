package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

public interface FlightRepository {

  Flight findFlightByFlightNumber(String flightNumber);

  boolean flightExists(String flightNumber);

  void save(Flight flight);

}