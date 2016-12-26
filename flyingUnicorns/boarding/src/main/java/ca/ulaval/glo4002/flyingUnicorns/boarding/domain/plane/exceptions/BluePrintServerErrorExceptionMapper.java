package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class BluePrintServerErrorExceptionMapper implements ExceptionMapper<BluePrintServerErrorException> {

  @Override
  public Response toResponse(BluePrintServerErrorException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}
