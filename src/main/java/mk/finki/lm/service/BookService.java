package mk.finki.lm.service;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Doc;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();
    Book findByIsbn(Long id);
    Optional<Book> findByName(String name);
    Optional<Book> findByPublicationDate(LocalDate date);
    List<Book> listBooksByTopic(Long topicId);
//    Book create(String name,String description,LocalDate publicationDate,List<Long> topicsIds,String author,MultipartFile image)throws IOException;
//    Book update(Long id,String name,String description,LocalDate publicationDate,List<Long> topicsIds,String author,MultipartFile image) throws IOException;
    void deleteById(Long id);
    Book create(String name,String description,LocalDate publicationDate,List<Long> topicsIds,String author,MultipartFile image, MultipartFile file)throws IOException;
    Book update(Long id,String name,String description,LocalDate publicationDate,List<Long> topicsIds,String author,MultipartFile image,MultipartFile file) throws IOException;
    Page<Book> findPaginated(int pageNo, int pageSize);


}
