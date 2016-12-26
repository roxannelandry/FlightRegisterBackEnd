package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.flyingUnicorns.boarding.BoardingServer;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.reservation.ReservationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpRequestBuilder;
import ca.ulaval.glo4002.flyingUnicorns.boarding.helper.HttpUrlBuilder;

public class ReservationServiceFromReservation implements ReservationService {

  @Override
  public PassengerDto findPassengerByPassengerHash(String passengerHash) {
    Response response = getPassengerFromReservationServer(passengerHash);
    ensureResponseStatusOK(response);

    return response.readEntity(PassengerDto.class);
  }

  @Override
  public ReservationDto findReservationByPassengerHash(String passengerHash) {
    Response response = getReservationFromReservationServer(passengerHash);
    ensureResponseStatusOK(response);

    return response.readEntity(ReservationDto.class);
  }

  @Override
  public String findFlightNumberByPassengerHash(String passengerHash) {
    return findReservationByPassengerHash(passengerHash).flightNumber;
  }

  private Response getReservationFromReservationServer(String passengerHash) {
    String reservationServerPort = BoardingServer.getReservationPort();
    String url = HttpUrlBuilder.buildReservationUrl(reservationServerPort, passengerHash);

    Invocation.Builder request = HttpRequestBuilder.buildHttpRequestFromUrl(url);
    Response response = request.get();

    return response;
  }

  private Response getPassengerFromReservationServer(String passengerHash) {
    String reservationServerPort = BoardingServer.getReservationPort();
    String url = HttpUrlBuilder.buildPassengerUrl(reservationServerPort, passengerHash);

    Invocation.Builder request = HttpRequestBuilder.buildHttpRequestFromUrl(url);
    Response response = request.get();

    return response;
  }

  private void ensureResponseStatusOK(Response response) {
    if (response.getStatus() != Status.OK.getStatusCode()) {
      throw new PassengerNotFoundException("Passenger not found");
    }
  }

}