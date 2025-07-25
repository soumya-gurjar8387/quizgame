import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class QuizService {

    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();

        try {
            URL url = new URL("https://opentdb.com/api.php?amount=5&type=multiple");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            Scanner scanner = new Scanner(con.getInputStream());
            StringBuilder responseText = new StringBuilder();
            while (scanner.hasNext()) {
                responseText.append(scanner.nextLine());
            }
            scanner.close();

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(responseText.toString());
            JSONArray results = (JSONArray) data.get("results");

            for (Object resultObj : results) {
                JSONObject result = (JSONObject) resultObj;

                String question = decodeHTML((String) result.get("question"));
                String correct = decodeHTML((String) result.get("correct_answer"));

                JSONArray incorrect = (JSONArray) result.get("incorrect_answers");
                List<String> options = new ArrayList<>();
                for (Object wrong : incorrect) {
                    options.add(decodeHTML((String) wrong));
                }
                options.add(correct);
                Collections.shuffle(options);

                questionList.add(new Question(question, options, correct));
            }

        } catch (Exception e) {
            System.out.println("Error fetching questions.");
            e.printStackTrace();
        }

        return questionList;
    }

    private String decodeHTML(String text) {
        return text.replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&")
                .replace("&eacute;", "é");
    }
}
