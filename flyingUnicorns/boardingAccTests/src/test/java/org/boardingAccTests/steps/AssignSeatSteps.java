package org.boardingAccTests.steps;

import org.boardingAccTests.context.AcceptanceLargeTestsContext;
import org.boardingAccTests.fixtures.FlightInRepositoryFixture;
import org.boardingAccTests.fixtures.PassengerDtoFixture;
import org.boardingAccTests.fixtures.ReservationDtoFixture;
import org.boardingAccTests.fixtures.ReservationWithPassengerFixture;
import org.boardingAccTests.fixtures.SeatAssignationRequestDtoFixture;
import org.boardingAccTests.fixtures.large.RestPassengerCheckinFixture;
import org.boardingAccTests.fixtures.large.RestReservationWithPassengerFixture;
import org.boardingAccTests.fixtures.large.RestSeatAssignationFixture;
import org.boardingAccTests.runner.ServersRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationModes;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class AssignSeatSteps implements En {

  private AcceptanceLargeTestsContext acceptanceLargeTestsContext;

  private String passengerHash;
  private PassengerDto passengerDto;
  private ReservationDto reservationDto;

  private PassengerDtoFixture passengerDtoFixture;
  private ReservationDtoFixture resevationDtoFixture;
  private SeatAssignationRequestDtoFixture seatAssignationRequestDtoFixture;

  private ReservationWithPassengerFixture reservationWithPassengerFixture;
  private RestPassengerCheckinFixture passengerCheckinFixture;
  private RestSeatAssignationFixture restSeatAssignationFixture;
  private FlightInRepositoryFixture flightInRepositoryFixture;

  @Before
  public void beforeScenario() throws Throwable {
    String testsScope = System.getProperty("acctests.scope", "UNDEFINED");

    switch (testsScope) {
    case "large":
      acceptanceLargeTestsContext = new AcceptanceLargeTestsContext();
      acceptanceLargeTestsContext.createApplication();

      new ServersRunner(acceptanceLargeTestsContext).beforeAll();

      passengerDtoFixture = new PassengerDtoFixture();
      resevationDtoFixture = new ReservationDtoFixture();
      seatAssignationRequestDtoFixture = new SeatAssignationRequestDtoFixture();

      reservationWithPassengerFixture = new RestReservationWithPassengerFixture();
      passengerCheckinFixture = new RestPassengerCheckinFixture();
      restSeatAssignationFixture = new RestSeatAssignationFixture();
      flightInRepositoryFixture = new FlightInRepositoryFixture(acceptanceLargeTestsContext.flightService);

      break;
    case "mediumapp":
      break;
    default:
      throw new UnsupportedOperationException(String.format("Acceptance test scope '%s' is not supported", testsScope));
    }
  }

  public AssignSeatSteps() {
    Given("^a passenger Alice that have a reservation in a flight$", () -> {
      passengerDto = passengerDtoFixture.createPassengerDto("Alice", SeatClass.ECONOMY);
      reservationDto = resevationDtoFixture.createReservationDto("QK-432");

      reservationWithPassengerFixture.addPassengerToReservation(reservationDto, passengerDto);
      reservationWithPassengerFixture.saveReservationWithPassenger(reservationDto);

      reservationWithPassengerFixture.reservationIsSaved(reservationDto.reservationNumber);
    });

    Given("^that the flight contains available seats$", () -> {
      flightInRepositoryFixture.hasAvailableSeats(passengerDto.seatClass, reservationDto.flightNumber);
    });

    Given("^that her checkin has been done$", () -> {
      passengerHash = passengerCheckinFixture.getPassengerHash(reservationDto.reservationNumber);
      passengerCheckinFixture.checkPassengerIn(passengerHash);
      passengerCheckinFixture.passengerIsCheckin();
    });

    When("^she ask for random seat assignation$", () -> {
      SeatAssignationRequestDto seatAssignationRequestDto = seatAssignationRequestDtoFixture.createSeatAssignationDto(SeatAssignationModes.RANDOM,
          passengerHash);
      restSeatAssignationFixture.assignRandomSeat(seatAssignationRequestDto);
    });

    Then("^her seat is assigned$", () -> {
      restSeatAssignationFixture.seatIsAssigned();
    });
  }

}