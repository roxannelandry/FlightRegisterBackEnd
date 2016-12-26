package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

public interface PassengerRepository {

  public Passenger findPassenger(String passengerHash);

  public void savePassenger(Passenger passenger);

  public boolean passengerExist(String passengerHash);

}