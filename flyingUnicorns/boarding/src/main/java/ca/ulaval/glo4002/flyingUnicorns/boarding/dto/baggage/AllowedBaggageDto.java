package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AllowedBaggageDto {

  public boolean allowed = true;

}