package com.argentinaprograma.portfolio.service;

import com.argentinaprograma.portfolio.model.AppUser;
import java.util.List;

public interface IAppUserService {

    // get all users
    public List<AppUser> getUsers();

    // create AppUser
    public void saveUser(AppUser user);

    // delete user
    public void deleteUser(Long id);

    // find user
    public AppUser findUser(Long id);
}
