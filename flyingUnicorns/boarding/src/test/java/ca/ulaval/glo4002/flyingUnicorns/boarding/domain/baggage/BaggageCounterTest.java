package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.TooManyBaggageException;

public class BaggageCounterTest {

  private BaggageCounter carryOnBaggageCounter;

  private BaggageCounter personnalBaggageCounter;

  private BaggageCounter sportNonVipBaggageCounter;

  private BaggageCounter sportVipBaggageCounter;

  private BaggageCounter checkedNonVipBaggageCounter;

  private BaggageCounter checkedVipBaggageCounter;

  private BaggageType baggageType;

  @Test(expected = TooManyBaggageException.class)
  public void tooManyCarryOnBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.CARRY_ON;
    carryOnBaggageCounter = new BaggageCounter();

    carryOnBaggageCounter.incrementBaggageQuantity(baggageType);
    carryOnBaggageCounter.incrementBaggageQuantity(baggageType);
    carryOnBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManyPersonalBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.PERSONAL;
    personnalBaggageCounter = new BaggageCounter();

    personnalBaggageCounter.incrementBaggageQuantity(baggageType);
    personnalBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManySportNonVipBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.SPORT;
    sportNonVipBaggageCounter = new BaggageCounter();

    sportNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportNonVipBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManySportVipBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.SPORT;
    sportVipBaggageCounter = new BaggageCounter();
    sportVipBaggageCounter.setVIPCheckedLimit();

    sportVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportVipBaggageCounter.incrementBaggageQuantity(baggageType);
    sportVipBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManyCheckedNonVipBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.CHECKED;
    checkedNonVipBaggageCounter = new BaggageCounter();

    checkedNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedNonVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedNonVipBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManyCheckedVipBaggage_ensurePassengerCanAddBaggage_throwsTooManyBaggage() {
    baggageType = BaggageType.CHECKED;
    checkedVipBaggageCounter = new BaggageCounter();
    checkedVipBaggageCounter.setVIPCheckedLimit();

    checkedVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedVipBaggageCounter.incrementBaggageQuantity(baggageType);
    checkedVipBaggageCounter.ensurePassengerCanAddBaggage(baggageType);
  }

}