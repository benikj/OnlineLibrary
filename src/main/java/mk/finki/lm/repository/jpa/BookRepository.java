package mk.finki.lm.repository.jpa;

import mk.finki.lm.model.Book;
import mk.finki.lm.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(Long id);
    Optional<Book> findByName(String name);
    List<Book> findByNameLike(String name);
    Optional<Book> findByPublicationDate(LocalDate date);
    List<Book> findByNameLikeAndDescriptionLike(String name,String description);
    List<Book> findByDescriptionLike(String description);
    List<Book> findAllByTopicsContaining(Topic topic);
    List<Book> findByTopicsContaining(Topic topic);
    void deleteByName(String name);
}
