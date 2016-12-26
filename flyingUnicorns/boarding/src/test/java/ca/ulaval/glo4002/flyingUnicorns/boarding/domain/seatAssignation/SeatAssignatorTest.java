package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationStrategy;

@RunWith(MockitoJUnitRunner.class)
public class SeatAssignatorTest {

  private List<Seat> oneSeatList;

  private SeatAssignator seatAssignator;

  @Mock
  private Plane plane;

  @Mock
  private Seat seat;

  @Mock
  private Seat previousSeat;

  @Mock
  private SeatAssignationStrategy strategy;

  @Mock
  private Passenger passenger;

  @Before
  public void setup() {
    seatAssignator = new SeatAssignator(strategy);

    oneSeatList = new ArrayList<Seat>();
    oneSeatList.add(seat);

    willReturn(oneSeatList).given(plane).createFilteredSeatsList(passenger);
    willReturn(seat).given(strategy).determineSeatToAssign(oneSeatList);
  }

  @Test
  public void validPassengerAndPlane_assigningSeat_delegateToPlaneCreateFilteredSeatsList() {
    seatAssignator.assignSeat(plane, passenger);

    verify(plane).createFilteredSeatsList(passenger);
  }

  @Test
  public void validPassengerAndPlane_assigningSeat_delegateToStrategyDetermineSeatToAssign() {
    seatAssignator.assignSeat(plane, passenger);

    verify(strategy).determineSeatToAssign(oneSeatList);
  }

  @Test
  public void validPassengerAndPlane_assigningSeat_delegateToPlaneAssignSeat() {
    seatAssignator.assignSeat(plane, passenger);

    verify(plane).assignSeat(seat);
  }

  @Test
  public void validPassengerAndPlane_assigningSeat_returnRightSeat() {
    Seat returnedSeat = seatAssignator.assignSeat(plane, passenger);

    assertSame(seat, returnedSeat);
  }

  @Test
  public void validPassengerAndPlane_assigningSeat_callUnassignPreviousPassengerSeat() {
    willReturn(true).given(passenger).hasAssignedSeat();
    willReturn(previousSeat).given(passenger).getSeat();

    Seat previousSeat = passenger.getSeat();

    seatAssignator.assignSeat(plane, passenger);

    verify(plane).unassignSeat(previousSeat);
  }

}