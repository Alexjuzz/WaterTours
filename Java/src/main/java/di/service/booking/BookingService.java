package di.service.booking;

import di.customexceptions.boat.BoatNotFoundException;
import di.customexceptions.booking.BookingNotFoundException;
import di.customexceptions.booking.BookingReservationIsEmpty;
import di.customexceptions.seat.SeatAlreadyBookedException;
import di.customexceptions.seat.SeatNotFoundException;
import di.customexceptions.telephone.InvalidPhoneNumberException;
import di.customexceptions.telephone.TelephoneNotFoundException;
import di.enums.BookingTime;
import di.model.dto.booking.ResponseBooking;
import di.model.entity.booking.Booking;
import di.model.entity.seats.Seat;
import di.model.entity.telephone.Telephone;
import di.repository.booking.BookingRepository;
import di.repository.seat.SeatRepository;

import di.repository.telephone.TelephoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//TODO :  1) Подумать как правильно обработать все возможные исключения.
//        2) Сделать Class user и связать его с бронированием.
//        3) Создать методы обновления всех данных для каждого нового дня. Создать метод бы закрывал любое
//              бронирование если у судна trip = private.
//        4) Подумать о том стоит ли  сделать обратную регистрацию -> Место имеет уже все готовые временные интервалы
//                      которые сразу проинициализированы, а при добавлении убирать из списка выбраное время.
//        5) Доделать логирование.


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final TelephoneRepository telephoneRepository;


    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository, TelephoneRepository telephoneRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.telephoneRepository = telephoneRepository;
    }


    /**
     * Метод резервации места по выбранному времени.
     *
     * @param seatId      - айди места в базе данных.
     * @param bookingTime - Enum - в котором записаны итенрвалы для записи каждые 3 часа.
     * @return - возращает обьект ответа для фронта.
     */
    @Transactional
    public ResponseBooking setBookingToPlace(Long seatId, BookingTime bookingTime, String number) {

        Telephone telephone = telephoneRepository.getTelephoneByNumber(number).
                orElseThrow(() -> new TelephoneNotFoundException("Telephone not found"));


        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat with ID: " + seatId + " not found"));

        if (!checkReservedPlace(seat, bookingTime)) {
            throw new SeatAlreadyBookedException("The seat is already booked for the selected time");
        }

        Booking booking = new Booking();
        booking.setDate(LocalDate.now());
        booking.setSeat(seat);
        booking.setBookingTime(bookingTime);
        booking.setTelephone(telephone);
        return convetBookingToResponseBooking(bookingRepository.save(booking));
    }

    @Transactional
    public boolean cancelReservation(Long seatId, BookingTime bookingTime, String phoneNumber) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));


        //поиск забранированного времени в списке всех броней.
        Booking bookingToRemove = seat.getBookings().stream()
                .filter(booking -> booking.getBookingTime().equals(bookingTime))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        Telephone tel = telephoneRepository.getTelephoneByNumber(phoneNumber).orElseThrow(() -> new TelephoneNotFoundException("Phone with number:" + phoneNumber + " is not found"));
        if (bookingToRemove.getTelephone().getId().equals(tel.getId())) {
            bookingRepository.delete(bookingToRemove); // Удаляем бронирование
        } else {
            throw new InvalidPhoneNumberException("Invalid phone number, reservation was recorded on another number");
        }


        return true;
    }

    /**
     * Метод для изменения времени бронирования. Метод принимает ID нужного места и меняет в нем
     * старое старое время которое было указано на новое.
     *
     * @param seatId  - ID места в базе данных
     * @param oldTime - старое время
     * @param newTime - новое время
     * @return - Возвращает обьект для ответа на front
     */
    @Transactional
    //TODO : Посмотреть как правильно обработать oldTime если его нету.
    public ResponseBooking changeReservedBookingTime(Long seatId, BookingTime oldTime, BookingTime newTime) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new SeatNotFoundException("Seat not found With ID : " + seatId));
        Booking booking = seat.getBookings()
                .stream()
                .filter(b -> b.getBookingTime().equals(oldTime)).
                findFirst().
                orElseThrow(() -> new BoatNotFoundException("Booking not found for the specified time"));


        boolean isTimeAvailable = seat.getBookings().stream() // Обработка не произошла ли попытка установки на уже занятое время.
                .noneMatch(b -> b.getBookingTime().equals(newTime));

        if (!isTimeAvailable) {
            throw new IllegalStateException("The new time is already booked");
        }

        booking.setBookingTime(newTime);
        return convetBookingToResponseBooking(bookingRepository.save(booking));
    }

    public List<ResponseBooking> getBookingReservationBySeatId(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new SeatNotFoundException("Seat not found with ID: " + id));
        List<ResponseBooking> responseBookings = seat.getBookings().stream().map(this::convetBookingToResponseBooking).toList();
        if (responseBookings.isEmpty()) {
            throw new BookingReservationIsEmpty("Booking reservation is empty on seat id: " + id);
        }
        return responseBookings;
    }

    /**
     * Метод для очистки всех бронирований на текущем месте
     *
     * @param id - ID места в базе данных
     * @return true - если операция была успешна, если место было не найдено будеь выбрашено исключение.
     */
    @Transactional
    public boolean clearAllReservedBookingTimeOnSeat(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new SeatNotFoundException("Seat not found With ID : " + id));
        bookingRepository.deleteAll(seat.getBookings());
        seat.getBookings().clear();
        seatRepository.save(seat);
        return true;
    }


    /**
     * Метод проверки на наличе резервации, что оно свободно для регистрации
     *
     * @param seat        - номер места
     * @param bookingTime - время корое необходимо проверить
     * @return - true - если свободно. false - если занято.
     */
    private boolean checkReservedPlace(Seat seat, BookingTime bookingTime) {
        return seat.getBookings().stream()
                .noneMatch(b -> b.getBookingTime().equals(bookingTime));
    }


    //region методы конвертации обьектов с базы данных в ответы для фронта и обратно.
    private ResponseBooking convetBookingToResponseBooking(Booking booking) {
        ResponseBooking responseBooking = new ResponseBooking();
        responseBooking.setBookingTime(booking.getBookingTime());
        responseBooking.setDate(booking.getDate());
        responseBooking.setId(booking.getId());
        responseBooking.setSeat(booking.getSeat());
        return responseBooking;
    }

    private Booking convertResponseBookingToBooking(ResponseBooking responseBooking) {
        Booking booking = new Booking();
        booking.setBookingTime(responseBooking.getBookingTime());
        booking.setId(responseBooking.getId());
        booking.setSeat(responseBooking.getSeat());
        booking.setDate(responseBooking.getDate());
        return booking;
    }
    //endregion
}
