package br.com.kelvinsantiago.example.dto.user;

import br.com.kelvinsantiago.example.entity.Telephone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EditUserDTO {

    @Positive
    private long id;

    @NotNull(message = "ME01")
    @Size(min=11,max = 11, message="invalid CPF")
    private String cpf;

    @NotNull(message = "ME01")
    private String username;

    @NotNull(message = "ME01")
    private String name;

    @Size(min = 6,message = "6-character minimum password length")
    private String password;

    @NotNull(message = "ME01")
    @Email
    private String mail;

    @Past
    private Date birth;

    private List<Telephone> telephones;

    @Positive
    private Long idCity;

    @Positive
    private Long idImage;
}
