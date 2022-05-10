package mk.finki.lm.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException(){
        super("User with that username already exist");
    }
}

