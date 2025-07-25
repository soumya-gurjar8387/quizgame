import java.util.List;
import java.util.Scanner;

public class QuizGame {
    public static void main(String[] args) {
        QuizService quizService = new QuizService();
        List<Question> questions = quizService.getQuestions();
        Scanner input = new Scanner(System.in);
        int score = 0;

        if (questions.isEmpty()) {
            System.out.println("No questions found. Try again later.");
            return;
        }

        int count = 1;
        for (Question q : questions) {
            System.out.println("\nQ" + count + ": " + q.getQuestionText());
            List<String> options = q.getOptions();

            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Your answer (1-" + options.size() + "): ");
            int answer = input.nextInt();

            if (answer >= 1 && answer <= options.size()) {
                if (options.get(answer - 1).equals(q.getCorrectAnswer())) {
                    System.out.println("✅ Correct!");
                    score++;
                } else {
                    System.out.println("❌ Wrong. Correct answer: " + q.getCorrectAnswer());
                }
            } else {
                System.out.println("Invalid input. Skipping...");
            }
            count++;
        }

        System.out.println("\n🎉 Quiz Complete!");
        System.out.println("Your final score: " + score + "/" + questions.size());
        input.close();
    }
}
