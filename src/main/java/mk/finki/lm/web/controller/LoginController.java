package mk.finki.lm.web.controller;

import mk.finki.lm.model.User;
import mk.finki.lm.model.exception.InvalidUserCredentials;
import mk.finki.lm.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

        @GetMapping
         public String getLoginPage(){
               return "login";
        }

        @PostMapping
        public String login(HttpServletRequest request, Model model){
            User user =null;
            try{
                user =this.authService.login(request.getParameter("username"),
                        request.getParameter("password"));
                request.getSession().setAttribute("user",user);
                return "redirect:/home";

            }catch (InvalidUserCredentials exception){
                     model.addAttribute("hasError",true);
                     model.addAttribute("error",exception.getMessage());
                     return "login";


    }
        }
}
