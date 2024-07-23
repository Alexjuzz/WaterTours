package di.customexceptions.boat;

public class BoatValidCapacityException extends RuntimeException{
    public BoatValidCapacityException (String message){
        super(message);
    }
}
