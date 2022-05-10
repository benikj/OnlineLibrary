package mk.finki.lm.service.impl;

import mk.finki.lm.model.University;
import mk.finki.lm.model.exception.InvalidUniversityNameException;
import mk.finki.lm.model.exception.UniversityNotFoundException;
import mk.finki.lm.repository.jpa.UniversityRepository;
import mk.finki.lm.service.UniversityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public University findByName(String name) {
        return this.universityRepository.findByName(name).orElseThrow(InvalidUniversityNameException::new);
    }

    @Override
    public University findById(Long id) {
        return this.universityRepository.findById(id).orElseThrow(()->new UniversityNotFoundException());
    }

    @Override
    public List<University> findAll() {
        return this.universityRepository.findAll();
    }

    @Override
    public University create(String name) {
        University university = new University(name);
        return this.universityRepository.save(university);
    }

    @Override
    public University update(Long id, String name) {
        University university = this.findById(id);
        university.setName(name);
        return this.universityRepository.save(university);
    }

    @Override
    public University delete(Long id) {
        University university = this.findById(id);
        this.universityRepository.delete(university);
        return university;

    }

    @Override
    public Page<University> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.universityRepository.findAll(pageable);
    }


}
