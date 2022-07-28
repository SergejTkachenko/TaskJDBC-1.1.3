package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.imageio.spi.ServiceRegistry;
import java.net.ServerSocket;
import java.util.*;
import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.net.URISyntaxException;

public class Util {
    private static Connection connection;
    private static SessionFactory currentSessionFactory;

    public static SessionFactory getCurrentSessionFactory() {
        if (currentSessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.setProperty("hibernate.connection.url",
                        "jdbc:mysql://localhost/users?serverTimezone=Europe/Moscow&useSSL=false");
                properties.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "12345qwert");
                properties.setProperty("current_session_context_class", "threat");
                properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
                properties.setProperty("show_sql", "true");
                properties.setProperty("hibernate.hbm2ddl.auto", "update");

                currentSessionFactory = new Configuration().setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
                System.out.println("Session has been created");

            } catch (Exception e) {
                System.out.println("There is a problem with creating session");
                e.printStackTrace();
            }
        }
        return currentSessionFactory;
    }

    private Util() {
        try {
            if (connection == null || connection.isClosed())
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = getConnection()) {
                System.out.println("Connection successful");
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e);
        }
    }
    public static Connection getConnection() throws SQLException, IOException {

        Properties properties = new Properties();

        try (InputStream input = Files.newInputStream
                (Paths.get(Objects.requireNonNull(Util.class.getResource("/database.properties")).toURI()))) {
            properties.load(input);
        } catch (URISyntaxException | IOException e) {
            throw new IOException("Configuration file is not found, check resources");
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
