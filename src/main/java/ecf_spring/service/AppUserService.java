package ecf_spring.service;

import ecf_spring.entity.AppUser;
import ecf_spring.exception.NotAdminException;
import ecf_spring.exception.NotSignInException;
import ecf_spring.exception.UserExistException;
import ecf_spring.exception.UserNotExistException;
import ecf_spring.repository.AppUserRepository;
import ecf_spring.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PartieRepository partieRepository;

    @Autowired
    private LoginService loginService;

    public boolean signUp(String firstName, String lastName, String email, String password) throws UserExistException {
        try {
            appUserRepository.findByEmail(email);
            throw new UserExistException();
        } catch (Exception ex) {
            AppUser user = AppUser.builder().firstName(firstName).lastName(lastName).email(email).password(password).build();
            appUserRepository.save(user);
            return user.getId() > 0;
        }
    }

    public boolean signIn(String email, String password) throws UserNotExistException {
        try {
            AppUser user = appUserRepository.findByEmailAndPassword(email, password);
            return loginService.login(user);
        } catch (Exception ex) {
            throw new UserNotExistException();
        }
    }

    public List<AppUser> getUsers() throws NotSignInException {
        if (loginService.isLogged()) {
            return (List<AppUser>) appUserRepository.findAll();
        }
        throw new NotSignInException();
    }

    public boolean disableUser(int userId) throws NotAdminException, NotSignInException, UserNotExistException {
        if (loginService.isLogged()) {
            if (loginService.isAdmin()) {
                try {
                    AppUser user = appUserRepository.findById(userId).get();
                    appUserRepository.save(user);
                    return true;
                } catch (Exception ex) {
                    throw new UserNotExistException();
                }
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

    public AppUser getAppUserById1(int appUser1Id) throws NotSignInException, UserNotExistException {
        if (loginService.isLogged()) {
            try {
                return appUserRepository.findById(appUser1Id).get();
            } catch (Exception ex) {
                throw new UserNotExistException();
            }
        }
        throw new NotSignInException();
    }

    public AppUser getAppUserById2(int appUser2Id) throws NotSignInException, UserNotExistException {
        if (loginService.isLogged()) {
            try {
                return appUserRepository.findById(appUser2Id).get();
            } catch (Exception ex) {
                throw new UserNotExistException();
            }
        }
        throw new NotSignInException();
    }

}

