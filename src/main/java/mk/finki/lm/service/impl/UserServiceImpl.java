package mk.finki.lm.service.impl;

import mk.finki.lm.model.University;
import mk.finki.lm.model.User;
import mk.finki.lm.model.exception.*;
import mk.finki.lm.repository.jpa.UniversityRepository;
import mk.finki.lm.repository.jpa.UserRepository;
import mk.finki.lm.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.inline.StandardHTMLInliner;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(UniversityRepository universityRepository, UserRepository userRepository) {
        this.universityRepository = universityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException());
    }

    @Override
    public User register(User user) {
        if (this.userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistException();
        }
        return this.userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }


}
