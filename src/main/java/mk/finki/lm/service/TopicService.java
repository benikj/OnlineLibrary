package mk.finki.lm.service;

import mk.finki.lm.model.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {

    Topic findById(Long id);
    Topic create(String name,String description);
    Topic update(Long id,String name,String description);
    Topic delete(Long id);
    List<Topic> listTopics();
    List<Topic> searchTopics(String text);
    Page<Topic> findPaginated(int pageNo, int pageSize);
}
