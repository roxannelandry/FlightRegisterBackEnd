package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationCheapest;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationLandscape;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationLegroom;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.strategies.SeatAssignationRandom;

public class SeatAssignatorFactoryTest {

  private static final SeatAssignationModes ASSIGNATION_MODES_RANDOM = SeatAssignationModes.RANDOM;
  private static final SeatAssignationModes ASSIGNATION_MODES_CHEAPEST = SeatAssignationModes.CHEAPEST;
  private static final SeatAssignationModes ASSIGNATION_MODES_LEGS = SeatAssignationModes.LEGS;
  private static final SeatAssignationModes ASSIGNATION_MODES_LANDSCAPE = SeatAssignationModes.LANDSCAPE;

  private SeatAssignatorFactory seatAssignatorFactory;

  @Before
  public void setup() {
    seatAssignatorFactory = new SeatAssignatorFactory();
  }

  @Test
  public void assignationModeRandom_creatingSeatAssignator_returnSeatAssignatorWithRandomStrategy() {
    SeatAssignator seatAssignator = seatAssignatorFactory.createSeatAssignator(ASSIGNATION_MODES_RANDOM);

    assertTrue(SeatAssignationRandom.class.isInstance(seatAssignator.getSeatAssignationStrategy()));
  }

  @Test
  public void assignationModeCheapest_creatingSeatAssignator_returnSeatAssignatorWithCheapestStrategy() {
    SeatAssignator seatAssignator = seatAssignatorFactory.createSeatAssignator(ASSIGNATION_MODES_CHEAPEST);

    assertTrue(SeatAssignationCheapest.class.isInstance(seatAssignator.getSeatAssignationStrategy()));
  }

  @Test
  public void assignationModeLegRoom_creatingSeatAssignator_returnSeatAssignatorWithLegRoomStrategy() {
    SeatAssignator seatAssignator = seatAssignatorFactory.createSeatAssignator(ASSIGNATION_MODES_LEGS);

    assertTrue(SeatAssignationLegroom.class.isInstance(seatAssignator.getSeatAssignationStrategy()));
  }

  @Test
  public void assignationModeLandscape_creatingSeatAssignator_returnSeatAssignatorWithLandscapeStrategy() {
    SeatAssignator seatAssignator = seatAssignatorFactory.createSeatAssignator(ASSIGNATION_MODES_LANDSCAPE);

    assertTrue(SeatAssignationLandscape.class.isInstance(seatAssignator.getSeatAssignationStrategy()));
  }

}