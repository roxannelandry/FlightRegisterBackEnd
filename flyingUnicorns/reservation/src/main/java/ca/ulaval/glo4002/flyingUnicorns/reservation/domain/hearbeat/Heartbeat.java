package ca.ulaval.glo4002.flyingUnicorns.reservation.domain.hearbeat;

public class Heartbeat {

  public final long date;
  public final String token;

  public Heartbeat(String token) {
    date = System.currentTimeMillis();
    this.token = token;
  }

}