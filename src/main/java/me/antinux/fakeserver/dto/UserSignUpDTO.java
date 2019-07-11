package me.antinux.fakeserver.dto;

import me.antinux.fakeserver.repository.ValidationPair;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Component
public class UserSignUpDTO implements ValidationPair {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    public String getEmail() { return email; }


    public String getPassword() { return password; }


    public String getNickname() { return nickname; }


}
