package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;

@RunWith(MockitoJUnitRunner.class)
public class FlightTest {

  private static final String SEAT_NUMBER = "R-12";

  private static final int ANY_WEIGHT_IN_GRAMS = 100;
  private static final int INITIAL_BAGGAGE_WEIGHT = 0;

  @Mock
  private Passenger passenger;

  @Mock
  private SeatAssignationStrategy seatAssignationStrategy;

  @Mock
  private Plane plane;

  @Mock
  private Seat seat;

  @InjectMocks
  private Flight flight;

  @Before
  public void setUp() {
    willReturn(SEAT_NUMBER).given(seat).getSeatNumber();
  }

  @Test
  public void baggageToAdd_ensureBaggageCanBeAdded_delegateToPlane() {
    flight.ensureBaggageCanBeAdded(ANY_WEIGHT_IN_GRAMS);

    verify(plane).ensureBaggageCanBeAdded(flight.baggagesWeightInGrams, ANY_WEIGHT_IN_GRAMS);
  }

  @Test
  public void constructFlight_baggageWeightIsZero() {
    assertEquals(flight.baggagesWeightInGrams, INITIAL_BAGGAGE_WEIGHT);
  }

  @Test
  public void baggageToAdd_addingBaggageWeight_weightAddedToBaggagesWeightInGrams() {
    flight.addBaggageWeight(INITIAL_BAGGAGE_WEIGHT);

    assertEquals(flight.baggagesWeightInGrams, INITIAL_BAGGAGE_WEIGHT);
  }

  @Test
  public void plane_updatingPlane_planeIsUpdated() {
    willReturn(false).given(plane).hasAvailableSeats(SeatClass.ECONOMY);

    flight.updatePlane(plane);

    assertFalse(flight.getPlane().hasAvailableSeats(SeatClass.ECONOMY));
  }

}