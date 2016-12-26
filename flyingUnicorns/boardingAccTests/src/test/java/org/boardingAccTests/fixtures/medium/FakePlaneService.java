package org.boardingAccTests.fixtures.medium;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneWebService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.PlaneInformationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.SeatPlanDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpRequestBuilder;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpUrlBuilder;

public class FakePlaneService implements PlaneWebService {

  private static final List<String> PLANE_MODELS = Arrays.asList("dash-8", "a320", "boeing-777-300");

  private PlaneAssembler planeAssembler;

  public FakePlaneService(PlaneAssembler planeAssembler) {
    this.planeAssembler = planeAssembler;
  }

  @Override
  public Plane createPlane(String flightNumber) {
    SeatPlanDto seatPlaneDto = seatPlaneFromFlightNumber(flightNumber);
    PlaneInformationDto planeInformationDto = planeInformationFromFlightNumber(flightNumber);

    return planeAssembler.assemblePlane(seatPlaneDto, planeInformationDto);
  }

  private SeatPlanDto seatPlaneFromFlightNumber(String flightNumber) {
    String model = getAMSModelRandom(flightNumber);
    Response seatPlaneResponse = getPlaneSeatFromPlaneBlueprint(model);

    return seatPlaneResponse.readEntity(SeatPlanDto.class);
  }

  private PlaneInformationDto planeInformationFromFlightNumber(String flightNumber) {
    String model = getAMSModelRandom(flightNumber);
    Response planeInformationResponse = getPlaneInformation(model);

    return planeInformationResponse.readEntity(PlaneInformationDto.class);
  }

  @Override
  public Response getPlaneInformation(String model) {
    String url = HttpUrlBuilder.buildPlaneModelUrl(model);
    Invocation.Builder request = HttpRequestBuilder.buildHttpRequestFromUrl(url);
    Response response = request.get();

    return response;
  }

  private String getAMSModelRandom(String flightNumber) {
    Random rand = new Random();

    return PLANE_MODELS.get(rand.nextInt(PLANE_MODELS.size()));
  }

  private Response getPlaneSeatFromPlaneBlueprint(String model) {
    String url = HttpUrlBuilder.buildPlaneSeatPlanUrl(model);
    Invocation.Builder request = HttpRequestBuilder.buildHttpRequestFromUrl(url);
    Response response = request.get();

    return response;
  }

}