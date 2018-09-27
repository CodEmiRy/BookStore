package pl.sda.intermediate11.bookstore.users.daos;

import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.users.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDAO {

   // public static final String PATHNAME = "C:/projects/usersData";

    private List<User> users = new ArrayList<>();
//    {
//        try(FileInputStream fileInputStream = new FileInputStream(PATHNAME);
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
//            users = (List<User>) objectInputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public Optional<User> findUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public void addUser(User user) {
        users.add(user);
    //    try (FileOutputStream fileOutputStream = new FileOutputStream(new File(PATHNAME));
//             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
//            objectOutputStream.writeObject(users);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}