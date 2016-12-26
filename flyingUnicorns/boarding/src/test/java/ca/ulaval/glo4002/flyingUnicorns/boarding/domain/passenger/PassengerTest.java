package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

@RunWith(MockitoJUnitRunner.class)
public class PassengerTest {

  private static final String ANY_PASSENGER_HASH = "My name is Jeff";

  private static final float ZERO = 0;
  private static final float DELTA = 0;
  private static final float BAGGAGE_ONE_COST = 42;
  private static final float BAGGAGE_TWO_PLUS_BAGGAGE_ONE_COST = 142;
  private static final float DISCOUNT = 0.95f;

  private static final boolean IS_NOT_CHILD = false;
  private static final boolean IS_CHILD = true;
  private static final boolean IS_NOT_VIP = false;
  private static final boolean IS_VIP = true;
  private static final boolean IS_NOT_CHECKEDIN = false;
  private static final boolean IS_CHECKEDIN = true;

  private static final SeatClass ANY_SEAT_CLASS = SeatClass.ECONOMY;

  private Passenger notCheckedInpassenger;

  private Passenger checkedInPassenger;

  private Passenger vipPassenger;

  @Mock
  private Baggage baggageOne;

  @Mock
  private Baggage baggageTwo;

  @Mock
  private Seat reservedSeat;

  @Mock
  private BaggageCounter baggageCounter;

  @Before
  public void setup() {
    checkedInPassenger = new Passenger(ANY_PASSENGER_HASH, ANY_SEAT_CLASS, IS_NOT_CHILD, IS_NOT_VIP, IS_CHECKEDIN);
    notCheckedInpassenger = new Passenger(ANY_PASSENGER_HASH, ANY_SEAT_CLASS, IS_CHILD, IS_NOT_VIP, IS_NOT_CHECKEDIN);
    vipPassenger = new Passenger(ANY_PASSENGER_HASH, ANY_SEAT_CLASS, IS_CHILD, IS_VIP, IS_CHECKEDIN);

    willReturn(BAGGAGE_ONE_COST).given(baggageOne).addToPrice(ZERO);
    willReturn(BAGGAGE_TWO_PLUS_BAGGAGE_ONE_COST).given(baggageTwo).addToPrice(BAGGAGE_ONE_COST);
  }

  @Test
  public void creatingNewPassenger_passengerHasNoBaggage() {
    assertTrue(notCheckedInpassenger.baggageList.isEmpty());
  }

  @Test
  public void passenger_addingBaggage_baggageIsAddedToBaggageList() {
    willReturn(BaggageType.CHECKED).given(baggageOne).getType();
    checkedInPassenger.registerBaggage(baggageOne);

    assertTrue(checkedInPassenger.baggageList.contains(baggageOne));
  }

  @Test
  public void passengerWithNoBaggage_gettingTotalBaggageCost_returnZero() {
    float totalCost = checkedInPassenger.getTotalBaggageCost();

    assertEquals(ZERO, totalCost, DELTA);
  }

  @Test
  public void passengerWithBagages_gettingTotalBaggageCost_returnTotalCost() {
    willReturn(BaggageType.CHECKED).given(baggageOne).getType();
    willReturn(BaggageType.CHECKED).given(baggageTwo).getType();
    checkedInPassenger.registerBaggage(baggageOne);
    checkedInPassenger.registerBaggage(baggageTwo);

    float totalCost = checkedInPassenger.getTotalBaggageCost();

    assertEquals(BAGGAGE_TWO_PLUS_BAGGAGE_ONE_COST, totalCost, DELTA);
  }

  @Test
  public void vipPassengerWithBagages_gettingTotalBaggageCost_returnTotalCostWithDiscount() {
    willReturn(BaggageType.CHECKED).given(baggageOne).getType();
    willReturn(BaggageType.CHECKED).given(baggageTwo).getType();
    vipPassenger.registerBaggage(baggageOne);
    vipPassenger.registerBaggage(baggageTwo);

    float totalCost = vipPassenger.getTotalBaggageCost();

    assertEquals(BAGGAGE_TWO_PLUS_BAGGAGE_ONE_COST * DISCOUNT, totalCost, DELTA);
  }

  @Test(expected = PassengerNotCheckedInException.class)
  public void passengerThatIsNotCheckedIn_registerBaggage_throwPassengerNotCheckedIn() {
    notCheckedInpassenger.registerBaggage(baggageOne);
  }

  @Test
  public void childPassenger_checkingIfChild_returnTrue() {
    assertTrue(notCheckedInpassenger.isChild());
  }

  @Test
  public void adultPassenger_checkingIfChild_returnFalse() {
    assertFalse(checkedInPassenger.isChild());
  }

  @Test
  public void checkedInPassenger_checkingIfCheckin_returnTrue() {
    assertTrue(checkedInPassenger.isCheckin());
  }

  @Test
  public void notCheckedInPassenger_checkingIfCheckin_returnFalse() {
    assertFalse(notCheckedInpassenger.isCheckin());
  }

  @Test
  public void passengerWithAssignSeat_checkingIfHasAssignSeat_returnTrue() {
    notCheckedInpassenger.assignSeat(reservedSeat);

    assertTrue(notCheckedInpassenger.hasAssignedSeat());
  }

  @Test
  public void passengerWithNoAssignSeat_checkingIfHasAssignSeat_returnFalse() {
    assertFalse(checkedInPassenger.hasAssignedSeat());
  }

  @Test
  public void passengerWithNoAssignSeat_assigningSeat_seatIsAssigned() {
    checkedInPassenger.assignSeat(reservedSeat);

    assertTrue(checkedInPassenger.hasAssignedSeat());
    assertEquals(checkedInPassenger.getSeat(), reservedSeat);
  }

  @Test(expected = NoSeatFoundException.class)
  public void passengerWithNoAssignSeat_gettingSeat_throwNotFoundException() {
    checkedInPassenger.getSeat();
  }

}