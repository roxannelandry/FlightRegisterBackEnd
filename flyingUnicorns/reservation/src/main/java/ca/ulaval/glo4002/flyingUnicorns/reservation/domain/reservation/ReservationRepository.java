package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;

public interface ReservationRepository {

  Passenger findPassengerByHash(String passengerHash);

  Reservation findReservationByPassengerHash(String passengerHash);

  Reservation findReservationByReservationNumber(int reservationNumber);

  void save(Reservation reservation);

}