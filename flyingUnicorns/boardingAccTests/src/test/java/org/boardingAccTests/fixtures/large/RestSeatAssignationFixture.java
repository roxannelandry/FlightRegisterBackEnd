package org.boardingAccTests.fixtures.large;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;
import io.restassured.response.Response;

public class RestSeatAssignationFixture extends BaseRestBoardingFixture {
  
  private Response currentRequest;

  public void assignRandomSeat(SeatAssignationRequestDto seatAssignationRequestDto) {
    URI uri = UriBuilder.fromPath("/seat-assignations").build();
    
    String bodyRequest = "{\"passenger_hash\": \""+ seatAssignationRequestDto.passengerHash + "\",\"mode\": \""+ seatAssignationRequestDto.mode.toString() +"\"}\"";

    currentRequest = givenBoardingRequest().body(bodyRequest).when().post(uri);
  }

  public void seatIsAssigned() {
    currentRequest.then().statusCode(Status.CREATED.getStatusCode());    
  }
  
}