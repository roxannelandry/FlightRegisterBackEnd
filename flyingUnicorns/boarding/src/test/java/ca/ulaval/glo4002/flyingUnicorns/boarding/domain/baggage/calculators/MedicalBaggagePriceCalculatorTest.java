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
public class MedicalBaggagePriceCalculatorTest {

  private static final int VALID_WEIGHT_IN_GRAMS = 1;
  private static final int VALID_LINEAR_DIMENSION_IN_MM = 1;

  private static float DELTA = 0;

  @Mock
  private BaggageCounter baggageCounter;

  private MedicalBaggagePriceCalculator medicalBaggagePriceCalculator;

  @Before
  public void setUp() {
    medicalBaggagePriceCalculator = new MedicalBaggagePriceCalculator();
  }

  @Test
  public void firstValidMedicalBaggage_calculateBaggageCost_returnsFree() {
    medicalBaggagePriceCalculator = new MedicalBaggagePriceCalculator();
    float returnedValue = medicalBaggagePriceCalculator.calculateBaggageCost(VALID_WEIGHT_IN_GRAMS, VALID_LINEAR_DIMENSION_IN_MM, baggageCounter);
    float expectedPrice = 0;

    Assert.assertEquals(expectedPrice, returnedValue, DELTA);
  }

  @Test(expected = OversizedBaggageException.class)
  public void oversizeMedicalBaggage_calculateBaggageCost_throwsOversizedBaggage() {
    int validWeightInGrams = 10000;
    int invalidLinearDimensionInMM = 1181;

    medicalBaggagePriceCalculator.calculateBaggageCost(validWeightInGrams, invalidLinearDimensionInMM, baggageCounter);
  }

  @Test(expected = OverweightBaggageException.class)
  public void overWeightmedicalBaggage_calculateBaggageCost_throwsOverweightBaggage() {
    int invalidWeightInGrams = 10001;
    int validLinearDimensionInMM = 1180;

    medicalBaggagePriceCalculator.calculateBaggageCost(invalidWeightInGrams, validLinearDimensionInMM, baggageCounter);
  }

}