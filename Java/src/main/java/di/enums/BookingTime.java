package di.enums;

import lombok.Getter;

@Getter
public enum BookingTime {
    T09_00("09:00"),
    T09_30("09:30"),
    T10_00("10:00"),
    T10_30("10:30"),
    T11_00("11:00"),
    T11_30("11:30"),
    T12_00("12:00"),
    T12_30("12:30"),
    T13_00("13:00"),
    T13_30("13:30"),
    T14_00("14:00"),
    T14_30("14:30"),
    T15_00("15:00"),
    T15_30("15:30"),
    T16_00("16:00"),
    T16_30("16:30"),
    T17_00("17:00"),
    T17_30("17:30"),
    T18_00("17:00"),
    T18_30("18:30"),
    T19_00("17:00"),
    T19_30("19:30"),
    T20_00("20:00");

    private final String time;

    BookingTime(String time) {
        this.time = time;
    }

    //  метод для получения экземпляра enum по строковому представлению времени
    public static BookingTime fromString(String text) {
        for (BookingTime b : BookingTime.values()) {
            if (b.time.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}


