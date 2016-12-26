package ca.ulaval.glo4002.flyingUnicorns.reservation;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.flyingUnicorns.reservation.api.checkin.CheckinResource;
import ca.ulaval.glo4002.flyingUnicorns.reservation.api.filter.EntityManagerContextFilter;
import ca.ulaval.glo4002.flyingUnicorns.reservation.api.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.flyingUnicorns.reservation.api.reservation.ReservationResource;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinAlreadyDoneMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.CheckinTimeExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.checkin.exceptions.InvalidCheckinMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.InvalidPassengerMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.PassengerNotFoundMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.ReservationNotFoundMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.helper.exception.UriExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.reservation.infrastructure.HibernateReservationRepository;

public class ReservationServer implements Runnable {

  private String httpPort;
  private Server reservationServer;

  public ReservationServer(String httpPort) {
    this.httpPort = httpPort;
  }

  public void start(int httpPort) {
    ReservationRepository reservationRepository = createReservationInMemory();
    ReservationResource reservationResource = createReservationResource(reservationRepository);
    CheckinResource checkinResource = createCheckinResource(reservationRepository);
    HeartbeatResource heartbeatResource = createHeartBeatResource();
    
    reservationServer = new Server(httpPort);
    ServletContextHandler servletContextHandler = new ServletContextHandler(reservationServer, "/");
    
    configurerJersey(servletContextHandler, heartbeatResource, reservationResource, checkinResource);
    
    try {
      reservationServer.start();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      tryStopServer();
    }
  }

  private void tryStopServer() {
    try {
      reservationServer.destroy();
    } catch (Exception e) {
      return;
    }
  }

  public void run() {
    ReservationRepository reservationRepository = createReservationInMemory();
    ReservationResource reservationResource = createReservationResource(reservationRepository);
    CheckinResource checkinResource = createCheckinResource(reservationRepository);
    HeartbeatResource heartbeatResource = createHeartBeatResource();

    reservationServer = new Server(Integer.valueOf(httpPort));

    ServletContextHandler servletContextHandler = new ServletContextHandler(reservationServer, "/");
    servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    
    configurerJersey(servletContextHandler, heartbeatResource, reservationResource, checkinResource);
    
    try {
      reservationServer.start();
      reservationServer.join();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reservationServer.destroy();
    }
  }

  private void configurerJersey(ServletContextHandler servletContextHandler, HeartbeatResource heartbeatResource,
      ReservationResource reservationResource, CheckinResource checkinResource) {

    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();
        resources.add(heartbeatResource);
        resources.add(reservationResource);
        resources.add(checkinResource);
        return resources;
      }
    });
    
    resourceConfig.register(JacksonFeature.class);
    resourceConfig.register(new CheckinAlreadyDoneMapper());
    resourceConfig.register(new CheckinTimeExceptionMapper());
    resourceConfig.register(new InvalidCheckinMapper());
    resourceConfig.register(new InvalidPassengerMapper());
    resourceConfig.register(new PassengerNotFoundMapper());
    resourceConfig.register(new ReservationNotFoundMapper());
    resourceConfig.register(new InvalidReservationMapper());
    resourceConfig.register(new UriExceptionMapper());
    resourceConfig.packages("ca.ulaval.glo4002.flyingUnicorns.reservation.api.filter");
    
    ServletContainer container = new ServletContainer(resourceConfig);
    ServletHolder jerseyServletHolder = new ServletHolder(container);
    
    servletContextHandler.addServlet(jerseyServletHolder, "/*");
  }

  public void setPort(String port) {
    httpPort = port;
  }

  private ReservationRepository createReservationInMemory() {
    return new HibernateReservationRepository();
  }

  private ReservationResource createReservationResource(ReservationRepository reservationRepository) {
    return new ReservationResource(reservationRepository);
  }

  private CheckinResource createCheckinResource(ReservationRepository reservationRepository) {
    return new CheckinResource(reservationRepository);
  }

  private HeartbeatResource createHeartBeatResource() {
    return new HeartbeatResource();
  }

  public void stop() {
    try {
      reservationServer.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}