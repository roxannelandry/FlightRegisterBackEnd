package org.boardingAccTests.fixtures.medium;

import org.boardingAccTests.fixtures.RegisterBaggagesFixture;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;

public class MediumRegisterBaggagesFixture implements RegisterBaggagesFixture {
  
  private BaggageService baggageService;
  
  public MediumRegisterBaggagesFixture(BaggageService baggageService) {
    this.baggageService = baggageService;
  }

  @Override
  public void whenRegisterBaggage(String passengerHash, BaggageRequestDto baggageDto) {
    baggageService.registerBaggage(passengerHash, baggageDto);
  }
  
}