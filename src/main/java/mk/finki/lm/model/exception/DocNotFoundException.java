package mk.finki.lm.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DocNotFoundException extends RuntimeException {

    public DocNotFoundException(){
        super("Document with the given id wasn't found");
    }
}
