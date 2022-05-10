package mk.finki.lm.service.impl;

import mk.finki.lm.model.Doc;
import mk.finki.lm.model.exception.DocNotFoundException;
import mk.finki.lm.repository.jpa.DocRepository;
import mk.finki.lm.service.DocService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocServiceImpl implements DocService {

    private final DocRepository repository;

    public DocServiceImpl(DocRepository repository) {
        this.repository = repository;
    }

    @Override
    public Doc saveDoc(MultipartFile file) {
        String docName = file.getOriginalFilename();
        try {
            Doc doc = new Doc(docName,file.getContentType(),file.getBytes());
            return repository.save(doc);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


//    @Override
//    public Integer saveDoc(MultipartFile file) {
//        Integer docId = null;
//        if (!file.isEmpty()){
//            Doc doc = new Doc();
//            doc.setDocName(file.getOriginalFilename());
//            doc.setDocType(file.getContentType());
//            try{
//                doc.setData(file.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Doc doc1 = repository.save(doc);
//            docId=doc.getId();
//        }
//        return docId;
//    }



    @Override
    public Doc getFile(Integer fileId) {
        return this.repository.findById(fileId).orElseThrow(DocNotFoundException::new);
    }

    @Override
    public List<Doc> getFiles() {
        return this.repository.findAll();
    }
}
