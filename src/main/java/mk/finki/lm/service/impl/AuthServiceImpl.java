package mk.finki.lm.service.impl;

import mk.finki.lm.model.Role;
import mk.finki.lm.model.University;
import mk.finki.lm.model.User;
import mk.finki.lm.model.exception.InvalidArgumentException;
import mk.finki.lm.model.exception.InvalidUserCredentials;
import mk.finki.lm.model.exception.PasswordsDoNotMatchException;
import mk.finki.lm.repository.jpa.RoleRepository;
import mk.finki.lm.repository.jpa.UserRepository;
import mk.finki.lm.service.AuthService;
import mk.finki.lm.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() ||password==null ||password.isEmpty()){
            throw new InvalidArgumentException();
        }

        return userRepository.findByUsernameAndPassword(username,password).orElseThrow(InvalidUserCredentials::new);
    }

    @Override
    public User register(String username, String password, String repeatedPass, String name, String surname, Integer age, String email, University university) {
        //prvo proveruvame pass dali ni se isti
            if (!password.equals(repeatedPass)){
                throw new PasswordsDoNotMatchException();
            }
            User user = new User();
            user.setUsername(username);
            user.setAge(age);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setUniversity(university);
            user.setPassword(passwordEncoder.encode(password));
//            Role role = this.roleRepository.findByName("ROLE_USER");
//            user.setRoles(Collections.singletonList(role));
        Role role;
        if(user.getUsername().contains("ADMIN") || user.getUsername().contains("admin")){
            role = this.roleRepository.findByName("ROLE_ADMIN");
        }else{
            role = this.roleRepository.findByName("ROLE_USER");
        }
        user.setRoles(Collections.singletonList(role));
      //  user.setRoles(Arrays.asList(role));
        return this.userService.register(user);
    }

    @PostConstruct
    public void init(){
        if(!this.userRepository.existsById("admin")){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.passwordEncoder.encode("Admin1@"));
            admin.setEmail("admin1@gmail.com");

            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }

    }
}
