package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection =  Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "users", null);
            if (tables.next())  return;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = connection.createStatement()) {
             statement.execute("CREATE TABLE kata.Users (id int PRIMARY KEY AUTO_INCREMENT, " +
                    "Name varchar(20), LastName varchar(20), Age int)");
        } catch (SQLException e) {
            System.out.print("ошибка создания");
        }
    }

    public void dropUsersTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "users", null);
            if (!tables.next())  return;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE kata.Users");
        } catch (SQLException e) {
            System.out.print("ошибка удаления");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement1 = connection.prepareStatement(
                "INSERT kata.Users(Name, LastName, Age) VALUES (?, ?, ?)");) {

            statement1.setString(1, name);
            statement1.setString(2, lastName);
            statement1.setInt(3, age);
            statement1.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement1 = connection.prepareStatement(
                "DELETE FROM kata.Users WHERE id = ?");) {
            statement1.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        try (Statement statement = connection.createStatement();ResultSet result = statement.executeQuery("SELECT * FROM kata.Users")) {
               while (result.next()) {
                   list.add(new User(result.getString("Name"),
                                    result.getString("LastName"),
                                    result.getByte("Age")));
               }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM kata.Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
