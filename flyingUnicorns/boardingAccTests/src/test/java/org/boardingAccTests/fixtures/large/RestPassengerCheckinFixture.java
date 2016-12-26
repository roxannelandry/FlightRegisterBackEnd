package org.boardingAccTests.fixtures.large;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import io.restassured.response.Response;

public class RestPassengerCheckinFixture extends BaseRestReservationFixture {
  
  private Response currentRequest;
  
  public String getPassengerHash(int reservationNumber) {
    String path = "/reservations/{reservationNumber}";
    URI uri = UriBuilder.fromPath(path).build(reservationNumber);

    List<String> passengerHash = givenReservationRequest().get(uri).path("passengers.passenger_hash");
    return passengerHash.get(0);
  }
  
  public void checkPassengerIn(String passengerHash) {  
    String path = "/checkins";
    URI uri = UriBuilder.fromPath(path).build();
    
    String bodyRequest = "{\"passenger_hash\": \""+ passengerHash + "\",\"by\": \"AGENT\"}\"";

    currentRequest = givenReservationRequest().body(bodyRequest).when().post(uri);
  }
  
  public void passengerIsCheckin() {
    currentRequest.then().statusCode(Status.CREATED.getStatusCode());
  }
  
}