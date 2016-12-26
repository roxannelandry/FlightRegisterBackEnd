package ca.ulaval.glo4002.flyingUnicorns.boarding;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.flyingUnicorns.boarding.api.filter.EntityManagerContextFilter;
import ca.ulaval.glo4002.flyingUnicorns.boarding.contexts.ContextBase;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.InvalidBaggagePriceCalculatorMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OversizedBaggageExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.OverweightBaggageExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.baggage.exceptions.TooManyBaggageExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightAlreadyExistsExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightNotFoundExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerAlreadyExistsExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotCheckedInExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotFoundExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.BluePrintServerErrorExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.FullPlaneExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.plane.exceptions.PlaneModelNotFoundExceptionMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.InvalidSeatAssignationModeMapper;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.seatAssignation.exceptions.NoSeatFoundExceptionMapper;

public class BoardingServer implements Runnable {

  private String httpPort;

  private ContextBase context;

  private Server boardingServer;

  private static String reservationHttpPort;

  public BoardingServer(String httpPort, ContextBase context) {
    this.httpPort = httpPort;
    this.context = context;
  }

  public void run() {
    boardingServer = new Server(Integer.valueOf(httpPort));
    ServletContextHandler servletContextHandler = new ServletContextHandler(boardingServer, "/");
    servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    configurerJersey(servletContextHandler, this.context.createApplication());

    try {
      boardingServer.start();
      boardingServer.join();

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      boardingServer.destroy();
    }
  }

  public void setPort(String port) {
    this.httpPort = port;
  }

  public static void setReservationPort(String reservationPort) {
    BoardingServer.reservationHttpPort = reservationPort;
  }

  public static String getReservationPort() {
    return BoardingServer.reservationHttpPort;
  }

  private void configurerJersey(ServletContextHandler servletContextHandler, Application application) {
    ResourceConfig resourceConfig = ResourceConfig.forApplication(application);

    resourceConfig.register(JacksonFeature.class);
    resourceConfig.register(InvalidBaggagePriceCalculatorMapper.class);
    resourceConfig.register(TooManyBaggageExceptionMapper.class);
    resourceConfig.register(OversizedBaggageExceptionMapper.class);
    resourceConfig.register(OverweightBaggageExceptionMapper.class);
    resourceConfig.register(InvalidSeatAssignationModeMapper.class);
    resourceConfig.register(NoSeatFoundExceptionMapper.class);
    resourceConfig.register(FlightAlreadyExistsExceptionMapper.class);
    resourceConfig.register(FlightNotFoundExceptionMapper.class);
    resourceConfig.register(PassengerAlreadyExistsExceptionMapper.class);
    resourceConfig.register(PassengerNotCheckedInExceptionMapper.class);
    resourceConfig.register(PassengerNotFoundExceptionMapper.class);
    resourceConfig.register(BluePrintServerErrorExceptionMapper.class);
    resourceConfig.register(FullPlaneExceptionMapper.class);
    resourceConfig.register(PlaneModelNotFoundExceptionMapper.class);
    resourceConfig.packages("ca.ulaval.glo4002.flyingUnicorns.boarding.api.filter");

    ServletContainer container = new ServletContainer(resourceConfig);
    ServletHolder jerseyServletHolder = new ServletHolder(container);
    servletContextHandler.addServlet(jerseyServletHolder, "/*");
  }

  public void stop() {
    try {
      boardingServer.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}