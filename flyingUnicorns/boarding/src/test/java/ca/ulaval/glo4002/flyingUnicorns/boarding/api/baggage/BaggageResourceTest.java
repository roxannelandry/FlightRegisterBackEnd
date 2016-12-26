package ca.ulaval.glo4002.flyingUnicorns.boarding.api.baggage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.TooManyBaggageException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.PassengerBaggagesDto;

@RunWith(MockitoJUnitRunner.class)
public class BaggageResourceTest {

  private static final String PASSENGER_HASH = "j6gd63hg7";
  private static final String NOT_FOUND_PASSENGER_HASH = "hg63ghf";

  private static final int OK_200 = 200;
  private static final int CREATED_201 = 201;

  private BaggageRequestDto baggageSentDto;

  private PassengerBaggagesDto passengerBaggagesDto;

  @Mock
  private BaggageAssembler baggageAssembler;

  @Mock
  private BaggageService baggageService;

  @InjectMocks
  private BaggageResource baggageResource;

  @Before
  public void setUp() {
    passengerBaggagesDto = new PassengerBaggagesDto();
    baggageSentDto = new BaggageRequestDto();
  }

  @Test
  public void baggageDto_registerBaggage_delegateToBaggageServices() {
    baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);

    verify(baggageService).registerBaggage(PASSENGER_HASH, baggageSentDto);
  }

  @Test(expected = PassengerNotFoundException.class)
  public void unexistingPassenger_registerBaggage_throwPassengerNotFound() {
    willThrow(PassengerNotFoundException.class).given(baggageService).registerBaggage(NOT_FOUND_PASSENGER_HASH, baggageSentDto);

    baggageResource.registerBaggage(NOT_FOUND_PASSENGER_HASH, baggageSentDto);
  }

  @Test(expected = TooManyBaggageException.class)
  public void tooManyBaggage_registerBaggage_throwTooManyBaggage() {
    willThrow(TooManyBaggageException.class).given(baggageService).registerBaggage(PASSENGER_HASH, baggageSentDto);

    baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);
  }

  @Test
  public void allowedBaggage_registerBaggage_returnAllowedEqualTrueInResponse() {
    AllowedBaggageDto allowedBaggageDtoResponse = new AllowedBaggageDto();
    willReturn(allowedBaggageDtoResponse).given(baggageService).registerBaggage(PASSENGER_HASH, baggageSentDto);

    Response response = baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);
    AllowedBaggageDto allowedBaggageDto = (AllowedBaggageDto) response.getEntity();

    assertTrue(allowedBaggageDto.allowed);
  }

  @Test
  public void allowedBaggage_registerBaggage_return201Created() {
    Response response = baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);

    assertEquals(CREATED_201, response.getStatus());
  }

  @Test(expected = PassengerAlreadyExistsException.class)
  public void passengerAlreadyExistInDatabase_registerBaggage_throwPassengerAlreadyExists() {
    willThrow(PassengerAlreadyExistsException.class).given(baggageService).registerBaggage(PASSENGER_HASH, baggageSentDto);

    baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);
  }

  @Test
  public void passengerHash_gettingBaggage_delegateToBaggageService() {
    baggageResource.getBaggageList(PASSENGER_HASH);

    verify(baggageService).getPassengerBaggages(PASSENGER_HASH);
  }

  @Test
  public void passegerHash_gettingBaggage_return200OK() {
    willReturn(passengerBaggagesDto).given(baggageService).getPassengerBaggages(PASSENGER_HASH);

    Response response = baggageResource.getBaggageList(PASSENGER_HASH);

    assertEquals(response.getStatus(), OK_200);
  }

  @Test(expected = PassengerNotFoundException.class)
  public void aPassengerHashThatDosentExsit_gettingBaggage_throwsPassengerNotFound() {
    willThrow(PassengerNotFoundException.class).given(baggageService).getPassengerBaggages(NOT_FOUND_PASSENGER_HASH);

    baggageResource.getBaggageList(NOT_FOUND_PASSENGER_HASH);
  }

  @Test(expected = URISyntaxException.class)
  public void uriSyntaxException_registerBaggage_throwURISyntax() {
    willThrow(URISyntaxException.class).given(baggageService).registerBaggage(PASSENGER_HASH, baggageSentDto);

    baggageResource.registerBaggage(PASSENGER_HASH, baggageSentDto);
  }

}