package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OverweightBaggageException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.FullPlaneException;

@RunWith(MockitoJUnitRunner.class)
public class PlaneTest {

  private static final String SEAT_NUMBER = "1-1";

  private static final int CARGO_WEIGHT = 3000;
  private static final int CARGO_WEIGHT_HIGHER_THAN_CARGO_WEIGHT_IN_GRAMS = 3000001;

  private static final boolean IS_CHILD = true;

  private static final SeatClass SEAT_CLASS = SeatClass.ECONOMY;

  private Seat seat;

  private List<Seat> emptySeatList;
  private List<Seat> oneSeatSameClassList;
  private List<Seat> oneSeatListDifferentClass;
  private List<Seat> twoSeatList;

  private Plane emptyPlane;
  private Plane oneSeatPlane;
  private Plane oneSeatPlaneDifferentClass;
  private Plane twoSeatPlane;

  @Mock
  private Passenger passenger;

  @Mock
  private Seat seatWithDifferentClass;

  @Before
  public void setUp() {
    emptySeatList = new ArrayList<Seat>();
    emptyPlane = new Plane(emptySeatList, CARGO_WEIGHT);

    seat = new Seat(1, "1", 1, true, true, 20.50f);
    seat.setSeatClass(SEAT_CLASS);
    seat.setIsExitRow(true);

    oneSeatSameClassList = new ArrayList<Seat>();
    oneSeatSameClassList.add(seat);
    oneSeatPlane = new Plane(oneSeatSameClassList, CARGO_WEIGHT);

    oneSeatListDifferentClass = new ArrayList<Seat>();
    oneSeatListDifferentClass.add(seatWithDifferentClass);
    oneSeatPlaneDifferentClass = new Plane(oneSeatListDifferentClass, CARGO_WEIGHT);

    twoSeatList = new ArrayList<Seat>();
    twoSeatList.add(seat);
    twoSeatList.add(seatWithDifferentClass);
    twoSeatPlane = new Plane(twoSeatList, CARGO_WEIGHT);

    willReturn(false).given(seatWithDifferentClass).isSeatAssigned();
    willReturn(false).given(seatWithDifferentClass).hasSameSeatClass(SEAT_CLASS);

    willReturn(SEAT_CLASS).given(passenger).getSeatClass();
  }

  @Test
  public void planeWithOneSeat_assigningSeat_planeHasNoMoreSeatAvailable() {
    oneSeatPlane.assignSeat(seat);

    assertFalse(oneSeatPlane.hasAvailableSeats(SEAT_CLASS));
  }

  @Test(expected = FullPlaneException.class)
  public void planeWithOneSeatThatIsExitRow_creatingFilteredSeatsListWithChildPassenger_throwFullPlane() {
    willReturn(IS_CHILD).given(passenger).isChild();

    oneSeatPlane.createFilteredSeatsList(passenger);
  }

  @Test
  public void planeTwoSeatsWithDifferentClasses_creatingFilteredSeatsList_returnListWithOneSameClassSeat() {
    List<Seat> filteredSeatList = twoSeatPlane.createFilteredSeatsList(passenger);

    assertEquals(SEAT_NUMBER, filteredSeatList.get(0).getSeatNumber());
  }

  @Test(expected = FullPlaneException.class)
  public void planeOneSeatWithDifferentClassLeft_creatingFilteredSeatsListthrowFullPlane() {
    oneSeatPlaneDifferentClass.createFilteredSeatsList(passenger);
  }

  @Test(expected = OverweightBaggageException.class)
  public void baggageThatPutsCargoWeightOverLimit_ensureBaggageCanBeAdded_throwOverweightBaggage() {
    emptyPlane.ensureBaggageCanBeAdded(0, CARGO_WEIGHT_HIGHER_THAN_CARGO_WEIGHT_IN_GRAMS);
  }

  @Test
  public void planeWithEconomyAvailableSeats_checkingIfHasAvailableSeatsForEconomy_returnTrue() {
    assertTrue(oneSeatPlane.hasAvailableSeats(SEAT_CLASS));
  }

  @Test
  public void planeWithNoEconomyAvailableSeats_checkingIfHasAvailableSeatsForEconomy_returnFalse() {
    assertFalse(emptyPlane.hasAvailableSeats(SEAT_CLASS));
  }

  @Test
  public void planeWithOneSeat_assigningSeatAndUnasigned_planeHasSeatAvailable() {
    oneSeatPlane.assignSeat(seat);
    assertFalse(oneSeatPlane.hasAvailableSeats(SEAT_CLASS));
    oneSeatPlane.unassignSeat(seat);
    assertTrue(oneSeatPlane.hasAvailableSeats(SEAT_CLASS));
  }

}