package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "flightNumber" }))
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "flightId")
  private int id;

  @Column
  protected int baggagesWeightInGrams;

  @Column(name = "flightNumber")
  private String flightNumber;

  @OneToOne(cascade = { CascadeType.ALL })
  @JoinColumn(name = "plane")
  private Plane plane;

  public Flight() { // for Hibernate
  }

  public Flight(String flightNumber, Plane plane) {
    this.flightNumber = flightNumber;
    this.plane = plane;
    baggagesWeightInGrams = 0;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void ensureBaggageCanBeAdded(int newBaggageWeightInGrams) {
    plane.ensureBaggageCanBeAdded(baggagesWeightInGrams, newBaggageWeightInGrams);
  }

  public void addBaggageWeight(int weightInGrams) {
    baggagesWeightInGrams += weightInGrams;
  }

  public Plane getPlane() {
    return plane;
  }

  public void updatePlane(Plane plane) {
    this.plane = plane;
  }

}