import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class Assignment {
    private String title;
    private int maxMarks;
    private LocalDate dueDate;

    public Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public abstract double applyLatePenalty(double marks, long lateDays);
}

class CodingAssignment extends Assignment {

    public CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double applyLatePenalty(double marks, long lateDays) {
        double penalty = lateDays * 0.10;
        return marks * (1 - penalty);
    }
}

class WrittenAssignment extends Assignment {

    public WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    public double applyLatePenalty(double marks, long lateDays) {
        double penalty = lateDays * 0.20;
        return marks * (1 - penalty);
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum SubmissionStatus {
    SUBMITTED,
    GRADED
}

class Submission {
    private Student student;
    private Assignment assignment;
    private LocalDate submissionDate;
    private SubmissionStatus status;
    private double finalMarks;

    public Submission(Student student, Assignment assignment,
                      LocalDate submissionDate) {

        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public void grade(double awardedMarks) {

        if (status == SubmissionStatus.GRADED) {
            System.out.println(
                    "Cannot grade again: '" +
                            assignment.getTitle() +
                            "' has already been graded."
            );
            return;
        }

        long lateDays = 0;

        if (submissionDate.isAfter(assignment.getDueDate())) {
            lateDays = ChronoUnit.DAYS.between(
                    assignment.getDueDate(),
                    submissionDate
            );
        }

        finalMarks = assignment.applyLatePenalty(
                awardedMarks,
                lateDays
        );

        status = SubmissionStatus.GRADED;

        if (lateDays == 0) {
            System.out.println(
                    student.getName() +
                            " graded: " +
                            String.format("%.0f", finalMarks) +
                            "/" +
                            assignment.getMaxMarks() +
                            ". Status: Graded."
            );
        } else {
            double penalty = finalMarks < awardedMarks
                    ? ((awardedMarks - finalMarks) / awardedMarks) * 100
                    : 0;

            System.out.println(
                    student.getName() +
                            " graded: " +
                            String.format("%.0f", finalMarks) +
                            "/" +
                            assignment.getMaxMarks() +
                            " after " +
                            String.format("%.0f", penalty) +
                            "% late penalty. Status: Graded."
            );
        }
    }

    public void resubmit(LocalDate newDate) {

        if (status == SubmissionStatus.GRADED) {
            System.out.println(
                    "Cannot resubmit: '" +
                            assignment.getTitle() +
                            "' has already been graded."
            );
            return;
        }

        submissionDate = newDate;

        System.out.println(
                student.getName() +
                        " resubmitted '" +
                        assignment.getTitle() +
                        "'."
        );
    }
}

public class Main {

    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Assignment coding = new CodingAssignment(
                "Linked List Lab",
                50,
                LocalDate.of(2026, 3, 10)
        );

        Assignment written = new WrittenAssignment(
                "Design Essay",
                50,
                LocalDate.of(2026, 3, 12)
        );

        // Asha submits on time
        Submission ashaSubmission = new Submission(
                asha,
                coding,
                LocalDate.of(2026, 3, 10)
        );

        System.out.println(
                "Asha's submission for 'Linked List Lab' received " +
                        "(on time). Status: Submitted."
        );

        // Ravi submits 2 days late
        Submission raviSubmission = new Submission(
                ravi,
                written,
                LocalDate.of(2026, 3, 14)
        );

        System.out.println(
                "Ravi's submission for 'Design Essay' received " +
                        "(2 days late). Status: Submitted."
        );

        // Grading
        ashaSubmission.grade(45);
        raviSubmission.grade(40);

        // Resubmission after grading
        ashaSubmission.resubmit(
                LocalDate.of(2026, 3, 11)
        );
    }
}