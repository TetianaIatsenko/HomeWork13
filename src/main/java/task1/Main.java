package task1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static String URL_USERS = "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        URI uri = new URI(URL_USERS);

        List<User> userList = HttpUtils.sendGet(uri);
        System.out.println("List of users:");
        userList.stream().forEach(n-> System.out.println(n));
        int maxUserId = userList.stream().max(Comparator.comparing(User::getId)).get().getId();
        User newUser = createDefaultUser();
        User user = HttpUtils.sendPost(uri, newUser);
        if(user.getId() > maxUserId)
            System.out.println("New User " + newUser.getName() + " was created with id " + user.getId());

        User userById = HttpUtils.sentGetByUserId(URL_USERS, 1);
        System.out.println("User with id " + userById.getId() + ":");
        System.out.println(userById);

        userById.setUsername("BUUFFFAAA");
        User userPut = HttpUtils.sentPut(URL_USERS, userById);
        if(userById.equals(userPut))
            System.out.println("User was changed " + userPut);

        System.out.println("User by User Name Samantha:");
        HttpUtils.sentGetByUserName(URL_USERS, "Samantha").stream().forEach(n-> System.out.println(n));

        int responseCode = HttpUtils.sendDelete(URL_USERS, userById);
        if (responseCode == 200)
            System.out.println("User " + userById.getId() + " was deleted.");
    }

    public static User createDefaultUser(){
        User user = new User();
        user.setId(-1);
        user.setName("Vasya");
        user.setUsername("VasyaUserName");
        user.setAddress(new Address("street", "suite", "city", "zipcode", new GeoLocation(1.2, 1.3)));
        user.setCompany(new Company("CompanyName", "catchPhrase", "bs"));
        user.setEmail("Vasya@gmail.com");
        user.setPhone("01-25-25");
        user.setWebsite("website");
        return user;
    }
}
