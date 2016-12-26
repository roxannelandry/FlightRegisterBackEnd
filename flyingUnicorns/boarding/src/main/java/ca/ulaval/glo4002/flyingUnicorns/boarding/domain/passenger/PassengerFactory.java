package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

public class PassengerFactory {

  public Passenger createPassenger(PassengerDto passengerDto) {
    return new Passenger(passengerDto.passengerHash, passengerDto.seatClass, passengerDto.child, passengerDto.isVip, passengerDto.isCheckin);
  }

}