package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageResponseDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.PassengerBaggagesDto;

public class BaggageAssembler {

  private static final int KG_INTO_G = 1000;
  private static final int CM_INTO_MM = 10;

  private static final double INCH_INTO_MM = 25.4;
  private static final double POUND_INTO_GRAMS = 453.59237;

  private BaggagePriceCalculatorFactory baggagePriceCalculatorFactory;

  public BaggageAssembler(BaggagePriceCalculatorFactory baggagePriceCalculatorFactory) {
    this.baggagePriceCalculatorFactory = baggagePriceCalculatorFactory;
  }

  public Baggage assembleBaggage(BaggageRequestDto baggageDto, Passenger passenger) {
    BaggagePriceCalculator baggagePriceCalculator = baggagePriceCalculatorFactory.createBaggagePriceCalculator(baggageDto.type, passenger);
    int linearDimensionInMM = convertLinearDimensionToMM(baggageDto.linearDimensionUnit, baggageDto.linearDimension);
    int weightInGrams = convertWeigthToGrams(baggageDto.weightUnit, baggageDto.weight);

    return new Baggage(linearDimensionInMM, weightInGrams, baggageDto.type, baggagePriceCalculator);
  }

  private int convertLinearDimensionToMM(String linearDimensionUnit, int linearDimensiongiven) {
    int linearDimensionInMM;

    if (linearDimensionUnit.equals("cm")) {
      linearDimensionInMM = convertCMintoMM(linearDimensiongiven);
    } else {
      linearDimensionInMM = convertInchesIntoMM(linearDimensiongiven);
    }
    return linearDimensionInMM;
  }

  private int convertWeigthToGrams(String weightUnit, int weight) {
    int weightInGrams;

    if (weightUnit.equals("kg")) {
      weightInGrams = convertKGintoGrams(weight);
    } else {
      weightInGrams = convertPoundsIntoGrams(weight);
    }

    return weightInGrams;
  }

  private int convertKGintoGrams(int weight) {
    return weight * KG_INTO_G;
  }

  private int convertPoundsIntoGrams(int weight) {
    return (int) Math.ceil(weight * POUND_INTO_GRAMS);
  }

  private int convertCMintoMM(int linearDimension) {
    return linearDimension * CM_INTO_MM;
  }

  private int convertInchesIntoMM(int linearDimension) {
    return (int) Math.ceil(linearDimension * INCH_INTO_MM);
  }

  public AllowedBaggageDto assembleAllowedBaggageDto() {
    return new AllowedBaggageDto();
  }

  public BaggageResponseDto assembleBaggageDto(Baggage baggage) {
    return new BaggageResponseDto(baggage.getLinearDimensionInMM(), baggage.getWeightInGrams(), baggage.getPrice());
  }

  public PassengerBaggagesDto assembleFromPassenger(Passenger passenger) {
    PassengerBaggagesDto passengerBaggagesDto = new PassengerBaggagesDto();

    List<BaggageResponseDto> baggagesDto = new ArrayList<>();
    for (Baggage baggage : passenger.getBaggages()) {
      baggagesDto.add(assembleBaggageDto(baggage));
    }

    passengerBaggagesDto.baggages = baggagesDto;
    passengerBaggagesDto.totalPrice = passenger.getTotalBaggageCost();

    return passengerBaggagesDto;
  }

}