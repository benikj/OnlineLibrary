package mk.finki.lm.service;

import mk.finki.lm.model.Topic;
import mk.finki.lm.model.University;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UniversityService  {
    University findByName(String name);
    University findById(Long id);
    List<University> findAll();
    University create(String name);
    University update(Long id,String name);
    University delete(Long id);
    Page<University> findPaginated(int pageNo, int pageSize);


}
