package mk.finki.lm.service.impl;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Doc;
import mk.finki.lm.model.Topic;
import mk.finki.lm.model.exception.InvalidBookIdException;
import mk.finki.lm.model.exception.TopicNotFoundException;
import mk.finki.lm.repository.jpa.BookRepository;
import mk.finki.lm.repository.jpa.DocRepository;
import mk.finki.lm.repository.jpa.TopicRepository;
import mk.finki.lm.service.BookService;
import mk.finki.lm.service.DocService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final TopicRepository topicRepository;
    private final DocService docService;
    private final DocRepository docRepository;


    public BookServiceImpl(BookRepository bookRepository, TopicRepository topicRepository, DocService docService, DocRepository docRepository) {
        this.bookRepository = bookRepository;
        this.topicRepository = topicRepository;

        this.docService = docService;
        this.docRepository = docRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Transactional
    @Override
    public Book findByIsbn(Long id) {
        return this.bookRepository.findByIsbn(id).orElseThrow(InvalidBookIdException::new);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.bookRepository.findByName(name);
    }

    @Override
    public Optional<Book> findByPublicationDate(LocalDate date) {
        return this.bookRepository.findByPublicationDate(date);
    }



//    @Override
//    public Book create(String name, String description, LocalDate publicationDate, List<Long> topicsIds, String author,MultipartFile image) throws IOException {
//        List<Topic> topics = this.topicRepository.findAllById(topicsIds);
//        Book book = new Book(name,description,publicationDate,topics,author);
//        return getBook(image, book);
//    }
//
//    @Transactional
//    @Override
//    public Book update(Long id, String name, String description, LocalDate publicationDate, List<Long> topicsIds, String author,MultipartFile image) throws IOException{
//       Book book = this.findByIsbn(id);
//       book.setName(name);
//       book.setDescription(description);
//       book.setPublicationDate(publicationDate);
//       List<Topic> topics = this.topicRepository.findAllById(topicsIds);
//       book.setTopics(topics);
//       book.setAuthor(author);
//        return getBook(image, book);
//    }
//
//    private Book getBook(MultipartFile image, Book book) throws IOException {
//        if(image!=null && !image.getName().isEmpty()){
//             byte[] bytes = image.getBytes();
//             String base64Image = String.format("data:%s;base64,%s",image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//             book.setImageBase64(base64Image);
//         }
//        return this.bookRepository.save(book);
//    }




    @Override
    @Transactional
    public List<Book> listBooksByTopic(Long topicId) {

        Topic topic = this.topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);
        if (topicId!=null) {
            return this.bookRepository.findByTopicsContaining(topic);
        }else {
//            return this.bookRepository.findAll();
            return null;
        }

    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Book create(String name, String description, LocalDate publicationDate, List<Long> topicsIds, String author, MultipartFile image, MultipartFile file) throws IOException {
        List<Topic> topics = this.topicRepository.findAllById(topicsIds);
        Book book = new Book(name,description,publicationDate,topics,author);
        return getBook(image, file, book);
    }

    @Transactional
    @Override
    public Book update(Long id, String name, String description, LocalDate publicationDate, List<Long> topicsIds, String author, MultipartFile image, MultipartFile file) throws IOException {
        Book book = this.findByIsbn(id);
       book.setName(name);
       book.setDescription(description);
       book.setPublicationDate(publicationDate);
       List<Topic> topics = this.topicRepository.findAllById(topicsIds);
       book.setTopics(topics);
       book.setAuthor(author);
        return getBook(image, file, book);
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.bookRepository.findAll(pageable);
    }

    private Book getBook(MultipartFile image, MultipartFile file, Book book) throws IOException {
        if(image!=null && !image.getName().isEmpty()){
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s",image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            book.setImageBase64(base64Image);
        }
          Doc doc = this.docService.saveDoc(file);
          book.setDoc(doc);

//        String docName = file.getOriginalFilename();
//        try {
//            Doc doc = new Doc(docName,file.getContentType(),file.getBytes());
//            this.docRepository.save(doc);
//            book.setDoc(doc);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        String docName = file.getOriginalFilename();
//        try{
//            byte[] bytes  = file.getBytes();
//
//            String doc = String.format("name:%s,data:%s,bytes: %s",docName,file.getContentType(), Arrays.toString(bytes));
//            book.setDoc(doc);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        Doc doc = this.docService.saveDoc(file);
//        book.setDoc(doc);
        return this.bookRepository.save(book);
    }



}
