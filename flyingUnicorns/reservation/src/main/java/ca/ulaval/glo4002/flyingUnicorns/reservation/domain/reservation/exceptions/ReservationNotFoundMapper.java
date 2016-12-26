package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class ReservationNotFoundMapper implements ExceptionMapper<ReservationNotFoundException> {

  @Override
  public Response toResponse(ReservationNotFoundException exception) {
    return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type("application/json").build();
  }

}