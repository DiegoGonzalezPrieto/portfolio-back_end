package com.argentinaprograma.portfolio.security.dto;

import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String userName;
    private String personId;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String userName, Collection<? extends GrantedAuthority> authorities, String personId) {
        this.token = token;
        this.userName = userName;
        this.authorities = authorities;
        this.personId = personId;
    }
    
    
    
}
