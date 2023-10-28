package task3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private static String URL_USERS = "https://jsonplaceholder.typicode.com/users";//  /1/todos";
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        printOpenTodos();
    }

    private static void printOpenTodos() throws IOException, URISyntaxException, InterruptedException {
        List<Todos> list = HttpUtils.sendGetTodosByUserId(URL_USERS, 1);
        list.stream().filter(n-> n.isCompleted() == false).forEach(n-> System.out.println(n));
    }
}
