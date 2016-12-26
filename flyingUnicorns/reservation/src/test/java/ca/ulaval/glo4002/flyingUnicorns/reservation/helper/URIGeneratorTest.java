package ca.ulaval.glo4002.flyingUnicorns.reservation.helper;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception.UriException;

public class URIGeneratorTest {

  private static final String VALID_PATH_PARAM = "test";
  private static final String VALID_PATH_PARAM_WITH_SLASH = "/test/";
  private static final String INVALID_PATH_PARAM = "%$test";
  private static final String REDIRECTION_STRING = "454Y7";

  @Test
  public void response_addLocationToResponseHeader_returnGoodLocationToResponseHeader() throws URISyntaxException {
    Response response = URIGenerator.addLocationToResponseHeader(VALID_PATH_PARAM, REDIRECTION_STRING);

    URI expectedLocation = new java.net.URI(VALID_PATH_PARAM_WITH_SLASH + REDIRECTION_STRING);

    assertEquals(expectedLocation, response.getLocation());
  }

  @Test(expected = UriException.class)
  public void invalidResponseParameters_addLocationToResponseHeader_throwsURISyntax() throws URISyntaxException {
    URIGenerator.addLocationToResponseHeader(INVALID_PATH_PARAM, REDIRECTION_STRING);
  }

}