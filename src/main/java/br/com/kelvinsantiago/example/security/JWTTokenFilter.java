package br.com.kelvinsantiago.example.security;

import br.com.kelvinsantiago.example.exception.CustomException;
import br.com.kelvinsantiago.example.service.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTTokenFilter extends GenericFilterBean {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = TokenAuthenticationService.resolveToken((HttpServletRequest) request);
        try {
            if (token != null && TokenAuthenticationService.validateToken(token)) {
                Authentication auth = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(auth);

                // Generate new token in header in every request
                TokenAuthenticationService.refreshToken((HttpServletResponse) response);
            }
        } catch (CustomException ex) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(ex.getHttpStatus().value(), ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
