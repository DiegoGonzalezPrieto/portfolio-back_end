package com.argentinaprograma.portfolio.security.jwt;

// generates Token and checks it

import com.argentinaprograma.portfolio.security.model.UserSecurity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class JwtProvider {

    private static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    // these values are set in application.properties
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private int expiration;

    //returns token
    public String generateToken(Authentication auth){
        UserSecurity user = (UserSecurity) auth.getPrincipal();
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .claim("pid", user.getPerson().getId().toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                .getBody().getSubject();
    }
    
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Malformed token.");
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported token.");
        } catch (ExpiredJwtException e){
            logger.error("Expired token.");
        } catch (IllegalArgumentException e){
            logger.error("Empty token.");
        } catch (SignatureException e){
            logger.error("Signature fail.");
        }
        return false;
    }
}
