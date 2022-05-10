package mk.finki.lm.service;

import mk.finki.lm.model.University;
import mk.finki.lm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User register(User user);
    List<User> listUsers();
    Page<User> findPaginated(int pageNo,int pageSize);


}
