package ca.ulaval.glo4002.flyingUnicorns.boarding.api.seatAssignation;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.BluePrintServerErrorException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationResponseDto;

@RunWith(MockitoJUnitRunner.class)
public class SeatAssignationResourceTest {

  private static final String SEAT_NUMBER = "A-10";

  private static final int SEAT_ASSIGNATION_ID = 2;

  private SeatAssignationResponseDto seatAssignationReturnDto;

  private SeatAssignationRequestDto seatAssignationSentDto;

  @Mock
  private SeatAssignationService seatAssignationService;

  @InjectMocks
  private SeatAssignationResource seatAssignationResource;

  @Before
  public void setUp() {
    seatAssignationSentDto = new SeatAssignationRequestDto();
    seatAssignationReturnDto = new SeatAssignationResponseDto(SEAT_ASSIGNATION_ID, SEAT_NUMBER);

    seatAssignationReturnDto.seatAssignationId = SEAT_ASSIGNATION_ID;
    willReturn(seatAssignationReturnDto).given(seatAssignationService).assignSeat(seatAssignationSentDto);
  }

  @Test
  public void seatAssignationSentDto_assigningSeatToPassenger_delegateToSeatAssignationService() {
    seatAssignationResource.assignSeatToPassenger(seatAssignationSentDto);

    verify(seatAssignationService).assignSeat(seatAssignationSentDto);
  }

  @Test
  public void seatAssignationSentDto_assigningSeatToPassenger_returnGoodLocationToResponseHeader() throws URISyntaxException {
    Response response = seatAssignationResource.assignSeatToPassenger(seatAssignationSentDto);

    URI expectedLocation = new java.net.URI("/seat-assignations/" + SEAT_ASSIGNATION_ID);

    assertEquals(expectedLocation, response.getLocation());
  }

  @Test(expected = URISyntaxException.class)
  public void uriSyntaxException_assigningSeat_throwURISyntax() {
    willThrow(URISyntaxException.class).given(seatAssignationService).assignSeat(seatAssignationSentDto);

    seatAssignationResource.assignSeatToPassenger(seatAssignationSentDto);
  }

  @Test(expected = BluePrintServerErrorException.class)
  public void bluePrintServerError_assigningSeat_throwBluePrintServerError() {
    willThrow(BluePrintServerErrorException.class).given(seatAssignationService).assignSeat(seatAssignationSentDto);

    seatAssignationResource.assignSeatToPassenger(seatAssignationSentDto);
  }

  @Test(expected = FlightAlreadyExistsException.class)
  public void flightAlreadyExistException_assigningSeat_throwFlightAlreadyExists() {
    willThrow(FlightAlreadyExistsException.class).given(seatAssignationService).assignSeat(seatAssignationSentDto);

    seatAssignationResource.assignSeatToPassenger(seatAssignationSentDto);
  }

}