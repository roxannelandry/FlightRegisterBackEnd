package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import org.junit.Assert;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class CheckedBaggagePriceCalculatorTest {

  private static final int VALID_WEIGHT_IN_GRAMS = 10000;
  private static final int VALID_LINEAR_DIMENSION_IN_MM = 1080;

  private static final float DELTA = 0;
  private static final float BASIC_CHECKED_PRICE = 50f;
  protected static final float OVERWEIGHT_CHARGE_IN_PERCENTAGE = 0.1f;
  protected static final float OVERSIZED_CHARGE_IN_PERCENTAGE = 0.1f;

  private static final boolean IS_NOT_VIP = false;
  private static final boolean ANY_IS_VIP = true;

  private static final SeatClass ANY_SEAT_CLASS = SeatClass.ECONOMY;
  private static final SeatClass BUSINESS_SEAT_CLASS = SeatClass.BUSINESS;

  private static final BaggageType CHECKED_BAGGAGE_TYPE = BaggageType.CHECKED;

  private CheckedBaggagePriceCalculator anyCheckedBaggagePriceCalculator;

  private CheckedBaggagePriceCalculator checkedNonVipBaggagePriceCalculator;

  private CheckedBaggagePriceCalculator checkedEconomyBaggagePriceCalculator;

  private CheckedBaggagePriceCalculator checkedBusinessBaggagePriceCalculator;

  private BaggageCounter baggageCounter;

  @Test
  public void firstCheckedEconomyBaggage_calculateBaggageCost_returnsFree() {
    anyCheckedBaggagePriceCalculator = new CheckedBaggagePriceCalculator(ANY_IS_VIP, ANY_SEAT_CLASS);
    baggageCounter = new BaggageCounter();
    float returnedValue = anyCheckedBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);

    float expectedPrice = 0;
    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test
  public void firstandSecondCheckedBusinessBaggage_calculateBaggageCost_returnsFree() {
    anyCheckedBaggagePriceCalculator = new CheckedBaggagePriceCalculator(ANY_IS_VIP, BUSINESS_SEAT_CLASS);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);
    float returnedValue = anyCheckedBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);

    float expectedPrice = 0;
    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test
  public void oneRegisteredBaggage_calculateBaggageCost_priceCalculatedIs50() {
    checkedNonVipBaggagePriceCalculator = new CheckedBaggagePriceCalculator(IS_NOT_VIP, SeatClass.ECONOMY);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);
    float returnedPrice = checkedNonVipBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM,
        baggageCounter);
    float expectedPrice = 50;

    Assert.assertEquals(expectedPrice, returnedPrice, DELTA);
  }

  @Test
  public void overWeightCheckedEconomyBaggage_calculateBaggageCost_returnsCorrectPrice() {
    int invalidWeightInGrams = 23001;
    float delta = 0;
    checkedEconomyBaggagePriceCalculator = new CheckedBaggagePriceCalculator(IS_NOT_VIP, SeatClass.ECONOMY);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);

    float returnedPrice = checkedEconomyBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, VALID_LINEAR_DIMENSION_IN_MM,
        baggageCounter);
    float expectedPrice = BASIC_CHECKED_PRICE * (1 + OVERWEIGHT_CHARGE_IN_PERCENTAGE);

    Assert.assertEquals(expectedPrice, returnedPrice, delta);
  }

  @Test
  public void overWeightCheckedBusinessBaggage_calculateBaggageCost_returnsCorrectPrice() {
    int invalidWeightInGrams = 30001;
    float delta = 0;
    checkedBusinessBaggagePriceCalculator = new CheckedBaggagePriceCalculator(IS_NOT_VIP, BUSINESS_SEAT_CLASS);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);

    float returnedPrice = checkedBusinessBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, VALID_LINEAR_DIMENSION_IN_MM,
        baggageCounter);
    float expectedPrice = BASIC_CHECKED_PRICE * (1 + OVERWEIGHT_CHARGE_IN_PERCENTAGE);

    Assert.assertEquals(expectedPrice, returnedPrice, delta);
  }

  @Test
  public void oversizedCheckedBaggage_calculateBaggageCost_returnsCorrectPrice() {
    int invalidSizeInMm = 1581;
    float delta = 0;
    checkedEconomyBaggagePriceCalculator = new CheckedBaggagePriceCalculator(IS_NOT_VIP, SeatClass.ECONOMY);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(CHECKED_BAGGAGE_TYPE);

    float returnedPrice = checkedEconomyBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, invalidSizeInMm, baggageCounter);
    float expectedPrice = BASIC_CHECKED_PRICE * (1 + OVERSIZED_CHARGE_IN_PERCENTAGE);

    Assert.assertEquals(expectedPrice, returnedPrice, delta);
  }

}