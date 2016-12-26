package ca.ulaval.glo4002.flyingUnicorns.reservation.api.checkin;

import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.Checkin;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.URIGenerator;

@Path("")
public class CheckinResource {

  private ReservationRepository reservationRepository;

  public CheckinResource(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @POST
  @Path("/checkins")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response checkPassengerIn(Checkin checkin) {
    return checkinPassenger(checkin);
  }

  private Response checkinPassenger(Checkin checkin) {
    String passengerHash = checkin.getPassengerToCheckinHash();
    Passenger passenger = reservationRepository.findPassengerByHash(passengerHash);
    Reservation reservation = reservationRepository.findReservationByPassengerHash(passengerHash);

    LocalDateTime flightDate = reservation.getFlightDate();
    if (checkin.checkinTimeframeRespected(flightDate, LocalDateTime.now())) {
      passenger.checkin();
      passenger.setVipProperty(checkin.isVip());
      reservationRepository.save(reservation);
    }

    return URIGenerator.addLocationToResponseHeader("checkins", checkin.getCheckinId());
  }

}