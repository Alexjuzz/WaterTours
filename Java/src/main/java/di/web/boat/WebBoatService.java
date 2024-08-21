//package di.web.boat;
//
//import di.customexceptions.boat.BoatEmptyResultDataException;
//import di.customexceptions.boat.BoatNotFoundException;
//import di.model.dto.boats.ResponseBoat;
//import di.model.entity.boats.AbstractBoat;
//import di.repository.boat.BoatRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class WebBoatService {
//
//    private final BoatRepository repository;
//
//    @Autowired
//    public WebBoatService(BoatRepository repository) {
//        this.repository = repository;
//    }
//
//    public ResponseBoat getBoatById(Long boatId) {
//        return convertBoatToResponseBoat(repository.findById(boatId).orElseThrow(() -> new BoatNotFoundException("Boat not found.")));
//    }
//
//
//
//
//    private ResponseBoat convertBoatToResponseBoat(AbstractBoat boat) {
//        ResponseBoat response = new ResponseBoat();
//        response.setId(boat.getId());
//        response.setName(boat.getName());
//        response.setTrip(boat.getTrip());
//        response.setPlaces(boat.getPlaces());
//        response.setTripType(boat.getTripType());
//        return response;
//    }
//
//    public List<ResponseBoat> getAllBoats() {
//    List<ResponseBoat> responseBoats = repository.findAll().stream().map(this::convertBoatToResponseBoat).toList();
//        if(responseBoats.isEmpty()){
//            throw  new BoatEmptyResultDataException("Корабли не найдены.");
//        }
//        return responseBoats;
//    }
//}
