package pl.sda.intermediate11.bookstore.users.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.database.HibernateUserRepository;
import pl.sda.intermediate11.bookstore.users.daos.UserDAO;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;
import pl.sda.intermediate11.bookstore.users.entities.User;
import pl.sda.intermediate11.bookstore.users.exceptions.UserExitstsException;

@Service
public class UserRegistrationService {

    @Autowired
    //private UserDAO userDAO;
    private HibernateUserRepository userDAO;

    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userExists(userRegistrationDTO)) {
            throw new UserExitstsException("Użytkownik istnieje");
        }
        User user = rewriteDtoToUser(userRegistrationDTO);
        userDAO.addUser(user);

    }

    private User rewriteDtoToUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setZipCode(userRegistrationDTO.getUserAddress().getZipCode());
        user.setCity(userRegistrationDTO.getUserAddress().getCity());
        user.setCountry(userRegistrationDTO.getUserAddress().getCountry());
        user.setStreet(userRegistrationDTO.getUserAddress().getStreet());
        user.setBirthDate(userRegistrationDTO.getBirthDate());
        user.setPesel(userRegistrationDTO.getPesel());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(DigestUtils.sha512Hex(userRegistrationDTO.getPassword()));
        user.setPhone(userRegistrationDTO.getPhone());
        user.setPreferEmails(userRegistrationDTO.isPreferEmails());
        
        return user;
    }

    private boolean userExists(UserRegistrationDTO userRegistrationDTO) {
        return userDAO.findUserByEmail(userRegistrationDTO.getEmail()).isPresent();
    }
}
