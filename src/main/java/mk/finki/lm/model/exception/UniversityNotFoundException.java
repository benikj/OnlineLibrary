package mk.finki.lm.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UniversityNotFoundException extends RuntimeException {

    public UniversityNotFoundException(){
        super("University not found");
    }
}
