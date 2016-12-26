package org.boardingAccTests.steps;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.boardingAccTests.context.AcceptanceMediumTestsContext;
import org.boardingAccTests.fixtures.BaggageDtoFixture;
import org.boardingAccTests.fixtures.PassengerDtoFixture;
import org.boardingAccTests.fixtures.PassengerInRepositoryFixture;
import org.boardingAccTests.fixtures.RegisterBaggagesFixture;
import org.boardingAccTests.fixtures.ReservationDtoFixture;
import org.boardingAccTests.fixtures.ReservationWithPassengerFixture;
import org.boardingAccTests.fixtures.medium.MediumPassengerInRepositoryFixture;
import org.boardingAccTests.fixtures.medium.MediumRegisterBaggagesFixture;
import org.boardingAccTests.fixtures.medium.MediumReservationWithPassengerFixture;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class RegisterBaggagesSteps implements En {

  private AcceptanceMediumTestsContext acceptanceMediumTestsContext;

  private Passenger passenger;
  private PassengerDtoFixture passengerDtoFixture;
  private ReservationDtoFixture resevationDtoFixture;
  private ReservationWithPassengerFixture reservationWithPassengerFixture;
  private PassengerInRepositoryFixture passengerInRepositoryFixture;

  private BaggageDtoFixture baggageDtoFixture;
  private RegisterBaggagesFixture registerBaggagesFixture;

  @Before
  public void beforeScenario() throws Throwable {
    String testsScope = System.getProperty("acctests.scope", "UNDEFINED");

    switch (testsScope) {
    case "mediumapp":
      acceptanceMediumTestsContext = new AcceptanceMediumTestsContext();
      acceptanceMediumTestsContext.createApplication();

      resevationDtoFixture = new ReservationDtoFixture();
      passengerDtoFixture = new PassengerDtoFixture();

      reservationWithPassengerFixture = new MediumReservationWithPassengerFixture(acceptanceMediumTestsContext.reservationRepository);
      passengerInRepositoryFixture = new MediumPassengerInRepositoryFixture(acceptanceMediumTestsContext.passengerService);

      baggageDtoFixture = new BaggageDtoFixture();
      registerBaggagesFixture = new MediumRegisterBaggagesFixture(acceptanceMediumTestsContext.baggageService);
      break;
    case "large":
      break;
    default:
      throw new UnsupportedOperationException(String.format("Acceptance test scope '%s' is not supported", testsScope));
    }
  }

  public RegisterBaggagesSteps() {
    Given("^a passenger Bob who is register on flight AC-(\\d+) in economy class$", (Integer arg1) -> {
      PassengerDto passengerDto = passengerDtoFixture.createPassengerDto("Bob", SeatClass.ECONOMY);
      ReservationDto reservationDto = resevationDtoFixture.createReservationDto("AC-" + arg1);

      reservationWithPassengerFixture.addPassengerToReservation(reservationDto, passengerDto);
      reservationWithPassengerFixture.saveReservationWithPassenger(reservationDto);

      reservationWithPassengerFixture.reservationIsSaved(reservationDto.reservationNumber);

      passengerInRepositoryFixture.savePassengerInRepository(passengerDto.passengerHash);

      passenger = passengerInRepositoryFixture.getPassengerInRepository(passengerDto.passengerHash);
    });

    Given("^Bob already has a checked baggage that respect rules$", () -> {
      BaggageRequestDto baggageDto = baggageDtoFixture.createBaggageDto("CHECKED", "10kg", "10cm");

      registerBaggagesFixture.whenRegisterBaggage(passenger.getPassengerHash(), baggageDto);
    });

    When("^registering this second baggage:$", (DataTable arg1) -> {
      List<List<String>> data = arg1.raw();

      BaggageRequestDto baggageDto = baggageDtoFixture.createBaggageDto(data.get(1).get(0), data.get(1).get(1), data.get(1).get(2));

      registerBaggagesFixture.whenRegisterBaggage(passenger.getPassengerHash(), baggageDto);
    });

    Then("^the total cost is (\\d+)\\$$", (Integer arg1) -> {
      float cost = passenger.getTotalBaggageCost();

      assertEquals((float) arg1, cost, 0);
    });
  }

}