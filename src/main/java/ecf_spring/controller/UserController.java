package ecf_spring.controller;

import ecf_spring.exception.UserExistException;
import ecf_spring.exception.UserNotExistException;
import ecf_spring.service.AppUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private HttpServletResponse response;

//    public UserController(AppUserService appUserService) {
//    }

    @GetMapping("signin")
    public ModelAndView signIn(@RequestParam(required = false) String message) {
        ModelAndView mv = new ModelAndView("signin");

        // Vérifier si le message est présent
        if (message != null && !message.isEmpty()) {
            mv.addObject("message", message);
        }

        return mv;
    }

    @PostMapping("signin")
    public String signIn(@RequestParam String email, @RequestParam String password) throws IOException {
        try {
            if (appUserService.signIn(email, password)) {
                ModelAndView mv = new ModelAndView("userExist");
                mv.addObject("message", "Bienvenue");
                return "userExist";
            } else {
                return "redirect:/user/signup";
            }
        } catch (UserNotExistException ex) {
            return "redirect:/user/signup";
        }
    }

    @ExceptionHandler(UserNotExistException.class)
    public ModelAndView handleUserNotExist(UserNotExistException ex) {
        ModelAndView mv = new ModelAndView("userNotExist");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @GetMapping("signup")
    public ModelAndView postSignIn() {
        ModelAndView mv = new ModelAndView("signup");
        return mv;
    }

    @PostMapping("signup")
    public String postSignUp(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) throws UserExistException, IOException {
        if(appUserService.signUp(firstName, lastName, email, password)) {
            return "redirect:/user/signin";
        }
        return null;
    }

    @ExceptionHandler(UserExistException.class)
    public ModelAndView handleUserExist(UserExistException ex) {
        ModelAndView mv = new ModelAndView("signup");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @RequestMapping("/error")
    public ModelAndView handleError() {
        ModelAndView mv = new ModelAndView("error");
        return mv;
    }
}
