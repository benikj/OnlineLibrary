package mk.finki.lm.service;

import mk.finki.lm.model.Doc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocService {
    Doc saveDoc(MultipartFile file);
    public Doc getFile(Integer fileId);
    public List<Doc> getFiles();



}
