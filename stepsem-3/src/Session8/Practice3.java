import java.util.HashMap;
import java.util.Map;

class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Question {

    protected int questionNumber;
    protected String questionText;
    protected int points;

    public Question(
            int questionNumber,
            String questionText,
            int points) {

        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.points = points;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {

    private String correctOption;

    public MultipleChoiceQuestion(
            int questionNumber,
            String questionText,
            int points,
            String correctOption) {

        super(
                questionNumber,
                questionText,
                points
        );

        this.correctOption = correctOption;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctOption.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    public TrueFalseQuestion(
            int questionNumber,
            String questionText,
            int points,
            boolean correctAnswer) {

        super(
                questionNumber,
                questionText,
                points
        );

        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {

        return Boolean.parseBoolean(answer)
                == correctAnswer;
    }
}

class ShortAnswerQuestion extends Question {

    private String correctAnswer;

    public ShortAnswerQuestion(
            int questionNumber,
            String questionText,
            int points,
            String correctAnswer) {

        super(
                questionNumber,
                questionText,
                points
        );

        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {

        return correctAnswer.equalsIgnoreCase(
                answer
        );
    }
}

class Examination {

    private String examName;

    private Map<Integer, Question> questions =
            new HashMap<>();

    public Examination(String examName) {
        this.examName = examName;
    }

    public void addQuestion(Question question) {

        questions.put(
                question.getQuestionNumber(),
                question
        );
    }

    public String getExamName() {
        return examName;
    }

    public Map<Integer, Question> getQuestions() {
        return questions;
    }
}

class Attempt {

    private Student student;
    private Examination examination;

    private Map<Integer, String> answers =
            new HashMap<>();

    private boolean submitted = false;

    public Attempt(
            Student student,
            Examination examination) {

        this.student = student;
        this.examination = examination;
    }

    public void recordAnswer(
            int questionNumber,
            String answer) {

        if (submitted) {

            System.out.println(
                    "Cannot change answers for a submitted examination."
            );

            return;
        }

        answers.put(questionNumber, answer);

        System.out.println(
                "Answer recorded for Question "
                        + questionNumber
        );
    }

    public void submit() {

        if (submitted) {
            return;
        }

        submitted = true;

        System.out.println(
                examination.getExamName()
                        + " submitted by "
                        + student.getName()
        );

        calculateResult();
    }

    private void calculateResult() {

        int totalScore = 0;
        int totalPoints = 0;

        for (Question question :
                examination.getQuestions().values()) {

            totalPoints += question.getPoints();

            String answer =
                    answers.get(
                            question.getQuestionNumber()
                    );

            boolean correct =
                    answer != null
                            && question.evaluate(answer);

            if (correct) {

                totalScore += question.getPoints();

                System.out.println(
                        "Question "
                                + question.getQuestionNumber()
                                + ": Correct ("
                                + question.getPoints()
                                + " points)"
                );

            } else {

                System.out.println(
                        "Question "
                                + question.getQuestionNumber()
                                + ": Incorrect (0 points)"
                );
            }
        }

        System.out.println(
                "Total score: "
                        + totalScore
                        + "/"
                        + totalPoints
        );
    }
}

public class Main {

    public static void main(String[] args) {

        Student student =
                new Student("Student 1");

        Examination exam =
                new Examination("Exam A");

        Question q1 =
                new MultipleChoiceQuestion(
                        1,
                        "Which is a programming language?",
                        5,
                        "C"
                );

        Question q2 =
                new TrueFalseQuestion(
                        2,
                        "Java is a programming language.",
                        5,
                        false
                );

        exam.addQuestion(q1);
        exam.addQuestion(q2);

        System.out.println(
                "Exam A started by Student 1."
        );

        Attempt attempt =
                new Attempt(student, exam);

        // Answer Question 1
        attempt.recordAnswer(1, "C");

        // Answer Question 2
        attempt.recordAnswer(2, "True");

        // Submit
        attempt.submit();

        // Try changing answer after submission
        attempt.recordAnswer(1, "A");
    }
}