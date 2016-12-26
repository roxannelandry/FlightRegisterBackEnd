package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.passenger.PassengerDto;

@RunWith(MockitoJUnitRunner.class)
public class PassengerFactoryTest {

  private static final SeatClass ANY_SEAT_CLASS = SeatClass.ECONOMY;

  private PassengerDto passengerDto;

  private PassengerFactory passengerFactory;

  @Before
  public void setUp() {
    passengerDto = new PassengerDto();
    passengerFactory = new PassengerFactory();
  }

  @Test
  public void passengerDtoWithAnySeatClass_creatingPassenger_returnPassengerWithSameSeatClass() {
    passengerDto.seatClass = ANY_SEAT_CLASS;

    Passenger passenger = passengerFactory.createPassenger(passengerDto);

    assertEquals(passenger.seatClass, ANY_SEAT_CLASS);
  }

}