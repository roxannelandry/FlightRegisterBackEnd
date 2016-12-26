package ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class UriExceptionMapper implements ExceptionMapper<UriException> {

  @Override
  public Response toResponse(UriException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}