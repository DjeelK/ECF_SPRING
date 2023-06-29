package ecf_spring.service;

import ecf_spring.entity.AppUser;
import ecf_spring.entity.Partie;
import ecf_spring.entity.Resultat;
import ecf_spring.entity.Tournoi;
import ecf_spring.exception.*;
import ecf_spring.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartieService {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PartieRepository partieRepository;

    public boolean savePartie(Tournoi tournoi, AppUser appUser1, AppUser appUser2) throws PartieExistException, EmptyFieldsException, NotAdminException, NotSignInException {
        if (loginService.isLogged()) {
            if (loginService.isAdmin()) {
                if (tournoi != null && appUser1 != null && appUser2 != null) {
                    Partie partie = Partie.builder()
                            .tournoi(tournoi)
                            .appUser_1(appUser1)
                            .appUser_2(appUser2)
                            .build();
                    if (partieRepository.existsByTournoi(tournoi, appUser1, appUser2)) {
                        throw new PartieExistException();
                    }
                    partieRepository.save(partie);
                    return true;
                }
                throw new EmptyFieldsException("tournoi, appUser1, appUser2");
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

    public List<Partie> getParties() throws NotSignInException {
        if (loginService.isLogged()) {
            return (List<Partie>) partieRepository.findAll();
        }
        throw new NotSignInException();
    }

    public Partie getPartieById(int id) throws NotSignInException, PartieNotExistException {
        if(loginService.isLogged()) {
            try {
                return partieRepository.findById(id).get();
            }catch (Exception ex) {
                throw new PartieNotExistException();
            }
        }
        throw new NotSignInException();
    }

    public boolean updatePartie(int id, Resultat resultat) throws EmptyFieldsException, NotAdminException, NotSignInException, PartieNotExistException {
        if (loginService.isLogged()) {
            if (loginService.isAdmin()) {
                if (resultat != null) {
                    Partie partie = partieRepository.findById(id).get();
                    partie.setResultat(resultat);
                    partieRepository.save(partie);
                    return true;
                }
                throw new EmptyFieldsException("resultat");
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

}
