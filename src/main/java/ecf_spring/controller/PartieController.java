package ecf_spring.controller;
import ecf_spring.entity.Resultat;
import ecf_spring.exception.*;
import ecf_spring.service.LoginService;
import ecf_spring.service.PartieService;
import ecf_spring.service.TournoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/party")
public class PartieController {
    @Autowired
    private PartieService partieService;

    @GetMapping("")
    public ModelAndView get() throws NotSignInException {
        ModelAndView mv = new ModelAndView("party");
        mv.addObject("parties", partieService.getParties());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView formAddCategory() {
        ModelAndView mv = new ModelAndView("partyform");
        return mv;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView formEditCategory(@PathVariable int id) throws NotSignInException, PartieNotExistException {
        ModelAndView mv = new ModelAndView("partyform");
        mv.addObject("partie", partieService.getPartieById(id));
        return mv;
    }
    @PostMapping("/edit/{id}")
    public String submitFormEditPartie(@PathVariable int id, @RequestParam Resultat resultat) throws NotSignInException, PartieNotExistException, NotAdminException, EmptyFieldsException {
        if(partieService.updatePartie(id, resultat)){
            return "redirect:/party";
        }
        return null;
    }
    @ExceptionHandler(NotSignInException.class)
    public ModelAndView handleException(NotSignInException ex) {
        ModelAndView mv = new ModelAndView("signin");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
    @ExceptionHandler(NotAdminException.class)
    public ModelAndView handleException(NotAdminException ex) {
        ModelAndView mv = new ModelAndView("partyform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
    @ExceptionHandler(PartieNotExistException.class)
    public ModelAndView handleException(PartieNotExistException ex) {
        ModelAndView mv = new ModelAndView("partyform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
    @ExceptionHandler(PartieExistException.class)
    public ModelAndView handleException(PartieExistException ex) {
        ModelAndView mv = new ModelAndView("partyform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
    @ExceptionHandler(EmptyFieldsException.class)
    public ModelAndView handleException(EmptyFieldsException ex) {
        ModelAndView mv = new ModelAndView("partyform");
        mv.addObject("errorMessage", ex.getMessage());
        return mv;
    }
}
