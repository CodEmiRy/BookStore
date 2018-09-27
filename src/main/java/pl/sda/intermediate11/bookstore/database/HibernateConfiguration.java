package pl.sda.intermediate11.bookstore.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConfiguration {

    private static final EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("bookstore_database");
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }


    public static void close() {
        factory.close();
    }
}
