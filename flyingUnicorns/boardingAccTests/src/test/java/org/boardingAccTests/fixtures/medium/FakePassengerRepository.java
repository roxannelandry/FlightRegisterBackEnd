package org.boardingAccTests.fixtures.medium;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerRepository;

public class FakePassengerRepository implements PassengerRepository {
  
  public Map<String, Passenger> passengers = new HashMap<>();

  @Override
  public Passenger findPassenger(String passengerHash) {
    return passengers.get(passengerHash);
  }

  @Override
  public void savePassenger(Passenger passenger) {
    passengers.put(passenger.getPassengerHash(), passenger);
  }

  @Override
  public boolean passengerExist(String passengerHash) {
    return passengers.containsKey(passengerHash);
  }

}