package mk.finki.lm.model.exception;

public class InvalidUserCredentials extends RuntimeException{
    public InvalidUserCredentials(){
        super("Invalid username or password");
    }
}
