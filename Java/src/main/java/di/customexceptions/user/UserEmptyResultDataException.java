package di.customexceptions.user;

public class UserEmptyResultDataException extends RuntimeException{
    public UserEmptyResultDataException(String message){
        super(message);
    }
}
