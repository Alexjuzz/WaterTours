package di.customexceptions.telephone;

public class TelephoneNotFoundException extends RuntimeException {
    public TelephoneNotFoundException(String message){
        super(message);
    }
}
