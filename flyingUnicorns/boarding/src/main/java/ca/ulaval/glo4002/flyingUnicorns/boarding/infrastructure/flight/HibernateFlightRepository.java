package ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.flight;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.flight.exception.FlightNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerProvider;

public class HibernateFlightRepository implements FlightRepository {

  private EntityManagerProvider entityManagerProdivder;

  public HibernateFlightRepository() {
    entityManagerProdivder = new EntityManagerProvider();
  }

  @Override
  public void save(Flight flight) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      entityManager.getTransaction().begin();

      entityManager.persist(flight);

      entityManager.getTransaction().commit();

    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }

      throw new FlightAlreadyExistsException("A flight with this number already exist.");
    }
  }

  @Override
  public Flight findFlightByFlightNumber(String flightNumber) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      Query query = entityManager.createQuery("from Flight f where f.flightNumber = :flightNumber");
      query.setParameter("flightNumber", flightNumber);

      return (Flight) query.getSingleResult();

    } catch (NoResultException e) {
      throw new FlightNotFoundException("No flight with this number exist.");
    }
  }

  @Override
  public boolean flightExists(String flightNumber) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      Query query = entityManager.createQuery("from Flight f where f.flightNumber = :flightNumber");
      query.setParameter("flightNumber", flightNumber);

      query.getSingleResult();

    } catch (NoResultException e) {
      return false;
    }

    return true;
  }

}