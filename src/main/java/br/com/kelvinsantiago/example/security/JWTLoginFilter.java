package br.com.kelvinsantiago.example.security;

import br.com.kelvinsantiago.example.dto.login.AuthenticationDTO;
import br.com.kelvinsantiago.example.dto.security.UserPrincipalDTO;
import br.com.kelvinsantiago.example.service.CustomUserDetailService;
import br.com.kelvinsantiago.example.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by kelvin on 23/02/19.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;
    private ModelMapper modelMapper;

    private CustomUserDetailService customUserDetailService;

    public JWTLoginFilter(String url, AuthenticationManager authManager, CustomUserDetailService customUserDetailService, ModelMapper modelMapper) {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, "POST"));
        this.authManager = authManager;
        this.modelMapper = modelMapper;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            // Get credentials from request
            AuthenticationDTO authenticationDTO = new ObjectMapper().readValue(request.getInputStream(),
                    AuthenticationDTO.class);

            // Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getUsername(), authenticationDTO.getPassword(), Collections.emptyList());

            // Authentication manager authenticate the user, and use CustomUserDetailService::loadUserByUsername()
            // method to load the user.
            return authManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) {

        // Convert model to DTO
        UserPrincipalDTO userPrincipalDTO = modelMapper.map(auth.getPrincipal(), UserPrincipalDTO.class);

        TokenAuthenticationService.addAuthentication(response, userPrincipalDTO);
    }
}
