package org.boardingAccTests.fixtures;

import java.util.ArrayList;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;

public class ReservationDtoFixture {

  public ReservationDto createReservationDto(String flightNumber) {
    ReservationDto reservationDto = new ReservationDto();
    
    reservationDto.flightDate = "2016-10-30T00:00:00Z";
    reservationDto.flightNumber = flightNumber;
    reservationDto.reservationNumber = 1234;
    reservationDto.passengers = new ArrayList<PassengerDto>();;
    
    return reservationDto;
  }

}