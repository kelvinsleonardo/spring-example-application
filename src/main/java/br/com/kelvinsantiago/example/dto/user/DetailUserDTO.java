package br.com.kelvinsantiago.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserDTO {

    private String name;
    private String cpf;
    private String mail;
    private String situation;
    private String urlDownloadImage;
    private boolean adm;

}
