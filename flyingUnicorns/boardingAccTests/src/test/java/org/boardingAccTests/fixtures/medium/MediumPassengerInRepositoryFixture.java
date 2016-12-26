package org.boardingAccTests.fixtures.medium;

import static org.junit.Assert.assertNotNull;

import org.boardingAccTests.fixtures.PassengerInRepositoryFixture;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;

public class MediumPassengerInRepositoryFixture implements PassengerInRepositoryFixture {

  private PassengerService passengerService;

  public MediumPassengerInRepositoryFixture(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @Override
  public void savePassengerInRepository(String passengerHash) {
    passengerService.findPassenger(passengerHash);
  }

  @Override
  public Passenger getPassengerInRepository(String passengerHash) {
    return passengerService.findPassenger(passengerHash);
  }

  @Override
  public void PassengerIsSaved(String passengerHash) {
    Passenger passengerReturn = passengerService.findPassenger(passengerHash);

    assertNotNull(passengerReturn);
  }

}