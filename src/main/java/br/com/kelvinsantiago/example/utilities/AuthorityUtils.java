package br.com.kelvinsantiago.example.utilities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityUtils {

    public static List<GrantedAuthority> createAuthorityLists(List<String> roles) {
        return createAuthorityList(roles.toArray(new String[0]));
    }

    public static List<GrantedAuthority> createAuthorityList(String... roles) {
        ArrayList authorities = new ArrayList(roles.length);
        String[] var2 = roles;
        int var3 = roles.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String role = var2[var4];
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    public static List<String> toList(List<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
