package ca.ulaval.glo4002.flyingUnicorns.boarding.api.seatAssignation;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationResponseDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.URIGenerator;

@Path("")
public class SeatAssignationResource {

  private SeatAssignationService seatAssignationService;

  public SeatAssignationResource(SeatAssignationService seatAssignationService) {
    this.seatAssignationService = seatAssignationService;
  }

  @POST
  @Path("/seat-assignations")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response assignSeatToPassenger(SeatAssignationRequestDto seatAssignationDto) {
    SeatAssignationResponseDto assignedSeatWithId = seatAssignationService.assignSeat(seatAssignationDto);
    return createResponse(assignedSeatWithId);
  }

  private Response createResponse(SeatAssignationResponseDto seatAssignationDtoResponse) {
    URI responseHeaderWithId = URIGenerator.addLocationToResponseHeader("seat-assignations", seatAssignationDtoResponse.seatAssignationId);
    return Response.created(responseHeaderWithId).entity(seatAssignationDtoResponse).type(MediaType.APPLICATION_JSON).build();
  }

}