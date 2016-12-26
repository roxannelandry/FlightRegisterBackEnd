package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Baggage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column
  private int linearDimensionInMM;

  @Column
  private int weightInGrams;

  @Column
  private BaggageType type;

  @Column
  private float price;

  @ElementCollection
  private Map<BaggageType, Boolean> canExceedWeightLimit;

  @OneToOne(targetEntity = BaggagePriceCalculator.class, cascade = { CascadeType.ALL })
  @JoinColumn(name = "baggage")
  private BaggagePriceCalculator calculator;

  public Baggage() { // for Hibernate
  }

  public Baggage(int linearDimension, int weight, BaggageType type, BaggagePriceCalculator baggagePriceCalculator) {
    linearDimensionInMM = linearDimension;
    weightInGrams = weight;
    this.type = type;
    calculator = baggagePriceCalculator;
    price = 0;
  }

  public void setPrice(BaggageCounter baggageCounter) {
    price = calculator.calculateBaggageCost(weightInGrams, linearDimensionInMM, baggageCounter);
  }

  public float addToPrice(float totalPrice) {
    return totalPrice + price;
  }

  protected int getLinearDimensionInMM() {
    return linearDimensionInMM;
  }

  protected int getWeightInGrams() {
    return weightInGrams;
  }

  protected void setPrice(float price) {
    this.price = price;
  }

  protected float getPrice() {
    return price;
  }

  public BaggageType getType() {
    return type;
  }

}