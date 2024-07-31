package di.customexceptions.booking;

public class BookingReservationIsEmpty extends RuntimeException{
    public BookingReservationIsEmpty(String message){
        super(message);
    }
}
