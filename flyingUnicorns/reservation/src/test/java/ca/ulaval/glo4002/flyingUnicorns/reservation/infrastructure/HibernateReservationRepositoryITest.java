package ca.ulaval.glo4002.flyingUnicorns.reservation.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.ReservationNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class HibernateReservationRepositoryITest {
  
  private static final String ANY_VALID_FIRSTNAME = "AnyFirstName";
  private static final String ANY_VALID_LASTNAME = "AnyLastName";
  private static final String ANY_VALID_PASSPORT_NUMBER = "12345";
  private static final String ANY_VALID_SEAT_CLASS = "A";
  private static final String FLIGHT_NUMBER = "A3525";
  private static final String NON_EXISTENT_PASSENGER_HASH = "thdas5215jh";
  
  private static final int RESERVATION_NUMBER = 4;
  private static final int AGE = 12;

  private static final LocalDateTime FLIGHT_DATE = LocalDateTime.now();

  private List<Passenger> passengers;

  private Passenger passenger;

  private EntityManager entityManager;
  private HibernateReservationRepository reservationRepository;

  @Before
  public void createRepository() {
    passenger = new Passenger(ANY_VALID_FIRSTNAME, ANY_VALID_LASTNAME, AGE, ANY_VALID_PASSPORT_NUMBER, ANY_VALID_SEAT_CLASS);

    passengers = new ArrayList<Passenger>();
    passengers.add(passenger);

    entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
    EntityManagerProvider.setEntityManager(entityManager);
    reservationRepository = new HibernateReservationRepository();
  }

  @After
  public void closeEntityManager() {
    entityManager.close();
    EntityManagerFactoryProvider.reset();
  }
  
  @Test
  public void validResevation_savingReservation_savedReservation() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);

    reservationRepository.save(reservation);
        
    assertNotNull(reservationRepository.findReservationByReservationNumber(RESERVATION_NUMBER));
  }

  @Test(expected = InvalidReservationException.class)
  public void twoReservationWithSameNumber_savingReservation_throwInvalidReservation() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
    Reservation sameReservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);

    reservationRepository.save(reservation);

    reservationRepository.save(sameReservation);
  }

  @Test
  public void repositoryWithReservation_tryingToFindReservationByReservationNumber_returnReservationWithSameNumber() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
    reservationRepository.save(reservation);

    Reservation reservationFound = reservationRepository.findReservationByReservationNumber(RESERVATION_NUMBER);

    assertEquals(reservation.getReservationNumber(), reservationFound.getReservationNumber());
  }

  @Test(expected = ReservationNotFoundException.class)
  public void repositoryWithNoReservation_tryingToFindReservationByReservationNumber_throwReservationNotFound() {
    reservationRepository.findReservationByReservationNumber(RESERVATION_NUMBER);
  }

  @Test
  public void repositoryWithPassenger_tryingToFindPassengerWithHash_returnPassengerWithSamePassengerHash() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
    reservationRepository.save(reservation);

    Passenger passengerFound = reservationRepository.findPassengerByHash(passenger.getPassengerHash());

    assertEquals(passenger.getPassengerHash(), passengerFound.getPassengerHash());
  }

  @Test(expected = PassengerNotFoundException.class)
  public void repositoryWithPassenger_tryingToFindPassengerWithHash_throwPassengerNotFound() {
    reservationRepository.findReservationByPassengerHash(passenger.getPassengerHash());
  }

  @Test
  public void repositoryWithReservation_tryingToFindReservationByPassengerHash_returnReservationWithSamePassengerHash() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
    reservationRepository.save(reservation);

    Reservation reservationFound = reservationRepository.findReservationByPassengerHash(passenger.getPassengerHash());

    assertTrue(reservationFound.hasPassenger(passenger.getPassengerHash()));
  }

  @Test(expected = PassengerNotFoundException.class)
  public void repositoryWithNoReservation_tryingToFindReservationByPassengerHash_throwPassengerNotFound() {
    Reservation reservation = new Reservation(RESERVATION_NUMBER, FLIGHT_NUMBER, FLIGHT_DATE, passengers);
    reservationRepository.save(reservation);

    reservationRepository.findReservationByPassengerHash(NON_EXISTENT_PASSENGER_HASH);
  }

}