package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.*;

public class UserServiceImpl implements UserService {

    private UserDao userDao = null;
    public UserServiceImpl() {
        this.userDao = new UserDaoJDBCImpl();
    }
    public void createUsersTable() throws SQLException {
        userDao.createUsersTable();
        System.out.println("The table has been created");
    }

    public void dropUsersTable() throws SQLException {
        userDao.dropUsersTable();
        System.out.println("The table has been dropped");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userDao.saveUser(name, lastName, age);
        System.out.println("User " + name + " added");
    }

    public void removeUserById(long id) throws SQLException {
        userDao.removeUserById(id);
        System.out.println("User id " + id + " removed");
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() throws SQLException {
        userDao.cleanUsersTable();
        System.out.println("The table has been cleared");
    }
}
