package pl.sda.intermediate11.bookstore.database;

import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.users.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Service
public class HibernateUserRepository {


    public Optional<User> findUserByEmail(String email) {
        EntityManager entityManager = HibernateConfiguration.getEntityManager();

        List<User> users = entityManager.createQuery("FROM User", User.class).getResultList();
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public void addUser(User user) {
        EntityManager entityManager = HibernateConfiguration.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
    }
}
