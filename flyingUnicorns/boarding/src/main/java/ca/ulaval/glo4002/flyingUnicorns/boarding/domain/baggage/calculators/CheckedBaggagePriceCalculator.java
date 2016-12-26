package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators;

import javax.persistence.Column;
import javax.persistence.Entity;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

@Entity
public class CheckedBaggagePriceCalculator extends BaggagePriceCalculator {

  protected static final int FREE_ECONOMY_BAGGAGE_ALLOWANCE = 1;
  protected static final int FREE_BUSINESS_BAGGAGE_ALLOWANCE = 2;

  protected static final int MAXIMUM_ECONOMY_WEIGHT_IN_GRAMS = 23000;
  protected static final int MAXIMUM_BUSINESS_WEIGHT_IN_GRAMS = 30000;
  protected static final int MAXIMUM_SIZE_IN_MM = 1580;

  protected static final float INITIAL_COST = 0;
  protected static final float EXCESS_BAGGAGE_CHARGE = 50;

  protected static final float OVERWEIGHT_CHARGE_IN_PERCENTAGE = 0.1f;
  protected static final float OVERSIZED_CHARGE_IN_PERCENTAGE = 0.1f;

  protected int baggageAllowance;

  protected int maximumWeightInGrams;

  protected int freeBaggageAllowance;

  @Column
  private boolean isVip;

  public CheckedBaggagePriceCalculator() { // for Jackson & Hibernate
  }

  public CheckedBaggagePriceCalculator(boolean isVip, SeatClass seatClass) {
    initializeValues(seatClass);
  }

  private void initializeValues(SeatClass seatClass) {
    if (seatClass.equals(SeatClass.BUSINESS)) {
      maximumWeightInGrams = MAXIMUM_BUSINESS_WEIGHT_IN_GRAMS;
      freeBaggageAllowance = FREE_BUSINESS_BAGGAGE_ALLOWANCE;
    } else {
      maximumWeightInGrams = MAXIMUM_ECONOMY_WEIGHT_IN_GRAMS;
      freeBaggageAllowance = FREE_ECONOMY_BAGGAGE_ALLOWANCE;
    }
  }

  @Override
  public float calculateBaggageCost(int weightInGrams, int linearDimensionInMM, BaggageCounter baggageCounter) {
    float cost = INITIAL_COST;
    if (!isFree(baggageCounter.countSportAndCheckedBaggages())) {
      cost = EXCESS_BAGGAGE_CHARGE;
      if (isOverweight(weightInGrams, maximumWeightInGrams)) {
        cost += cost * OVERWEIGHT_CHARGE_IN_PERCENTAGE;
      }
      if (isOversized(linearDimensionInMM, MAXIMUM_SIZE_IN_MM)) {
        cost += cost * OVERSIZED_CHARGE_IN_PERCENTAGE;
      }
    }
    return cost;
  }

  protected boolean isFree(int count) {
    return count < freeBaggageAllowance;
  }

}