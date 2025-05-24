import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USER_FILE = "data/users.txt";

    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean userExists(String username) {
        return loadUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static boolean checkCredentials(String username, String password) {
        return loadUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}

