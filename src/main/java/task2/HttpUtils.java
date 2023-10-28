package task2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import task1.User;

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

    private static HttpResponse<String> getGetResponse(URI url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public static List<Post> sendGetPostByUserId(String url, int userId) throws IOException, InterruptedException, URISyntaxException {
        Type itemsListType = new TypeToken<List<Post>>() {}.getType();
        List<Post> list = GSON.fromJson(getGetResponse(new URI(String.format("%s/%s/posts", url, userId))).body(), itemsListType);
        return list;
    }

    public static String sendGetCommentByPostId(String url, int postId) throws IOException, InterruptedException, URISyntaxException {
        return getGetResponse(new URI(String.format("%s/%s/comments", url, postId))).body();
    }

}
