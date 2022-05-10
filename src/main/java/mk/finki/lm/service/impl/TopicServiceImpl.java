package mk.finki.lm.service.impl;

import mk.finki.lm.model.Topic;
import mk.finki.lm.model.exception.InvalidTopicIdException;
import mk.finki.lm.repository.jpa.TopicRepository;
import mk.finki.lm.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TopicServiceImpl  implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Override
    public Topic findById(Long id) {
        return this.topicRepository.findById(id).orElseThrow(InvalidTopicIdException::new);
    }



    @Override
    public Topic create(String name, String description) {
        if (name.isEmpty()){
            throw new IllegalArgumentException();
        }
        Topic topic = new Topic(name,description);
        return topicRepository.save(topic);

    }

    @Override
    public Topic update(Long id,String name, String description) {
        Topic topic = this.findById(id);
        topic.setName(name);
        topic.setDescription(description);
        return this.topicRepository.save(topic);
    }

    @Transactional
    @Override
    public Topic delete(Long id) {
        Topic topic = this.findById(id);
        topic.getBooks().forEach(book -> book.setTopics(null));
        this.topicRepository.delete(topic);
        return topic;
    }

    @Override
    public List<Topic> listTopics() {
        return this.topicRepository.findAll();
    }

    @Override
    public List<Topic> searchTopics(String text) {
        return this.topicRepository.findAllByNameLike(text);
    }

    @Override
    public Page<Topic> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.topicRepository.findAll(pageable);
    }
}
