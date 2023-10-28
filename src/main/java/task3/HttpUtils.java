package task3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import task2.Post;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtils {
    private static HttpClient CLIENT = HttpClient.newHttpClient();
    private static Gson GSON = new Gson();

    public static List<Todos> sendGetTodosByUserId(String url, int userId) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("%s/%s/todos", url, userId)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Type itemsListType = new TypeToken<List<Todos>>() {}.getType();
        List<Todos> list = GSON.fromJson(response.body(), itemsListType);
        return list;
    }

}
