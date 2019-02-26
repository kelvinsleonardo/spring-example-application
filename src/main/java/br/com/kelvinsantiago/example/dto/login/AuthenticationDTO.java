package br.com.kelvinsantiago.example.dto.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;
}