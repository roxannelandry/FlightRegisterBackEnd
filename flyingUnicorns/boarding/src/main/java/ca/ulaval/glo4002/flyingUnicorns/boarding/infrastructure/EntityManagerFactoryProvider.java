package ca.ulaval.glo4002.flyingUnicorns.boarding.infrastructure;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

  private static EntityManagerFactory instance;

  public static EntityManagerFactory getFactory() {
    if (instance == null) {
      instance = Persistence.createEntityManagerFactory("boarding");
    }

    return instance;
  }

  public static void reset() {
    if (instance != null) {
      instance.close();
      instance = null;
    }
  }

}