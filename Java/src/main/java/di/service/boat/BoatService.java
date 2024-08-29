package di.service.boat;


import di.customexceptions.boat.BoatNotFoundException;
import di.customexceptions.boat.BoatValidCapacityException;
import di.customexceptions.seat.SeatsWithBoatIsEmpty;
import di.enums.TripType;
import di.model.dto.boats.ResponseBoat;
import di.model.entity.boats.AbstractBoat;
import di.model.entity.boats.Boat;
import di.model.entity.seats.Seat;
import di.repository.boat.BoatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO - доделать логирование
@Service
public class BoatService {
    private final BoatRepository repository;


    @Autowired
    public BoatService(BoatRepository repository) {
        this.repository = repository;

    }

    public ResponseBoat createBoat(Boat boat) { // Создание корабля.
        AbstractBoat newBoat =  new Boat();
        newBoat.setName(boat.getName());
        newBoat.setTrip(boat.getTrip());
        newBoat.setTripType(boat.getTripType());
        return convertBoatToResponseBoat(repository.save(newBoat));
    }


    public List<ResponseBoat> getAllBoats() { // получение списка всех найденых судов
        return repository.findAll().stream().map(this::convertBoatToResponseBoat).toList();
    }

    /**
     * Медод для добавления мест на судно. Прнимает ID существуещего корабля из базы данных.
     * Если на корабле уже обьявлены были места то он просто возвращается какой и был.
     * А если места были не инициализированы, то будет инициализация всех мест.
     *
     * @param boatId   - ID судна в базе.
     * @param capacity - Обьем необходимых мест.
     * @return возращает искомый корабль
     */
    public ResponseBoat setPlacesToBoat(Long boatId, Integer capacity) {
        AbstractBoat boat = repository.findById(boatId)
                .orElseThrow(() -> new BoatNotFoundException("Boat with ID :" + boatId + " not found" )); // поиск нужного судна

        if(capacity < 5 || capacity > 100){
            throw new BoatValidCapacityException("Capacity equals: " + capacity);
        }


        if (boat.getPlaces().isEmpty()) {  // проверка были ли инициализированы уже места.
            for (int i = 1; i <= capacity; i++) {
                Seat seat = new Seat();
                seat.setSeatNumber(i);
                seat.setBoat(boat);
                boat.getPlaces().add(seat);
            }
        }
        return convertBoatToResponseBoat(repository.save(boat));
    }


    /**
     * Метод получения всех мест у судна
     * @param boatId - ID Судна.
     * @return - List<Seat> всех мест.
     */
    public List<Seat> getSeatsByBoatId(Long boatId) {
        AbstractBoat boat = repository.findById(boatId)
                .orElseThrow(() -> new BoatNotFoundException("Boat not found with ID: " + boatId));

        List<Seat> seatList = boat.getPlaces();
        if (seatList.isEmpty()) {
            throw new SeatsWithBoatIsEmpty("No seats found for boat with ID: " + boatId);
        }
        return seatList;
    }


    /**
     * Метод для установки имени у судна. Будет установлено имя, если оно ранее не было установлено
     *
     * @param id   - ID Судна
     * @param name - Имя
     * @return - обьект ответа ResponseBoat
     */
    public ResponseBoat setNameToBoat(Long id, String name) {
        AbstractBoat boat = repository.findById(id)
                .orElseThrow(() -> new BoatNotFoundException("Boat not found: " + id)); // поиск нужного судна

        boat.setName(name);
        return convertBoatToResponseBoat(repository.save(boat));
    }

    public ResponseBoat setTripBoat(Long boatId, TripType tripType) {

        AbstractBoat boat = repository.findById(boatId)
                .orElseThrow(() -> new EntityNotFoundException("Boat not found: " + boatId)); // поиск нужного судна

        boat.setTripType(tripType);
        return convertBoatToResponseBoat(repository.save(boat));
    }

    /**
     * Метод поиска судна по его ID
     *
     * @param id - ID судна в базе данных
     * @return - возращает обьект ответа ResponseBoat
     * <p>
     */
    public ResponseBoat getBoatById(Long id) {
       return  convertBoatToResponseBoat(repository.findById(id).
               orElseThrow(() -> new BoatNotFoundException("Boat with ID:"  + id + " not found")));
    }


    // region convert methods

    /**
     * Метод конвертации обьекта из базы данных для ответа на фронт.
     *
     * @param boat - Принимает обькт судна из базы данных и конвертирует для отправки на фронт
     * @return - возвращает обьект ответа.
     */

    private ResponseBoat convertBoatToResponseBoat(AbstractBoat boat) {
        ResponseBoat response = new ResponseBoat();
        response.setId(boat.getId());
        response.setName(boat.getName());
        response.setTrip(boat.getTrip());
        response.setPlaces(boat.getPlaces());
        response.setTripType(boat.getTripType());
        return response;
    }

    /**
     * Метод конвертации принятого обьекта в обьект для сохранения.
     *
     * @param boat - Принимает обькт принятый с фронта и конвертирует в обьект для сохранения в базе.
     * @return - возвращает обьект сохраняемый в базу
     */

    private AbstractBoat convertResponseBoatToBoat(ResponseBoat boat) {
        AbstractBoat abstractBoat = new Boat();
        abstractBoat.setId(boat.getId());
        abstractBoat.setName(boat.getName());
        abstractBoat.setTrip(boat.getTrip());
        abstractBoat.setTripType(boat.getTripType());
        abstractBoat.setPlaces(boat.getPlaces());
        return abstractBoat;
    }


    //endregion
}
