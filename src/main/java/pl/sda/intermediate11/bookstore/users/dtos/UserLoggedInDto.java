package pl.sda.intermediate11.bookstore.users.dtos;

import lombok.Data;

@Data
public class UserLoggedInDto {

    private String login;

    public UserLoggedInDto(String email) {
        this.login = email;
    }
}
