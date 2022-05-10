package mk.finki.lm.web.controller;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Topic;
import mk.finki.lm.model.University;
import mk.finki.lm.service.UniversityService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/university")
    @Secured("ROLE_ADMIN")
    public String showUniversity(@RequestParam(required=false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
//        List<University> universities = this.universityService.findAll();
//        model.addAttribute("universities",universities);
//        return "university";
        return findPaginated(1,model);
    }

    @GetMapping("/university/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,Model model){
        int pageSize =4;
        Page<University> page = universityService.findPaginated(pageNo,pageSize);
        List<University> universities = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("universities",universities);
        return "university";
    }

    //mi treba da go prikaze dodavanjeto
    @GetMapping("/university/add")
    public String showAdd(Model model){
        //ova e formata so koja dodavame topic
        List<University> universities = this.universityService.findAll();
        model.addAttribute("universities",universities);
        return "add-university";
    }

    //da go prikaze edituvanjeto
    @GetMapping("/university/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model){
        //ova e formata so koja editirame topic
        // mi treba topic so kje go izmenuvam
        University university =this.universityService.findById(id);
        model.addAttribute("university",university);
        return "add-university";
    }

    //kreiranejto
    @PostMapping("/university")
    public String create(@RequestParam String name){
        //ovde kako se kako argumenti oni vo formata
        //po krieranejto na produktot se prikazuvaat site produkti
        this.universityService.create(name);
        return "redirect:/university";
    }


    //updatiranje
    @PostMapping("/university/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name){
        this.universityService.update(id,name);
        return "redirect:/university";
    }


    @PostMapping("/university/{id}/delete")
    public String delete(@PathVariable Long id){
        this.universityService.delete(id);
        return "redirect:/university";
    }

}
