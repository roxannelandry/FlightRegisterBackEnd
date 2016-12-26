package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaggageTest {

  private int ANY_LINEAR_DIMENSION = 50;
  private int ANY_WEIGHT = 50;

  private BaggageType ANY_BAGGAGE_TYPE = BaggageType.CHECKED;

  @Mock
  private BaggagePriceCalculator anyBaggagePriceCalculator;

  @Mock
  private BaggageCounter baggageCounter;

  private Baggage anyBaggage;

  @Before
  public void setUp() {
    anyBaggage = new Baggage(ANY_LINEAR_DIMENSION, ANY_WEIGHT, ANY_BAGGAGE_TYPE, anyBaggagePriceCalculator);
  }

  @Test
  public void baggageCount_setPrice_delegateToBaggagePriceCalculator() {
    anyBaggage.setPrice(baggageCounter);

    verify(anyBaggagePriceCalculator).calculateBaggageCost(ANY_WEIGHT, ANY_LINEAR_DIMENSION, baggageCounter);
  }

  @Test
  public void totalBaggagePrice_addToPrice_baggagePriceAddedToTotalBaggagePrice() {
    float initialTotalPrice = 50;
    float priceOfBaggage = 100;
    anyBaggage.setPrice(priceOfBaggage);

    float expectedPrice = initialTotalPrice + priceOfBaggage;
    float actualPrice = anyBaggage.addToPrice(initialTotalPrice);

    Assert.assertEquals(actualPrice, expectedPrice, 0);
  }

}