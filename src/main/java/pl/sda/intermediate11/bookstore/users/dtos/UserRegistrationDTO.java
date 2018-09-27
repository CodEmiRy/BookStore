package pl.sda.intermediate11.bookstore.users.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.sda.intermediate11.bookstore.users.entities.UserAddress;

@Getter
@Setter
public class UserRegistrationDTO {

    private String firstName;
    private String lastName;
    private UserAddress userAddress;
    private String birthDate;
    private String pesel;
    private String email;
    private String password;
    private String phone;
    private boolean preferEmails;
}
