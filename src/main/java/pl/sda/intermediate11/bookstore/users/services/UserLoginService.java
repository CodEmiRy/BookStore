package pl.sda.intermediate11.bookstore.users.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.database.HibernateUserRepository;
import pl.sda.intermediate11.bookstore.users.daos.UserDAO;
import pl.sda.intermediate11.bookstore.users.dtos.UserLoginDTO;
import pl.sda.intermediate11.bookstore.users.entities.User;
import pl.sda.intermediate11.bookstore.users.exceptions.PasswordDoesNotMatchException;
import pl.sda.intermediate11.bookstore.users.exceptions.UserNotExistsException;

import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private HibernateUserRepository userDAO;

    public void login(UserLoginDTO userLoginDTO){
        Optional<User> userByEmail = userDAO.findUserByEmail(userLoginDTO.getLogin());
        if (!userByEmail.isPresent()) {
            throw new UserNotExistsException("Nie ma takiego użytkownika " + userLoginDTO.getLogin());
        }
        if (passwordDoesNotMatch(userLoginDTO, userByEmail)){
            throw new PasswordDoesNotMatchException("Hasło nie pasuje");
        }
        UserContextHolder.logInUser(userByEmail.get().getEmail());
    }

    private boolean passwordDoesNotMatch(UserLoginDTO userLoginDTO, Optional<User> userByEmail) {
        return !DigestUtils.sha512Hex(userLoginDTO.getPassword()).equals(userByEmail.get().getPassword());
    }
}
