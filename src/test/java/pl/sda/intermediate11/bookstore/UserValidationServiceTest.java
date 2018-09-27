package pl.sda.intermediate11.bookstore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sda.intermediate11.bookstore.users.entities.UserAddress;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;
import pl.sda.intermediate11.bookstore.users.services.UserValidationService;

import java.util.Map;

class UserValidationServiceTest {

    @Test
    void shouldNotPassUserWithBlankFirstName() {
        UserRegistrationDTO userRegistrationDTO = populateValidUser();
        userRegistrationDTO.setFirstName(" ");
        UserValidationService userValidationService = new UserValidationService();
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);

        Assertions.assertTrue(errorsMap.containsKey(UserValidationService.FIRST_NAME_VAL_RES));
    }

    @Test
    void shouldNotPassUserWithTooShortFirstName() {
        UserRegistrationDTO userRegistrationDTO = populateValidUser();
        userRegistrationDTO.setFirstName("Bo");
        UserValidationService userValidationService = new UserValidationService();
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);

        Assertions.assertTrue(errorsMap.containsKey(UserValidationService.FIRST_NAME_VAL_RES));
    }

    private UserRegistrationDTO populateValidUser() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();

        UserAddress userAddress = new UserAddress();
        userAddress.setCity("New York");
        userAddress.setCountry("USA");
        userAddress.setStreet("Akacjowa");
        userAddress.setZipCode("23-922");

        userRegistrationDTO.setFirstName("Anna");
        userRegistrationDTO.setLastName("Nowak");
        userRegistrationDTO.setBirthDate("1991-12-24");
        userRegistrationDTO.setPesel("92748294029");
        userRegistrationDTO.setEmail("a.nowak@pg.com");
        userRegistrationDTO.setPhone("723-999-219");
        userRegistrationDTO.setPassword("Intelijjestsuper11");
        userRegistrationDTO.setUserAddress(userAddress);

        return userRegistrationDTO;
    }
}