package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.PlaneInformationDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.plane.SeatPlanDto;

public class PlaneAssembler {

  public Plane assemblePlane(SeatPlanDto seatPlanDto, PlaneInformationDto planeInformationDto) {
    assignExitRow(seatPlanDto);
    assignClasse(seatPlanDto);

    return new Plane(seatPlanDto.seats, planeInformationDto.cargoWeight);
  }

  private void assignExitRow(SeatPlanDto seatPlanDto) {
    for (Seat seat : seatPlanDto.seats) {
      seat.defineExitRow(seatPlanDto.exitRows);
    }
  }

  private void assignClasse(SeatPlanDto seatPlanDto) {
    for (PlaneSeatMap classe : seatPlanDto.classes) {
      for (Seat seat : seatPlanDto.seats) {
        seat.defineClass(classe.getName(), classe.getRows());
      }
    }
  }

}