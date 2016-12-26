package ca.ulaval.glo4002.flyingUnicorns.reservation.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.InvalidReservationException;
import ca.ulaval.glo4002.flyingUnicorns.reservation.domain.reservation.exceptions.ReservationNotFoundException;

public class HibernateReservationRepository implements ReservationRepository {

  private EntityManagerProvider entityManagerProdivder;

  public HibernateReservationRepository() {
    entityManagerProdivder = new EntityManagerProvider();
  }

  @Override
  public void save(Reservation reservation) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();
    
    try {
      entityManager.getTransaction().begin();

      entityManager.persist(reservation);

      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new InvalidReservationException("This reservation number is already taken.");
    }
  }

  @Override
  public Reservation findReservationByReservationNumber(int reservationNumber) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      return getReservation(reservationNumber);
    } catch (NoResultException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new ReservationNotFoundException("No reservation with this number");
    }
  }

  @Override
  public Passenger findPassengerByHash(String passengerHash) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      return getPassenger(passengerHash);
    } catch (NoResultException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new PassengerNotFoundException("No reservation with this hash");
    }
  }

  @Override
  public Reservation findReservationByPassengerHash(String passengerHash) {
    List<Reservation> reservations = findAllReservationNumbers();
    
    for (Reservation reservation : reservations) {
      if (reservation.hasPassenger(passengerHash)) {
        return reservation;
      }
    }
    throw new PassengerNotFoundException("Passenger has not yet been registered");
  }

  private List<Reservation> findAllReservationNumbers() {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    entityManager.getTransaction().begin();

    TypedQuery<Reservation> query = entityManager.createQuery("FROM Reservation", Reservation.class);

    List<Reservation> results = query.getResultList();

    entityManager.getTransaction().commit();

    return results;
  }

  private Reservation getReservation(int reservationNumber) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    entityManager.getTransaction().begin();

    TypedQuery<Reservation> query = entityManager.createQuery("FROM Reservation r WHERE r.reservationNumber = :reservationNumber", Reservation.class);
    query.setParameter("reservationNumber", reservationNumber);

    entityManager.getTransaction().commit();

    return query.getSingleResult();
  }

  private Passenger getPassenger(String passengerHash) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    entityManager.getTransaction().begin();

    TypedQuery<Passenger> query = entityManager.createQuery("from Passenger p where p.passengerHash = :passengerHash", Passenger.class);
    query.setParameter("passengerHash", passengerHash);

    entityManager.getTransaction().commit();

    return query.getSingleResult();
  }

}