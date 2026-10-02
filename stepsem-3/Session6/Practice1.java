class LibraryMember {

    private String memberId;
    private int borrowLimit;
    private int booksBorrowed;

    public LibraryMember(String memberId, int borrowLimit) {

        if (memberId == null ||
                memberId.trim().isEmpty() ||
                memberId.length() < 4) {

            throw new IllegalArgumentException("Invalid member ID");
        }

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;
    }

    public void borrowBook() {

        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }
}


class StudentMember extends LibraryMember {

    private String course;

    public StudentMember(String memberId,
                         int borrowLimit,
                         String course) {

        super(memberId, borrowLimit);
        this.course = course;
    }
}


public class Main {

    static String enrollBatch(String[] memberIds,
                              int borrowLimit) {

        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {

            try {
                LibraryMember member =
                        new LibraryMember(id, borrowLimit);

                enrolled++;

            } catch (IllegalArgumentException e) {

                rejected++;
            }
        }

        return "Enrolled: " + enrolled
                + " | Rejected: " + rejected;
    }


    public static void main(String[] args) {

        try {
            LibraryMember m =
                    new LibraryMember("LB1", 3);

        } catch (IllegalArgumentException e) {

            System.out.println("Construction rejected");
        }


        StudentMember s =
                new StudentMember("STU10", 3, "CSE");

        s.borrowBook();
        s.borrowBook();

        System.out.println(
                s.getBooksBorrowed()
        );


        String[] ids = {
                "STU1",
                "LB1",
                "STU2",
                " ",
                "STU3"
        };

        System.out.println(
                enrollBatch(ids, 3)
        );
    }
}