package di.customexceptions.boat;

public class BoatNotFoundException extends RuntimeException{
    public BoatNotFoundException(String message){
        super(message);
    }
}
