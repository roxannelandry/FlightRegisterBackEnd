package ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.passenger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerProvider;

public class HibernatePassengerRepositoryITest {

  private static final String PASSENGER_HASH = "fgsfgs3287fnsdbf";

  private static final boolean ANY_IS_CHILD = false;
  private static final boolean IS_NOT_VIP = false;
  private static final boolean IS_CHECKEDIN = true;

  private static final SeatClass SEAT_CLASS = SeatClass.ECONOMY;

  private EntityManager entityManager;

  private HibernatePassengerRepository passengerRepository;

  @Before
  public void createRepository() {
    entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
    EntityManagerProvider.setEntityManager(entityManager);
    passengerRepository = new HibernatePassengerRepository();
  }

  @After
  public void closeEntityManager() {
    entityManager.close();
    EntityManagerFactoryProvider.reset();
  }

  @Test
  public void passengerHash_findingPassenger_passengerIsReturned() {
    Passenger passenger = new Passenger(PASSENGER_HASH, SEAT_CLASS, ANY_IS_CHILD, IS_NOT_VIP, IS_CHECKEDIN);

    passengerRepository.savePassenger(passenger);

    passengerRepository.findPassenger(PASSENGER_HASH);

    assertEquals(passenger, passengerRepository.findPassenger(PASSENGER_HASH));
  }

  @Test(expected = PassengerNotFoundException.class)
  public void passengerHash_findingPassenger_throwPersistence() {
    passengerRepository.findPassenger(PASSENGER_HASH);
  }

  @Test
  public void passenger_savingPassenger_passengerIsSaved() {
    Passenger passenger = new Passenger(PASSENGER_HASH, SEAT_CLASS, ANY_IS_CHILD, IS_NOT_VIP, IS_CHECKEDIN);

    passengerRepository.savePassenger(passenger);

    assertEquals(passenger, passengerRepository.findPassenger(PASSENGER_HASH));
  }

  @Test(expected = PassengerAlreadyExistsException.class)
  public void twoPassengerWithSameHash_savingPassenger_throwPassengerAlreadyExists() {
    Passenger passenger = new Passenger(PASSENGER_HASH, SEAT_CLASS, ANY_IS_CHILD, IS_NOT_VIP, IS_CHECKEDIN);
    Passenger samePassenger = new Passenger(PASSENGER_HASH, SEAT_CLASS, ANY_IS_CHILD, IS_NOT_VIP, IS_CHECKEDIN);

    passengerRepository.savePassenger(passenger);

    passengerRepository.savePassenger(samePassenger);
  }

  @Test
  public void existingPassenger_checkingIfPassengerExist_returnTrue() {
    Passenger passenger = new Passenger(PASSENGER_HASH, SEAT_CLASS, ANY_IS_CHILD, IS_NOT_VIP, IS_CHECKEDIN);

    passengerRepository.savePassenger(passenger);

    assertTrue(passengerRepository.passengerExist(PASSENGER_HASH));
  }

  @Test
  public void nonExistentPassenger_checkingIfPassengerExist_returnFalse() {
    assertFalse(passengerRepository.passengerExist(PASSENGER_HASH));
  }

}