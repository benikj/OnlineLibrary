package mk.finki.lm.web.controller;

import mk.finki.lm.model.Doc;
import mk.finki.lm.service.DocService;
import org.h2.engine.Mode;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
public class DocumentController {

    private final DocService docService;

    public DocumentController(DocService docService) {
        this.docService = docService;
    }


    @GetMapping("/docs")
    public String getDocPage(Model model){
        List<Doc> docs = docService.getFiles();
        model.addAttribute("docs",docs);
        return "doc";
    }



    @PostMapping("/uploadFile")
    public String upload(@RequestParam("file")MultipartFile file, Model model){
        docService.saveDoc(file);
        return "redirect:/docs";
    }

    @GetMapping("/downloadFiles/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Integer id, Model model){
        Doc doc = this.docService.getFile(id);
        model.addAttribute("doc",doc);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+ "\"")
                .body(new ByteArrayResource(doc.getData()));
    }


}

