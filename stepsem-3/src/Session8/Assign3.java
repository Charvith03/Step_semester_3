import java.util.ArrayList;
import java.util.List;

abstract class Seat {
    private String seatNumber;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public abstract double getPrice();
}

class RegularSeat extends Seat {

    public RegularSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 150;
    }
}

class PremiumSeat extends Seat {

    public PremiumSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 250;
    }
}

class ReclinerSeat extends Seat {

    public ReclinerSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double getPrice() {
        return 400;
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

class Show {
    private String showTime;
    private List<Seat> bookedSeats;

    public Show(String showTime) {
        this.showTime = showTime;
        bookedSeats = new ArrayList<>();
    }

    public boolean isAvailable(Seat seat) {
        return !bookedSeats.contains(seat);
    }

    public boolean bookSeat(Seat seat) {
        if (!isAvailable(seat)) {
            return false;
        }

        bookedSeats.add(seat);
        return true;
    }

    public void releaseSeat(Seat seat) {
        bookedSeats.remove(seat);
    }

    public String getShowTime() {
        return showTime;
    }
}

class Booking {
    private Customer customer;
    private Show show;
    private List<Seat> seats;
    private boolean cancelled;

    public Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = seats;
        this.cancelled = false;
    }

    public double calculateTotal() {
        double total = 0;

        for (Seat seat : seats) {
            total += seat.getPrice();
        }

        return total;
    }

    public void cancel(boolean showStarted) {

        if (cancelled) {
            return;
        }

        if (showStarted) {
            System.out.println(
                    "Cannot cancel: show has already started."
            );
            return;
        }

        for (Seat seat : seats) {
            show.releaseSeat(seat);
        }

        cancelled = true;

        System.out.println(
                customer.getName() +
                        "'s booking cancelled. Seats " +
                        getSeatNames() +
                        " released."
        );
    }

    private String getSeatNames() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < seats.size(); i++) {

            result.append(seats.get(i).getSeatNumber());

            if (i < seats.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
}

class TicketSystem {

    public Booking createBooking(
            Customer customer,
            Show show,
            List<Seat> seats) {

        if (seats.size() > 6) {
            System.out.println(
                    "Cannot book more than 6 seats."
            );
            return null;
        }

        for (Seat seat : seats) {
            if (!show.isAvailable(seat)) {
                System.out.println(
                        "Seat " +
                                seat.getSeatNumber() +
                                " is already booked for this show."
                );
                return null;
            }
        }

        for (Seat seat : seats) {
            show.bookSeat(seat);
        }

        Booking booking =
                new Booking(customer, show, seats);

        System.out.println(
                "Booking confirmed for " +
                        customer.getName() +
                        ": " +
                        getSeatNames(seats)
        );

        System.out.printf(
                "Total: ₹%.2f%n",
                booking.calculateTotal()
        );

        return booking;
    }

    private String getSeatNames(List<Seat> seats) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < seats.size(); i++) {

            result.append(
                    seats.get(i).getSeatNumber()
            );

            if (i < seats.size() - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
}

public class Main {

    public static void main(String[] args) {

        TicketSystem system = new TicketSystem();

        Show show = new Show("7 PM");

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Seat a1 = new RegularSeat("A1");
        Seat a2 = new RegularSeat("A2");
        Seat f5 = new PremiumSeat("F5");
        Seat r1 = new ReclinerSeat("R1");

        // Asha books A1, A2 and F5
        List<Seat> ashaSeats = new ArrayList<>();
        ashaSeats.add(a1);
        ashaSeats.add(a2);
        ashaSeats.add(f5);

        Booking ashaBooking =
                system.createBooking(
                        asha,
                        show,
                        ashaSeats
                );

        // Ravi tries A2
        List<Seat> raviSeats1 = new ArrayList<>();
        raviSeats1.add(a2);

        system.createBooking(
                ravi,
                show,
                raviSeats1
        );

        // Ravi books R1
        List<Seat> raviSeats2 = new ArrayList<>();
        raviSeats2.add(r1);

        Booking raviBooking =
                system.createBooking(
                        ravi,
                        show,
                        raviSeats2
                );

        // Asha cancels before show starts
        ashaBooking.cancel(false);

        // Neha books released A2
        List<Seat> nehaSeats = new ArrayList<>();
        nehaSeats.add(a2);

        system.createBooking(
                neha,
                show,
                nehaSeats
        );
    }
}