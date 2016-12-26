package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CheckinAlreadyDoneMapper implements ExceptionMapper<CheckinAlreadyDoneException> {

  @Override
  public Response toResponse(CheckinAlreadyDoneException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}