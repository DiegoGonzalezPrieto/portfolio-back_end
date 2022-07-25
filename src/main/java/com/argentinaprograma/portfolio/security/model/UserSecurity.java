package com.argentinaprograma.portfolio.security.model;

import com.argentinaprograma.portfolio.model.Person;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class UserSecurity implements UserDetails {

    private String name;
    private String userName;
    private String email;
    private String password;
    private Person person;
	private int userId;
    private Collection<? extends GrantedAuthority> authorities;
    


    // turns User into UserSecurity
    public static UserSecurity build(User user){
        List<GrantedAuthority> authorities =
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(
                role.getRoleName().name())).collect(Collectors.toList());
        
        return new UserSecurity(user.getName(), user.getUserName(), user.getEmail(), user.getPassword(), user.getPerson(), user.getId(), authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public Person getPerson(){
        return person;
    }

    public int getUserId(){
        return userId;
    }
    
}
