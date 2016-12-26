package org.boardingAccTests.fixtures;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;

public interface PassengerInRepositoryFixture {

  public void savePassengerInRepository(String passengerHash);
  
  public Passenger getPassengerInRepository(String passengerHash);
  
  public void PassengerIsSaved(String passengerHash);
  
}