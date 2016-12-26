package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class FlightNotFoundExceptionMapper implements ExceptionMapper<FlightNotFoundException> {

  @Override
  public Response toResponse(FlightNotFoundException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}