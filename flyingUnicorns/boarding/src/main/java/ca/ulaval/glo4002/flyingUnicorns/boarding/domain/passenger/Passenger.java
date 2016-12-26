package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageCounter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundException;

@Entity(name = "boardingPassenger")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "passengerHash" }))
public class Passenger {

  private static final float VIP_COUPON = 0.05f;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "passengerId")
  private int id;

  @OneToOne
  @Cascade(value = { CascadeType.ALL })
  @JoinColumn(name = "seatDetails")
  private Seat seat;

  @Column
  private boolean hasSeatAssigned;

  @Column(name = "passengerHash")
  private String passengerHash;

  @Column
  protected SeatClass seatClass;

  @Column
  float vipCoupon = VIP_COUPON;

  @Column
  protected boolean child;

  @Column
  protected boolean isVip;

  @Column
  protected boolean isCheckin;

  @OneToOne
  @Cascade(value = { CascadeType.ALL })
  @JoinColumn(name = "baggageCounter")
  protected BaggageCounter baggageCounter;

  @OneToMany
  @JoinColumn(name = "baggages")
  @Cascade(value = { CascadeType.ALL })
  protected List<Baggage> baggageList = new ArrayList<Baggage>();

  public Passenger() { // for Jackson & Hibernate
  }

  public Passenger(String passengerHash, SeatClass seatClass, boolean child, boolean isVip, boolean isCheckin) {
    this.passengerHash = passengerHash;
    this.seatClass = seatClass;
    this.child = child;
    this.isVip = isVip;
    this.isCheckin = isCheckin;
    hasSeatAssigned = false;
    baggageCounter = new BaggageCounter();

    if (this.isVip) {
      makePassengerVip();
    }
  }

  public void makePassengerVip() {
    baggageCounter.setVIPCheckedLimit();
  }

  public void registerBaggage(Baggage baggage) {
    if (!isCheckin) {
      throw new PassengerNotCheckedInException("You can not register your baggage, you need to checkin first");
    }

    baggageCounter.ensurePassengerCanAddBaggage(baggage.getType());
    baggage.setPrice(baggageCounter);
    baggageCounter.incrementBaggageQuantity(baggage.getType());
    baggageList.add(baggage);
  }

  public float getTotalBaggageCost() {
    float totalPrice = 0;
    for (Baggage baggage : baggageList) {
      totalPrice = baggage.addToPrice(totalPrice);
    }

    if (isVip) {
      totalPrice = (float) (totalPrice * (1 - vipCoupon));
    }

    return totalPrice;
  }

  public boolean isChild() {
    return child;
  }

  public boolean isVip() {
    return isVip;
  }

  public boolean isCheckin() {
    return isCheckin;
  }

  public boolean hasAssignedSeat() {
    return hasSeatAssigned;
  }

  public void assignSeat(Seat reservedSeat) {
    seat = reservedSeat;
    hasSeatAssigned = true;
  }

  public Seat getSeat() {
    if (hasSeatAssigned) {
      return seat;
    }

    throw new NoSeatFoundException("This passenger has no seat assigned");
  }

  public String getPassengerHash() {
    return passengerHash;
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }

  public List<Baggage> getBaggages() {
    return baggageList;
  }

}