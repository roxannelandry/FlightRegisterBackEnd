package org.boardingAccTests.fixtures;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;

public interface ReservationWithPassengerFixture {
  
  public void addPassengerToReservation(ReservationDto reservationDto, PassengerDto passengerDto);
  
  public void saveReservationWithPassenger(ReservationDto reservationDto);
  
  public void reservationIsSaved(int reservationNumber);
      
}