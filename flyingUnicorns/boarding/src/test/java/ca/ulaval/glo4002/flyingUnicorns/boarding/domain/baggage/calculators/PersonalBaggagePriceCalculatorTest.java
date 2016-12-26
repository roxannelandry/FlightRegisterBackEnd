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
public class PersonalBaggagePriceCalculatorTest {

  private static int VALID_WEIGHT_IN_GRAMS = 10000;
  private static int VALID_LINEAR_DIMENSION_IN_MM = 920;

  private static float DELTA = 0;

  @Mock
  private BaggageCounter baggageCounter;

  private PersonalBaggagePriceCalculator personalBaggagePriceCalculator;

  @Before
  public void setUp() {
    personalBaggagePriceCalculator = new PersonalBaggagePriceCalculator();
  }

  @Test
  public void firstValidPersonalBaggage_calculateBaggageCost_returnsFree() {
    personalBaggagePriceCalculator = new PersonalBaggagePriceCalculator();

    float returnedValue = personalBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);

    float expectedPrice = 0;
    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test(expected = OversizedBaggageException.class)
  public void oversizePersonalBaggage_calculateBaggageCost_throwsOversize() {
    int invalidLinearDimensionInMM = 921;
    personalBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, invalidLinearDimensionInMM, baggageCounter);
  }

  @Test(expected = OverweightBaggageException.class)
  public void overWeightPersonalBaggage_calculateBaggageCost_throwsOversize() {
    int invalidWeightInGrams = 10001;
    personalBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);
  }

}
