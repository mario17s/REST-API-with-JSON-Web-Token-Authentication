package com.example.api.utils;

import com.example.api.serv.UserDetailsImpl;
import io.jsonwebtoken.*;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Log4j2
public class JwtUtil {
    @Value(value = "${app.jwtSecret}")
    private String jwtSecret;

    @Value(value = "${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl udd = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject(udd.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUsernameFromJwtToken(String token){
        String username = null;
        try{
            username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){

        }
        return username;
    }

    public boolean validateJwtToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){

        }catch (MalformedJwtException e){

        }catch (ExpiredJwtException e){

        }catch (UnsupportedJwtException e){

        }catch (IllegalArgumentException e){

        }
        return false;
    }


}
