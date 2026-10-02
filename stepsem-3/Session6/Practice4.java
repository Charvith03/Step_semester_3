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


    public String displayInfo() {

        return "General | Books: "
                + booksBorrowed;
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


    public String getCourse() {
        return course;
    }


    @Override
    public String displayInfo() {

        return "Student | Course: "
                + course
                + " | Books: "
                + booksBorrowed;
    }
}


public class Main {

    static String batchPrint(
            LibraryMember[] members) {

        StringBuilder report =
                new StringBuilder();

        for (LibraryMember member : members) {

            // Polymorphism
            report.append(
                    member.displayInfo()
            );

            // Safe downcast
            if (member instanceof StudentMember) {

                StudentMember student =
                        (StudentMember) member;

                report.append(
                        " [Course via downcast: "
                                + student.getCourse()
                                + "]"
                );
            }

            report.append(" | ");
        }

        return report.toString();
    }


    public static void main(String[] args) {

        LibraryMember general =
                new LibraryMember("LB5", 3);

        StudentMember student =
                new StudentMember(
                        "STU6",
                        3,
                        "ECE"
                );

        LibraryMember[] members = {
                general,
                student
        };

        System.out.println(
                batchPrint(members)
        );


        // Demonstration of unsafe downcast
        LibraryMember plain =
                new LibraryMember("LB6", 3);

        // This would compile but fail at runtime:
        //
        // StudentMember bad =
        //     (StudentMember) plain;
    }
}