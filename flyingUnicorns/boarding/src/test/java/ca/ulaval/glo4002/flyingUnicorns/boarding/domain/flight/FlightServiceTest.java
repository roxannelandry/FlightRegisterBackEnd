package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

  private static final String ANY_FLIGHT_NUMBER = "1234";
  private static final String OTHER_FLIGHT_NUMBER = "9876";

  private static final int ANY_WEIGHT_IN_GRAMS = 10;

  @Mock
  private Passenger passenger;

  @Mock
  private Flight flight;

  @Mock
  private Plane plane;

  @Mock
  private FlightRepository flightRepository;

  @Mock
  private FlightFactory flightFactory;

  @Mock
  private SeatAssignationStrategy seatAssignationStrategy;

  @InjectMocks
  private FlightService flightService;

  @Before
  public void setup() {
    willReturn(flight).given(flightRepository).findFlightByFlightNumber(ANY_FLIGHT_NUMBER);
  }

  @Test
  public void existingFlightNumber_findingFlightByFlightNumber_returnFlightFoundInRepository() {
    Flight returnedFlight = flightService.findFlightByFlightNumber(ANY_FLIGHT_NUMBER);

    assertSame(flight, returnedFlight);
  }

  @Test
  public void existingFlightNumber_findingFlightByFlightNumber_delegateToRepository() {
    willReturn(true).given(flightRepository).flightExists(OTHER_FLIGHT_NUMBER);

    flightService.findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    verify(flightRepository).findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);
  }

  @Test
  public void nonExistentFlightNumber_findingFlightByFlightNumber_newFlightCreated() {
    willReturn(false).given(flightRepository).flightExists(OTHER_FLIGHT_NUMBER);

    flightService.findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    verify(flightFactory).createFlight(OTHER_FLIGHT_NUMBER);
  }

  @Test
  public void nonExistentFlightNumber_findingFlightByFlightNumber_saveInRepository() {
    willReturn(false).given(flightRepository).flightExists(OTHER_FLIGHT_NUMBER);
    willReturn(flight).given(flightFactory).createFlight(OTHER_FLIGHT_NUMBER);

    flightService.findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    verify(flightRepository).save(flight);
  }

  @Test
  public void flightNumber_findingPlaneByFlightNumber_returnPlaneInFlight() {
    willReturn(flight).given(flightRepository).findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    flightService.findPlaneFromFlightNumber(OTHER_FLIGHT_NUMBER);

    verify(flight).getPlane();
  }

  @Test
  public void flightNumberAndPlane_savingPlane_delegateToFlightRepository() {
    willReturn(flight).given(flightRepository).findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    flightService.updatePlane(OTHER_FLIGHT_NUMBER, plane);

    verify(flightRepository).findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);
  }

  @Test
  public void flightNumberAndPlane_updatingPlane_planeIsUpdated() {
    willReturn(flight).given(flightRepository).findFlightByFlightNumber(OTHER_FLIGHT_NUMBER);

    flightService.updatePlane(OTHER_FLIGHT_NUMBER, plane);

    verify(flight).updatePlane(plane);
  }

  @Test
  public void baggageAndFlightNumber_ensureBaggageCanBeAdded_delegateToFlight() {
    flightService.ensureBaggageCanBeAdded(ANY_FLIGHT_NUMBER, ANY_WEIGHT_IN_GRAMS);

    verify(flight).ensureBaggageCanBeAdded(ANY_WEIGHT_IN_GRAMS);
  }

  @Test
  public void baggageAndFlightNumber_addingBaggageWeight_delegateToFlight() {
    flightService.addBaggageWeight(ANY_FLIGHT_NUMBER, ANY_WEIGHT_IN_GRAMS);

    verify(flight).addBaggageWeight(ANY_WEIGHT_IN_GRAMS);
  }

}