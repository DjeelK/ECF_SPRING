package ecf_spring.service;

import ecf_spring.entity.AppUser;
import ecf_spring.entity.Partie;
import ecf_spring.entity.Resultat;
import ecf_spring.entity.Tournoi;
import ecf_spring.exception.EmptyFieldsException;
import ecf_spring.exception.NotAdminException;
import ecf_spring.exception.NotSignInException;

import ecf_spring.exception.PartieNotExistException;
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

    public Partie createPartie(Tournoi tournoi, AppUser appUser1, AppUser appUser2) throws NotAdminException {
        if (loginService.isLogged() && loginService.isAdmin()) {
            Partie partie = Partie.builder()
                    .tournoi(tournoi)
                    .appUser_1(appUser1)
                    .appUser_2(appUser2)
                    .build();
            return partieRepository.save(partie);
        } else {
            throw new NotAdminException();
        }
    }

    public List<Partie> getParties() throws NotSignInException {
        if (loginService.isLogged()) {
            return (List<Partie>) partieRepository.findAll();
        }
        throw new NotSignInException();
    }

    public Partie getPartieById(int id) throws NotSignInException, PartieNotExistException {
        if (loginService.isLogged()) {
            try {
                return partieRepository.findById(id).get();
            } catch (Exception ex) {
                throw new PartieNotExistException();
            }
        }
        throw new NotSignInException();
    }

    public boolean updatePartie(int id, Resultat resultat) throws EmptyFieldsException, NotAdminException, NotSignInException, PartieNotExistException {
        if (loginService.isLogged()) {
            if (loginService.isAdmin()) {
                if (resultat != null) {
                    try {
                        Partie partie = partieRepository.findById(id).get();
                        partie.setResultat(resultat);
                        partieRepository.save(partie);
                        return true;
                    }catch (Exception ex) {
                        throw new PartieNotExistException ();
                    }

                }
                throw EmptyFieldsException.with("title");
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

}
