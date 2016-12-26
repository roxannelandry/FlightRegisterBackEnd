package ca.ulaval.glo4002.flyingUnicorns.boarding.api.baggage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.PassengerBaggagesDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.exception.UriException;

@Path("")
public class BaggageResource {

  private BaggageService baggageService;

  public BaggageResource(BaggageService baggageService, BaggageAssembler baggageAssembler) {
    this.baggageService = baggageService;
  }

  @POST
  @Path("/passengers/{passengerHash}/baggages")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registerBaggage(@PathParam("passengerHash") String passengerHash, BaggageRequestDto baggageDto) {
    AllowedBaggageDto allowedBaggageDto = baggageService.registerBaggage(passengerHash, baggageDto);
    return createResponse(allowedBaggageDto, passengerHash);
  }

  private Response createResponse(AllowedBaggageDto allowedBaggageDto, String passengerHash) {
    try {
      String baggageHash = UUID.randomUUID().toString();
      URI location;
      location = new URI("/passengers/" + passengerHash + "/baggages/" + baggageHash);
      return Response.created(location).entity(allowedBaggageDto).build();

    } catch (URISyntaxException e) {
      throw new UriException("A problem happened on our side, please try again");
    }
  }

  @GET
  @Path("/passengers/{passengerHash}/baggages")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBaggageList(@PathParam("passengerHash") String passengerHash) {
    PassengerBaggagesDto passengerBaggagesDto = baggageService.getPassengerBaggages(passengerHash);
    return Response.status(Response.Status.OK).entity(passengerBaggagesDto).build();
  }

}