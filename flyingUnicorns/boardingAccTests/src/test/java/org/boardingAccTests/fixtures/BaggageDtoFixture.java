package org.boardingAccTests.fixtures;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;

public class BaggageDtoFixture {

  private static Pattern pattern = Pattern.compile("[a-zA-Z]");

  public BaggageRequestDto createBaggageDto(String baggageType, String weight, String dimension) {
    BaggageRequestDto baggageRequestDto = new BaggageRequestDto();
    baggageRequestDto.type = BaggageType.fromString(baggageType);

    mapWeightToBaggageDto(weight, baggageRequestDto);
    mapDimensionToBaggageDto(dimension, baggageRequestDto);

    return baggageRequestDto;
  }

  private void mapDimensionToBaggageDto(String dimension, BaggageRequestDto baggageRequestDto) {
    Matcher matcher = pattern.matcher(dimension);
    matcher.find();
    baggageRequestDto.linearDimension = Integer.parseInt(dimension.substring(0, matcher.start()));
    baggageRequestDto.linearDimensionUnit = dimension.substring(matcher.start());
  }

  private Matcher mapWeightToBaggageDto(String weight, BaggageRequestDto baggageRequestDto) {
    Matcher matcher = pattern.matcher(weight);
    matcher.find();
    baggageRequestDto.weight = Integer.parseInt(weight.substring(0, matcher.start()));
    baggageRequestDto.weightUnit = weight.substring(matcher.start());
    
    return matcher;
  }

}