package ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.AllowedBaggageDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.BaggageRequestDto;
import ca.ulaval.glo4002.flyingUnicorns.boarding.dto.baggage.PassengerBaggagesDto;

public class BaggageService {

  private FlightService flightService;

  private BaggageAssembler baggageAssembler;

  private PassengerService passengerService;
  private PassengerRepository passengerRepository;

  private ReservationService reservationRepository;

  public BaggageService(BaggageAssembler baggageAssembler, PassengerService passengerService, ReservationService reservationRepository,
      FlightService flightService, PassengerRepository passengerRepository) {
    this.baggageAssembler = baggageAssembler;
    this.passengerService = passengerService;
    this.reservationRepository = reservationRepository;
    this.flightService = flightService;
    this.passengerRepository = passengerRepository;
  }

  public AllowedBaggageDto registerBaggage(String passengerHash, BaggageRequestDto baggageDto) {
    Passenger passengerToAddBaggage = passengerService.findPassenger(passengerHash);

    String flightNumber = reservationRepository.findFlightNumberByPassengerHash(passengerHash);
    Baggage baggage = baggageAssembler.assembleBaggage(baggageDto, passengerToAddBaggage);
    flightService.ensureBaggageCanBeAdded(flightNumber, baggage.getWeightInGrams());

    passengerToAddBaggage.registerBaggage(baggage);
    flightService.addBaggageWeight(flightNumber, baggage.getWeightInGrams());
    passengerRepository.savePassenger(passengerToAddBaggage);

    return baggageAssembler.assembleAllowedBaggageDto();
  }

  public PassengerBaggagesDto getPassengerBaggages(String passengerHash) {
    Passenger passenger = passengerService.findPassenger(passengerHash);

    return baggageAssembler.assembleFromPassenger(passenger);
  }

}