package pl.sda.intermediate11.bookstore.users.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddress {

    private String zipCode;
    private String city;
    private String country;
    private String street;
}
