package di.customexceptions.seat;

public class SeatsWithBoatIsEmpty extends RuntimeException{
    public SeatsWithBoatIsEmpty(String message){
        super(message);
    }
}
