package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import java.util.ArrayList;
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

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OverweightBaggageException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.FullPlaneException;

@Entity
public class Plane {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @OneToMany
  @JoinColumn(name = "seats")
  @Cascade(value = { CascadeType.ALL })
  private List<Seat> seats;

  @Column
  private int cargoWeight;

  public Plane() { // for Hibernate
  }

  public Plane(List<Seat> seats, int cargoWeight) {
    this.seats = seats;
    this.cargoWeight = cargoWeight * 1000;
  }

  public void assignSeat(Seat seat) {
    int indexOfSeatToAssign = seats.indexOf(seat);
    seats.get(indexOfSeatToAssign).assign();
  }

  public List<Seat> createFilteredSeatsList(Passenger passenger) {
    List<Seat> filteredSeatsList = new ArrayList<Seat>(seats);

    if (passenger.isChild()) {
      filteredSeatsList.removeIf(seat -> seat.isExitRow());
    }
    filteredSeatsList.removeIf(seat -> seat.isSeatAssigned());
    filteredSeatsList.removeIf(seat -> !seat.hasSameSeatClass(passenger.getSeatClass()));

    checkIfPlaneIsFull(filteredSeatsList);

    return filteredSeatsList;
  }

  private void checkIfPlaneIsFull(List<Seat> filteredList) {
    if (filteredList.isEmpty()) {
      throw new FullPlaneException("The plane is full.");
    }
  }

  public void ensureBaggageCanBeAdded(int baggagesWeightInGrams, int newBaggageWeightInGrams) {
    if ((baggagesWeightInGrams + newBaggageWeightInGrams) > cargoWeight) {
      throw (new OverweightBaggageException("New total baggage weight would exceed plane limit"));
    }
  }

  public void unassignSeat(Seat seatToUnassign) {
    for (Seat seat : seats) {
      if (seat == seatToUnassign) {
        seat.unassign();
        return;
      }
    }
  }

  public boolean hasAvailableSeats(SeatClass seatClass) {
    for (Seat seat : seats) {
      if (seat.hasSameSeatClass(seatClass) && !seat.isSeatAssigned()) {
        return true;
      }
    }
    return false;
  }

}