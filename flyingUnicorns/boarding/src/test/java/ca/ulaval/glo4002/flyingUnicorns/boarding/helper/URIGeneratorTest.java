package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.exception.UriException;

public class URIGeneratorTest {

  private static String VALID_PATH_PARAM = "test";
  private static String VALID_PATH_PARAM_WITH_SLASH = "/test/";
  private static String INVALID_PATH_PARAM = "%$test";

  @Test
  public void location_addingLocationToResponseHeader_returnGoodLocationToResponseHeader() throws URISyntaxException {
    URI uri = URIGenerator.addLocationToResponseHeader(VALID_PATH_PARAM, 0);

    URI expectedLocation = new java.net.URI(VALID_PATH_PARAM_WITH_SLASH + 0);

    assertEquals(expectedLocation, uri);
  }

  @Test(expected = UriException.class)
  public void invalidResponseParameters_addingLocationToResponseHeader_throwsURISyntax() throws URISyntaxException {
    URIGenerator.addLocationToResponseHeader(INVALID_PATH_PARAM, 0);
  }

}