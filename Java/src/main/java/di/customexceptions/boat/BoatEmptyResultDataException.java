package di.customexceptions.boat;

public class BoatEmptyResultDataException extends RuntimeException{
    public BoatEmptyResultDataException (String message){
        super(message);
    }
}
