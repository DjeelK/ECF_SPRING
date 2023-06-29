package ecf_spring.service;
import ecf_spring.entity.Tournoi;
import ecf_spring.exception.*;
import ecf_spring.repository.TournoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournoiService {
    @Autowired
    private TournoiRepository tournoiRepository;
    @Autowired
    private LoginService loginService;

    public boolean saveTournoi(String title, String dateDebut, String dateFin) throws TournoiExistException, EmptyFieldsException, NotAdminException, NotSignInException {
        if (loginService.isLogged()) {
            if (loginService.isAdmin()) {
                if (title != null && dateDebut != null && dateFin != null) {
                    if (!tournoiRepository.existsTournoiByTitle(title)) {
                        Tournoi tournoi = Tournoi.builder()
                                .title(title)
                                .dateDebut(dateDebut)
                                .dateFin(dateFin)
                                .build();
                        tournoiRepository.save(tournoi);
                        return tournoi.getId() > 0;
                    }
                    throw new TournoiExistException();
                }
                throw EmptyFieldsException.with("title, dateDebut, dateFin");
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

    public List<Tournoi> getTournois() throws NotSignInException {
        if(loginService.isLogged()) {
            return (List<Tournoi>) tournoiRepository.findAll();
        }
        throw new NotSignInException();
    }

    public Tournoi getTournoiById(int id) throws NotSignInException, TournoiNotExistException {
        if(loginService.isLogged()) {
            try {
                return tournoiRepository.findById(id).get();
            }catch (Exception ex) {
                throw new TournoiNotExistException();
            }
        }
        throw new NotSignInException();
    }
}

