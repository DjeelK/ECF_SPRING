package ecf_spring.controller;

import ecf_spring.entity.AppUser;
import ecf_spring.entity.Resultat;
import ecf_spring.entity.Tournoi;
import ecf_spring.exception.*;
import ecf_spring.service.AppUserService;
import ecf_spring.service.PartieService;
import ecf_spring.service.TournoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/partie")
public class PartieController {
    @Autowired
    private  PartieService partieService;
    @Autowired
    private TournoiService tournoiService;
    @Autowired
    private AppUserService appUserService;

    @GetMapping("")
    public ModelAndView getParties() throws NotSignInException {
        ModelAndView mv = new ModelAndView("partie");
        mv.addObject("parties", partieService.getParties());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView formAddPartie() {
        ModelAndView mv = new ModelAndView("partieform");
        return mv;
    }

    @PostMapping("/add")
    public String submitFormAddPartie(int tournoiId, int appUser1Id, int appUser2Id) throws EmptyFieldsException, NotAdminException, NotSignInException, TournoiNotExistException, PartieExistException, UserNotExistException {
        Tournoi tournoi = tournoiService.getTournoiById(tournoiId);
        AppUser appUser1 = appUserService.getAppUserById1(appUser1Id);
        AppUser appUser2 = appUserService.getAppUserById2(appUser2Id);
        if (partieService.savePartie(tournoi, appUser1, appUser2)) {
            return "redirect:/partie";
        }
        return null;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView formEditPartie(@PathVariable int id) throws NotSignInException, PartieNotExistException {
        ModelAndView mv = new ModelAndView("partieform");
        mv.addObject("partie", partieService.getPartieById(id));
        return mv;
    }

    @PostMapping("/update/{id}")
    public String submitFormUpdatePartie(@PathVariable int id, Resultat resultat) throws EmptyFieldsException, NotAdminException, NotSignInException, PartieNotExistException {
        if (partieService.updatePartie(id, resultat)) {
            return "redirect:/partie";
        }
        return null;
    }

    @ExceptionHandler(NotSignInException.class)
    public ModelAndView handleNotSignInException(NotSignInException ex) {
        ModelAndView mv = new ModelAndView("signin");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(NotAdminException.class)
    public ModelAndView handleNotAdminException(NotAdminException ex) {
        ModelAndView mv = new ModelAndView("partieform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(PartieNotExistException.class)
    public ModelAndView handlePartieNotExistException(PartieNotExistException ex) {
        ModelAndView mv = new ModelAndView("partieform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
}
