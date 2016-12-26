package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidReservationMapper implements ExceptionMapper<InvalidReservationException> {

  @Override
  public Response toResponse(InvalidReservationException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}