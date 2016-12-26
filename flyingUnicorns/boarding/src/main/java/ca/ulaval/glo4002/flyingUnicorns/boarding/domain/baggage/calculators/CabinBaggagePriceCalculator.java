package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OversizedBaggageException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OverweightBaggageException;

public abstract class CabinBaggagePriceCalculator extends BaggagePriceCalculator {

  protected void ensureHasValidDimensions(int weightInGrams, int linearDimensionInMM, int maximumWeightInGrams, int maximumDimensionInMM) {
    if (isOverweight(weightInGrams, maximumWeightInGrams)) {
      throw new OverweightBaggageException("Overweight baggage");
    }
    if (isOversized(linearDimensionInMM, maximumDimensionInMM)) {
      throw new OversizedBaggageException("Oversized baggage");
    }
  }

}