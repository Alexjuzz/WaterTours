package di.customexceptions.seat;

public class SeatAlreadyBookedException extends RuntimeException{
    public SeatAlreadyBookedException(String message){
        super(message);
    }
}
