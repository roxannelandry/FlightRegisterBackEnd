package org.boardingAccTests.fixtures.medium;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;

public class FakeReservationService implements ReservationService {

  public Map<Integer, ReservationDto> reservations = new HashMap<>();

  @Override
  public PassengerDto findPassengerByPassengerHash(String passengerHash) {
    for(Map.Entry<Integer, ReservationDto> reservation : reservations.entrySet()) {
      for (PassengerDto passengerDto : reservation.getValue().passengers) {
        if(passengerDto.passengerHash.equals(passengerHash)) {
          return passengerDto;
        }
      }
    }
    return null;
  }

  @Override
  public ReservationDto findReservationByPassengerHash(String passengerHash) {
    for(Map.Entry<Integer, ReservationDto> reservation : reservations.entrySet()) {
      for (PassengerDto passengerDto : reservation.getValue().passengers) {
        if(passengerDto.passengerHash.equals(passengerHash)) {
          return reservation.getValue();
        }
      }
    }
    return null;
  }

  @Override
  public String findFlightNumberByPassengerHash(String passengerHash) {
    for(Map.Entry<Integer, ReservationDto> reservation : reservations.entrySet()) {
      for (PassengerDto passengerDto : reservation.getValue().passengers) {
        if(passengerDto.passengerHash.equals(passengerHash)) {
          return reservation.getValue().flightNumber;
        }
      }
    }
    return null;
  }

  public void saveReservation(ReservationDto reservation) {
    reservations.put(reservation.reservationNumber, reservation);
  }

}