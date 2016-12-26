package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BaggageType {

  SPORT("sport"), MEDICAL("medical"), CHECKED("checked"), CARRY_ON("carry-on"), PERSONAL("personal");

  private final String value;

  private BaggageType(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  public static BaggageType fromString(String text) {
    return BaggageType.valueOf(text);
  }

}