package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class FlightAlreadyExistsExceptionMapper implements ExceptionMapper<FlightAlreadyExistsException> {

  @Override
  public Response toResponse(FlightAlreadyExistsException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}