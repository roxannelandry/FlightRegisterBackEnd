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
public class SeatAssignationLegroomTest {

  private static final String NUMBER = "A-12";

  private static final int ROW = 2;
  private static final int LEG_ROOM_HIGH = 3;
  private static final int LEG_ROOM_LOW = 2;

  private static final float PRICE_HIGH = 1.0f;
  private static final float PRICE_LOW = 0.0f;

  private static final boolean WINDOW = true;
  private static final boolean CLEAR_VIEW = true;

  private Seat seatHighLegRoom;
  private Seat seatLowLegRoom;
  private Seat seatSameLegRoom;
  private Seat seatSameLegRoomSamePrice;

  private List<Seat> seats;

  private SeatAssignationLegroom seatAssignationLegRoom;

  @Before
  public void setUp() {
    createSeats();

    seatAssignationLegRoom = new SeatAssignationLegroom();
  }

  @Test
  public void seatsWithDifferentLegRoom_determineSeatToAssign_maxLegRoomSeatReturn() {
    seats.add(seatHighLegRoom);
    seats.add(seatLowLegRoom);

    Seat highLegRoomSeat = seatAssignationLegRoom.determineSeatToAssign(seats);

    assertEquals(seatHighLegRoom, highLegRoomSeat);
  }

  @Test
  public void sameLegRoomTwoSeatDifferentPrice_determineSeatToAssign_minPriceSeatReturn() {
    seats.add(seatHighLegRoom);
    seats.add(seatSameLegRoom);

    Seat sameLegRoomSeat = seatAssignationLegRoom.determineSeatToAssign(seats);

    assertEquals(seatSameLegRoom, sameLegRoomSeat);
  }

  @Test
  public void sameLegRoomTwoSeatSamePrice_determineSeatToAssign_firstSeatInBothIsReturn() {
    seats.add(seatSameLegRoom);
    seats.add(seatSameLegRoomSamePrice);

    Seat sameLegRoomSeat = seatAssignationLegRoom.determineSeatToAssign(seats);

    assertEquals(seatSameLegRoom, sameLegRoomSeat);
  }

  @Test(expected = NoSeatFoundException.class)
  public void noSeatAvailable_determineSeatToAssign_throwNoSeatFound() {
    seatAssignationLegRoom.determineSeatToAssign(seats);
  }

  private void createSeats() {
    seatLowLegRoom = new Seat(ROW, NUMBER, LEG_ROOM_LOW, WINDOW, CLEAR_VIEW, PRICE_LOW);
    seatHighLegRoom = new Seat(ROW, NUMBER, LEG_ROOM_HIGH, WINDOW, CLEAR_VIEW, PRICE_HIGH);
    seatSameLegRoom = new Seat(ROW, NUMBER, LEG_ROOM_HIGH, WINDOW, CLEAR_VIEW, PRICE_LOW);
    seatSameLegRoomSamePrice = new Seat(ROW, NUMBER, LEG_ROOM_HIGH, WINDOW, CLEAR_VIEW, PRICE_LOW);

    seats = new ArrayList<Seat>();

    seats.clear();
  }

}