package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidBaggagePriceCalculatorMapper implements ExceptionMapper<InvalidBaggagePriceCaculatorException> {

  @Override
  public Response toResponse(InvalidBaggagePriceCaculatorException exception) {
    return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type("application/json").build();
  }

}