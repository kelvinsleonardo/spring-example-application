package br.com.kelvinsantiago.example.service;

import br.com.kelvinsantiago.example.entity.User;
import br.com.kelvinsantiago.example.enums.Role;
import br.com.kelvinsantiago.example.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rhandy on 23/02/19.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        User user = userService.getByMail(mail);

        if (user == null) {
            throw new UsernameNotFoundException("Username: " + mail + " not found");
        } else {
            List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList(Role.ADMIN.getName(), Role.USER.getName());
            List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList(Role.USER.getName());

            return new UserPrincipal(user.getId(), user.getMail(), user.getPassword(), user.isAdm() ? authorityListAdmin : authorityListUser);
        }
    }
}