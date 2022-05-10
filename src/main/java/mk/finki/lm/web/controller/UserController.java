package mk.finki.lm.web.controller;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.User;
import mk.finki.lm.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
//        List<User> users = this.userService.listUsers();
//        model.addAttribute("users", users);
//        return "users";
        return findPaginated(1,model);
    }

    @GetMapping("/users/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 6;
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> users = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        return "users";
    }
}
