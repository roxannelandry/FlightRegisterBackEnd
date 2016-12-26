package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidPassengerMapper implements ExceptionMapper<InvalidPassengerException> {

  @Override
  public Response toResponse(InvalidPassengerException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}