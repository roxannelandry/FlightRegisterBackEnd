package ca.ulaval.glo4002.flyingUnicorns.boarding.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneService;

@RunWith(MockitoJUnitRunner.class)
public class PlaneBluePrintRestTest {

  private PlaneAssembler planeAssembler;
  private PlaneService planeService;

  @Before
  public void setUp() {
    planeAssembler = new PlaneAssembler();
    planeService = new PlaneService(planeAssembler);
  }

  @Test
  public void ensureBluePrintServerIsUp() {
    Response response = planeService.getPlaneInformation("dash-8");
    assertEquals(response.getStatus(), 200);
  }

}