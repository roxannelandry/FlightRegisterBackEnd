package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

@RunWith(MockitoJUnitRunner.class)
public class SeatTest {

  private static final String NUMBER = "15";

  private static final int ROW = 5;
  private static final int LEGROOM = 15;

  private static final float PRICE = 150.99f;
  private static final float PRICE_LOWER_THAN_SEAT_PRICE = 100.0f;
  private static final float PRICE_HIGHER_THAN_SEAT_PRICE = 200.0f;
  private static final float MAX_SEAT_PRICE_HIGHER = 300;
  private static final float MAX_SEAT_PRICE_LOWER = 100;

  private static final boolean WINDOW = true;
  private static final boolean NOT_WINDOW = false;
  private static final boolean CLEAR_VIEW = true;
  private static final boolean NOT_CLEAR_VIEW = false;

  private static final SeatClass CLASS_NAME = SeatClass.ECONOMY;
  private static final SeatClass OTHER_CLASS_NAME = SeatClass.BUSINESS;

  private List<Integer> rowsOfClasse;
  private List<Integer> exitRows;

  private Seat seat;

  @Before
  public void setUp() {
    rowsOfClasse = new ArrayList<Integer>();
    rowsOfClasse.add(ROW);

    exitRows = new ArrayList<Integer>();
    exitRows.add(ROW);

    seat = new Seat(ROW, NUMBER, LEGROOM, WINDOW, CLEAR_VIEW, PRICE);
  }

  @Test
  public void seat_gettingSeatNumber_returnFormatedSeatNumber() {
    String formatedSeatNumber = seat.getSeatNumber();

    assertEquals(formatedSeatNumber, Integer.toString(ROW) + "-" + NUMBER);
  }

  @Test
  public void unasignedSeat_assigningSeat_seatIsAssigned() {
    seat.assign();

    assertTrue(seat.isSeatAssigned());
  }

  @Test
  public void seatWithUndefinedClass_definingClass_seatNowHaveAClass() {
    seat.defineClass(CLASS_NAME, rowsOfClasse);

    assertTrue(seat.hasSameSeatClass(CLASS_NAME));
  }

  @Test
  public void seatWithDefinedClass_checkingIfHasSameSeatClassWithDifferentClass_returnFalse() {
    seat.defineClass(CLASS_NAME, rowsOfClasse);

    assertFalse(seat.hasSameSeatClass(OTHER_CLASS_NAME));
  }

  @Test
  public void seatWithRowNotInRowsOfClass_definingClasse_seatStillHaveNullClass() {
    seat.defineClass(CLASS_NAME, rowsOfClasse);

    assertFalse(seat.hasSameSeatClass(OTHER_CLASS_NAME));
  }

  @Test(expected = NullPointerException.class)
  public void seatWithDefinedClass_definingClasse_seatClassIsStillNull() {
    rowsOfClasse.clear();

    seat.defineClass(CLASS_NAME, rowsOfClasse);

    seat.hasSameSeatClass(OTHER_CLASS_NAME);
  }

  @Test
  public void exitRows_definingExitRow_setSeatExitRowIfInList() {
    seat.defineExitRow(exitRows);

    assertTrue(seat.isExitRow());
  }

  @Test
  public void priceHigherThatSeatPrice_comparingPrice_returnTrue() {
    assertTrue(seat.comparePrice(PRICE_HIGHER_THAN_SEAT_PRICE));
  }

  @Test
  public void priceLowerThatSeatPrice_comparingPrice_returnTrue() {
    assertFalse(seat.comparePrice(PRICE_LOWER_THAN_SEAT_PRICE));
  }

  @Test
  public void seatPriceLessThatMaxPrice_checkinIfSeatHasLowerPriceForSameView_returnTrue() {
    assertTrue(seat.hasLowerPriceForSameView(WINDOW, CLEAR_VIEW, MAX_SEAT_PRICE_HIGHER));
  }

  @Test
  public void seatPriceMoreThanMaxPrice_checkinIfSeatHasLowerPriceForSameView_returnFalse() {
    assertFalse(seat.hasLowerPriceForSameView(WINDOW, CLEAR_VIEW, MAX_SEAT_PRICE_LOWER));
  }

  @Test
  public void seatWithNoWindow_checkinIfSeatHasLowerPriceForSameView_returnFalse() {
    assertFalse(seat.hasLowerPriceForSameView(NOT_WINDOW, CLEAR_VIEW, MAX_SEAT_PRICE_HIGHER));
  }

  @Test
  public void seatWithNoClearView_checkinIfSeatHasLowerPriceForSameView_returnFalse() {
    assertFalse(seat.hasLowerPriceForSameView(WINDOW, NOT_CLEAR_VIEW, MAX_SEAT_PRICE_HIGHER));
  }

}