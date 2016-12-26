package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import javax.persistence.Entity;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;

@Entity
public class CarryOnBaggagePriceCalculator extends CabinBaggagePriceCalculator {

  private static final float COST = 0;
  private static final int MAXIMUM_WEIGHT_IN_GRAMS = 10000;
  private static final int MAXIMUM_SIZE_IN_MM = 1180;

  @Override
  public float calculateBaggageCost(int weightInGrams, int linearDimensionInMM, BaggageCounter baggageCounter) {
    ensureHasValidDimensions(weightInGrams, linearDimensionInMM, MAXIMUM_WEIGHT_IN_GRAMS, MAXIMUM_SIZE_IN_MM);
    return COST;
  }

}