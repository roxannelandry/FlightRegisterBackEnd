package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.CarryOnBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.CheckedBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.MedicalBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.PersonalBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.SportsBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class BaggagePriceCalculatorFactoryTest {

  private static final String PASSENGER_HASH = "passenger_hash";

  private static final boolean IS_CHILD = false;
  private static final boolean IS_VIP = false;
  private static final boolean IS_CHECKIN = true;

  private Passenger passenger;

  private BaggagePriceCalculatorFactory baggagePriceCalculatorFactory;

  @Before
  public void setup() {
    baggagePriceCalculatorFactory = new BaggagePriceCalculatorFactory();
    passenger = new Passenger(PASSENGER_HASH, SeatClass.ECONOMY, IS_CHILD, IS_VIP, IS_CHECKIN);
  }

  @Test
  public void baggageTypeChecked_creatingBaggagePriceCalculator_returnCheckedBaggagePriceCalculator() {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(BaggageType.CHECKED, passenger);

    assertTrue(baggagePriceCalculator instanceof CheckedBaggagePriceCalculator);
  }

  @Test
  public void baggageTypeCarryOn_creatingBaggagePriceCalculator_returnCarryOnBaggagePriceCalculator() {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(BaggageType.CARRY_ON, passenger);

    assertTrue(baggagePriceCalculator instanceof CarryOnBaggagePriceCalculator);
  }

  @Test
  public void baggageTypeMedical_creatingBaggagePriceCalculator_returnMedicalBaggagePriceCalculator() {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(BaggageType.MEDICAL, passenger);

    assertTrue(baggagePriceCalculator instanceof MedicalBaggagePriceCalculator);
  }

  @Test
  public void baggageTypePersonal_creatingBaggagePriceCalculator_returnPersonalBaggagePriceCalculator() {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(BaggageType.PERSONAL, passenger);

    assertTrue(baggagePriceCalculator instanceof PersonalBaggagePriceCalculator);
  }

  @Test
  public void baggageTypeSport_creatingBaggagePriceCalculator_returnSportsBaggagePriceCalculator() {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(BaggageType.SPORT, passenger);

    assertTrue(baggagePriceCalculator instanceof SportsBaggagePriceCalculator);
  }

}