class CineScreen {

    private int seatsTotal;
    private int seatsAvailable;

    // Constructor
    CineScreen(int seatsTotal) {

        if (seatsTotal <= 0) {
            throw new IllegalArgumentException("Invalid seat count");
        }

        this.seatsTotal = seatsTotal;
        this.seatsAvailable = seatsTotal;
    }

    // Book one seat
    void bookSeat() {

        if (seatsAvailable > 0) {
            seatsAvailable--;
        }
    }

    // Cancel one booking
    void cancelBooking() {

        if (seatsAvailable < seatsTotal) {
            seatsAvailable++;
        }
    }

    // Getter
    int getSeatsAvailable() {
        return seatsAvailable;
    }
}

public class Main {

    public static void main(String[] args) {

        CineScreen c = new CineScreen(2);

        c.bookSeat();
        c.bookSeat();
        c.bookSeat();

        System.out.println(c.getSeatsAvailable());

        c.cancelBooking();
        c.cancelBooking();
        c.cancelBooking();

        System.out.println(c.getSeatsAvailable());
    }
}