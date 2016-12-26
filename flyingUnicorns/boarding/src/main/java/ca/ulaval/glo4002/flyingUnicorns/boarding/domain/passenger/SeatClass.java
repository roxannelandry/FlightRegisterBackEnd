package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SeatClass {

  ECONOMY("economy"), BUSINESS("business"), BIGFRONT("big-front"), PREMIUMECONOMY("premium-economy");

  private final String value;

  private SeatClass(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

}