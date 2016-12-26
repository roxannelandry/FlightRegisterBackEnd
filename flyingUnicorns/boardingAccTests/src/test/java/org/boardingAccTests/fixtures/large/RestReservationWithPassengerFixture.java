package org.boardingAccTests.fixtures.large;

import java.net.URI;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.boardingAccTests.fixtures.ReservationWithPassengerFixture;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import io.restassured.response.Response;

public class RestReservationWithPassengerFixture extends BaseRestReservationFixture implements ReservationWithPassengerFixture {

  private Response currentRequest;

  @Override
  public void addPassengerToReservation(ReservationDto reservationDto, PassengerDto passengerDto) {
    reservationDto.passengers.add(passengerDto);
  }

  @Override
  public void saveReservationWithPassenger(ReservationDto reservationDto) {
    String path = "/events/reservation-created";
    URI uri = UriBuilder.fromPath(path).build();

    String bodyRequest = createBodyForRequest(reservationDto);

    currentRequest = givenReservationRequest().body(bodyRequest).when().post(uri);
  }

  @Override
  public void reservationIsSaved(int reservationNumber) {
    currentRequest.then().statusCode(Status.CREATED.getStatusCode());
  }

  private String createBodyForRequest(ReservationDto reservationDto) {
    return "{\"reservation_number\":" + reservationDto.reservationNumber + ",\"reservation_date\": \"2016-01-31\",\"flight_number\": \""
        + reservationDto.flightNumber + "\",\"flight_date\": \"" + reservationDto.flightDate + "\",\"passengers\": [{ \"first_name\": \""
        + reservationDto.passengers.get(0).firstName + "\",\"last_name\": \"" + reservationDto.passengers.get(0).lastName
        + "\",\"age\": 18,\"passport_number\": \"" + reservationDto.passengers.get(0).passportNumber + "\",\"seat_class\": \""
        + reservationDto.passengers.get(0).seatClass.value() + "\"}]}";
  }

}