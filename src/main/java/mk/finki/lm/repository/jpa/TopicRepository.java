package mk.finki.lm.repository.jpa;

import mk.finki.lm.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
        List<Topic>findAllByNameLike(String text);
        List<Topic> findAllById(Long id);
        void deleteByName(String name);
        Optional<Topic> findById(Long id);
}
