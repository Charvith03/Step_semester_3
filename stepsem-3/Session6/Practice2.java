class LibraryMember {

    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public LibraryMember(String memberId, int borrowLimit) {

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

    public void displayInfo() {

        System.out.println(
                "General Member | Books Borrowed: "
                        + booksBorrowed
        );
    }
}


class StudentMember extends LibraryMember {

    protected String course;

    public StudentMember(String memberId,
                         int borrowLimit,
                         String course) {

        super(memberId, borrowLimit);
        this.course = course;
    }

    @Override
    public void displayInfo() {

        System.out.println(
                "Student Member | Course: "
                        + course
                        + " | Books Borrowed: "
                        + booksBorrowed
        );
    }
}


class HonorsStudentMember extends StudentMember {

    private int bonusLimit;

    public HonorsStudentMember(String memberId,
                               int borrowLimit,
                               String course,
                               int bonusLimit) {

        super(memberId, borrowLimit, course);
        this.bonusLimit = bonusLimit;
    }

    @Override
    public void displayInfo() {

        System.out.println(
                "Honors Student Member | Course: "
                        + course
                        + " | Bonus Limit: "
                        + bonusLimit
                        + " | Books Borrowed: "
                        + booksBorrowed
        );
    }
}


class FacultyMember extends LibraryMember {

    private String department;

    public FacultyMember(String memberId,
                         int borrowLimit,
                         String department) {

        super(memberId, borrowLimit);
        this.department = department;
    }

    @Override
    public void displayInfo() {

        System.out.println(
                "Faculty Member | Department: "
                        + department
                        + " | Books Borrowed: "
                        + booksBorrowed
        );
    }
}


public class Main {

    static String classifyGeneration(
            LibraryMember member) {

        if (member instanceof HonorsStudentMember) {

            return "Multilevel descendant (3 generations deep)";

        } else if (member instanceof FacultyMember) {

            return "Hierarchical sibling (independent branch)";

        } else if (member instanceof StudentMember) {

            return "Student branch";

        } else {

            return "Base class";
        }
    }


    static int getTotalBooksBorrowed(
            LibraryMember[] members) {

        int total = 0;

        for (LibraryMember member : members) {

            total += member.getBooksBorrowed();
        }

        return total;
    }


    public static void main(String[] args) {

        LibraryMember general =
                new LibraryMember("STU1", 3);

        StudentMember student =
                new StudentMember("STU2", 3, "CSE");

        HonorsStudentMember honors =
                new HonorsStudentMember(
                        "STU3",
                        3,
                        "ECE",
                        2
                );

        FacultyMember faculty =
                new FacultyMember(
                        "STU4",
                        5,
                        "Physics"
                );


        general.displayInfo();
        student.displayInfo();
        honors.displayInfo();
        faculty.displayInfo();


        System.out.println(
                classifyGeneration(honors)
        );

        System.out.println(
                classifyGeneration(faculty)
        );


        // Student borrows 2
        student.borrowBook();
        student.borrowBook();

        // Honors student borrows 1
        honors.borrowBook();

        // Faculty borrows 3
        faculty.borrowBook();
        faculty.borrowBook();
        faculty.borrowBook();


        LibraryMember[] members = {
                student,
                honors,
                faculty
        };

        System.out.println(
                getTotalBooksBorrowed(members)
        );
    }
}