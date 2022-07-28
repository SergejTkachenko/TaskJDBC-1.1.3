package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("name1", "lastName1", (byte) 25);
        userService.saveUser("name2", "lastName2", (byte) 26);
        userService.saveUser("name3", "lastName3", (byte) 27);
        userService.saveUser("name4", "lastName4", (byte) 28);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
