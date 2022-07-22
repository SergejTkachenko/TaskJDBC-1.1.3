package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        // реализуйте алгоритм здесь
        Connection connection = Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("name1", "lastName1", (byte) 25);
        userDao.saveUser("name2", "lastName2", (byte) 26);
        userDao.saveUser("name3", "lastName3", (byte) 27);
        userDao.saveUser("name4", "lastName4", (byte) 28);

        connection.setAutoCommit(false);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.saveUser("Sergej", "Tkachenko", (byte) 27);
        userDao.dropUsersTable();

        Savepoint savepoint = connection.setSavepoint();

        connection.rollback();
        connection.commit();

        connection.releaseSavepoint(savepoint);
    }
}
