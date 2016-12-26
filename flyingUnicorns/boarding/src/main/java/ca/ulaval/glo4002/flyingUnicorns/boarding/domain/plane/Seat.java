package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

@Entity
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column
  private int row;

  @Column
  private String seat;

  @Column
  private int legRoom;

  @Column
  private boolean window;

  @Column
  private boolean clearView;

  @Column
  private float price;

  @Column
  private boolean isAssigned;

  @Column
  private SeatClass seatClass;

  @Column
  private boolean isExitRow;

  public Seat() { // for Hibernate
  }

  @JsonCreator
  public Seat(@JsonProperty("row") int row, @JsonProperty("seat") String number, @JsonProperty("legroom") int legRoom,
      @JsonProperty("window") boolean window, @JsonProperty("clear_view") boolean clearView, @JsonProperty("price") float price) {
    this.row = row;
    this.seat = number;
    this.legRoom = legRoom;
    this.window = window;
    this.clearView = clearView;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public boolean isSeatAssigned() {
    return isAssigned;
  }

  public boolean isExitRow() {
    return isExitRow;
  }

  public boolean hasSameSeatClass(SeatClass seatClass) {
    return this.seatClass.equals(seatClass);
  }

  public void assign() {
    isAssigned = true;
  }

  public void unassign() {
    isAssigned = false;
  }

  public String getSeatNumber() {
    return Integer.toString(row) + "-" + seat;
  }

  public void defineExitRow(List<Integer> exitRows) {
    isExitRow = exitRows.contains(row);
  }

  public void defineClass(SeatClass className, List<Integer> rowsOfClasse) {
    if (rowsOfClasse.contains(row)) {
      seatClass = className;
    }
  }

  public boolean comparePrice(float maxPriceValue) {
    return price < maxPriceValue;
  }

  public boolean hasMoreLegRoomThen(int minLegRoom) {
    return legRoom > minLegRoom;
  }

  public boolean hasLowerPriceForSameLegroom(int maxLegRoom, float minPrice) {
    return (legRoom == maxLegRoom) && (price < minPrice);
  }

  public float getPrice() {
    return price;
  }

  public int getLegRoom() {
    return legRoom;
  }

  public boolean hasWindow() {
    return window;
  }

  public boolean hasClearView() {
    return clearView;
  }

  public boolean hasLowerPriceForSameView(boolean hasWindow, boolean hasClearView, float maxSeatPrice) {
    return window == hasWindow && clearView == hasClearView && price < maxSeatPrice;
  }

  protected void setSeatClass(SeatClass seatClass) {
    this.seatClass = seatClass;
  }

  protected void setIsExitRow(boolean isExitRow) {
    this.isExitRow = isExitRow;
  }

}