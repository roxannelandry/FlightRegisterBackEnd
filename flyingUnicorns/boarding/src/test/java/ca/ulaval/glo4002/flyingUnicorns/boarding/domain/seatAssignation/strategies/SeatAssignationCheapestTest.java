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
public class SeatAssignationCheapestTest {

  private static final String NUMBER = "A-12";

  private static final int ROW = 2;
  private static final int LEG_ROOM = 2;

  private static final float PRICE_HIGH = 1.0f;
  private static final float PRICE_LOW = 0.0f;

  private static final boolean WINDOW = true;
  private static final boolean CLEAR_VIEW = true;

  private Seat seatHighPrice;
  private Seat seatLowPrice;
  private Seat secondSeatLowPrice;

  private List<Seat> seats;

  private SeatAssignationCheapest seatAssignationCheapest;

  @Before
  public void setUp() {
    createSeats();

    seatAssignationCheapest = new SeatAssignationCheapest();
  }

  @Test
  public void seatsWithDifferentPrices_determineSeatToAssign_cheapestSeatReturn() {
    seats.add(seatHighPrice);
    seats.add(seatLowPrice);

    Seat lowPriceSeat = seatAssignationCheapest.determineSeatToAssign(seats);

    assertEquals(seatLowPrice, lowPriceSeat);
  }

  @Test
  public void seatsWithSamePrices_determineSeatToAssign_firstCheapestSeatReturn() {
    seats.add(seatLowPrice);
    seats.add(secondSeatLowPrice);

    Seat lowPriceSeat = seatAssignationCheapest.determineSeatToAssign(seats);

    assertEquals(seatLowPrice, lowPriceSeat);
  }

  @Test(expected = NoSeatFoundException.class)
  public void noSeatAvailable_determineSeatToAssign_throwNoSeatFound() {
    seatAssignationCheapest.determineSeatToAssign(seats);
  }

  private void createSeats() {
    seatLowPrice = new Seat(ROW, NUMBER, LEG_ROOM, WINDOW, CLEAR_VIEW, PRICE_LOW);
    secondSeatLowPrice = new Seat(ROW, NUMBER, LEG_ROOM, WINDOW, CLEAR_VIEW, PRICE_LOW);
    seatHighPrice = new Seat(ROW, NUMBER, LEG_ROOM, WINDOW, CLEAR_VIEW, PRICE_HIGH);

    seats = new ArrayList<Seat>();

    seats.clear();
  }

}