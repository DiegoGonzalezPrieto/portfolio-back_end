package com.argentinaprograma.portfolio.security.service;

import com.argentinaprograma.portfolio.security.model.User;
import com.argentinaprograma.portfolio.security.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    public Optional<User> getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    
    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    
    public void save(User user){
        userRepository.save(user);
    }
    
}
