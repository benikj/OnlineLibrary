package mk.finki.lm.web.controller;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Doc;
import mk.finki.lm.model.Topic;
import mk.finki.lm.service.BookService;
import mk.finki.lm.service.DocService;
import mk.finki.lm.service.TopicService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;
    private final TopicService topicService;
    private final DocService docService;


    public BookController(BookService bookService, TopicService topicService, DocService docService) {
        this.bookService = bookService;
        this.topicService = topicService;

        this.docService = docService;
    }

    //mi treba da gi prikaze knigite
    @GetMapping("/books")
    @Transactional
    public String showBooks(@RequestParam(required=false) String error, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }

//        List<Book> books = this.bookService.findAll();
//        List<Topic> topics =this.topicService.listTopics();
//        model.addAttribute("topics",topics);
//        model.addAttribute("books",books);
        List<Doc> docs = this.docService.getFiles();

        model.addAttribute("docs",docs);
        return findPaginated(1,model);
//        return "books";
    }

    @GetMapping("/books/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,Model model){
        int pageSize =2;
        Page<Book> page = bookService.findPaginated(pageNo,pageSize);
        List<Book> books = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("books",books);
        return "books";
    }


   @GetMapping("/books/topics/{id}")
   public String showBooksByTopic(@PathVariable Long id,Model model){
        Topic topic = this.topicService.findById(id);
        List<Topic> topics = this.topicService.listTopics();
        List<Book> books = this.bookService.listBooksByTopic(id);
        model.addAttribute("books",books);
        model.addAttribute("topics",topics);
        model.addAttribute("topic",topic);
        return "book-topic";
   }

    @GetMapping("/books/add")
    public String showAdd(Model model){
        //ova e formata so koja dodavame kniga
        List<Book> books = this.bookService.findAll();
        List<Topic> topics = this.topicService.listTopics();
        model.addAttribute("books",books);
        model.addAttribute("topics",topics);
        return "add-book";
    }

    @GetMapping("/books/{id}/edit")
    public String showEdit(@PathVariable Long id,Model model){
        //ova e formata so koja editirame kniga
        // mi treba knigata so kje ja izmenuvam
        Book book = this.bookService.findByIsbn(id);
        List<Topic> topics = this.topicService.listTopics();
        model.addAttribute("book",book);
        model.addAttribute("topics",topics);
        return "add-book";
    }

    @PostMapping("/books")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                         @RequestParam List<Long> topics,
                         @RequestParam String author,
                         @RequestParam MultipartFile image,
                         @RequestParam("file") MultipartFile file) throws IOException {

        this.bookService.create(name,description,date,topics,author,image,file);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                         @RequestParam List<Long> topics,
                         @RequestParam String author,
                         @RequestParam MultipartFile image,
                         @RequestParam("file") MultipartFile file) throws IOException {
        this.bookService.update(id, name, description, date, topics, author,image,file);
        return "redirect:/books";
    }



    @PostMapping("/books/{id}/delete")
    public String delete(@PathVariable Long id){
        this.bookService.deleteById(id);
        return "redirect:/books";
    }




}
