package mk.finki.lm.web.controller;

import mk.finki.lm.model.University;
import mk.finki.lm.service.AuthService;
import mk.finki.lm.service.UniversityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;
    private final UniversityService universityService;

    public RegisterController(AuthService authService, UniversityService universityService) {
        this.authService = authService;
        this.universityService = universityService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required=false) String error,Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        List<University> university=this.universityService.findAll();
        model.addAttribute("university",university);
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam Integer age,
                               @RequestParam String email,
                               @RequestParam(required = false) University university){

        try{
            this.authService.register(username, password, repeatedPassword, name, surname, age, email, university);
            return "redirect:/login?info=Successful registration!";
        }catch (RuntimeException e){
            return "redirect:/register?error= " + e.getLocalizedMessage();
        }

    }

}
