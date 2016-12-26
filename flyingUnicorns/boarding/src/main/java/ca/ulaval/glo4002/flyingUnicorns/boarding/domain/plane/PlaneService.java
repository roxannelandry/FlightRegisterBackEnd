package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.BluePrintServerErrorException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.PlaneModelNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.PlaneInformationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.SeatPlanDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpRequestBuilder;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpUrlBuilder;

public class PlaneService implements PlaneWebService {

  private static final int FIRST_PLANE_MODEL = 0;
  private static final int SECOND_PLANE_MODEL = 1;
  private static final int THIRD_PLANE_MODEL = 2;

  private static final List<String> PLANE_MODELS = Arrays.asList("dash-8", "a320", "boeing-777-300");

  private PlaneAssembler planeAssembler;

  public PlaneService(PlaneAssembler planeAssembler) {
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

    ensurePlaneModelExists(seatPlaneResponse);
    ensureResponseIsValid(seatPlaneResponse);

    return seatPlaneResponse.readEntity(SeatPlanDto.class);
  }

  private PlaneInformationDto planeInformationFromFlightNumber(String flightNumber) {
    String model = getAMSModelRandom(flightNumber);
    Response planeInformationResponse = getPlaneInformation(model);

    ensurePlaneModelExists(planeInformationResponse);
    ensureResponseIsValid(planeInformationResponse);

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
    if (flightNumber == null) {
      return getRandomPlaneInList();
    }

    switch (flightNumber) {
    case "QK-918":
      return PLANE_MODELS.get(FIRST_PLANE_MODEL);
    case "NK-750":
      return PLANE_MODELS.get(SECOND_PLANE_MODEL);
    case "QK-432":
      return PLANE_MODELS.get(THIRD_PLANE_MODEL);
    default:
      return getRandomPlaneInList();
    }
  }

  private String getRandomPlaneInList() {
    Random rand = new Random();
    return PLANE_MODELS.get(rand.nextInt(PLANE_MODELS.size()));
  }

  private Response getPlaneSeatFromPlaneBlueprint(String model) {
    String url = HttpUrlBuilder.buildPlaneSeatPlanUrl(model);
    Invocation.Builder request = HttpRequestBuilder.buildHttpRequestFromUrl(url);
    Response response = request.get();

    return response;
  }

  private void ensurePlaneModelExists(Response response) {
    if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
      throw new PlaneModelNotFoundException("Plane model not found.");
    }
  }

  private void ensureResponseIsValid(Response response) {
    if (response.getStatus() != Status.OK.getStatusCode()) {
      throw new BluePrintServerErrorException("Unable to call blueprint server.");
    }
  }

}