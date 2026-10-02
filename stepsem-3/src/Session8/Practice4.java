import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract class Room {

    protected String roomNumber;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public abstract double calculatePrice(
            LocalDate startDate,
            LocalDate endDate
    );
}

class StandardRoom extends Room {

    public StandardRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                java.time.temporal.ChronoUnit.DAYS.between(
                        startDate,
                        endDate
                );

        return days * 100;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                java.time.temporal.ChronoUnit.DAYS.between(
                        startDate,
                        endDate
                );

        return days * 150;
    }
}

class Suite extends Room {

    public Suite(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(
            LocalDate startDate,
            LocalDate endDate) {

        long days =
                java.time.temporal.ChronoUnit.DAYS.between(
                        startDate,
                        endDate
                );

        return days * 250;
    }
}

class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Reservation {

    private Customer customer;
    private Room room;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean active;

    public Reservation(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate) {

        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
    }

    public boolean overlaps(
            LocalDate start,
            LocalDate end) {

        if (!active) {
            return false;
        }

        return start.isBefore(endDate)
                && end.isAfter(startDate);
    }

    public void cancel() {

        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public double getPrice() {

        return room.calculatePrice(
                startDate,
                endDate
        );
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

class HotelBookingSystem {

    private List<Reservation> reservations =
            new ArrayList<>();

    public boolean isAvailable(
            Room room,
            LocalDate start,
            LocalDate end) {

        for (Reservation reservation :
                reservations) {

            if (reservation.getRoom()
                    .getRoomNumber()
                    .equals(room.getRoomNumber())) {

                if (reservation.overlaps(
                        start,
                        end)) {

                    return false;
                }
            }
        }

        return true;
    }

    public Reservation reserve(
            Customer customer,
            Room room,
            LocalDate start,
            LocalDate end) {

        if (!isAvailable(room, start, end)) {

            System.out.println(
                    room.getRoomNumber()
                            + " is not available from "
                            + start
                            + " to "
                            + end
            );

            return null;
        }

        Reservation reservation =
                new Reservation(
                        customer,
                        room,
                        start,
                        end
                );

        reservations.add(reservation);

        System.out.println(
                "Reservation confirmed for "
                        + customer.getName()
                        + ", "
                        + room.getRoomNumber()
                        + " ("
                        + start
                        + "-"
                        + end
                        + ")"
        );

        System.out.println(
                "Price: $"
                        + reservation.getPrice()
        );

        return reservation;
    }

    public void cancelReservation(
            Reservation reservation) {

        if (reservation != null
                && reservation.isActive()) {

            reservation.cancel();

            System.out.println(
                    "Reservation for "
                            + reservation.getCustomer().getName()
                            + ", "
                            + reservation.getRoom().getRoomNumber()
                            + " cancelled successfully."
            );
        }
    }
}

public class Main {

    public static void main(String[] args) {

        HotelBookingSystem system =
                new HotelBookingSystem();

        Customer customerA =
                new Customer("Customer A");

        Customer customerB =
                new Customer("Customer B");

        Customer customerC =
                new Customer("Customer C");

        Room standard101 =
                new StandardRoom("Standard Room 101");

        Room deluxe201 =
                new DeluxeRoom("Deluxe Room 201");

        LocalDate jan1 =
                LocalDate.of(2026, 1, 1);

        LocalDate jan5 =
                LocalDate.of(2026, 1, 5);

        LocalDate jan3 =
                LocalDate.of(2026, 1, 3);

        LocalDate jan7 =
                LocalDate.of(2026, 1, 7);

        // Check availability
        System.out.println(
                "Standard Room 101 is available from "
                        + jan1
                        + " to "
                        + jan5
        );

        // Customer A reserves room
        Reservation reservation =
                system.reserve(
                        customerA,
                        standard101,
                        jan1,
                        jan5
                );

        // Customer B tries overlapping reservation
        system.reserve(
                customerB,
                standard101,
                jan3,
                jan7
        );

        // Customer A cancels
        system.cancelReservation(reservation);

        // Customer C reserves Deluxe Room
        system.reserve(
                customerC,
                deluxe201,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 12)
        );
    }
}