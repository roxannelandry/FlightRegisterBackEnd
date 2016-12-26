package ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.flight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerProvider;

@RunWith(MockitoJUnitRunner.class)
public class HibernateFlightRepositoryITest {

  private static final String FLIGHT_NUMBER = "flightNumber";
  private static final String NON_EXISTENT_FLIGHT_NUMBER = "nonExistentFlightNumber";
  private static final String SEAT_NUMBER = "RS-123";

  private static final int SEAT_ROW = 3;
  private static final int SEAT_LEGROOM = 2;
  private static final int ANY_CARGO_WEIGHT = 3000;

  private static final float SEAT_PRICE = 20.0f;

  private static final boolean SEAT_WINDOW = true;
  private static final boolean SEAT_CLEAR_VIEW = true;

  private Plane plane;

  private List<Seat> seats;

  private Seat seat;

  private EntityManager entityManager;

  private HibernateFlightRepository flightRepository;

  @Before
  public void createRepository() {
    seat = new Seat(SEAT_ROW, SEAT_NUMBER, SEAT_LEGROOM, SEAT_WINDOW, SEAT_CLEAR_VIEW, SEAT_PRICE);

    seats = new ArrayList<Seat>();
    seats.add(seat);

    plane = new Plane(seats, ANY_CARGO_WEIGHT);

    entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
    EntityManagerProvider.setEntityManager(entityManager);
    flightRepository = new HibernateFlightRepository();
  }

  @After
  public void closeEntityManager() {
    entityManager.close();
    EntityManagerFactoryProvider.reset();
  }

  @Test(expected = FlightAlreadyExistsException.class)
  public void twoFlighWithSameNumber_savingFlight_throwFlightAlreadyExist() {
    Flight flight = new Flight(FLIGHT_NUMBER, plane);
    Flight sameFlight = new Flight(FLIGHT_NUMBER, plane);

    flightRepository.save(flight);

    flightRepository.save(sameFlight);
  }

  @Test
  public void repositoryWithFlight_tryingToFindFlightByFlightNumber_returnFlightWithSameNumber() {
    Flight flight = new Flight(FLIGHT_NUMBER, plane);
    flightRepository.save(flight);

    Flight flightFound = flightRepository.findFlightByFlightNumber(FLIGHT_NUMBER);

    assertEquals(flight.getFlightNumber(), flightFound.getFlightNumber());
  }

  @Test(expected = FlightNotFoundException.class)
  public void repositoryWithNoFlight_tryingToFindFlightByFlightNumber_throwFlightNotFound() {
    flightRepository.findFlightByFlightNumber(NON_EXISTENT_FLIGHT_NUMBER);
  }

  @Test
  public void repositoryWithFlight_checkingIfFlightExist_returnTrue() {
    Flight flight = new Flight(FLIGHT_NUMBER, plane);
    flightRepository.save(flight);

    assertTrue(flightRepository.flightExists(FLIGHT_NUMBER));
  }

  @Test
  public void repositoryWithoutFlight_checkingIfFlightExist_returnFalse() {
    assertFalse(flightRepository.flightExists(FLIGHT_NUMBER));
  }

}