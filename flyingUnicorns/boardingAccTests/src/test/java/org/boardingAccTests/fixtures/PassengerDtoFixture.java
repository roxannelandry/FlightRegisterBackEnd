package org.boardingAccTests.fixtures;


import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

public class PassengerDtoFixture {

  public PassengerDto createPassengerDto(String passengerName, SeatClass seatClass) {
    PassengerDto passengerDto = new PassengerDto();
    
    passengerDto.seatClass = seatClass;
    passengerDto.child = false;
    passengerDto.firstName = passengerName;
    passengerDto.isVip = false;
    passengerDto.lastName = passengerName;
    passengerDto.passportNumber = "123456789";
    passengerDto.isCheckin = true;
    passengerDto.passengerHash = "passengerHash";
    
    return passengerDto;
  }

}