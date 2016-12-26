package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.RefusedBaggageDto;

public class OverweightBaggageExceptionMapper implements ExceptionMapper<OverweightBaggageException> {

  @Override
  public Response toResponse(OverweightBaggageException exception) {
    return Response.status(Status.OK).entity(getRefusalReason(exception)).type("application/json").build();
  }

  private RefusedBaggageDto getRefusalReason(OverweightBaggageException exception) {
    RefusedBaggageDto refusedBaggageDto = new RefusedBaggageDto();
    refusedBaggageDto.refusalReason = exception.getMessage();
    return refusedBaggageDto;
  }

}