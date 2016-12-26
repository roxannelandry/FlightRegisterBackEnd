package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidSeatAssignationModeMapper implements ExceptionMapper<InvalidSeatAssignationMode> {

  @Override
  public Response toResponse(InvalidSeatAssignationMode exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}