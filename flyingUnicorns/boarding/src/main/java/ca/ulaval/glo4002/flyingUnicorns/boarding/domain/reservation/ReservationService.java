package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;

public interface ReservationService {

  public PassengerDto findPassengerByPassengerHash(String passengerHash);

  public ReservationDto findReservationByPassengerHash(String passengerHash);

  public String findFlightNumberByPassengerHash(String passengerHash);

}