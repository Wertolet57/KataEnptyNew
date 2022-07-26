package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Sergey", "Kazmin", (byte)26);
        System.out.println("User с именем – Sergey добавлен в базу данных");
        userService.saveUser("Julia", "Pavlyuk", (byte)19);
        System.out.println("User с именем – Julia добавлен в базу данных");
        userService.saveUser("Vitaliy", "Sidorov", (byte)26);
        System.out.println("User с именем – Vitaliy добавлен в базу данных");
        userService.saveUser("Grigory", "Kazmin", (byte)20);
        System.out.println("User с именем – Grigory добавлен в базу данных");
        List<User> users =  userService.getAllUsers();
        for (var user : users) {
            System.out.println(user);

        }
        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
