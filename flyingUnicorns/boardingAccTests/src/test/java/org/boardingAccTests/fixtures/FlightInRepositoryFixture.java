package org.boardingAccTests.fixtures;

import static org.junit.Assert.assertTrue;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class FlightInRepositoryFixture {
  
  private FlightService flightService;
  
  public FlightInRepositoryFixture(FlightService flightService) {
    this.flightService = flightService;
  }
  
  public void hasAvailableSeats(SeatClass seatClass, String flightNumber) {
    Flight flight = flightService.findFlightByFlightNumber(flightNumber);
    
    assertTrue(flight.getPlane().hasAvailableSeats(seatClass));
  }

}