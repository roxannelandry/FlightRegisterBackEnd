package org.boardingAccTests.fixtures;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;

public interface RegisterBaggagesFixture {

  void whenRegisterBaggage(String passengerHash, BaggageRequestDto baggageDto);
  
}