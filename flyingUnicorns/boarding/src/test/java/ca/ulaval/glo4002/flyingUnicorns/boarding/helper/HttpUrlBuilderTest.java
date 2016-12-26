package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HttpUrlBuilderTest {

  private static final String PLANE_MODEL_STRING = "QK-918";
  private static final String VALID_PLANE_MODEL_URL = "http://glo3000.ift.ulaval.ca/plane-blueprint/planes/" + PLANE_MODEL_STRING + "/seats.json";
  private static final String PASSENGER_HASH_STRING = "54345";
  private static final String RESERVATION_SERVER_PORT = "8888";
  private static final String VALID_RESERVATION_URL = "http://localhost:" + RESERVATION_SERVER_PORT + "/reservations/passenger/"
      + PASSENGER_HASH_STRING;
  private static final String VALID_PASSENGER_URL = "http://localhost:" + RESERVATION_SERVER_PORT + "/reservations/" + PASSENGER_HASH_STRING
      + "/passenger";

  @Test
  public void planeModelString_buildingPlaneModelUrl_builtUrlMatchValidUrl() {
    String actualPlaneModelUrl = HttpUrlBuilder.buildPlaneSeatPlanUrl(PLANE_MODEL_STRING);

    assertEquals(VALID_PLANE_MODEL_URL, actualPlaneModelUrl);
  }

  @Test
  public void passengerHashAndReservationPort_buildingReservationUrl_builtUrlMatchValidUrl() {
    String actualReservationUrl = HttpUrlBuilder.buildReservationUrl(RESERVATION_SERVER_PORT, PASSENGER_HASH_STRING);

    assertEquals(VALID_RESERVATION_URL, actualReservationUrl);
  }

  @Test
  public void passengerHashAndReservationPort_buildingPassengerUrl_builtUrlMatchValidUrl() {
    String actualReservationUrl = HttpUrlBuilder.buildPassengerUrl(RESERVATION_SERVER_PORT, PASSENGER_HASH_STRING);

    assertEquals(VALID_PASSENGER_URL, actualReservationUrl);
  }

}