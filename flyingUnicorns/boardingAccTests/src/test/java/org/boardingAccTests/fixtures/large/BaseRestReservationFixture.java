package org.boardingAccTests.fixtures.large;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

import org.boardingAccTests.runner.ServersRunner;

import io.restassured.specification.RequestSpecification;

public class BaseRestReservationFixture {
  
  protected RequestSpecification givenReservationRequest() {
    return given().accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .port(Integer.parseInt(ServersRunner.DEFAULT_RESERVATION_SERVER_PORT));
  }
  
}