package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.CarryOnBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.CheckedBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.MedicalBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.PersonalBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.SportsBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.InvalidBaggagePriceCaculatorException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;

public class BaggagePriceCalculatorFactory {

  public BaggagePriceCalculator createBaggagePriceCalculator(BaggageType type, Passenger passenger) {
    BaggagePriceCalculator calculator;

    switch (type) {
    case CHECKED:
      calculator = new CheckedBaggagePriceCalculator(passenger.isVip(), passenger.getSeatClass());
      break;
    case CARRY_ON:
      calculator = new CarryOnBaggagePriceCalculator();
      break;
    case MEDICAL:
      calculator = new MedicalBaggagePriceCalculator();
      break;
    case PERSONAL:
      calculator = new PersonalBaggagePriceCalculator();
      break;
    case SPORT:
      calculator = new SportsBaggagePriceCalculator(passenger.isVip(), passenger.getSeatClass());
      break;
    default:
      throw new InvalidBaggagePriceCaculatorException("The baggage type submitted does not exist");
    }
    return calculator;
  }

}