package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;
    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    // Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "users (name, last_name, age) VALUES (?, ?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    // Удаление User из таблицы (по id)
    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return users;
    }

    // Очистка таблицы
    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
