package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.calculators.CheckedBaggagePriceCalculator;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageResponseDto;

@RunWith(MockitoJUnitRunner.class)
public class BaggageAssemblerTest {

  private static final int LINEAR_DIMENSION = 10;
  private static final int WEIGHT = 20;

  private static final float DELTA = 0;
  private static final float PRICE = 100;

  @Mock
  private BaggagePriceCalculatorFactory baggagePriceCalculatorFactory;

  @Mock
  private CheckedBaggagePriceCalculator checkedBaggagePriceCalculator;

  @Mock
  private Baggage baggage;

  private Passenger passenger;

  private BaggageAssembler baggageAssembler;

  private BaggageRequestDto baggageDto;

  @Before
  public void setup() {
    passenger = new Passenger("abc", SeatClass.ECONOMY, false, false, true);
    baggageAssembler = new BaggageAssembler(baggagePriceCalculatorFactory);

    willReturn(checkedBaggagePriceCalculator).given(baggagePriceCalculatorFactory).createBaggagePriceCalculator(BaggageType.CHECKED, passenger);
  }

  @Test
  public void linearDimensionsInCM_assemblingBaggage_cmIsConvertedIntoMM() {
    baggageDto = new BaggageRequestDto();
    baggageDto.linearDimension = 100;
    baggageDto.linearDimensionUnit = "cm";
    baggageDto.weight = 10;
    baggageDto.weightUnit = "kg";
    baggageDto.type = BaggageType.CHECKED;
    baggage = baggageAssembler.assembleBaggage(baggageDto, passenger);

    int expectedValue = 1000;
    assertEquals(expectedValue, baggage.getLinearDimensionInMM());
  }

  @Test
  public void linearDimensionsInInches_assemblingBaggage_cmIsConvertedIntoMM() {
    baggageDto = new BaggageRequestDto();
    baggageDto.linearDimension = 10;
    baggageDto.linearDimensionUnit = "po";
    baggageDto.weight = 10;
    baggageDto.weightUnit = "kg";
    baggageDto.type = BaggageType.CHECKED;
    baggage = baggageAssembler.assembleBaggage(baggageDto, passenger);

    int expectedValue = 254;
    assertEquals(expectedValue, baggage.getLinearDimensionInMM());
  }

  @Test
  public void weightInKg_assemblingBaggage_kgIsConvertedIntoGrams() {
    baggageDto = new BaggageRequestDto();
    baggageDto.linearDimension = 100;
    baggageDto.linearDimensionUnit = "cm";
    baggageDto.weight = 10;
    baggageDto.weightUnit = "kg";
    baggageDto.type = BaggageType.CHECKED;
    baggage = baggageAssembler.assembleBaggage(baggageDto, passenger);

    int expectedValue = 10000;
    assertEquals(expectedValue, baggage.getWeightInGrams());
  }

  @Test
  public void weightInPounds_assemblingBaggage_poundsIsConvertedIntoGrams() {
    baggageDto = new BaggageRequestDto();
    baggageDto.linearDimension = 100;
    baggageDto.linearDimensionUnit = "cm";
    baggageDto.weight = 5;
    baggageDto.weightUnit = "lbs";
    baggageDto.type = BaggageType.CHECKED;
    baggage = baggageAssembler.assembleBaggage(baggageDto, passenger);

    int expectedValue = 2268;
    assertEquals(expectedValue, baggage.getWeightInGrams());
  }

  @Test
  public void assemblingAllowedBaggageDto_returnNewAssembleAllowedBaggageDto() {
    AllowedBaggageDto allowedBaggageDto = baggageAssembler.assembleAllowedBaggageDto();
    assertTrue(allowedBaggageDto.allowed);
  }

  @Test
  public void baggage_assemblingBaggageDto_returnNewBaggageResponseDto() {
    willReturn(LINEAR_DIMENSION).given(baggage).getLinearDimensionInMM();
    willReturn(WEIGHT).given(baggage).getWeightInGrams();
    willReturn(PRICE).given(baggage).getPrice();

    BaggageResponseDto baggageResponseDto = baggageAssembler.assembleBaggageDto(baggage);

    assertEquals(baggageResponseDto.linearDimension, baggage.getLinearDimensionInMM());
    assertEquals(baggageResponseDto.weight, baggage.getWeightInGrams());
    assertEquals(baggageResponseDto.price, baggage.getPrice(), DELTA);
  }

}