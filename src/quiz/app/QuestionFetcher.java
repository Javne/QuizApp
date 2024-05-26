package quiz.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.text.StringEscapeUtils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class QuestionFetcher {

    private static final String BASE_API_URL = "https://opentdb.com/api.php?amount=10&type=multiple";

    public static List<Map<String, Object>> fetchQuestions(int category) throws Exception {
        String apiUrlWithCategory = BASE_API_URL + "&category=" + category;
        URL url = new URL(apiUrlWithCategory);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(new InputStreamReader(conn.getInputStream())).getAsJsonObject();

        Gson gson = new Gson();
        List<Map<String, Object>> questions = gson.fromJson(
                jsonObject.get("results"), new TypeToken<List<Map<String, Object>>>(){}.getType());

        conn.disconnect();

        return questions;
    }
}
