package org.boardingAccTests.fixtures.large;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

import org.boardingAccTests.runner.ServersRunner;

import io.restassured.specification.RequestSpecification;

public class BaseRestBoardingFixture {

  protected RequestSpecification givenBoardingRequest() {
    return given().accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .port(Integer.parseInt(ServersRunner.DEFAULT_BOARDING_SERVER_PORT));
  }

}