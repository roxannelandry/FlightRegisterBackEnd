package org.boardingAccTests.fixtures.medium;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightRepository;

public class FakeFlightRepository implements FlightRepository {
  
  public Map<String, Flight> flights = new HashMap<>();

  @Override
  public Flight findFlightByFlightNumber(String flightNumber) {
    return flights.get(flightNumber);
  }

  @Override
  public boolean flightExists(String flightNumber) {
    return flights.containsKey(flightNumber);
  }

  @Override
  public void save(Flight flight) {
    flights.put(flight.getFlightNumber(), flight);
  }

}