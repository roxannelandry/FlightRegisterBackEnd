package ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.passenger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerAlreadyExistsException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.domain.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure.EntityManagerProvider;

public class HibernatePassengerRepository implements PassengerRepository {

  private EntityManagerProvider entityManagerProdivder;

  public HibernatePassengerRepository() {
    entityManagerProdivder = new EntityManagerProvider();
  }

  @Override
  public Passenger findPassenger(String passengerHash) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      entityManager.getTransaction().begin();

      TypedQuery<Passenger> query = entityManager.createQuery("FROM boardingPassenger p WHERE p.passengerHash = :passengerHash", Passenger.class);
      query.setParameter("passengerHash", passengerHash);
      entityManager.getTransaction().commit();

      Passenger passenger = query.getSingleResult();

      return passenger;

    } catch (NoResultException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }

      throw new PassengerNotFoundException("No passenger with this hash");
    }
  }

  @Override
  public void savePassenger(Passenger passenger) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      entityManager.getTransaction().begin();

      entityManager.persist(passenger);

      entityManager.getTransaction().commit();

    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }

      throw new PassengerAlreadyExistsException("This passenger already exist in the database");
    }
  }

  @Override
  public boolean passengerExist(String passengerHash) {
    EntityManager entityManager = entityManagerProdivder.getEntityManager();

    try {
      TypedQuery<Passenger> query = entityManager.createQuery("FROM boardingPassenger p WHERE p.passengerHash=:passengerHash", Passenger.class);
      query.setParameter("passengerHash", passengerHash);

      query.getSingleResult();

    } catch (NoResultException e) {
      return false;
    }
    return true;
  }

}