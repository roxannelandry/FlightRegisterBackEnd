
package ca.ulaval.glo4002.flyingUnicorns.app;

import ca.ulaval.glo4002.flyingUnicorns.boarding.BoardingServer;
import ca.ulaval.glo4002.flyingUnicorns.boarding.contexts.BoardingContext;
import ca.ulaval.glo4002.flyingUnicorns.reservation.ReservationServer;

public class Main {

  private static final String DEFAULT_RESERVATION_SERVER_PORT = "8888";
  private static final String DEFAULT_BOARDING_SERVER_PORT = "8787";

  public static void main(String[] args) throws InterruptedException {
    ReservationServer reservationServer = new ReservationServer(findReservationServerPortProperty());
    BoardingServer boardingServer = new BoardingServer(findBoardingServerPortProperty(), new BoardingContext());
    
    BoardingServer.setReservationPort(findReservationServerPortProperty());

    Thread boardingThread = new Thread(boardingServer);
    Thread resevationThread = new Thread(reservationServer);

    boardingThread.start();
    resevationThread.start();

    boardingThread.join();
    resevationThread.join();
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
  
}