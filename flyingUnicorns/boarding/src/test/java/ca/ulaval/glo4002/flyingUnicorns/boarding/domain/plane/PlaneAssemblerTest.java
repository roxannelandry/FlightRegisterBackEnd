package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.SeatClass;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.PlaneInformationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.SeatPlanDto;

@RunWith(MockitoJUnitRunner.class)
public class PlaneAssemblerTest {

  private List<Integer> exitRows;
  private List<Integer> rows;
  private List<Seat> seats;
  private List<PlaneSeatMap> classes;

  private PlaneSeatMap planeSeatMap;

  private SeatPlanDto seatPlanDto;

  private PlaneInformationDto planeInformationDto;

  private PlaneAssembler planeAssembler;

  @Mock
  private Seat seat;

  @Before
  public void setUp() {
    exitRows = new ArrayList<Integer>();
    exitRows.add(5);

    rows = new ArrayList<Integer>();
    rows.add(6);

    seats = new ArrayList<Seat>();
    seats.add(seat);

    planeSeatMap = new PlaneSeatMap(SeatClass.ECONOMY, rows);

    classes = new ArrayList<PlaneSeatMap>();
    classes.add(planeSeatMap);

    planeInformationDto = new PlaneInformationDto();

    seatPlanDto = new SeatPlanDto();
    seatPlanDto.exitRows = exitRows;
    seatPlanDto.seats = seats;
    seatPlanDto.classes = classes;

    planeAssembler = new PlaneAssembler();
  }

  @Test
  public void planeDto_assemblingPlane_delegateToSeatDefineExitRow() {
    planeAssembler.assemblePlane(seatPlanDto, planeInformationDto);

    verify(seat).defineExitRow(seatPlanDto.exitRows);
  }

  @Test
  public void planeDto_assemblingPlane_delegateToSeatDefineClasse() {
    planeAssembler.assemblePlane(seatPlanDto, planeInformationDto);

    verify(seat).defineClass(planeSeatMap.getName(), planeSeatMap.getRows());
  }

}