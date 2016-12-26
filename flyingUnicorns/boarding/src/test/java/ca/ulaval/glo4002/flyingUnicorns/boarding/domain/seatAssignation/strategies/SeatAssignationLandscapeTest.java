package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

@RunWith(MockitoJUnitRunner.class)
public class SeatAssignationLandscapeTest {

  private static final String NUMBER = "A-12";

  private static final int ROW = 2;
  private static final int LEG_ROOM_LOW = 2;

  private static final float PRICE_HIGH = 1.0f;
  private static final float PRICE_LOW = 0.0f;

  private static final boolean WINDOW = true;
  private static final boolean NO_WINDOW = false;
  private static final boolean CLEAR_VIEW = true;
  private static final boolean NO_CLEAR_VIEW = false;

  private Seat worstSeat;
  private Seat seatWithWindow;
  private Seat seatWithoutWindow;
  private Seat seatWithWindowAndClearView;
  private Seat moreExpensiveSeatWithWindowAndClearView;
  private Seat seatWithWindowAndWithoutClearView;

  private List<Seat> seats = new ArrayList<>();

  private SeatAssignationLandscape seatAssignationLandscape;

  @Before
  public void setup() {
    seats.clear();
    worstSeat = new Seat(ROW, NUMBER, LEG_ROOM_LOW, NO_WINDOW, NO_CLEAR_VIEW, PRICE_HIGH);
    seats.add(worstSeat);

    seatAssignationLandscape = new SeatAssignationLandscape();
  }

  @Test
  public void seatListWithOneSeat_determineSeatToAssign_returnTheSeat() {
    Seat returnedSeat = seatAssignationLandscape.determineSeatToAssign(seats);

    assertEquals(worstSeat, returnedSeat);
  }

  @Test(expected = NoSeatFoundException.class)
  public void emptySeatList_determineSeatToAssign_throwNoSeatFound() {
    seats.clear();
    seatAssignationLandscape.determineSeatToAssign(seats);
  }

  @Test
  public void listWithaSeatWithWindowAndaSeatWithoutWindow_determineSeatToAssign_returnSeatWithWindow() {
    seatWithWindow = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, CLEAR_VIEW, PRICE_LOW);
    seatWithoutWindow = new Seat(ROW, NUMBER, LEG_ROOM_LOW, NO_WINDOW, CLEAR_VIEW, PRICE_LOW);
    seats.add(seatWithoutWindow);
    seats.add(seatWithWindow);

    Seat returnedSeat = seatAssignationLandscape.determineSeatToAssign(seats);

    assertEquals(seatWithWindow, returnedSeat);
  }

  @Test
  public void listWithaSeatWindowsOneWithClearViewoneWithoutCleatView_determineSeatToAssign_returnSeatWithClearView() {
    seatWithWindowAndClearView = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, CLEAR_VIEW, PRICE_LOW);
    seatWithWindowAndWithoutClearView = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, NO_CLEAR_VIEW, PRICE_LOW);

    seats.add(seatWithWindowAndClearView);
    seats.add(seatWithWindowAndWithoutClearView);

    Seat returnedSeat = seatAssignationLandscape.determineSeatToAssign(seats);

    assertEquals(seatWithWindowAndClearView, returnedSeat);
  }

  @Test
  public void seatsWithSameViewAndDifferentPrice_determineSeatToAssign_returnSeatWithCheapestPrice() {
    seatWithWindowAndClearView = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, CLEAR_VIEW, PRICE_LOW);
    moreExpensiveSeatWithWindowAndClearView = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, CLEAR_VIEW, PRICE_HIGH);

    seats.add(moreExpensiveSeatWithWindowAndClearView);
    seats.add(seatWithWindowAndClearView);

    Seat returnedSeat = seatAssignationLandscape.determineSeatToAssign(seats);

    assertEquals(seatWithWindowAndClearView, returnedSeat);
  }

}