package ecf_spring.service.impl;

import ecf_spring.entity.AppUser;
import ecf_spring.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionLoginServiceImpl implements LoginService {

    @Autowired
    HttpSession httpSession;

    @Override
    public boolean login(AppUser user) {
        httpSession.setAttribute("isLogged", true);
        httpSession.setAttribute("fullName", user.getFirstName() + " "+user.getLastName());
        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("isAdmin", user.isAdmin());
        return true;
    }

    @Override
    public boolean isLogged() {
        return httpSession.getAttribute("isLogged") != null && (boolean) httpSession.getAttribute("isLogged");
    }

    @Override
    public int getUserId() {
        return (int)httpSession.getAttribute("userId");
    }

    @Override
    public boolean isAdmin() {
        Object isLogged = httpSession.getAttribute("isLogged");
        Object isAdmin = httpSession.getAttribute("isAdmin");
        return isLogged != null && isAdmin != null && (boolean) isAdmin;
    }


}
