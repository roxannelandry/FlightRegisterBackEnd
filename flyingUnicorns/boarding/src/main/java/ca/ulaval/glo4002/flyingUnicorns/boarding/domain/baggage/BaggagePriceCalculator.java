package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaggagePriceCalculator {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  public BaggagePriceCalculator() { // For Hibernate
  }

  public abstract float calculateBaggageCost(int weightInGrams, int linearDimensionInMM, BaggageCounter baggageCounter);

  protected boolean isOverweight(int weightInGrams, int maximumWeightInGram) {
    return weightInGrams > maximumWeightInGram;
  }

  protected boolean isOversized(int linearDimensionInMM, int maximumSizeInMM) {
    return linearDimensionInMM > maximumSizeInMM;
  }

}