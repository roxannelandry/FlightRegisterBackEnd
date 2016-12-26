package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneService;

@RunWith(MockitoJUnitRunner.class)
public class FlightFactoryTest {

  private static final String A_FLIGHT_NUMBER = "1234";

  @Mock
  private PlaneService planeService;

  @Mock
  private PlaneAssembler planeAssembler;

  private Plane plane;

  private FlightFactory flightFactory;

  @Before
  public void setup() {
    flightFactory = new FlightFactory(planeService);

    willReturn(plane).given(planeService).createPlane(A_FLIGHT_NUMBER);
  }

  @Test
  public void flightNumber_createFlight_delegateToPlaneServicePlaneFromFlightNumber() {
    flightFactory.createFlight(A_FLIGHT_NUMBER);

    verify(planeService).createPlane(A_FLIGHT_NUMBER);
  }

  @Test
  public void flightNumber_createFlight_assignAccordingFlightNumber() {
    Flight createdFlight = flightFactory.createFlight(A_FLIGHT_NUMBER);

    assertEquals(createdFlight.getFlightNumber(), A_FLIGHT_NUMBER);
  }

}