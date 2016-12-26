package ca.ulaval.glo4002.flyingUnicorns.reservation.helper;

import java.net.URI;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception.UriException;

public class URIGenerator {

  public static Response addLocationToResponseHeader(String direction, String idToRedirect) {
    try {
      URI location = new URI("/" + direction + "/" + idToRedirect);

      return Response.created(location).build();

    } catch (Exception e) {
      throw new UriException("A problem happened on our side, please try again");
    }
  }

}