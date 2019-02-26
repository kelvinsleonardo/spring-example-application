package br.com.kelvinsantiago.example.dto.security;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPrincipalDTO {
    private long id;

    private String username;

    private List<String> authorities;
}
