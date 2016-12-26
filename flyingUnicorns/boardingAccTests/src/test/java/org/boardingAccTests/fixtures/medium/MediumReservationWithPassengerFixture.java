package org.boardingAccTests.fixtures.medium;

import static org.junit.Assert.assertTrue;

import org.boardingAccTests.fixtures.ReservationWithPassengerFixture;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;

public class MediumReservationWithPassengerFixture implements ReservationWithPassengerFixture {

  private FakeReservationService reservationRepository;

  public MediumReservationWithPassengerFixture(FakeReservationService reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @Override
  public void saveReservationWithPassenger(ReservationDto reservationDto) {
    reservationRepository.saveReservation(reservationDto);
  }

  @Override
  public void addPassengerToReservation(ReservationDto reservationDto, PassengerDto passengerDto) {
    reservationDto.passengers.add(passengerDto);
  }

  @Override
  public void reservationIsSaved(int reservationNumber) {
    boolean containsReservation = reservationRepository.reservations.containsKey(reservationNumber);

    assertTrue(containsReservation);
  }

}