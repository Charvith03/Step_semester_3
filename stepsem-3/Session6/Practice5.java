class LibraryMember {

    // Shared counter for all members
    private static int membersEnrolled = 0;

    // Final member number
    public final String memberNumber;

    protected int borrowLimit;
    protected int booksBorrowed;

    // Stores the genre of the latest genre-based borrowing
    private String lastGenre;


    public LibraryMember(int borrowLimit) {

        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;

        // Increment counter once for every object
        membersEnrolled++;

        // Build member number
        memberNumber =
                "LIB-" + (100 + membersEnrolled);
    }


    // No-argument borrowBook()
    public void borrowBook() {

        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }


    // Overloaded borrowBook()
    public void borrowBook(String genre) {

        lastGenre = genre;

        // Reuse the existing logic
        borrowBook();
    }


    public int getBooksBorrowed() {
        return booksBorrowed;
    }


    // Renewal code validation
    public static boolean isValidRenewalCode(
            String code) {

        // Must be exactly 4 characters
        if (code == null || code.length() != 4) {
            return false;
        }

        // First character must be R
        if (code.charAt(0) != 'R') {
            return false;
        }

        // Characters 1 and 2 must be digits
        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        // Last character must be uppercase
        if (!Character.isUpperCase(code.charAt(3))) {
            return false;
        }

        return true;
    }


    // Get number of members enrolled
    public static int getMembersEnrolled() {
        return membersEnrolled;
    }
}


class FacultyMember extends LibraryMember {

    private String department;


    public FacultyMember(int borrowLimit,
                         String department) {

        // Faculty has no separate memberId
        // in this problem
        super(borrowLimit);

        this.department = department;
    }
}


public class Main {

    static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (LibraryMember member : members) {

            // Handle null safely
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            // Check actual object type
            if (member instanceof FacultyMember) {

                faculty++;

            } else {

                regular++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + faculty
                + " faculty | "
                + regular
                + " regular";
    }


    public static void main(String[] args) {

        // --------------------------------
        // Member number
        // --------------------------------

        LibraryMember m1 =
                new LibraryMember(3);

        System.out.println(
                m1.memberNumber
        );

        System.out.println(
                LibraryMember.getMembersEnrolled()
        );



        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R12A"
                )
        );

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R1A"
                )
        );

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "X12A"
                )
        );


        // --------------------------------
        // Method overloading
        // --------------------------------

        m1.borrowBook();

        m1.borrowBook("Fiction");

        System.out.println(
                m1.getBooksBorrowed()
        );


        // --------------------------------
        // Nightly audit
        // --------------------------------

        LibraryMember[] members = {

                new FacultyMember(
                        5,
                        "Physics"
                ),

                null,

                new LibraryMember(3)
        };

        System.out.println(
                processNightlyAudit(members)
        );
    }
}