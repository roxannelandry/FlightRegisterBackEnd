package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

import java.net.URI;
import java.net.URISyntaxException;

import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.exception.UriException;

public class URIGenerator {

  public static URI addLocationToResponseHeader(String direction, int idToRedirect) {
    try {
      return new URI("/" + direction + "/" + idToRedirect);
    } catch (URISyntaxException e) {
      throw new UriException("A problem happened on our side, please try again");
    }
  }

}