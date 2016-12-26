package ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneSeatMap;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Seat;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class SeatPlanDto {

  @JsonProperty("exit_rows")
  public List<Integer> exitRows = new ArrayList<Integer>();

  @JsonProperty("classes")
  public List<PlaneSeatMap> classes = new ArrayList<PlaneSeatMap>();

  @JsonProperty("seats")
  public List<Seat> seats = new ArrayList<Seat>();

}