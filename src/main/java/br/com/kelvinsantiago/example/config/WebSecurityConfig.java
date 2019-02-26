package br.com.kelvinsantiago.example.config;

import br.com.kelvinsantiago.example.security.JWTTokenFilter;
import br.com.kelvinsantiago.example.security.JWTLoginFilter;
import br.com.kelvinsantiago.example.service.CustomUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                // Make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterBefore(new JWTTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                // Add a filter to validate requests of the login
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), customUserDetailService, modelMapper()),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //make request to /login public
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // Any other request must be authenticated
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
