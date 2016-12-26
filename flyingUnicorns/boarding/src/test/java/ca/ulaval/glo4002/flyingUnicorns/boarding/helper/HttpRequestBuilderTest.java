package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

import org.junit.Test;

public class HttpRequestBuilderTest {

  private static final String VALID_URL = "http://glo3000.ift.ulaval.ca/plane-blueprint/planes/QK-918/seats.json";

  @Test
  public void validUrl_preparingRequest_returnBuilderWithValidParameters() {
    HttpRequestBuilder.buildHttpRequestFromUrl(VALID_URL);
  }

}