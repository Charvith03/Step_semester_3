import java.util.ArrayList;
import java.util.List;

interface NotificationChannel {
    void send(Student student, Notice notice);
}

class EmailChannel implements NotificationChannel {

    @Override
    public void send(Student student, Notice notice) {

        System.out.println(
                "[Email → " +
                        student.getName() +
                        "] " +
                        notice.getTitle()
        );
    }
}

class SmsChannel implements NotificationChannel {

    @Override
    public void send(Student student, Notice notice) {

        System.out.println(
                "[SMS → " +
                        student.getName() +
                        "] " +
                        notice.getTitle()
        );
    }
}

class AppChannel implements NotificationChannel {

    @Override
    public void send(Student student, Notice notice) {

        System.out.println(
                "[App → " +
                        student.getName() +
                        "] " +
                        notice.getTitle()
        );
    }
}

class Student {

    private String name;
    private String department;
    private List<NotificationChannel> channels;

    public Student(
            String name,
            String department) {

        this.name = name;
        this.department = department;
        channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void addChannel(
            NotificationChannel channel) {

        channels.add(channel);
    }

    public List<NotificationChannel> getChannels() {
        return channels;
    }
}

class Notice {

    private String title;
    private List<String> departments;

    public Notice(
            String title,
            List<String> departments) {

        this.title = title;
        this.departments = departments;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public boolean isValid() {

        return title != null &&
                !title.trim().isEmpty() &&
                departments != null &&
                !departments.isEmpty();
    }
}

class NoticeBoard {

    private List<Student> students;

    public NoticeBoard() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void postNotice(Notice notice) {

        if (notice.getTitle() == null ||
                notice.getTitle().trim().isEmpty()) {

            System.out.println(
                    "Cannot post notice: Title is required."
            );

            return;
        }

        if (notice.getDepartments() == null ||
                notice.getDepartments().isEmpty()) {

            System.out.println(
                    "Cannot post notice: " +
                            "At least one target department is required."
            );

            return;
        }

        System.out.println(
                "Notice '" +
                        notice.getTitle() +
                        "' posted to " +
                        String.join(
                                ", ",
                                notice.getDepartments()
                        ) +
                        "."
        );

        for (Student student : students) {

            if (notice.getDepartments()
                    .contains(student.getDepartment())) {

                for (NotificationChannel channel :
                        student.getChannels()) {

                    channel.send(student, notice);
                }
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        NoticeBoard board = new NoticeBoard();

        Student asha =
                new Student("Asha", "CSE");

        Student ravi =
                new Student("Ravi", "ECE");

        // Asha prefers Email and App
        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        // Ravi prefers SMS
        ravi.addChannel(new SmsChannel());

        board.addStudent(asha);
        board.addStudent(ravi);

        // Notice 1
        List<String> cse = new ArrayList<>();
        cse.add("CSE");

        Notice notice1 =
                new Notice(
                        "Lab Closed Tomorrow",
                        cse
                );

        board.postNotice(notice1);

        // Notice 2
        List<String> cseEce = new ArrayList<>();
        cseEce.add("CSE");
        cseEce.add("ECE");

        Notice notice2 =
                new Notice(
                        "Fee Deadline Extended",
                        cseEce
                );

        board.postNotice(notice2);

        // Invalid notice
        List<String> noDepartment =
                new ArrayList<>();

        Notice notice3 =
                new Notice(
                        "Sports Day",
                        noDepartment
                );

        board.postNotice(notice3);
    }
}