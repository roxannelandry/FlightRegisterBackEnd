package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class NoSeatFoundExceptionMapper implements ExceptionMapper<NoSeatFoundException> {

  @Override
  public Response toResponse(NoSeatFoundException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}