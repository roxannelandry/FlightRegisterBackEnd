package org.boardingAccTests.context;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.boardingAccTests.fixtures.medium.FakeFlightRepository;
import org.boardingAccTests.fixtures.medium.FakePassengerRepository;
import org.boardingAccTests.fixtures.medium.FakePlaneService;
import org.boardingAccTests.fixtures.medium.FakeReservationService;

import ca.ulaval.glo4002.flyingUnicorns.boarding.api.baggage.BaggageResource;
import ca.ulaval.glo4002.flyingUnicorns.boarding.contexts.ContextBase;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggagePriceCalculatorFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneWebService;

public class AcceptanceMediumTestsContext extends ContextBase {

  public PlaneWebService planeService;
  public FakeReservationService reservationRepository;
  public FakePassengerRepository passengerRepository;

  public PassengerFactory passengerFactory;
  public PassengerService passengerService;

  public PlaneAssembler planeAssembler;

  public FlightService flightService;

  public BaggagePriceCalculatorFactory baggagePriceCalculatorFactory;
  public BaggageAssembler baggageAssembler;
  public BaggageService baggageService;
  public BaggageResource baggageResource;

  @Override
  public Application createApplication() {
    reservationRepository = new FakeReservationService();

    passengerRepository = new FakePassengerRepository();
    passengerFactory = new PassengerFactory();

    passengerService = new PassengerService(passengerFactory, passengerRepository, reservationRepository);

    planeAssembler = new PlaneAssembler();
    planeService = new FakePlaneService(planeAssembler);

    flightService = createFlightService(planeService);
    baggagePriceCalculatorFactory = new BaggagePriceCalculatorFactory();
    baggageAssembler = new BaggageAssembler(baggagePriceCalculatorFactory);
    baggageService = new BaggageService(baggageAssembler, passengerService, reservationRepository, flightService, passengerRepository);
    baggageResource = createBaggageResource(baggageAssembler, baggageService);

    return new Application() {
      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();
        resources.add(baggageResource);
        return resources;
      }
    };
  }

  private FlightService createFlightService(PlaneWebService planeService) {
    FlightRepository flightRepository = new FakeFlightRepository();
    FlightFactory flightFactory = new FlightFactory(planeService);
    
    return new FlightService(flightRepository, flightFactory);
  }

  private BaggageResource createBaggageResource(BaggageAssembler baggageAssembler, BaggageService baggageService) {
    return new BaggageResource(baggageService, baggageAssembler);
  }

}