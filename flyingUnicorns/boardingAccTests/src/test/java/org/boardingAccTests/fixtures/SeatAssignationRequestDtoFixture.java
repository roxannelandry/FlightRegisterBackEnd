package org.boardingAccTests.fixtures;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationModes;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.seatAssignation.SeatAssignationRequestDto;

public class SeatAssignationRequestDtoFixture {

  public SeatAssignationRequestDto createSeatAssignationDto(SeatAssignationModes mode, String passengerHash) {
    SeatAssignationRequestDto seatAssignationRequestDto = new SeatAssignationRequestDto();
    seatAssignationRequestDto.mode = mode;
    seatAssignationRequestDto.passengerHash = passengerHash;

    return seatAssignationRequestDto;
  }

}