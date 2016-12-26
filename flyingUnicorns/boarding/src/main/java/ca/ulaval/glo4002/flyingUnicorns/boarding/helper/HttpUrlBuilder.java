package ca.ulaval.glo4002.flyingUnicorns.boarding.helper;

public class HttpUrlBuilder {

  public static String buildPlaneSeatPlanUrl(String model) {
    return "http://glo3000.ift.ulaval.ca/plane-blueprint/planes/" + model + "/seats.json";
  }

  public static String buildReservationUrl(String reservationPort, String passengerHash) {
    return "http://localhost:" + reservationPort + "/reservations/passenger/" + passengerHash;
  }

  public static String buildPassengerUrl(String reservationPort, String passengerHash) {
    return "http://localhost:" + reservationPort + "/reservations/" + passengerHash + "/passenger";
  }

  public static String buildPlaneModelUrl(String model) {
    return "http://glo3000.ift.ulaval.ca/plane-blueprint/planes/" + model + ".json";
  }

}