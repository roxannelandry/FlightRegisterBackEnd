package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties({ "passenger_hash" })
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class PassengerBaggagesDto {

  public float totalPrice;
  public List<BaggageResponseDto> baggages;

}