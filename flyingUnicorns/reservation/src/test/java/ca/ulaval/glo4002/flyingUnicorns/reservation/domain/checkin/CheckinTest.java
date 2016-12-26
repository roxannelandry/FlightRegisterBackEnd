package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinTimeException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.InvalidCheckinException;

@RunWith(MockitoJUnitRunner.class)
public class CheckinTest {

  private static final String ANY_PASSENGER_HASH = "VeryPassenger";
  
  private static final boolean IS_NOT_VIP = false;
  private static final boolean IS_VIP = true;

  private static final int ANY_VALUE_WITHIN_ALLOWED_CHECKIN_TIME_INTERVAL_IN_MINUTES = 400;
  private static final int ANY_VALUE_BELOW_LOWER_BOUND_IN_MINUTES = 359;
  private static final int ANY_VALUE_ABOVE_UPPER_BOUND_IN_MINUTES = 2881;

  private static final LocalDateTime DEPARTURE_DATE = LocalDateTime.of(2016, Month.JANUARY, 10, 10, 00, 00);
  private static final LocalDateTime VALID_SELF_CHECKIN_TIME = DEPARTURE_DATE.minusMinutes(ANY_VALUE_WITHIN_ALLOWED_CHECKIN_TIME_INTERVAL_IN_MINUTES);
  private static final LocalDateTime SELF_CHECKIN_TOO_LATE = DEPARTURE_DATE.minusMinutes(ANY_VALUE_BELOW_LOWER_BOUND_IN_MINUTES);
  private static final LocalDateTime SELF_CHECKIN_TOO_EARLY = DEPARTURE_DATE.minusMinutes(ANY_VALUE_ABOVE_UPPER_BOUND_IN_MINUTES);

  private static final CheckinMode SELF_CHECKIN = CheckinMode.SELF;
  private static final CheckinMode AGENT_CHECKIN = CheckinMode.AGENT;
  
  @Test
  public void constructCheckinObject_byIsNotEmpty() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    assertFalse(selfCheckin.getBy().equals(""));
  }

  @Test
  public void constructCheckinObject_passengerHashToCheckinNotEmpty() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    assertFalse(selfCheckin.getPassengerToCheckinHash().equals(""));
  }

  @Test
  public void onCheckin_checkinIdIsNotNull() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    assertTrue(selfCheckin.getCheckinId() != null);
  }

  @Test(expected = InvalidCheckinException.class)
  public void constructCheckinWithEmptyByValue_throwCheckinInvalid() {
    new Checkin(ANY_PASSENGER_HASH, null, IS_NOT_VIP);
  }

  @Test(expected = InvalidCheckinException.class)
  public void constructCheckinWithEmptyPassengerHash_throwCheckinInvalid() {
    new Checkin(null, SELF_CHECKIN, IS_NOT_VIP);
  }

  @Test
  public void checkinByAgent_checkinTimeframeRespected_returnTrue() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, AGENT_CHECKIN, IS_NOT_VIP);

    boolean returnedValue = selfCheckin.checkinTimeframeRespected(DEPARTURE_DATE, SELF_CHECKIN_TOO_LATE);

    assertTrue(returnedValue);
  }

  @Test
  public void validCheckinTime_checkinTimeframeRespected_returnTrue() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    boolean returnedValue = selfCheckin.checkinTimeframeRespected(DEPARTURE_DATE, VALID_SELF_CHECKIN_TIME);

    assertTrue(returnedValue);
  }

  @Test(expected = CheckinTimeException.class)
  public void chekinTimeAfterAllowedHours_selfCheckin_throwCheckinTime() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    selfCheckin.checkinTimeframeRespected(DEPARTURE_DATE, SELF_CHECKIN_TOO_LATE);
  }

  @Test(expected = CheckinTimeException.class)
  public void checkinTimeBeforeAllowedHours_selfCheckin_throwCheckinTime() {
    Checkin selfCheckin = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    selfCheckin.checkinTimeframeRespected(DEPARTURE_DATE, SELF_CHECKIN_TOO_EARLY);
  }
  
  @Test  
  public void checkinSelfWithVip_checkingIfIsVip_returnTrue() {
    Checkin selfCheckinVip = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_VIP);

    assertTrue(selfCheckinVip.isVip());
  }
  
  @Test  
  public void checkinSelfWithoutVip_checkingIfIsVip_returnFalse() {
    Checkin selfCheckinVip = new Checkin(ANY_PASSENGER_HASH, SELF_CHECKIN, IS_NOT_VIP);

    assertFalse(selfCheckinVip.isVip());
  }

}