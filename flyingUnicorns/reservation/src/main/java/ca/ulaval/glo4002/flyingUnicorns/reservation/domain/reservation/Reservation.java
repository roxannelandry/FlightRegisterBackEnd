package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.LocalDateTimeJsonDeserializer;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.LocalDateTimeJsonSerializer;

@Entity
@JsonIgnoreProperties({ "reservation_date", "reservation_confirmation", "payment_location" })
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservationId")
  private int id;

  @Column(name = "reservationNumber", unique = true)
  private int reservationNumber;

  @Column
  @JsonProperty("flight_number")
  private String flightNumber;

  @OneToMany
  @JoinColumn(name = "reservation")
  @Cascade(value = { CascadeType.ALL })
  @JsonProperty("passengers")
  private List<Passenger> passengers;

  @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
  @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
  @Column
  private LocalDateTime flightDate;

  public Reservation() { // for Jackson & Hibernate
  }

  @JsonCreator
  public Reservation(@JsonProperty("reservation_number") int reservationNumber, @JsonProperty("flight_number") String flightNumber,
      @JsonProperty("flight_date") LocalDateTime flightDate, @JsonProperty("passengers") List<Passenger> passengers) {
    this.reservationNumber = reservationNumber;
    this.flightNumber = flightNumber;
    this.flightDate = flightDate;
    this.passengers = passengers;

    ensureJsonFieldsAreValid();
  }

  private void ensureJsonFieldsAreValid() {
    if (reservationNumber == 0 || flightNumber == null || flightDate == null || passengers.isEmpty()) {
      throw new InvalidReservationException("Missing fields when submitting reservation form.");
    }
  }

  public boolean hasPassenger(String passengerHash) {
    for (Passenger passenger : passengers) {
      if (passenger.hasHash(passengerHash)) {
        return true;
      }
    }
    return false;
  }

  @JsonProperty("reservation_number")
  public int getReservationNumber() {
    return reservationNumber;
  }

  @JsonProperty("flight_date")
  public LocalDateTime getFlightDate() {
    return flightDate;
  }

}