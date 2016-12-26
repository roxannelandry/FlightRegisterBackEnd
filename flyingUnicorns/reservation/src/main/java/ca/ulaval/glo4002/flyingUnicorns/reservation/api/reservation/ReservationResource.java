package ca.ulaval.glo4002.flyingUnicorns.reservation.api.reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.URIGenerator;

@Path("")
public class ReservationResource {

  private ReservationRepository reservationRepository;

  public ReservationResource(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @POST
  @Path("/events/reservation-created")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addReservation(Reservation reservation) {
    reservationRepository.save(reservation);
    return URIGenerator.addLocationToResponseHeader("reservations", String.valueOf(reservation.getReservationNumber()));
  }

  @GET
  @Path("/reservations/{reservationNumber}")
  public Reservation getReservationByReservationNumber(@PathParam("reservationNumber") int reservationNumber) {
    return reservationRepository.findReservationByReservationNumber(reservationNumber);
  }

  @GET
  @Path("/reservations/passenger/{passengerHash}")
  public Reservation getReservationByPassengerHash(@PathParam("passengerHash") String passengerHash) {
    return reservationRepository.findReservationByPassengerHash(passengerHash);
  }

  @GET
  @Path("/reservations/{passengerHash}/passenger")
  public Response getPassengerByPassengerHash(@PathParam("passengerHash") String passengerHash) {
    Passenger passenger = reservationRepository.findPassengerByHash(passengerHash);
    return Response.status(Response.Status.OK).entity(passenger).build();
  }

}