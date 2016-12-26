package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OversizedBaggageException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OverweightBaggageException;

@RunWith(MockitoJUnitRunner.class)
public class CarryOnBaggagePriceCalculatorTest {

  private static final int VALID_WEIGHT_IN_GRAMS = 10000;
  private static final int VALID_LINEAR_DIMENSION_IN_MM = 1080;

  private static final float DELTA = 0;

  @Mock
  private BaggageCounter baggageCounter;

  private CarryOnBaggagePriceCalculator carryOnBaggagePriceCalculator;

  @Before
  public void setUp() {
    carryOnBaggagePriceCalculator = new CarryOnBaggagePriceCalculator();
  }

  @Test
  public void firstValidCarryOnBaggage_calculateBaggageCost_returnsFree() {
    carryOnBaggagePriceCalculator = new CarryOnBaggagePriceCalculator();
    float expectedPrice = 0;
    float returnedValue = carryOnBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);

    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test(expected = OversizedBaggageException.class)
  public void oversizeCarryOnBaggage_calculateBaggageCost_throwsOversizeBaggage() {
    int invalidLinearDimensionInMM = 1181;

    carryOnBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, invalidLinearDimensionInMM, baggageCounter);
  }

  @Test(expected = OverweightBaggageException.class)
  public void overWeightCarryOnBaggage_calculateBaggageCost_throwsOversizeBaggage() {
    int invalidWeightInGrams = 10001;

    carryOnBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);
  }

}