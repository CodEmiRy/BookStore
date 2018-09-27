package pl.sda.intermediate11.bookstore.users.dtos;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String login;
    private String password;
}