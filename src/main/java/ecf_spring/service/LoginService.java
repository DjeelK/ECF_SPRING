package ecf_spring.service;

import ecf_spring.entity.AppUser;

public interface LoginService {

    public boolean login(AppUser user);
    public boolean isLogged();
    public int getUserId();
    public boolean isAdmin();

}
