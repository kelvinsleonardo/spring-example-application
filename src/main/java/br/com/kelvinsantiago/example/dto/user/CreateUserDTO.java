package br.com.kelvinsantiago.example.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserDTO {

    @NotNull(message = "ME01")
    @Size(min=11,max = 11, message="invalid CPF")
    private String cpf;

    @NotNull
    private String name;

    @NotNull(message = "ME01")
    @Email
    private String mail;

    @NotNull(message = "ME01")
    @Size(min = 6,message = "6-character minimum password length")
    private String password;
}
