package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import javax.persistence.Entity;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

@Entity
public class SportsBaggagePriceCalculator extends CheckedBaggagePriceCalculator {

  private static final float INITIAL_CHARGE_IN_PERCENTAGE = 0.25f;

  public SportsBaggagePriceCalculator() { // for Jackson & Hibernate
  }

  public SportsBaggagePriceCalculator(boolean isVip, SeatClass seatClass) {
    super(isVip, seatClass);
  }

  @Override
  public float calculateBaggageCost(int weightInGrams, int linearDimensionInMM, BaggageCounter baggageCounter) {
    float cost = CheckedBaggagePriceCalculator.INITIAL_COST;
    if (!isFree(baggageCounter.countSportAndCheckedBaggages())) {
      cost = EXCESS_BAGGAGE_CHARGE + EXCESS_BAGGAGE_CHARGE * INITIAL_CHARGE_IN_PERCENTAGE;
      if (isOverweight(weightInGrams, maximumWeightInGrams)) {
        cost += cost * OVERWEIGHT_CHARGE_IN_PERCENTAGE;
      }
    }
    return cost;
  }

}