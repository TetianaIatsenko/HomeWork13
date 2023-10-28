package task1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public static List<User> sendGet(URI url) throws IOException, InterruptedException {
        Type itemsListType = new TypeToken<List<User>>() {}.getType();
        List<User> list = GSON.fromJson(getGetResponse(url).body(), itemsListType);
        return list;
    }

    public static User sentGetByUserId(String url, int userId) throws IOException, InterruptedException, URISyntaxException {
        return GSON.fromJson(getGetResponse(new URI(String.format("%s/%s", url, userId))).body(), User.class);
    }

    public static List<User> sentGetByUserName(String url, String userName) throws IOException, InterruptedException, URISyntaxException {
        Type itemsListType = new TypeToken<List<User>>() {}.getType();
        List<User> list = GSON.fromJson(getGetResponse(new URI(String.format("%s?username=%s", url, userName))).body(), itemsListType);
        return list;
    }

    private static HttpResponse<String> getGetResponse(URI url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public static User sentPut(String url, User user) throws IOException, InterruptedException, URISyntaxException {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("%s/%s", url, user.getId())))
                .header("Content-type", "application/json;charset=UTF-8")
                .method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
                //.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        User userResponse = GSON.fromJson(response.body(), User.class);
        return userResponse;
    }

    public static User sendPost(URI url, User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        User userResponse = GSON.fromJson(response.body(), User.class);
        return userResponse;
    }

    public static int sendDelete(String url, User user) throws IOException, InterruptedException, URISyntaxException {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("%s/%s", url, user.getId())))
                .header("Content-type", "application/json;charset=UTF-8")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
