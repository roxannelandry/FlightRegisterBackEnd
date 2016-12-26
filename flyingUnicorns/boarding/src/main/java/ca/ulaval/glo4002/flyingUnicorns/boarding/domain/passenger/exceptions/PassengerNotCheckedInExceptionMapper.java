package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class PassengerNotCheckedInExceptionMapper implements ExceptionMapper<PassengerNotCheckedInException> {

  @Override
  public Response toResponse(PassengerNotCheckedInException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}