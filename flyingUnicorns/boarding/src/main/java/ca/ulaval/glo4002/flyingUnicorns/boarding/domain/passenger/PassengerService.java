package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

public class PassengerService {

  private PassengerFactory passengerFactory;
  private PassengerRepository passengerRepository;

  private ReservationService reservationRepository;

  public PassengerService(PassengerFactory passengerFactory, PassengerRepository passengerRepository, ReservationService reservationRepository) {
    this.passengerFactory = passengerFactory;
    this.passengerRepository = passengerRepository;
    this.reservationRepository = reservationRepository;
  }

  public Passenger findPassenger(String passengerHash) {
    return findPassengerInRepo(passengerHash);
  }

  private Passenger findPassengerInRepo(String passengerHash) {
    Passenger passenger = getPassengerFromReservationServer(passengerHash);

    if (!passengerRepository.passengerExist(passengerHash)) {
      savePassengerIfCheckedIn(passenger);

      return passenger;
    } else {
      return updatePassengerStatus(passengerHash, passenger);
    }
  }

  private Passenger updatePassengerStatus(String passengerHash, Passenger passenger) {
    Passenger passengerToUpdate = passengerRepository.findPassenger(passengerHash);

    passengerToUpdate.isCheckin = passenger.isCheckin;
    passengerToUpdate.isVip = passenger.isVip;

    if (passengerToUpdate.isVip) {
      passengerToUpdate.makePassengerVip();
    }

    passengerRepository.savePassenger(passengerToUpdate);

    return passengerToUpdate;
  }

  private void savePassengerIfCheckedIn(Passenger passenger) {
    if (passenger.isCheckin()) {
      passengerRepository.savePassenger(passenger);
    }
  }

  private Passenger getPassengerFromReservationServer(String passengerHash) {
    PassengerDto passengerDto = reservationRepository.findPassengerByPassengerHash(passengerHash);
    return passengerFactory.createPassenger(passengerDto);
  }

  public void savePassenger(Passenger passenger) {
    passengerRepository.savePassenger(passenger);
  }

}