package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.TooManyBaggageException;

@Entity
public class BaggageCounter {

  private static final int INITIAL_NUMBER_OF_BAGGAGES = 0;
  private static final int INITIAL_LIMIT_OF_CHECKED_BAGGAGES = 3;

  private static final int VIP_LIMIT_OF_CHECKED_BAGGAGES = 4;
  private static final int VIP_LIMIT_OF_SPORT_BAGGAGES = 4;

  private static final int LIMIT_OF_CARRY_ON_BAGGAGES = 1;
  private static final int LIMIT_OF_PERSONAL_BAGGAGES = 1;
  private static final int LIMIT_OF_MEDICAL_BAGGAGES = Integer.MAX_VALUE;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ElementCollection
  private Map<BaggageType, Integer> baggageCountByType;

  @ElementCollection
  private Map<BaggageType, Integer> baggageLimitByType;

  public BaggageCounter() {
    initializeBaggageCountByType();
    initializeBaggageLimitByType();
  }

  private void initializeBaggageCountByType() {
    baggageCountByType = new HashMap<>();
    baggageCountByType.put(BaggageType.CHECKED, INITIAL_NUMBER_OF_BAGGAGES);
    baggageCountByType.put(BaggageType.CARRY_ON, INITIAL_NUMBER_OF_BAGGAGES);
    baggageCountByType.put(BaggageType.PERSONAL, INITIAL_NUMBER_OF_BAGGAGES);
    baggageCountByType.put(BaggageType.MEDICAL, INITIAL_NUMBER_OF_BAGGAGES);
    baggageCountByType.put(BaggageType.SPORT, INITIAL_NUMBER_OF_BAGGAGES);
  }

  private void initializeBaggageLimitByType() {
    baggageLimitByType = new HashMap<>();
    baggageLimitByType.put(BaggageType.CHECKED, INITIAL_LIMIT_OF_CHECKED_BAGGAGES);
    baggageLimitByType.put(BaggageType.CARRY_ON, LIMIT_OF_CARRY_ON_BAGGAGES);
    baggageLimitByType.put(BaggageType.SPORT, INITIAL_LIMIT_OF_CHECKED_BAGGAGES);
    baggageLimitByType.put(BaggageType.PERSONAL, LIMIT_OF_PERSONAL_BAGGAGES);
    baggageLimitByType.put(BaggageType.MEDICAL, LIMIT_OF_MEDICAL_BAGGAGES);
  }

  public void setVIPCheckedLimit() {
    baggageLimitByType.put(BaggageType.CHECKED, VIP_LIMIT_OF_CHECKED_BAGGAGES);
    baggageLimitByType.put(BaggageType.SPORT, VIP_LIMIT_OF_SPORT_BAGGAGES);
  }

  public void ensurePassengerCanAddBaggage(BaggageType baggageType) {
    if (baggageType.equals(BaggageType.CHECKED) || baggageType.equals(BaggageType.SPORT)) {
      ensureQuantityOfBaggagesIsRespected(countSportAndCheckedBaggages(), baggageLimitByType.get(baggageType));
    } else {
      ensureQuantityOfBaggagesIsRespected(baggageCountByType.get(baggageType), baggageLimitByType.get(baggageType));
    }
  }

  public int countSportAndCheckedBaggages() {
    int numberOfCheckedBaggages = 0;
    if (baggageCountByType.containsKey(BaggageType.CHECKED)) {
      numberOfCheckedBaggages += baggageCountByType.get(BaggageType.CHECKED);
    }
    if (baggageCountByType.containsKey(BaggageType.SPORT)) {
      numberOfCheckedBaggages += baggageCountByType.get(BaggageType.SPORT);
    }

    return numberOfCheckedBaggages;
  }

  private void ensureQuantityOfBaggagesIsRespected(int count, int limit) {
    if ((count + 1) > limit) {
      throw new TooManyBaggageException("Too many baggages");
    }
  }

  public void incrementBaggageQuantity(BaggageType type) {
    baggageCountByType.put(type, baggageCountByType.get(type) + 1);
  }

}