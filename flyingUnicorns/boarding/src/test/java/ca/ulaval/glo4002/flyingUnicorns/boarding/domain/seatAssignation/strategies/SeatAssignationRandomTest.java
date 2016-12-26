package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

@RunWith(MockitoJUnitRunner.class)
public class SeatAssignationRandomTest {

  private static final String NUMBER = "A-12";

  private static final int ROW = 2;
  private static final int LEG_ROOM = 3;

  private static final float FIRST_PRICE = 90.0f;
  private static final float SECOND_PRICE = 70.0f;

  private static final boolean WINDOW = true;
  private static final boolean CLEAR_VIEW = true;

  private Seat firstSeat;
  private Seat secondSeat;

  private List<Seat> seats;

  private SeatAssignationRandom seatAssignationRandom;

  @Before
  public void setUp() {
    createSeats();

    seatAssignationRandom = new SeatAssignationRandom();
  }

  @Test
  public void seats_determineSeatToAssign_randomSeatReturn() {
    seats.add(firstSeat);
    seats.add(secondSeat);

    Seat randomSeat = seatAssignationRandom.determineSeatToAssign(seats);

    assertTrue(seats.contains(randomSeat));
  }

  @Test(expected = NoSeatFoundException.class)
  public void emptySeats_determineSeatToAssign_throwNoSeatFound() {
    seatAssignationRandom.determineSeatToAssign(seats);
  }

  private void createSeats() {
    firstSeat = new Seat(ROW, NUMBER, LEG_ROOM, WINDOW, CLEAR_VIEW, FIRST_PRICE);
    secondSeat = new Seat(ROW, NUMBER, LEG_ROOM, WINDOW, CLEAR_VIEW, SECOND_PRICE);

    seats = new ArrayList<Seat>();

    seats.clear();
  }

}