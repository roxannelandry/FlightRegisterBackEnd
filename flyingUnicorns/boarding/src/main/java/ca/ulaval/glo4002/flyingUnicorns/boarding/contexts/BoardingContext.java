package ca.ulaval.glo4002.flyingUnicorns.boarding.contexts;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Application;

import ca.ulaval.glo4002.flyingUnicorns.boarding.api.baggage.BaggageResource;
import ca.ulaval.glo4002.flyingUnicorns.boarding.api.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.flyingUnicorns.boarding.api.seatAssignation.SeatAssignationResource;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggagePriceCalculatorFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.BaggageService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneAssembler;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.PlaneService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.reservation.ReservationServiceFromReservation;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignationService;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.SeatAssignatorFactory;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerProvider;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.passenger.HibernatePassengerRepository;

public class BoardingContext extends ContextBase {

  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  private ReservationService reservationRepository;

  private PassengerRepository passengerRepository;
  private PassengerFactory passengerFactory;
  private PassengerService passengerService;

  private PlaneAssembler planeAssembler;
  private PlaneService planeService;

  private FlightService flightService;

  private BaggagePriceCalculatorFactory baggagePriceCalculatorFactory;
  private BaggageAssembler baggageAssembler;
  private BaggageService baggageService;
  private BaggageResource baggageResource;

  private HeartbeatResource heartbeatResource;

  private SeatAssignationService seatAssignationService;
  private SeatAssignationResource seatAssignationResource;
  private SeatAssignatorFactory seatAssignatorFactory;

  @Override
  public Application createApplication() {
    entityManagerFactory = EntityManagerFactoryProvider.getFactory();
    entityManager = entityManagerFactory.createEntityManager();
    EntityManagerProvider.setEntityManager(entityManager);

    reservationRepository = new ReservationServiceFromReservation();

    passengerRepository = new HibernatePassengerRepository();
    passengerFactory = new PassengerFactory();
    passengerService = new PassengerService(passengerFactory, passengerRepository, reservationRepository);

    planeAssembler = new PlaneAssembler();
    planeService = new PlaneService(planeAssembler);

    flightService = createFlightService(planeService);

    baggagePriceCalculatorFactory = new BaggagePriceCalculatorFactory();
    baggageAssembler = new BaggageAssembler(baggagePriceCalculatorFactory);
    baggageService = new BaggageService(baggageAssembler, passengerService, reservationRepository, flightService, passengerRepository);
    baggageResource = createBaggageResource(baggageAssembler, baggageService, reservationRepository);

    heartbeatResource = createHeartBeatResource();

    seatAssignatorFactory = new SeatAssignatorFactory();
    seatAssignationService = createSeatAssignationService(flightService, reservationRepository, passengerService, seatAssignatorFactory);
    seatAssignationResource = createSeatAssignationResource(seatAssignationService);

    return new Application() {
      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();
        resources.add(heartbeatResource);
        resources.add(seatAssignationResource);
        resources.add(baggageResource);
        return resources;
      }
    };
  }

  private FlightService createFlightService(PlaneService planeService) {
    FlightRepository flightRepository = new HibernateFlightRepository();
    FlightFactory flightFactory = new FlightFactory(planeService);

    return new FlightService(flightRepository, flightFactory);
  }

  private SeatAssignationService createSeatAssignationService(FlightService flightService, ReservationService reservationRepository,
      PassengerService passengerService, SeatAssignatorFactory seatAssignatorFactory) {
    return new SeatAssignationService(flightService, reservationRepository, passengerService, seatAssignatorFactory);
  }

  private SeatAssignationResource createSeatAssignationResource(SeatAssignationService seatAssignationService) {
    return new SeatAssignationResource(seatAssignationService);
  }

  private HeartbeatResource createHeartBeatResource() {
    return new HeartbeatResource();
  }

  private BaggageResource createBaggageResource(BaggageAssembler baggageAssembler, BaggageService baggageService,
      ReservationService reservationRepository) {
    return new BaggageResource(baggageService, baggageAssembler);
  }

}