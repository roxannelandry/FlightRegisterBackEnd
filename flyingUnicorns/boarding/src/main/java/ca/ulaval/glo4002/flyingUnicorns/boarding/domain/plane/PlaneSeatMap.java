package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;

public class PlaneSeatMap {

  private SeatClass name;
  private List<Integer> rows = new ArrayList<Integer>();

  public PlaneSeatMap() { // for Hibernate
  }

  public PlaneSeatMap(SeatClass name, List<Integer> rows) {
    this.name = name;
    this.rows = rows;
  }

  public SeatClass getName() {
    return name;
  }

  public List<Integer> getRows() {
    return rows;
  }

}