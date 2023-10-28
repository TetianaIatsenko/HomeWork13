package task2;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class Main {
//    public static String POSTS_URL = "https://jsonplaceholder.typicode.com/users
    private static String URL_USERS = "https://jsonplaceholder.typicode.com/users";//  /1/posts";
    private static String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";//  /10/comments";
    private static String JSON_FILE_PATH = "src\\main\\resources\\";
    private static int USER_ID = 6;

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        List<Post> postList = HttpUtils.sendGetPostByUserId(URL_USERS, USER_ID);
        postList.stream().forEach(n-> System.out.println(n));
        Post post = postList.stream().max(Comparator.comparingInt(Post::getId)).get();
        String commentsJson = HttpUtils.sendGetCommentByPostId(POSTS_URL, post.getId());
        writeJSONFile(commentsJson, String.format("%suser-%s-post-%s-comments.json", JSON_FILE_PATH, USER_ID, post.getId()));
    }

    public static void writeJSONFile(String json, String fileName){

        try (FileWriter file = new FileWriter(fileName)){
            file.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
