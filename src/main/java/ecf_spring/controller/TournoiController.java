package ecf_spring.controller;

import ecf_spring.exception.*;
import ecf_spring.service.TournoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/tournoi")
public class TournoiController {
    @Autowired
    private TournoiService tournoiService;


    @GetMapping("")
    public ModelAndView get() throws NotSignInException {
        ModelAndView mv = new ModelAndView("tournoi");
        mv.addObject("tournois", tournoiService.getTournois());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView formAddTournoi() {
        ModelAndView mv = new ModelAndView("tournoiform");
        return mv;
    }

    @PostMapping("/add")
    public String submitFormAddTournoi(String title, String dateDebut, String dateFin) throws TournoiExistException, EmptyFieldsException, NotAdminException, NotSignInException {
        if (tournoiService.saveTournoi(title, dateDebut, dateFin)) {
            return "redirect:/tournoi";
        }
        return null;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formEditTournoi(@PathVariable int id) throws NotSignInException, TournoiNotExistException {
        ModelAndView mv = new ModelAndView("tournoiform");
        mv.addObject("tournoi", tournoiService.getTournoiById(id));
        return mv;
    }

    @ExceptionHandler(NotSignInException.class)
    public ModelAndView handleNotSignInException(NotSignInException ex) {
        ModelAndView mv = new ModelAndView("signin");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(NotAdminException.class)
    public ModelAndView handleNotAdminException(NotAdminException ex) {
        ModelAndView mv = new ModelAndView("tournoiform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(TournoiNotExistException.class)
    public ModelAndView handleTournoiNotExistException(TournoiNotExistException ex) {
        ModelAndView mv = new ModelAndView("tournoiform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(TournoiExistException.class)
    public ModelAndView handleTournoiExistException(TournoiExistException ex) {
        ModelAndView mv = new ModelAndView("tournoiform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ModelAndView handleEmptyFieldsException(EmptyFieldsException ex) {
        ModelAndView mv = new ModelAndView("tournoiform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
}
