package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class PassengerNotFoundExceptionMapper implements ExceptionMapper<PassengerNotFoundException> {

  @Override
  public Response toResponse(PassengerNotFoundException exception) {
    return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type("application/json").build();
  }

}