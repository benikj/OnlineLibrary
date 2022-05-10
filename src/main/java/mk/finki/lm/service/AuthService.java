package mk.finki.lm.service;

import mk.finki.lm.model.University;
import mk.finki.lm.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User login(String username,String password);
    User register(String username, String password, String repeatedPass, String name, String surname, Integer age, String email, University university);

}
