package mk.finki.lm.web.controller;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Topic;
import mk.finki.lm.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TopicController {
    //ova mi e za dodavanje nov topic
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }


    @GetMapping("/topics")
    public String showTopics(@RequestParam(required=false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
//        List<Topic> topics = this.topicService.listTopics();
//        model.addAttribute("topics",topics);
//        return "topics";
        return findPaginated(1,model);
    }

    @GetMapping("/topics/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,Model model) {
        int pageSize =4;
        Page<Topic> page = this.topicService.findPaginated(pageNo,pageSize);
        List<Topic> topics = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("topics",topics);
        return "topics";

    }

    //mi treba da go prikaze dodavanjeto
    @GetMapping("/topics/add")
    public String showAdd(Model model){
        //ova e formata so koja dodavame topic
        List<Topic> topics = this.topicService.listTopics();
        model.addAttribute("topics",topics);
        return "add-topic";
    }

    //da go prikaze edituvanjeto
    @GetMapping("/topics/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model){
        //ova e formata so koja editirame topic
        // mi treba topic so kje go izmenuvam
        Topic topic =this.topicService.findById(id);
        model.addAttribute("topic",topic);
        return "add-topic";
    }

    //kreiranejto
    @PostMapping("/topics")
    public String create(@RequestParam String name,
                         @RequestParam String description){
        //ovde kako se kako argumenti oni vo formata
        //po krieranejto na produktot se prikazuvaat site produkti
        this.topicService.create(name,description);
        return "redirect:/topics";
    }


    //updatiranje
    @PostMapping("/topics/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String description){
        this.topicService.update(id,name,description);
        return "redirect:/topics";
    }


    @PostMapping("/topics/{id}/delete")
    public String delete(@PathVariable Long id){
        this.topicService.delete(id);
        return "redirect:/topics";
    }




}
