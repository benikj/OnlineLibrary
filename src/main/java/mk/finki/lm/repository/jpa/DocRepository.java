package mk.finki.lm.repository.jpa;

import mk.finki.lm.model.Doc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends JpaRepository<Doc,Integer> {
    Doc findByBook_Isbn(Long id);

}
