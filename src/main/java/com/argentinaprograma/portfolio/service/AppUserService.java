package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.AppUser;
import com.argentinaprograma.portfolio.repository.IAppUserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements IAppUserService {

    @Autowired
    private IAppUserRepo userRepo;
            
    @Override
    public List<AppUser> getUsers() {
        List<AppUser> users = userRepo.findAll();
        return users;
    }

    @Override
    public void saveUser(AppUser user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        AppUser u = userRepo.findById(id).orElse(null);
        userRepo.delete(u);
        
    }

    @Override
    public AppUser findUser(Long id) {
        AppUser u = userRepo.findById(id).orElse(null);
        return u;
    }

}
