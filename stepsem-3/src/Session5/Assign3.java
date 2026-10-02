class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    // Constructor
    BookInventory(int copiesTotal) {

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    // Check out one book
    void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    // Check in one book
    void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    // Getter
    int getCopiesAvailable() {
        return copiesAvailable;
    }
}

public class Main {

    public static void main(String[] args) {

        BookInventory b = new BookInventory(3);

        // 3 valid checkouts
        b.checkOut();
        b.checkOut();
        b.checkOut();

        // 4th checkout is rejected
        b.checkOut();

        System.out.println(
                b.getCopiesAvailable()
        );

        // 3 valid check-ins
        b.checkIn();
        b.checkIn();
        b.checkIn();

        // 4th check-in is rejected
        b.checkIn();

        System.out.println(
                b.getCopiesAvailable()
        );
    }
}