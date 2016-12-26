package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import org.junit.Assert;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class SportsBaggagePriceCalculatorTest {

  private static boolean IS_NOT_VIP = false;
  private static boolean ANY_IS_VIP = true;

  private static int VALID_WEIGHT_IN_GRAMS = 1;
  private static int VALID_LINEAR_DIMENSION_IN_MM = 1;

  private static float DELTA = 0;
  private static float BASIC_SPORT_PRICE = 50f;
  private static float SPORT_BAGGAGE_CHARGE_IN_PERCENTAGE = 0.25f;
  protected static float OVERWEIGHT_CHARGE_IN_PERCENTAGE = 0.1f;

  private static final BaggageType SPORT_BAGGAGE_TYPE = BaggageType.SPORT;

  private static final SeatClass anySeatClass = SeatClass.ECONOMY;

  private SportsBaggagePriceCalculator anySportsBaggagePriceCalculator;

  private SportsBaggagePriceCalculator nonVipSportsEconomyBaggagePriceCalculator;

  private SportsBaggagePriceCalculator sportsEconomyBaggagePriceCalculator;

  private BaggageCounter baggageCounter;

  @Test
  public void firstSportsBaggage_calculateBaggageCost_returnsFree() {
    anySportsBaggagePriceCalculator = new SportsBaggagePriceCalculator(ANY_IS_VIP, anySeatClass);
    baggageCounter = new BaggageCounter();
    float returnedValue = anySportsBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);

    float expectedPrice = 0;
    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test
  public void secondSportNonVipEconomyBaggage_calculateBaggageCost_returnsCorrectPrice() {
    float delta = 0;
    nonVipSportsEconomyBaggagePriceCalculator = new SportsBaggagePriceCalculator(IS_NOT_VIP, SeatClass.ECONOMY);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(SPORT_BAGGAGE_TYPE);

    float returnedPrice = nonVipSportsEconomyBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM,
        baggageCounter);
    float expectedPrice = BASIC_SPORT_PRICE * (1 + SPORT_BAGGAGE_CHARGE_IN_PERCENTAGE);

    Assert.assertEquals(expectedPrice, returnedPrice, delta);
  }

  @Test
  public void overWeightSportBaggage_calculateBaggageCost_returnsCorrectPrice() {
    int invalidWeightInGrams = 23001;
    float delta = 0;
    sportsEconomyBaggagePriceCalculator = new SportsBaggagePriceCalculator(IS_NOT_VIP, SeatClass.ECONOMY);
    baggageCounter = new BaggageCounter();
    baggageCounter.incrementBaggageQuantity(SPORT_BAGGAGE_TYPE);

    float returnedPrice = sportsEconomyBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, VALID_LINEAR_DIMENSION_IN_MM,
        baggageCounter);
    float expectedPrice = (BASIC_SPORT_PRICE * (1 + SPORT_BAGGAGE_CHARGE_IN_PERCENTAGE)) * (1 + OVERWEIGHT_CHARGE_IN_PERCENTAGE);

    Assert.assertEquals(expectedPrice, returnedPrice, delta);
  }

}