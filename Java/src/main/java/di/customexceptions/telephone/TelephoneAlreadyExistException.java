package di.customexceptions.telephone;

public class TelephoneAlreadyExistException extends RuntimeException{
    public TelephoneAlreadyExistException(String message){
        super(message);
    }
}
