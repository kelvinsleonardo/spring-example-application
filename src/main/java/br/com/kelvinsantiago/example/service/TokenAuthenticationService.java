package br.com.kelvinsantiago.example.service;

import br.com.kelvinsantiago.example.dto.security.UserPrincipalDTO;
import br.com.kelvinsantiago.example.exception.CustomException;
import br.com.kelvinsantiago.example.utilities.AuthorityUtils;
import br.com.kelvinsantiago.example.utilities.GsonUtils;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET_KEY = "secret-key";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse response, UserPrincipalDTO userPrincipalDTO) {

        // Convert object to json stringify
        String user = GsonUtils.stringify(userPrincipalDTO);

        String JWT = Jwts.builder()
                .setSubject(user)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static void refreshToken(HttpServletResponse response){
        addAuthentication(response, getUser());
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // faz parse do token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            // Extract the principalauthorities
            UserPrincipalDTO userPrincipalDTO = (UserPrincipalDTO) GsonUtils.fromJson(claims.getSubject(), UserPrincipalDTO.class);

            if (userPrincipalDTO != null) {

                // Extract authorities of token
                List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityLists(userPrincipalDTO.getAuthorities());

                return new UsernamePasswordAuthenticationToken(userPrincipalDTO, null, authorityList);
            }
        }
        return null;
    }

    public static String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new CustomException("Invalid JWT signature.", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            throw new CustomException("Invalid JWT token.", HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            throw new CustomException("Expired JWT token.", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            throw new CustomException("Unsupported JWT token.", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException ex) {
            throw new CustomException("JWT claims string is empty.", HttpStatus.UNAUTHORIZED);
        }
    }

    public static long getUserId() {
        return ofNullable(getUser())
                .map(UserPrincipalDTO::getId)
                .orElse(0L);
    }

    public static String getUserName() {
        return ofNullable(getUser())
                .map(UserPrincipalDTO::getUsername)
                .orElse("");
    }

    public static UserPrincipalDTO getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return (UserPrincipalDTO) authentication.getPrincipal();

        return null;
    }

    public static boolean isUserAuthenticated(long id){
        return ofNullable(getUser()).stream().anyMatch(user -> user.getId() == id);
    }
}
