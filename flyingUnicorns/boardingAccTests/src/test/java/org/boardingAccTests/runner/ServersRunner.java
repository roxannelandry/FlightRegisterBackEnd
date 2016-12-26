package org.boardingAccTests.runner;

import ca.ulaval.glo4002.flyingUnicorns.boarding.BoardingServer;
import ca.ulaval.glo4002.flyingUnicorns.boarding.contexts.ContextBase;
import ca.ulaval.glo4002.flyingUnicorns.reservation.ReservationServer;

public class ServersRunner {

  public static final String DEFAULT_RESERVATION_SERVER_PORT = "4040";
  public static final String DEFAULT_BOARDING_SERVER_PORT = "4141";

  private BoardingServer server;
  private ContextBase context;

  private static boolean isFirstFeature = true;
  
  public ServersRunner(ContextBase context) {
    this.context = context;
  }

  public void beforeAll() throws Exception {
    if (isFirstFeature) {
      Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
      startJettyServer();
      isFirstFeature = false;
    }
  }

  private void startJettyServer() throws Exception {
    ReservationServer reservationServer = new ReservationServer(findReservationServerPortProperty());
    BoardingServer boardingServer = new BoardingServer(findBoardingServerPortProperty(), context);

    BoardingServer.setReservationPort(findReservationServerPortProperty());

    Thread boardingThread = new Thread(boardingServer);
    Thread resevationThread = new Thread(reservationServer);

    boardingThread.start();
    resevationThread.start();
  }

  private static String findBoardingServerPortProperty() {
    String boardingPort = System.getProperty("boarding.port");
    if (boardingPort == null) {
      return DEFAULT_BOARDING_SERVER_PORT;
    }

    return boardingPort;
  }

  private static String findReservationServerPortProperty() {
    String reservationPort = System.getProperty("reservation.port");
    if (reservationPort == null) {
      return DEFAULT_RESERVATION_SERVER_PORT;
    }

    return reservationPort;
  }

  private class JettyServerShutdown extends Thread {
    public void run() {
      try {
        server.stop();
      } catch (Exception e) {
        // Nothing do to anyways
      }
    }
  }

}